package com.example.mlchallenge.ui.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mlchallenge.R;
import com.example.mlchallenge.di.ViewModelFactory;
import com.example.mlchallenge.ui.search.details.ProductDetailFragment;
import com.example.mlchallenge.ui.search.products.ProductListAdapter;
import com.example.mlchallenge.ui.search.products.ProductListFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class SearchFragment extends Fragment {

    private static final String TAG_PRODUCT_LIST = "productList";
    private static final String TAG_PRODUCT_DETAIL = "productDetail";

    @Inject
    ViewModelFactory viewModelFactory;
    SearchViewModel searchViewModel;

    @BindView(R.id.toolbar) Toolbar toolbar;

    private MenuItem searchMenu = null;
    private SearchView searchView = null;
    private ProductListAdapter productListAdapter;
    private ProductListFragment productListFragment;
    private ProductDetailFragment productDetailFragment;
    private Integer offset = 0;
    private Integer limit = 10;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
        setupSearchObservers();
        getProductListFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        searchMenu = null;
    }

    // Seteo del toolbar con el buscador

    private void setupToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
        searchMenu = menu.findItem(R.id.toolbar_search);
        searchView = (SearchView) searchMenu.getActionView();
        setHasOptionsMenu(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                offset = 0;
                searchViewModel.searchProducts(query, offset, limit);
                if (productDetailFragment != null && productDetailFragment.isVisible()) {
                    getChildFragmentManager().popBackStack();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // Observer del viewModel
    private void setupSearchObservers() {

        searchViewModel.searchResult.observe(this, resource -> {

            if (resource.isLoading()) {
                showProgressBar();
            } else if (resource.isSuccess() && resource.data.isEmpty()) {
                hideProgressBar();
                productListFragment.emptyListState();
            } else if (resource.isSuccess() && !resource.data.isEmpty()) {
                hideProgressBar();
                if (offset == 0) {
                    productListFragment.productListAdapter.productList.clear();
                }
                productListFragment.productListAdapter.productList = resource.data;
                productListFragment.productListAdapter.notifyDataSetChanged();
                productListFragment.returnToTopPositionOnList(offset);
            } else if (resource.isError()) {
                hideProgressBar();
                handleErrorResponse();
            }
        });
    }

    // Metodo para buscar mas resultado de una busqueda ya realizada

    public void searchMoreProducts() {
        offset += 10;
        searchViewModel.searchProducts(searchView.getQuery().toString(), offset, limit);
    }

    private void handleErrorResponse() {
        //Handle error view
        Toast.makeText(getContext(), R.string.errorToast, Toast.LENGTH_SHORT).show();
    }

    private void hideProgressBar() {
        productListFragment.hideProgressBar();
    }

    private void showProgressBar() {
        productListFragment.showProgressBar();
    }

    //Metodo para cambiar fragments

    private void fragmentChange(Fragment mFragment, String tag) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFragmentManager.beginTransaction();
        if (tag.equals(TAG_PRODUCT_DETAIL)) {
            childFragTrans.setCustomAnimations(R.anim.bottom_to_top, R.anim.top_to_bottom);
        }
        childFragTrans.replace(R.id.fragment_container_search, mFragment, tag);
        childFragTrans.addToBackStack(tag);
        childFragTrans.commit();
    }

    // Metodo para agregar el fragment que contiene la lista

    private void getProductListFragment() {
        if (productListFragment == null) {
            productListFragment = new ProductListFragment();
        }
        fragmentChange(productListFragment, TAG_PRODUCT_LIST);
    }

    // Metodo para agregar el fragment de detalles

    public void goToDetailFragment(String id) {
        if (productDetailFragment == null) {
            productDetailFragment = new ProductDetailFragment();
        }
        Bundle args = new Bundle();
        args.putString("productId", id);
        productDetailFragment.setArguments(args);
        fragmentChange(productDetailFragment, TAG_PRODUCT_DETAIL);
        toolbar.collapseActionView();
    }
}
