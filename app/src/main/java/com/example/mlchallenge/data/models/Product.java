package com.example.mlchallenge.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mlchallenge.data.database.converters.PictureTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

@Entity(primaryKeys = ("id"))
public class Product implements Parcelable {

    @SerializedName("id")
    @Expose
    @NonNull
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("price")
    @Expose
    private Float price;

    @SerializedName("condition")
    @Expose
    private String condition;

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @SerializedName("pictures")
    @Expose
    @TypeConverters(PictureTypeConverter.class)
    private List<Picture> pictures = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.title);
        dest.writeValue(this.price);
        dest.writeValue(this.condition);
        dest.writeValue(this.thumbnail);
        dest.writeList(pictures);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.price = in.readFloat();
        this.condition = in.readString();
        this.thumbnail = in.readString();
        in.readList(this.pictures, (Picture.class.getClassLoader()));
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
