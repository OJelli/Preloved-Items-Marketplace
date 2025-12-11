package com.example.prelovedmarketplaceapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.prelovedmarketplaceapp.model.User;

public class UserSessionManager {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_LOGGED_IN = "logged_in";

    private static UserSessionManager instance;
    private final SharedPreferences prefs;

    private UserSessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized UserSessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserSessionManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveUser(User user) {
        prefs.edit()
                .putString(KEY_NAME, user.getName())
                .putString(KEY_EMAIL, user.getEmail())
                .putString(KEY_PASSWORD, user.getPassword())
                .putBoolean(KEY_LOGGED_IN, true)
                .apply();
    }

    public User getUser() {
        String name = prefs.getString(KEY_NAME, null);
        String email = prefs.getString(KEY_EMAIL, null);
        String password = prefs.getString(KEY_PASSWORD, null);

        if (email == null || password == null) {
            return null;
        }

        return new User(name, email, password);
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public void logout() {
        prefs.edit().clear().apply();
    }
}
