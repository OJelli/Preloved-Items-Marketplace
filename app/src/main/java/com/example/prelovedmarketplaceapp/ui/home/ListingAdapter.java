package com.example.prelovedmarketplaceapp.ui.home;

import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.Listing;

import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ListingViewHolder> {

    public interface OnListingClickListener {
        void onListingClick(Listing listing);
    }

    private List<Listing> listings;
    private final OnListingClickListener listener;

    public ListingAdapter(List<Listing> listings, OnListingClickListener listener) {
        this.listings = listings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listing, parent, false);
        return new ListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingViewHolder holder, int position) {
        holder.bind(listings.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public void updateListings(List<Listing> newListing) {
        this.listings = newListing;
        notifyDataSetChanged();
    }

    static class ListingViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCategory, tvPrice, tvSeller;
        ImageView ivImage;

        public ListingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvCategory = itemView.findViewById(R.id.tv_item_category);
            tvPrice = itemView.findViewById(R.id.tv_item_price);
            tvSeller = itemView.findViewById(R.id.tv_item_seller);
            ivImage = itemView.findViewById(R.id.iv_item_image);
        }

        public void bind(Listing listing, OnListingClickListener listener) {
            tvTitle.setText(listing.getTitle());
            tvCategory.setText("Category: " + listing.getCategory());
            tvPrice.setText("$" + String.format("%.2f", listing.getPrice()));
            tvSeller.setText("Seller: " + listing.getSellerId());

            // 🎯 NEW LOGIC FOR IMAGE DISPLAY 🎯
            if (!TextUtils.isEmpty(listing.getImageUrl())) {
                // Load the image from the saved URI (local path)
                Uri imageUri = Uri.parse(listing.getImageUrl());
                ivImage.setImageURI(imageUri);
                ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                // Fallback to the shopping cart placeholder
                ivImage.setImageResource(R.drawable.ic_shopping_cart);
                ivImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            // ------------------------------------

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onListingClick(listing);
                }
            });
        }
    }
}