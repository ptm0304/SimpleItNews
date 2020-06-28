package com.example.simpleitnews.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpleitnews.dagger.scope.PerFragment;
import com.example.simpleitnews.model.dto.NewsDto;
import com.example.simpleitnews.model.repository.NewsRepository;

import java.util.HashSet;
import java.util.List;

@PerFragment
public class NewsViewModel extends ViewModel {
    private LiveData<List<NewsDto>> newsList;
    private NewsRepository mRepository;

    public NewsViewModel(NewsRepository repository) {
        mRepository = repository;
        newsList = mRepository.getNewsList();
        mRepository.updateNewsList("테크 | 기술 | 아이티 | IT | ICT | iot | 사물인터넷 | 4차산업혁명 | AI | 인공지능 | 빅데이터 | 자율주행");
    }

    public LiveData<List<NewsDto>> getNewsList() {
        return newsList;
    }

    public String toString() {
        return newsList.getValue().toString();
    }

    public void insertBookmark(NewsDto newsDto) {
        mRepository.insertBookmark(newsDto);
    }

    public void deleteBookmark(NewsDto newsDto) {
        mRepository.deleteBookmark(newsDto);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository.disposeDisposable();
    }

    public boolean isBookmark(String link) {
        return mRepository.getBookmarkLiveData().getValue().contains(link);
    }

    public LiveData<HashSet> getBookmarkLiveData() { return mRepository.getBookmarkLiveData(); }

}
