package com.example.apliacacioncomederomascotas.LogIn;

import android.content.Context;

public class Register {
    private static Register instance;
    private Context context;

    private Register(Context context) {
        this.context = context.getApplicationContext();
    }

    public static synchronized Register getInstance(Context context) {
        if (instance == null) {
            instance = new Register(context);
        }
        return instance;
    }

    public void registerUser(String username, String password) {
        // Perform registration process here
        // Save user data to the server database via API
    }
}

