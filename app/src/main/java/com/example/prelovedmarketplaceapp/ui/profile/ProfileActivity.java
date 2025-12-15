package com.example.prelovedmarketplaceapp.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.User;
import com.example.prelovedmarketplaceapp.repository.ListingRepository;
import com.example.prelovedmarketplaceapp.ui.auth.LoginActivity;
import com.example.prelovedmarketplaceapp.utils.UserSessionManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvScore, tvMessage;
    private Button btnLogout;
    private User currentUser;

    public static void start(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currentUser = UserSessionManager.getInstance(this).getUser();

        if (currentUser == null) {
            Toast.makeText(this, "Session expired. Please log in.", Toast.LENGTH_SHORT).show();
            // Redirect to Login if session is invalid
            LoginActivity.start(this);
            finish();
            return;
        }

        setupViews();
        displayUserData();
        calculateAndDisplayScore();
    }

    private void setupViews() {
        tvName = findViewById(R.id.tv_profile_name);
        tvEmail = findViewById(R.id.tv_profile_email);
        tvScore = findViewById(R.id.tv_sustainability_score);
        tvMessage = findViewById(R.id.tv_sustainability_message);
        btnLogout = findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void displayUserData() {
        tvName.setText("Name: " + currentUser.getName());
        tvEmail.setText("Email: " + currentUser.getEmail());
    }

    private void calculateAndDisplayScore() {
        // --- Score Calculation Logic ---
        // Score is based on the number of active listings the user has created.
        int numListings = ListingRepository.getListingsBySeller(currentUser.getName()).size();

        // Simple scoring formula: 10 points per listing
        int sustainabilityScore = numListings * 10;
        // -------------------------------

        tvScore.setText(String.valueOf(sustainabilityScore));

        if (numListings > 5) {
            tvMessage.setText("You are a super seller! Your activity greatly supports the circular economy.");
        } else if (numListings > 0) {
            tvMessage.setText("Great start! Every listing helps reduce waste and promote reuse.");
        } else {
            tvMessage.setText("Ready to boost your score? Create your first listing today!");
        }
    }

    private void logoutUser() {
        UserSessionManager.getInstance(this).logout();
        Toast.makeText(this, "Logged out successfully.", Toast.LENGTH_SHORT).show();

        // Navigate back to the Login screen and clear the activity stack
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}