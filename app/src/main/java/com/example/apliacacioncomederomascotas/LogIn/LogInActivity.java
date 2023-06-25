package com.example.apliacacioncomederomascotas.LogIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apliacacioncomederomascotas.MainActivity;
import com.example.apliacacioncomederomascotas.R;

public class LogInActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        databaseHelper = new DatabaseHelper(this);
        sessionManager = SessionManager.getInstance(this);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);

        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Call the login method
                loginUser(username, password);
            }
        });

        Button registerButton = findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Call the register method
                registerUser(username, password);
            }
        });

        // Check if the user is already logged in
        if (sessionManager.isLoggedIn()) {
            startMainActivity();
        }
    }

    private void loginUser(String username, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_USERNAME,
                DatabaseHelper.COLUMN_PASSWORD
        };

        String selection = DatabaseHelper.COLUMN_USERNAME + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            // Valid username and password
            sessionManager.saveLoginCredentials(username, password);
            startMainActivity();
        }
        else {
            // Invalid username or password
            Toast.makeText(LogInActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }

    private void registerUser(String username, String password) {
        String email = ""; // Retrieve the email from the user input (e.g., emailEditText.getText().toString())

        if (isInputValid(username, password)) {
            if (!isValidEmail(username)) {
                Toast.makeText(LogInActivity.this, "El Usuario Neceista un ejemplo de lo siguiente (e.g., example@gmail.com)", Toast.LENGTH_SHORT).show();
            } else if (isUserExists(username)) {
                Toast.makeText(LogInActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
            } else {
                Register.getInstance(LogInActivity.this).registerUser(username, password, email);
                Toast.makeText(LogInActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isValidEmail(String email) {
        // Simple email validation using a regular expression
        String emailPattern = "[a-zA-Z0-9._-]+@gmail.com";
        return email.matches(emailPattern);
    }

    private boolean isInputValid(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(LogInActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isUserExists(String username) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_USERNAME
        };

        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean userExists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return userExists;
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
