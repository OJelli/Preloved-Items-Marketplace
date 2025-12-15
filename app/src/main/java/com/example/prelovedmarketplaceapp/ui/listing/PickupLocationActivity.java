package com.example.prelovedmarketplaceapp.ui.listing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.PickupLocation;
import com.example.prelovedmarketplaceapp.repository.LocationRepository;

import java.util.List;

public class PickupLocationActivity extends AppCompatActivity {

    public static final String EXTRA_PICKUP_LOCATION = "extra_pickup_location";

    private Spinner spinnerLocation;
    private Button btnConfirm;
    private List<PickupLocation> availableLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_location);

        spinnerLocation = findViewById(R.id.spinner_pickup_location);
        btnConfirm = findViewById(R.id.btn_confirm_location);

        availableLocations = LocationRepository.getAllLocations();
        setupSpinner();

        btnConfirm.setOnClickListener(v -> confirmLocation());
    }

    private void setupSpinner() {
        // ArrayAdapter uses the overridden toString() method in PickupLocation
        ArrayAdapter<PickupLocation> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                availableLocations
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter);
    }

    private void confirmLocation() {
        PickupLocation selectedLocation = (PickupLocation) spinnerLocation.getSelectedItem();

        if (selectedLocation != null) {
            String locationString = selectedLocation.getName() + " (" + selectedLocation.getAddress() + ")";

            // Set the result to be returned to the calling activity
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_PICKUP_LOCATION, locationString);

            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Please select a location.", Toast.LENGTH_SHORT).show();
        }
    }
}