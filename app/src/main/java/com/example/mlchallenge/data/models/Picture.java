package com.example.mlchallenge.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Picture implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("secure_url")
    @Expose
    private String secureUrl;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("max_size")
    @Expose
    private String maxSize;
    @SerializedName("quality")
    @Expose
    private String quality;
    public final static Parcelable.Creator<Picture> CREATOR = new Creator<Picture>() {

        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        public Picture[] newArray(int size) {
            return (new Picture[size]);
        }
    };

    protected Picture(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.secureUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.size = ((String) in.readValue((String.class.getClassLoader())));
        this.maxSize = ((String) in.readValue((String.class.getClassLoader())));
        this.quality = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Picture() { }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSecureUrl() {
        return secureUrl;
    }

    public void setSecureUrl(String secureUrl) {
        this.secureUrl = secureUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(url);
        dest.writeValue(secureUrl);
        dest.writeValue(size);
        dest.writeValue(maxSize);
        dest.writeValue(quality);
    }

    public int describeContents() {
        return 0;
    }

}