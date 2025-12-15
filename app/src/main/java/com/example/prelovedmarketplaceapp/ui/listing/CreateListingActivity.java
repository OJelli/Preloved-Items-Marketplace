package com.example.prelovedmarketplaceapp.ui.listing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View; // Needed for View.VISIBLE/GONE
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView; // Needed for tvLocationDisplay
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.Listing;
import com.example.prelovedmarketplaceapp.model.User;
import com.example.prelovedmarketplaceapp.repository.ListingRepository;
import com.example.prelovedmarketplaceapp.utils.UserSessionManager;

import java.util.UUID;

public class CreateListingActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etPrice;
    private Spinner spinnerCategory;
    private ImageView ivListingImage;
    private Button btnListItem;

    // 🎯 NEW FIELDS FOR LOCATION 🎯
    private Button btnSelectLocation;
    private TextView tvLocationDisplay;

    // 🎯 STATE VARIABLES FOR 10-ARG CONSTRUCTOR 🎯
    private String selectedLocationName = "";
    // Using a generic placeholder ref, as the user doesn't upload a thumbnail
    private final String selectedLocationMapRef = "ic_map_placeholder";

    // Helper to start this activity from anywhere (e.g., HomeActivity)
    public static void start(Context context) {
        Intent intent = new Intent(context, CreateListingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        // Setup Toolbar for the back/close button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // This makes the icon in the XML actually close the activity
        toolbar.setNavigationOnClickListener(v -> finish());

        // Initialize UI components
        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        etPrice = findViewById(R.id.et_price);
        spinnerCategory = findViewById(R.id.spinner_category);
        ivListingImage = findViewById(R.id.iv_listing_image);
        btnListItem = findViewById(R.id.btn_list_item);

        // 🎯 INITIALIZE NEW LOCATION VIEWS 🎯
        btnSelectLocation = findViewById(R.id.btn_select_location);
        tvLocationDisplay = findViewById(R.id.tv_location_display);

        setupListeners();
    }

    private void setupListeners() {
        // Handle Image Click (Placeholder for future image picker)
        ivListingImage.setOnClickListener(v ->
                Toast.makeText(this, "Image Picker Feature Coming Soon!", Toast.LENGTH_SHORT).show()
        );

        // 🎯 Handle Location Selection 🎯
        btnSelectLocation.setOnClickListener(v -> {
            // TODO: In a real app, launch Map Picker Activity here
            // For now, simulate location selection
            simulateLocationSelection("Simulated Pick-up Location, KL");
        });

        // Handle Form Submission
        btnListItem.setOnClickListener(v -> handleListingSubmission());
    }

    // 🎯 NEW: Simulate Location Selection for Demo 🎯
    private void simulateLocationSelection(String location) {
        selectedLocationName = location;
        tvLocationDisplay.setText("Pickup Location: " + selectedLocationName);
        tvLocationDisplay.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Location selected!", Toast.LENGTH_SHORT).show();
    }


    private void handleListingSubmission() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String priceText = etPrice.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        // --- 1. Validation ---
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(priceText)) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_LONG).show();
            return;
        }
        if (category.equals("Select Category")) {
            Toast.makeText(this, "Please select a valid category.", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(selectedLocationName)) { // Check if location was selected
            Toast.makeText(this, "Please select a pickup location.", Toast.LENGTH_LONG).show();
            return;
        }


        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price <= 0) {
                Toast.makeText(this, "Price must be greater than zero.", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price format.", Toast.LENGTH_LONG).show();
            return;
        }

        // --- 2. Get User/ID and create Listing object ---
        User currentUser = UserSessionManager.getInstance(this).getUser();
        String sellerId = currentUser != null ? currentUser.getName() : "UnknownSeller"; // Using name as ID for demo

        // Generate unique ID and current timestamp
        String listingId = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();
        String dummyImageUrl = "placeholder"; // We can improve this later

        // 🎯 FIX: CALLING THE 10-ARGUMENT CONSTRUCTOR 🎯
        Listing newListing = new Listing(
                listingId,
                title,
                description,
                price,
                category,
                sellerId,                 // Argument 6 (sellerId)
                dummyImageUrl,            // Argument 7 (imageUrl)
                timestamp,                // Argument 8 (timestamp)
                selectedLocationName,     // Argument 9 (pickupLocation)
                selectedLocationMapRef    // Argument 10 (mapThumbnail)
        );

        // --- 3. Save to Repository and finish ---
        ListingRepository.addListing(newListing);

        Toast.makeText(this, "Listing created successfully!", Toast.LENGTH_SHORT).show();
        finish(); // Go back to the main Home screen
    }
}