package com.example.prelovedmarketplaceapp.ui.listing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.Listing;
import com.example.prelovedmarketplaceapp.repository.ListingRepository;
import com.example.prelovedmarketplaceapp.ui.chat.ChatActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ListingDetailActivity extends AppCompatActivity {

    public static final String EXTRA_LISTING_ID = "extra_listing_id";

    private TextView tvTitle, tvSeller, tvPrice, tvDescription, tvCategoryDate;
    private ImageView ivImage;
    private ImageButton btnBack;
    private Button btnMessageSeller;
    private ImageButton btnWishlist;

    // 🎯 NEW FIELDS 🎯
    private TextView tvLocationName;
    private ImageView ivMapThumbnail;
    private LinearLayout llLocationContainer;
    // -----------------

    private Listing currentListing;

    public static void start(Context context, String listingId) {
        Intent intent = new Intent(context, ListingDetailActivity.class);
        intent.putExtra(EXTRA_LISTING_ID, listingId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_detail);

        String listingId = getIntent().getStringExtra(EXTRA_LISTING_ID);
        currentListing = ListingRepository.getListingById(listingId);

        setupViews();
        setupListeners();

        if (currentListing != null) {
            displayListingDetails(currentListing);
        } else {
            Toast.makeText(this, "Listing not found.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupViews() {
        tvTitle = findViewById(R.id.tv_detail_title);
        tvSeller = findViewById(R.id.tv_detail_seller_name);
        tvPrice = findViewById(R.id.tv_detail_price);
        tvDescription = findViewById(R.id.tv_detail_description);
        tvCategoryDate = findViewById(R.id.tv_detail_category_date);
        ivImage = findViewById(R.id.iv_detail_image);
        btnBack = findViewById(R.id.btn_back);
        btnMessageSeller = findViewById(R.id.btn_message_seller);
        btnWishlist = findViewById(R.id.btn_wishlist);

        // 🎯 INITIALIZE NEW VIEWS 🎯
        tvLocationName = findViewById(R.id.tv_listing_detail_location_name);
        ivMapThumbnail = findViewById(R.id.iv_detail_map_thumbnail);
        llLocationContainer = findViewById(R.id.ll_pickup_location_container);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnMessageSeller.setOnClickListener(v -> {
            if (currentListing != null) {
                // Assuming ChatActivity can start a new chat with the seller
                ChatActivity.start(this, currentListing.getSellerId());
            }
        });

        // 🎯 SETUP CLICK LISTENER TO LAUNCH MAPS 🎯
        llLocationContainer.setOnClickListener(v -> launchGoogleMaps(currentListing.getPickupLocation()));
    }

    private void displayListingDetails(Listing listing) {
        tvTitle.setText(listing.getTitle());
        tvSeller.setText(listing.getSellerId() + " (Seller)");
        tvPrice.setText("RM " + String.format(Locale.getDefault(), "%.2f", listing.getPrice()));
        tvDescription.setText(listing.getDescription());

        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
        String dateString = sdf.format(new Date(listing.getTimestamp()));
        tvCategoryDate.setText("Category: " + listing.getCategory() + " | Posted: " + dateString);

        // Load image using Picasso (assuming you have the library imported)
        Picasso.get().load(listing.getImageUrl()).into(ivImage);

        // 🎯 DISPLAY LOCATION TEXT AND THUMBNAIL 🎯
        tvLocationName.setText("Tap to view map: " + listing.getPickupLocation());

        // Load map thumbnail using reflection on resource name
        int mapResId = getResources().getIdentifier(
                listing.getMapThumbnail(),
                "drawable",
                getPackageName()
        );

        if (mapResId != 0) {
            ivMapThumbnail.setImageResource(mapResId);
        } else {
            // Fallback if the specific resource is missing
            ivMapThumbnail.setImageResource(R.drawable.ic_map_placeholder);
        }
    }

    // 🎯 NEW METHOD: Launches Google Maps 🎯
    private void launchGoogleMaps(String locationName) {
        try {
            // Encode the location name/address to search on Google Maps
            String mapUri = "geo:0,0?q=" + Uri.encode(locationName);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUri));
            mapIntent.setPackage("com.google.android.apps.maps"); // Tries to use the dedicated Maps app

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Fallback to general browser search if Maps app is not installed
                Toast.makeText(this, "Google Maps app not found. Opening in browser.", Toast.LENGTH_SHORT).show();
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/?q=" + Uri.encode(locationName)));
                startActivity(webIntent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Could not open map.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}