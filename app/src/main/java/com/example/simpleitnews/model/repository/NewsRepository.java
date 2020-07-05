package com.example.simpleitnews.model.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.simpleitnews.model.dto.NewsDto;
import com.example.simpleitnews.util.AppDatabase;
import com.example.simpleitnews.util.NewsService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class NewsRepository {
    private AppDatabase mDb;
    private NewsService mNewsService;
    private Disposable mLoadNewsDisposable, mInsertBookmarkDisposable, mDeleteBookmarkDisposable, mLoadBookmarksDisposable;
    private HashSet<String> mBookmarkLinkSet;
    private MutableLiveData<List<NewsDto>> mNewsList;
    private MutableLiveData<HashSet> mBookmarkLiveData;
    private static int mPage = 1;
    private static final int mPageSize = 100;

    @Inject
    public NewsRepository(AppDatabase db, NewsService newsService) {
        this.mDb = db;
        this.mNewsService = newsService;
        this.mBookmarkLinkSet = new HashSet<>();
        this.mBookmarkLiveData = new MutableLiveData<>();
        this.mBookmarkLiveData.setValue(mBookmarkLinkSet);
        this.mNewsList = new MutableLiveData<>(new ArrayList<>());

        initBookmarks();
    }

    private void initBookmarks() {
        // 앱 시작 초기에 이미 있는 북마크만 확인한 후 옵저버 제거
        LiveData<List<NewsDto>> initialBookmarks = loadAllBookmarks();
        initialBookmarks.observeForever(new Observer<List<NewsDto>>() {
            @Override
            public void onChanged(List<NewsDto> newsDtos) {
                for (NewsDto news: newsDtos) {
                    mBookmarkLinkSet.add(news.getLink());
                }
                mBookmarkLiveData.postValue(mBookmarkLinkSet);
                initialBookmarks.removeObserver(this);
            }
        });
    }

    public MutableLiveData<List<NewsDto>> getNewsList() {
        return mNewsList;
    }

    public LiveData<HashSet> getBookmarkLiveData() {
        return mBookmarkLiveData;
    }

    public void insertBookmark(NewsDto news) {
        mBookmarkLinkSet.add(news.getLink());
        mBookmarkLiveData.setValue(mBookmarkLinkSet);
        new BookmarksInsertAsyncTask(news, mDb).execute();
    }

    public void deleteBookmark(NewsDto news) {
        mBookmarkLinkSet.remove(news.getLink());
        mBookmarkLiveData.setValue(mBookmarkLinkSet);
        new BookmarksDeleteAsyncTask(news, mDb).execute();
    }

    public void loadMoreNews(String query) {
        mLoadNewsDisposable = mNewsService.listNews(query, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> {
                    List<NewsDto> list = mNewsList.getValue();
                    list.addAll(items.getNewsDtoList());
                    mNewsList.setValue(list);
                });
        mPage += mPageSize;
    }

    public void disposeDisposables() {
        mLoadNewsDisposable.dispose();
        mDeleteBookmarkDisposable.dispose();
        mInsertBookmarkDisposable.dispose();
        mLoadBookmarksDisposable.dispose();
    }

//    public LiveData<List<NewsDto>> loadAllBookmarks() {
//        return mDb.newsDao().loadAllNews();
//    }

    public LiveData<List<NewsDto>> loadAllBookmarks() {
        try {
            return new BookmarksLoadAllAsyncTask(mDb).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class BookmarksInsertAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsDto mNews;
        private AppDatabase mDb;
        public BookmarksInsertAsyncTask(NewsDto news, AppDatabase db) {
            mNews = news;
            mDb = db;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mDb.newsDao().insert(mNews);
            return null;
        }
    }

    private static class BookmarksDeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsDto mNews;
        private AppDatabase mDb;
        public BookmarksDeleteAsyncTask(NewsDto news, AppDatabase db) {
            mNews = news;
            mDb = db;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mDb.newsDao().delete(mNews);
            return null;
        }
    }

    private static class BookmarksLoadAllAsyncTask extends AsyncTask<Void, Void, LiveData<List<NewsDto>>> {
        private AppDatabase mDb;
        public BookmarksLoadAllAsyncTask(AppDatabase db) {
            mDb = db;
        }
        @Override
        protected LiveData<List<NewsDto>> doInBackground(Void... voids) {
            LiveData<List<NewsDto>> newsList = mDb.newsDao().loadAllNews();
            return newsList;
        }
    }
}
