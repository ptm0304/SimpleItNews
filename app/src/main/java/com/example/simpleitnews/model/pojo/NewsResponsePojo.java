package com.example.simpleitnews.model.pojo;

import com.example.simpleitnews.model.dto.NewsDto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponsePojo {
    @SerializedName("items")
    @Expose
    private List<NewsDto> newsDtoList;

    public List<NewsDto> getNewsDtoList() {
        return newsDtoList;
    }
}
