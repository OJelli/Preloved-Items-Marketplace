package com.example.prelovedmarketplaceapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.example.prelovedmarketplaceapp.R;
import androidx.appcompat.app.AppCompatActivity;
import com.example.prelovedmarketplaceapp.ui.home.HomeActivity;
import com.example.prelovedmarketplaceapp.utils.UserSessionManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (UserSessionManager.getInstance(this).isLoggedIn()) {
                startActivity(new Intent(this, HomeActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        }, 1500); // 1.5 seconds
    }
}
