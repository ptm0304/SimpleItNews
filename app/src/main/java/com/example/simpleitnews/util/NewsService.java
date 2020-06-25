package com.example.simpleitnews.util;

import com.example.simpleitnews.model.pojo.NewsResponsePojo;
import com.example.simpleitnews.secret.NaverApiValues;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NewsService {
    @Headers(
            {"X-Naver-Client-Id: " + NaverApiValues.NAVER_CLIENT_ID,
                    "X-Naver-Client-Secret: " + NaverApiValues.NAVER_CLIENT_SECRET}
    )
    @GET("search/news.json?display=100&sort=sim")
    Observable<NewsResponsePojo> listNews(@Query("query") String query);

}