package com.example.ik.rssnewscollector;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

public class NewsItem implements Parcelable {
    static String LOG_TAG = "NewsItem";

    private String title;
    private String image;
    private String link;
    private String source;
    private String description;
    private String content;
    private String date;


    private ArrayList<String> textImages;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getTextImages() {
        return textImages;
    }

    public void setTextImages(ArrayList<String> textImages) {
        this.textImages = textImages;
    }

    public void addTextImages(String textImage) {
        this.textImages.add(textImage);
        Log.i(LOG_TAG, textImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // упаковываем объект в Parcel
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeString(link);
        parcel.writeString(source);
        parcel.writeString(description);
        parcel.writeString(content);
        parcel.writeString(date);
    }

    public static final Parcelable.Creator<NewsItem> CREATOR = new Parcelable.Creator<NewsItem>() {
        // распаковываем объект из Parcel
        public NewsItem createFromParcel(Parcel in) {
            Log.d(LOG_TAG, "распаковываем объект из Parcel");
            return new NewsItem(in);
        }

        public NewsItem[] newArray(int size) {
            Log.d(LOG_TAG, "распаковываем newArray из Parcel");
            return new NewsItem[size];
        }
    };

    // конструктор
    public NewsItem() {}

    // конструктор, считывающий данные из Parcel
    private NewsItem(Parcel parcel) {
        Log.d(LOG_TAG, "конструктор, считывающий данные из Parcel");
        title = parcel.readString() == null? null : parcel.readString();
        image = parcel.readString() == null? null : parcel.readString();
        link = parcel.readString() == null? null : parcel.readString();
        source = parcel.readString() == null? null : parcel.readString();
        description = parcel.readString() == null? null : parcel.readString();
        content = parcel.readString() == null? null : parcel.readString();
        date = parcel.readString() == null? null : parcel.readString();
    }

}
