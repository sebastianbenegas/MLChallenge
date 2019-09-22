package com.example.mlchallenge.di.module;

import com.example.mlchallenge.ui.search.SearchFragment;
import com.example.mlchallenge.ui.search.details.ProductDetailFragment;
import com.example.mlchallenge.ui.search.products.ProductListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract SearchFragment contributeSearchFragment();

    @ContributesAndroidInjector
    abstract ProductListFragment productListFragment();

    @ContributesAndroidInjector
    abstract ProductDetailFragment productDetailFragment();
}
