package com.example.simpleitnews.dagger;

import android.content.Context;

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
    @Provides //scope is not necessary for parameters stored within the module
    public AppDatabase provideDb() {
        return AppDatabase.getInstance(context);
    }
}
