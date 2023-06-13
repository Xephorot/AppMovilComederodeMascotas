package com.example.apliacacioncomederomascotas.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apliacacioncomederomascotas.LogIn.LogoutHelper;
import com.example.apliacacioncomederomascotas.LogIn.SessionManager;
import com.example.apliacacioncomederomascotas.MainActivity;
import com.example.apliacacioncomederomascotas.Menu.BottomNavigationHelper;
import com.example.apliacacioncomederomascotas.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private Button buttonSave;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = SessionManager.getInstance(this);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSave = findViewById(R.id.buttonSave);

        // Retrieve the user's information from the session manager
        String firstName = sessionManager.getFirstName();
        String lastName = sessionManager.getLastName();
        String email = sessionManager.getEmail();

        // Set the retrieved data in the corresponding EditText fields
        editTextFirstName.setText(firstName);
        editTextLastName.setText(lastName);
        editTextEmail.setText(email);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newFirstName = editTextFirstName.getText().toString().trim();
                String newLastName = editTextLastName.getText().toString().trim();
                String newEmail = editTextEmail.getText().toString().trim();

                // Validate input fields
                if (newFirstName.isEmpty() || newLastName.isEmpty() || newEmail.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save the updated user info
                sessionManager.saveUserInfo(newFirstName, newLastName);
                sessionManager.saveEmail(newEmail);

                Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            }
        });
        Button logoutButton = findViewById(R.id.buttonLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutHelper.logoutUser(ProfileActivity.this);
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile); // Seleccionar el botón correspondiente

        BottomNavigationHelper.setupBottomNavigation(bottomNavigationView, this);
    }
}
