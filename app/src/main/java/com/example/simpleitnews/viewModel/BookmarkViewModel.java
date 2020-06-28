package com.example.simpleitnews.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpleitnews.dagger.scope.PerFragment;
import com.example.simpleitnews.model.dto.NewsDto;
import com.example.simpleitnews.model.repository.NewsRepository;

import java.util.List;

@PerFragment
public class BookmarkViewModel extends ViewModel {
    private LiveData<List<NewsDto>> mBookmarkList;
    private NewsRepository mRepository;

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

    public void deleteBookmark(NewsDto news) { mRepository.deleteBookmark(news); }

    public boolean isBookmark(String link) { return mRepository.getBookmarkLiveData().getValue().contains(link) ;}
}
