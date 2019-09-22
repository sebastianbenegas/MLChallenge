package com.example.mlchallenge.data.database.converters;

import com.example.mlchallenge.data.models.Picture;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class PictureTypeConverter {

    @TypeConverter
    public List<Picture> fromString(String value) {
        Type listType = new TypeToken<List<Picture>>() {
        }.getType();
        List<Picture> pictures = new Gson().fromJson(value, listType);
        return pictures;
    }

    @TypeConverter
    public String fromList(List<Picture> pictures) {
        return new Gson().toJson(pictures);
    }
}
