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
    AppDatabase db;
    NewsService mNewsService;
    private Disposable mDisposable;
    private HashSet<String> mBookmarkLinkSet;
    private MutableLiveData<List<NewsDto>> mNewsList;
    private MutableLiveData<HashSet> mBookmarkLiveData;

    @Inject
    public NewsRepository(AppDatabase db, NewsService newsService) {
        this.db = db;
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
        new BookmarksInsertAsyncTask(news).execute();
    }

    public void deleteBookmark(NewsDto news) {
        mBookmarkLinkSet.remove(news.getLink());
        mBookmarkLiveData.setValue(mBookmarkLinkSet);
        new BookmarksDeleteAsyncTask(news).execute();
    }

    public void updateNewsList(String query) {
        mDisposable = mNewsService.listNews(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> {
                    List<NewsDto> list = mNewsList.getValue();
                    list.addAll(items.getNewsDtoList());
                    mNewsList.setValue(list);
                });
    }

    public void disposeDisposable() {
        mDisposable.dispose();
    }

//    public void updateNews(NewsDto news) {
//        bookmarkLinkSet.add(news.getLink());
//        bookmarkLiveData.setValue(bookmarkLinkSet);
//        new NewsUpdateAsyncTask(news).execute();
//    }

    public LiveData<List<NewsDto>> loadAllBookmarks() {
        try {
            return new BookmarksLoadAllAsyncTask().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public LiveData<List<NewsDto>> loadAllBookmarks() {
//        try {
//            return new BookmarkLoadAllAsyncTask().execute().get();
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private class BookmarksInsertAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsDto mNews;
        public BookmarksInsertAsyncTask(NewsDto news) {
            mNews = news;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            db.newsDao().insert(mNews);
            return null;
        }
    }

    private class BookmarksDeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsDto mNews;
        public BookmarksDeleteAsyncTask(NewsDto news) {
            mNews = news;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            db.newsDao().delete(mNews);
            return null;
        }
    }

    private class BookmarksLoadAllAsyncTask extends AsyncTask<Void, Void, LiveData<List<NewsDto>>> {
        @Override
        protected LiveData<List<NewsDto>> doInBackground(Void... voids) {
            LiveData<List<NewsDto>> newsList = db.newsDao().loadAllNews();
            return newsList;
        }
    }

//    private class NewsUpdateAsyncTask extends AsyncTask<Void, Void, Void> {
//        private NewsDto mNews;
//        public NewsUpdateAsyncTask(NewsDto news) {
//            mNews = news;
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            db.newsDao().update(mNews);
//            return null;
//        }
//    }

//    private class BookmarkLoadAllAsyncTask extends AsyncTask<Void, Void, LiveData<List<NewsDto>>> {
//        @Override
//        protected LiveData<List<NewsDto>> doInBackground(Void... voids) {
//            LiveData<List<NewsDto>> newsList = db.newsDao().loadBookmarks();
//            return newsList;
//        }
//    }
}
