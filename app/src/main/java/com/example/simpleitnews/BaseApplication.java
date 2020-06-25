package com.example.simpleitnews;

import android.app.Application;

import com.example.simpleitnews.dagger.AppComponent;
import com.example.simpleitnews.dagger.DaggerAppComponent;
import com.example.simpleitnews.dagger.module.DbModule;

public class BaseApplication extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .dbModule(new DbModule(getApplicationContext()))
                .build();
    }

    public AppComponent getAppComponent() {
        return component;
    }
}
