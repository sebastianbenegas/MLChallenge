package com.example.mlchallenge.ui.search.details;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mlchallenge.R;
import com.example.mlchallenge.data.models.Picture;
import com.example.mlchallenge.data.models.Product;
import com.example.mlchallenge.di.ViewModelFactory;
import com.example.mlchallenge.ui.search.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class ProductDetailFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;
    SearchViewModel searchViewModel;

    private VPAdapter adapterViewPager;

    @BindView(R.id.vp_pic_detail) ViewPager vpPictures;
    @BindView(R.id.tv_price_detail) TextView productPrice;
    @BindView(R.id.tv_id_detail) TextView productId;
    @BindView(R.id.tv_title_detail) TextView productTitle;
    @BindView(R.id.detail_progress_bar) ProgressBar progressBar;
    @BindView(R.id.vp_left_arrow) ImageView leftArrow;
    @BindView(R.id.vp_right_arrow) ImageView rightArrow;
    @BindView(R.id.error_thumbnail_detail) ImageView errorThumbnail;

    private List<Picture> pictureList = new ArrayList<Picture>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.bind(this, view);
        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchViewModel.searchProductDetails(getArguments().getString("productId"));
        setupProductObserver();
    }

    private void setupProductObserver() {
        vpPictures.setVisibility(View.VISIBLE);
        errorThumbnail.setVisibility(View.GONE);

        searchViewModel.product.observe(this, resource -> {

            if (resource.isLoading()) {
                showProgressBar();
            } else if (resource.isSuccess() && resource.data != null) {
                pictureList = resource.data.getPictures();
                hideProgressBar();
                setDetailFragmentData(resource.data, false);
                setImageViewPager(resource.data.getPictures());
            } else if (resource.isError() && resource.data != null) {
                handleErrorResponse();
                setDetailFragmentData(resource.data, true);
                hideProgressBar();
            }
        });
    }

    // Metodo para agregar en la vista la informacion del producto

    public void setDetailFragmentData(Product product, Boolean isError) {
        if (isError) {
            errorThumbnail.setVisibility(View.VISIBLE);
        }
        if (product != null) {
            if (product.getId() != null) {
                productId.setText(getContext().getResources().getString(R.string.product_code) + product.getId());
            } else {
                productId.setText(getContext().getResources().getString(R.string.default_value));
            }

            if (product.getTitle() != null) {
                productTitle.setText(product.getTitle());
            } else {
                productTitle.setText(getContext().getResources().getString(R.string.default_value));
            }

            if (product.getPrice() != null) {
                productPrice.setText(getContext().getResources().getString(R.string.currency) + product.getPrice().toString());
            } else {
                productPrice.setText(getContext().getResources().getString(R.string.default_value));
            }
        }
    }

    private void handleErrorResponse() {
        errorThumbnail.setVisibility(View.VISIBLE);
        vpPictures.setVisibility(View.GONE);
        Toast.makeText(getContext(), R.string.errorToast, Toast.LENGTH_SHORT).show();
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


    // View Pager para ver las fotos en el detalle

    private void setImageViewPager(List<Picture> pictures) {
        adapterViewPager = new VPAdapter(getContext(), pictures);
        vpPictures.setAdapter(adapterViewPager);
        vpPictures.setCurrentItem(0);

        vpPictures.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                // Manejar las flechas de las fotos programaticamente

                if (pictureList.size() == 1) {
                    leftArrow.setVisibility(View.GONE);
                    rightArrow.setVisibility(View.GONE);
                }
                else if (position == 0 && pictureList.size() != 1) {
                    leftArrow.setVisibility(View.GONE);
                    rightArrow.setVisibility(View.VISIBLE);
                } else if (position == pictureList.size() - 1) {
                    rightArrow.setVisibility(View.GONE);
                    leftArrow.setVisibility(View.VISIBLE);
                } else {
                    leftArrow.setVisibility(View.VISIBLE);
                    rightArrow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
