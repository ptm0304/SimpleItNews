package com.example.simpleitnews.util;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.simpleitnews.model.dao.NewsDao;
import com.example.simpleitnews.model.dto.NewsDto;

@Database(entities = {NewsDto.class}, version=1)
@TypeConverters({RoomTypeConverter.class}) // type converter를 사용하려면 포함
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
    private static AppDatabase mAppDatabase;

    // 싱글튼 패턴을 유지해야 데이터의 일치성을 보장할 수 있다
    public static AppDatabase getInstance(Context context) {
        if(mAppDatabase==null){
            mAppDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "it-news-db").build();
        }
        return mAppDatabase;
    }
}
