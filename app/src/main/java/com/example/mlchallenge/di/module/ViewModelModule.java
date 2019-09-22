package com.example.mlchallenge.di.module;

import com.example.mlchallenge.di.ViewModelFactory;
import com.example.mlchallenge.di.ViewModelKey;
import com.example.mlchallenge.ui.search.SearchViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    protected abstract ViewModel searchViewModel(SearchViewModel searchViewModel);

}
