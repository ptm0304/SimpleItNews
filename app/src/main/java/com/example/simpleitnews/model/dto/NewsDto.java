package com.example.simpleitnews.model.dto;

import android.text.Html;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "news")
public class NewsDto {
    @NonNull
    @PrimaryKey
    private String link;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "pubDate")
    private Date pubDate;
    @ColumnInfo(name = "isBookmark", defaultValue = "1")
    private boolean isBookmark;

    public NewsDto() {
    }

    @Ignore
    public NewsDto(String title, String link, String description, Date pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getTitle() { return title; }

    public CharSequence getTitleHtml() {
        return Html.fromHtml(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public CharSequence getDescriptionHtml() {
        return Html.fromHtml(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public String getPubDateStr() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy년 M월 d일 h:mm a", Locale.KOREA);
        return dateFormat.format(pubDate);
    }
    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public boolean isBookmark() {
        return isBookmark;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }

    @Override
    public String toString() {
        return "NewsDto{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", isBookmark=" + isBookmark +
                '}';
    }
}
