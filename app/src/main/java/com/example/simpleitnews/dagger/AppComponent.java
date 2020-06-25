package com.example.simpleitnews.dagger;

import com.example.simpleitnews.dagger.module.ApiModule;
import com.example.simpleitnews.dagger.module.DbModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={DbModule.class, ApiModule.class})
public interface AppComponent {
//    NewsRepositoryComponent getNewsRepositoryComponent();
//    NewsViewModelComponent getNewsViewModelComponent();
    NewsFragmentComponent getNewsFragmentComponent();

    @Component.Builder
    interface Builder{
        AppComponent build();
        AppComponent.Builder dbModule(DbModule module);
    }
}
