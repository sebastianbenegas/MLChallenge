package com.example.mlchallenge.di.module;

import android.app.Application;

import com.example.mlchallenge.data.database.AppDatabase;
import com.example.mlchallenge.data.database.ProductDao;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase(@NonNull Application application) {
        return Room.databaseBuilder(application,
                AppDatabase.class, "Entertainment.db")
                .allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    ProductDao provideProductDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.productDao();
    }
}