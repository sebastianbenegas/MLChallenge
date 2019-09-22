package com.example.mlchallenge.ui;


import android.content.DialogInterface;
import android.os.Bundle;

import com.example.mlchallenge.R;
import com.example.mlchallenge.ui.search.SearchFragment;
import com.example.mlchallenge.ui.search.products.ProductListAdapter;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector, ProductListAdapter.IOnClick {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    SearchFragment searchFragment;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchFragment = new SearchFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, searchFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void goToProductDetail(String id) {
        searchFragment.goToDetailFragment(id);
    }

    @Override
    public void onBackPressed() {
        if (searchFragment != null && searchFragment.getChildFragmentManager().getBackStackEntryCount() > 1) {
            searchFragment.getChildFragmentManager().popBackStack();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(R.string.confirm_exit);
            dialog.setPositiveButton(getText(R.string.accept), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.super.onBackPressed();
                }
            });
            dialog.setNegativeButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}