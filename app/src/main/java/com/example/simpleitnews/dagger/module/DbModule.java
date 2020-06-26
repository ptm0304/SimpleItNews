package com.example.simpleitnews.dagger.module;

import android.content.Context;

import androidx.room.Room;

import com.example.simpleitnews.util.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {
    private final Context context;

    public DbModule (Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public AppDatabase provideDb() {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                "it-news-db").build();
    }
}
