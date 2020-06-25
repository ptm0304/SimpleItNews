package com.example.simpleitnews.dagger.module;

import com.example.simpleitnews.util.NewsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private final static String BASE_URL = "https://openapi.naver.com/v1/";

    @Singleton
    @Provides
    public static NewsService getNewsService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("E, dd MMMM yyyy HH:mm:ss X")
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(NewsService.class);
    }
}
