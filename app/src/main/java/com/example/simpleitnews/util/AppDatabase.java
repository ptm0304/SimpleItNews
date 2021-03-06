package com.example.simpleitnews.util;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.simpleitnews.model.dao.NewsDao;
import com.example.simpleitnews.model.dto.NewsDto;

import javax.inject.Singleton;

@Database(entities = {NewsDto.class}, version=1)
@TypeConverters({RoomTypeConverter.class}) // type converter를 사용하려면 포함
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
