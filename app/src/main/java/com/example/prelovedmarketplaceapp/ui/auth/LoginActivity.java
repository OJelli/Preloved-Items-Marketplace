package com.example.prelovedmarketplaceapp.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prelovedmarketplaceapp.R;
import com.example.prelovedmarketplaceapp.model.User;
import com.example.prelovedmarketplaceapp.ui.home.HomeActivity;
import com.example.prelovedmarketplaceapp.utils.UserSessionManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvGoToRegister;

    // Helper to open Login from anywhere
    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);

        btnLogin.setOnClickListener(v -> handleLogin());

        tvGoToRegister.setOnClickListener(v -> {
            RegisterActivity.start(this);
        });
    }

    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User savedUser = UserSessionManager.getInstance(this).getUser();

        if (savedUser == null) {
            Toast.makeText(this, "No registered user found. Please register.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (savedUser.getEmail().equals(email) && savedUser.getPassword().equals(pass)) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

            UserSessionManager.getInstance(this).saveUser(savedUser);

            HomeActivity.start(this);
            finish();
        } else {
            Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
        }
    }
}
