package com.example.mlchallenge.ui.search.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mlchallenge.R;
import com.example.mlchallenge.data.models.Product;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.CustomViewHolder> {

    private Context context;
    public List<Product> productList;
    private IOnClick iOnClick;

    public ProductListAdapter(Context context) {
        this.context = context;
        this.productList = new ArrayList<>();
        iOnClick = (IOnClick) context;
    }

    @NonNull
    @Override
    public ProductListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View cellView = layoutInflater.inflate(R.layout.cell_product, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(cellView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        final Product product = productList.get(position);
        holder.bindTo(product);
        holder.cellLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClick.goToProductDetail(product.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_cell_layout) CardView cellLayout;
        @BindView(R.id.thumbnail) ImageView thumbnail;
        @BindView(R.id.tv_product_title) TextView productTitle;
        @BindView(R.id.tv_product_price) TextView productPrice;
        @BindView(R.id.tv_product_id) TextView productId;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(Product product) {
            Glide.with(thumbnail.getContext()).load(product.getThumbnail()).error(R.drawable.ic_image).into(thumbnail);
            productId.setText(context.getResources().getString(R.string.product_code) + product.getId());
            productTitle.setText(product.getTitle());
            productPrice.setText(context.getResources().getString(R.string.currency) + product.getPrice().toString());
        }
    }

    public interface IOnClick {
        void goToProductDetail(String id);
    }
}
