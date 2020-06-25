package com.example.simpleitnews.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.simpleitnews.model.dto.NewsDto;
import com.example.simpleitnews.model.repository.NewsRepository;
import com.example.simpleitnews.util.ApiUtil;
import com.example.simpleitnews.util.ArticleNavigator;
import com.example.simpleitnews.util.NewsService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewsViewModel extends ViewModel {
    private LiveData<List<NewsDto>> newsList;
    private NewsRepository mRepository;
    private ArticleNavigator mNav;
    private Disposable mDisposable;
    @Inject
    public NewsService newsService;

    public NewsViewModel(NewsRepository repository, ArticleNavigator nav) {
        mRepository = repository;
        newsList = mRepository.loadAllNews();
        mNav = nav;
        updateNewsList("테크 | 기술 | 아이티 | IT | ICT | iot | 사물인터넷 | 4차산업혁명 | AI | 인공지능 | 빅데이터 | 자율주행");
    }

    public LiveData<List<NewsDto>> getNewsList() {
        return newsList;
    }

    public String toString() {
        return newsList.getValue().toString();
    }

    public void updateNewsList(String query) {
        mDisposable = ApiUtil.getNewsService().listNews(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> mRepository.insertAllNews(items.getNewsDtoList()));
    }

    public void updateNews(NewsDto news) {
        mRepository.updateNews(news);
    }

    public static class NewsViewModelFactory implements ViewModelProvider.Factory {
        private NewsRepository mRepo;
        private ArticleNavigator mNav;

        public NewsViewModelFactory(NewsRepository repo, ArticleNavigator nav) {
            mRepo = repo;
            mNav = nav;
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new NewsViewModel(mRepo, mNav);
        }
    }

    public void navigateArticle(NewsDto news) {
        mNav.navigateArticle(news);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.dispose();
    }
}
