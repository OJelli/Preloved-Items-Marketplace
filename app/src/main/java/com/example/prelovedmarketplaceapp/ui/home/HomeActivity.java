package com.example.prelovedmarketplaceapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.User;
import com.example.prelovedmarketplaceapp.ui.auth.LoginActivity;
import com.example.prelovedmarketplaceapp.ui.chat.ChatListActivity;
import com.example.prelovedmarketplaceapp.utils.UserSessionManager;

public class HomeActivity extends AppCompatActivity {

    // Helper to open Home from anywhere
    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tvWelcomeUser = findViewById(R.id.tvWelcomeUser);
        Button btnOpenChat = findViewById(R.id.btnOpenChat);
        Button btnLogout = findViewById(R.id.btnLogout);

        // 🔹 Get current user from session and show their name
        User currentUser = UserSessionManager.getInstance(this).getUser();
        if (currentUser != null && currentUser.getName() != null) {
            tvWelcomeUser.setText("Welcome, " + currentUser.getName());
        } else {
            tvWelcomeUser.setText("Welcome!");
        }

        btnOpenChat.setOnClickListener(v -> {
            ChatListActivity.start(this);
        });

        btnLogout.setOnClickListener(v -> {
            UserSessionManager.getInstance(this).logout();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
