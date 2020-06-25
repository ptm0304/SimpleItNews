package com.example.simpleitnews.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.simpleitnews.model.dto.NewsDto;
import com.example.simpleitnews.model.repository.NewsRepository;
import com.example.simpleitnews.util.ArticleNavigator;

import java.util.List;

public class BookmarkViewModel extends ViewModel {
    private LiveData<List<NewsDto>> mBookmarkList;
    private NewsRepository mRepository;
    private ArticleNavigator mNav;

    public BookmarkViewModel(NewsRepository repository, ArticleNavigator nav) {
        mRepository = repository;
        mBookmarkList = mRepository.loadAllBookmarks();
        mNav = nav;
    }

    public LiveData<List<NewsDto>> getBookmarkList() {
        return mBookmarkList;
    }

    public String toString() {
        return mBookmarkList.getValue().toString();
    }

    public void updateNews(NewsDto news) {
        mRepository.updateNews(news);
    }

    public static class BookmarkViewModelFactory implements ViewModelProvider.Factory {
        private NewsRepository mRepo;
        private ArticleNavigator mNav;

        public BookmarkViewModelFactory(NewsRepository repo, ArticleNavigator nav) {
            mRepo = repo;
            mNav = nav;
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new BookmarkViewModel(mRepo, mNav);
        }
    }

    public void navigateArticle(NewsDto news) {
        mNav.navigateArticle(news);
    }
}
