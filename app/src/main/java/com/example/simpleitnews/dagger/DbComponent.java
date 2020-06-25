package com.example.simpleitnews.dagger;

import com.example.simpleitnews.model.repository.NewsRepository;
import com.example.simpleitnews.util.AppDatabase;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules={DbModule.class})
@Singleton
public interface DbComponent {
    AppDatabase makeDatabase();

    void inject(NewsRepository newsRepository);

    @Component.Builder
    interface Builder{
        DbComponent build();
        Builder DbModule(DbModule module);
    }
}