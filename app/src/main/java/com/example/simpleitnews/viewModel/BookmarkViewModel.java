package com.example.simpleitnews.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.simpleitnews.dagger.scope.PerFragment;
import com.example.simpleitnews.model.dto.NewsDto;
import com.example.simpleitnews.model.repository.NewsRepository;
import com.example.simpleitnews.util.ArticleNavigator;

import java.util.List;

import javax.inject.Inject;

@PerFragment
public class BookmarkViewModel extends ViewModel {
    private LiveData<List<NewsDto>> mBookmarkList;
    private NewsRepository mRepository;

    @Inject
    public BookmarkViewModel(NewsRepository repository) {
        mRepository = repository;
        mBookmarkList = mRepository.loadAllBookmarks();
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
}
