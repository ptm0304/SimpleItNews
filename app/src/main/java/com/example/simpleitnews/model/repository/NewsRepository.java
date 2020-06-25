package com.example.simpleitnews.model.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.simpleitnews.model.dto.NewsDto;
import com.example.simpleitnews.util.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

public class NewsRepository {
    @Inject
    AppDatabase db;

    public void insertAllNews(List<NewsDto> newsList) {
        new NewsInsertAllAsyncTask(newsList).execute();
    }

    public void updateNews(NewsDto news) {
        new NewsUpdateAsyncTask(news).execute();
    }

    public LiveData<List<NewsDto>> loadAllNews() {
        try {
            return new NewsLoadAllAsyncTask().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<NewsDto>> loadAllBookmarks() {
        try {
            return new BookmarkLoadAllAsyncTask().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class NewsInsertAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private List<NewsDto> mNews;
        public NewsInsertAllAsyncTask(List<NewsDto> news) {
            mNews = news;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("News Insert All Result: " + db.newsDao().insertAll(mNews));
            return null;
        }
    }

    private class NewsLoadAllAsyncTask extends AsyncTask<Void, Void, LiveData<List<NewsDto>>> {
        @Override
        protected LiveData<List<NewsDto>> doInBackground(Void... voids) {
            LiveData<List<NewsDto>> newsList = db.newsDao().loadAllNews();
            return newsList;
        }
    }

    private class NewsUpdateAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsDto mNews;
        public NewsUpdateAsyncTask(NewsDto news) {
            mNews = news;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            db.newsDao().update(mNews);
            return null;
        }
    }

    private class BookmarkLoadAllAsyncTask extends AsyncTask<Void, Void, LiveData<List<NewsDto>>> {
        @Override
        protected LiveData<List<NewsDto>> doInBackground(Void... voids) {
            LiveData<List<NewsDto>> newsList = db.newsDao().loadBookmarks();
            return newsList;
        }
    }
}
