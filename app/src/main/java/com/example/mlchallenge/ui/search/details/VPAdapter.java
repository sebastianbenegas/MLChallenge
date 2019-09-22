package com.example.mlchallenge.ui.search.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mlchallenge.R;
import com.example.mlchallenge.data.models.Picture;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class VPAdapter extends PagerAdapter {

    private List<Picture> picturesList;
    private Context context;

    public VPAdapter(Context context, List<Picture> picturesList) {
        this.context = context;
        this.picturesList = picturesList;
    }

    @Override
    public int getCount() {
        return picturesList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.pager_item, container, false);
        ImageView imageView = view.findViewById(R.id.vp_image);
        Glide.with(context)
                .load(picturesList.get(position).getUrl())
                .error(R.drawable.ic_image)
                .into(imageView);

        container.addView(view);
        return view;
    }
}
