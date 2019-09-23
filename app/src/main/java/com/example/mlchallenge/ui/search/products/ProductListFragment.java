package com.example.mlchallenge.ui.search.products;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mlchallenge.R;
import com.example.mlchallenge.di.ViewModelFactory;
import com.example.mlchallenge.ui.search.SearchFragment;
import com.example.mlchallenge.ui.search.SearchViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class ProductListFragment extends Fragment {

    public ProductListAdapter productListAdapter;
    @Inject
    ViewModelFactory viewModelFactory;
    SearchViewModel searchViewModel;

    @BindView(R.id.initial_text) TextView initialText;
    @BindView(R.id.tv_last_search) TextView tvLastSearch;
    @BindView(R.id.btn_show_more_results) FloatingActionButton btnShowMore;
    @BindView(R.id.rv_product_list) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;SearchFragment searchFragment;

    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, view);
        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        setupButton();
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public void setupButton() {
        btnShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFragment = (SearchFragment) getParentFragment();
                searchFragment.searchMoreProducts();
            }
        });
    }

    private void setupRecyclerView() {
        initialText.setVisibility(View.VISIBLE);

        productListAdapter = new ProductListAdapter(getContext());
        recyclerView.setAdapter(productListAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (!searchViewModel.getProducts().isEmpty()) {
            initialText.setVisibility(View.GONE);
            tvLastSearch.setVisibility(View.VISIBLE);
            productListAdapter.productList = searchViewModel.getProducts();
            productListAdapter.notifyDataSetChanged();
        }
    }

    public void emptyListState() {
        initialText.setVisibility(View.VISIBLE);
    }


    // Metodo para manejar la posicion de la lista cuando se busca un producto nuevo o cuando se agregan mas a la lista actual

    @SuppressLint("RestrictedApi")
    public void returnToTopPositionOnList(Integer offset) {
        initialText.setVisibility(View.GONE);
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        if (offset == 0) {
            tvLastSearch.setVisibility(View.GONE);
            btnShowMore.setVisibility(View.VISIBLE);
            smoothScroller.setTargetPosition(0);
            linearLayoutManager.startSmoothScroll(smoothScroller);
        } else {
            smoothScroller.setTargetPosition(productListAdapter.productList.size() - 10);
            linearLayoutManager.startSmoothScroll(smoothScroller);
        }
    }
}
