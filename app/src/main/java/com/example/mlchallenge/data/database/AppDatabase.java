package com.example.mlchallenge.data.database;

import com.example.mlchallenge.data.database.converters.PictureTypeConverter;
import com.example.mlchallenge.data.models.Product;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Product.class}, version = 2, exportSchema = false)
@TypeConverters({PictureTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
