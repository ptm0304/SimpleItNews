package com.example.simpleitnews;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.simpleitnews.model.dao.NewsDao;
import com.example.simpleitnews.model.dto.NewsDto;
import com.example.simpleitnews.util.AppDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class NewsViewModelTest {
    private AppDatabase db;
    private NewsDao dao;
    private Context context;

    @Before
    public void createDb() {
        context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(
                context, AppDatabase.class).build();
        dao = db.newsDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        LiveData<List<NewsDto>> newsList = dao.loadAllNews();
//        newsList.observeForever(new Observer<List<NewsDto>>() {
//            @Override
//            public void onChanged(List<NewsDto> newsDtos) {
//                System.out.println(newsList.getValue());
////                newsList.removeObserver(this);
//            }
//        });
//        NewsDto testNews = new NewsDto("test title", "test link", "test description",
//                "test pubdate");
//        dao.insert(testNews);
    }

}
