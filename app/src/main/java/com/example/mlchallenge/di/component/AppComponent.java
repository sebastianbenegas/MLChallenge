package com.example.mlchallenge.di.component;

import android.app.Application;

import com.example.mlchallenge.App;
import com.example.mlchallenge.di.module.ActivityModule;
import com.example.mlchallenge.di.module.DataModule;
import com.example.mlchallenge.di.module.FragmentModule;
import com.example.mlchallenge.di.module.NetworkModule;
import com.example.mlchallenge.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        NetworkModule.class,
        DataModule.class,
        ViewModelModule.class,
        ActivityModule.class,
        FragmentModule.class,
        AndroidSupportInjectionModule.class})
@Singleton
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(App app);
}
