package com.example.apliacacioncomederomascotas.LogIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apliacacioncomederomascotas.MainActivity;
import com.example.apliacacioncomederomascotas.R;

public class LogInActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;

    private Register register;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        register = Register.getInstance(this);
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

                // Call the registerUser method
                registerUser(username, password);
            }
        });

        // Check if the user is already logged in
        if (sessionManager.isLoggedIn()) {
            startMainActivity();
        }
    }

    private void loginUser(String username, String password) {
        // Implement the login process here
        // Validate user credentials and proceed accordingly

        if (username.equals("admin") && password.equals("admin")) {
            sessionManager.saveLoginCredentials(username, password);
            startMainActivity();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser(String username, String password) {
        register.registerUser(username, password);
        // Optionally, perform additional actions after registration
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
