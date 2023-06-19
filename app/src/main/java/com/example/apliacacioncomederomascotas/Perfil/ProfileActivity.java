package com.example.apliacacioncomederomascotas.Perfil;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apliacacioncomederomascotas.LogIn.DatabaseHelper;
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
    private EditText editTextCurrentPassword;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private Button buttonSave;
    private Button buttonChangePassword;

    private SessionManager sessionManager;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = SessionManager.getInstance(this);
        databaseHelper = new DatabaseHelper(this);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextCurrentPassword = findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSave = findViewById(R.id.buttonSave);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);

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
                try {
                    boolean success = databaseHelper.updateUserInfo(sessionManager.getUsername(), newFirstName, newLastName, newEmail);

                    if (success) {
                        // Save the updated values in SessionManager
                        sessionManager.saveFirstName(newFirstName);
                        sessionManager.saveLastName(newLastName);
                        sessionManager.saveEmail(newEmail);

                        Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // If update fails, delete and recreate the table
                        boolean tableRecreated = databaseHelper.recreateTable();
                        if (tableRecreated) {
                            Toast.makeText(ProfileActivity.this, "Error occurred. Regenerating table.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Error occurred while regenerating table.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (SQLiteException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Error occurred while updating profile.", Toast.LENGTH_SHORT).show();
                    databaseHelper.recreateTable();
                    Toast.makeText(ProfileActivity.this, "Error occurred. Regenerating table.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = editTextCurrentPassword.getText().toString().trim();
                String newPassword = editTextNewPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                // Validate input fields
                if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if new password and confirm password match
                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ProfileActivity.this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verify current password
                String storedPassword = sessionManager.getPassword();
                if (!currentPassword.equals(storedPassword)) {
                    Toast.makeText(ProfileActivity.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update the password in the database
                boolean success = databaseHelper.updatePassword(sessionManager.getUsername(), newPassword);

                if (success) {
                    // Update the password in SessionManager
                    sessionManager.savePassword(newPassword);
                    Toast.makeText(ProfileActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // If update fails, delete and recreate the table
                    boolean tableRecreated = databaseHelper.recreateTable();
                    if (tableRecreated) {
                        Toast.makeText(ProfileActivity.this, "Error occurred. Regenerating table.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Error occurred while regenerating table.", Toast.LENGTH_SHORT).show();
                    }
                }
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
