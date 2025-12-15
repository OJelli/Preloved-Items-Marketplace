package com.example.prelovedmarketplaceapp.ui.home;

import android.content.Context; // <-- Import required for the 'start' method
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.Listing;
import com.example.prelovedmarketplaceapp.model.User;
import com.example.prelovedmarketplaceapp.repository.ListingRepository;
import com.example.prelovedmarketplaceapp.ui.auth.LoginActivity;
import com.example.prelovedmarketplaceapp.ui.chat.ChatListActivity;
import com.example.prelovedmarketplaceapp.ui.listing.CreateListingActivity;
import com.example.prelovedmarketplaceapp.ui.listing.ListingDetailActivity;
import com.example.prelovedmarketplaceapp.ui.profile.ProfileActivity;
import com.example.prelovedmarketplaceapp.utils.UserSessionManager;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements ListingAdapter.OnListingClickListener {

    private RecyclerView rvFeaturedItems;
    private ListingAdapter listingAdapter;
    private UserSessionManager sessionManager;
    private User currentUser;

    // 🎯 MISSING METHOD ADDED HERE 🎯
    // Helper method to start this activity from other parts of the app
    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = UserSessionManager.getInstance(this);
        checkSessionAndSetupUser();

        setupUIListeners();
        setupFeaturedRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFeaturedListings();
    }

    // --- Core Setup Methods ---

    private void checkSessionAndSetupUser() {
        currentUser = sessionManager.getUser();
        if (currentUser == null) {
            // User is not logged in, redirect to login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setupUIListeners() {
        // Link 1: Profile Image
        ImageView profileImage = findViewById(R.id.profile_image);
        profileImage.setOnClickListener(v -> {
            ProfileActivity.start(HomeActivity.this);
        });

        // Link 2: List Item Button
        Button listItemBtn = findViewById(R.id.list_item_btn);
        listItemBtn.setOnClickListener(v -> {
            CreateListingActivity.start(HomeActivity.this);
        });

        // Link 3: Chat FAB
        ImageButton chatFab = findViewById(R.id.chat_fab);
        chatFab.setOnClickListener(v -> {
            ChatListActivity.start(HomeActivity.this);
        });

        // Link 4: Placeholder Wishlist Button
        Button wishListBtn = findViewById(R.id.wish_item_btn);
        wishListBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Wishlist Feature Coming Soon!", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupFeaturedRecyclerView() {
        rvFeaturedItems = findViewById(R.id.rv_featured_items);
        rvFeaturedItems.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with existing data
        listingAdapter = new ListingAdapter(ListingRepository.getAllListings(), this);
        rvFeaturedItems.setAdapter(listingAdapter);

        loadFeaturedListings();
    }

    private void loadFeaturedListings() {
        List<Listing> allListings = ListingRepository.getAllListings();
        listingAdapter.updateListings(allListings);

        // Optional: show a message if the list is still empty
        if (allListings.isEmpty()) {
            Toast.makeText(this, "No listings found. Try adding one!", Toast.LENGTH_LONG).show();
        }
    }

    // --- OnListingClickListener Implementation ---

    @Override
    public void onListingClick(Listing listing) {
        // Open the detail screen when an item is clicked
        String listingId = listing.getId();
        ListingDetailActivity.start(this, listingId);
    }
}