package com.example.apliacacioncomederomascotas.LogIn;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static SessionManager instance;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_NAME = "lastName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private DatabaseHelper databaseHelper;


    private SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(context);
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public void saveLoginCredentials(String username, String password) {
        // Guardar el nombre de usuario y contraseña en las preferencias compartidas
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);

        // Consultar y guardar los valores de nombre, apellido y correo electrónico desde la base de datos
        String[] userInfo = databaseHelper.getUserInfo(username);
        if (userInfo != null) {
            String firstName = userInfo[0];
            String lastName = userInfo[1];
            String email = userInfo[2];
            editor.putString(KEY_FIRST_NAME, firstName);
            editor.putString(KEY_LAST_NAME, lastName);
            editor.putString(KEY_EMAIL, email);
        }

        editor.apply();
    }
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logoutUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public String getFirstName() {
        return sharedPreferences.getString(KEY_FIRST_NAME, null);
    }

    public void saveFirstName(String firstName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.apply();
    }

    public String getLastName() {
        return sharedPreferences.getString(KEY_LAST_NAME, null);
    }

    public void saveLastName(String lastName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LAST_NAME, lastName);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public void saveEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public void saveUserInfo(String firstName, String lastName, String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.putString(KEY_LAST_NAME, lastName);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }
    public void savePassword(String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password", password);
        editor.apply();
    }
}
