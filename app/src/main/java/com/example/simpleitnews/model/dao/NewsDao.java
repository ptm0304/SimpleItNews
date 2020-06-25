package com.example.simpleitnews.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.simpleitnews.model.dto.NewsDto;

import java.util.List;

// Room dao to access all news saved in bookmark
@Dao
public interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NewsDto news);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertAll(List<NewsDto> news);

    @Delete
    void delete(NewsDto news);

    @Update
    void update(NewsDto news);

    @Query("SELECT * FROM NEWS ORDER BY pubDate DESC")
    LiveData<List<NewsDto>> loadAllNews();

    @Query("SELECT COUNT(*) FROM NEWS WHERE LINK = :link")
    LiveData<Integer> getNews(String link);

    @Query("SELECT * FROM NEWS WHERE isBookmark = 1 ORDER BY pubDate DESC")
    LiveData<List<NewsDto>> loadBookmarks();
}
