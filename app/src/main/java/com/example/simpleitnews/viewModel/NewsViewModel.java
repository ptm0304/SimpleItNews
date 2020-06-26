package com.example.simpleitnews.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.simpleitnews.dagger.scope.PerFragment;
import com.example.simpleitnews.model.dto.NewsDto;
import com.example.simpleitnews.model.repository.NewsRepository;
import com.example.simpleitnews.util.ArticleNavigator;
import com.example.simpleitnews.util.NewsService;

import java.util.List;

import javax.inject.Inject;

@PerFragment
public class NewsViewModel extends ViewModel {
    private LiveData<List<NewsDto>> newsList;
    private NewsRepository mRepository;
    @Inject
    public NewsService newsService;

    @Inject
    public NewsViewModel(NewsRepository repository) {
        mRepository = repository;
        newsList = mRepository.loadAllNews();
        mRepository.updateNewsList("테크 | 기술 | 아이티 | IT | ICT | iot | 사물인터넷 | 4차산업혁명 | AI | 인공지능 | 빅데이터 | 자율주행");
    }

    public LiveData<List<NewsDto>> getNewsList() {
        return newsList;
    }

    public String toString() {
        return newsList.getValue().toString();
    }

    public void updateNews(NewsDto news) {
        mRepository.updateNews(news);
    }

//    @PerFragment
//    public static class NewsViewModelFactory implements ViewModelProvider.Factory {
//        private NewsRepository mRepo;
//        private ArticleNavigator mNav;
//
//        @Inject
//        public NewsViewModelFactory(NewsRepository repo, ArticleNavigator nav) {
//            mRepo = repo;
//            mNav = nav;
//        }
//        @NonNull
//        @Override
//        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//            return (T) new NewsViewModel(mRepo, mNav);
//        }
//    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository.disposeDisposable();
    }
}
