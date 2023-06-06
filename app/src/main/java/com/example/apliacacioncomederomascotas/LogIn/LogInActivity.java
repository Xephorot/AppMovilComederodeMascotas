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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.apliacacioncomederomascotas.API.ApiClient;
import com.example.apliacacioncomederomascotas.API.ApiService;
import com.example.apliacacioncomederomascotas.API.Usuario;
import com.example.apliacacioncomederomascotas.LogIn.LoginRequest;

import java.util.List;


public class LogInActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;

    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        apiService = ApiClient.getClient();
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

        // Check if the user is already logged in
        if (sessionManager.isLoggedIn()) {
            startMainActivity();
        }
    }

    private void loginUser(String username, String password) {
        // Create a LoginRequest object with the entered credentials
        LoginRequest loginRequest = new LoginRequest(username, password);

        // Make the API call to verify the credentials
        Call<List<Usuario>> call = apiService.obtenerUsuariosPorCorreo(username);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    List<Usuario> usuarios = response.body();

                    // Check if the entered username and password match any user in the response
                    for (Usuario usuario : usuarios) {
                        if (usuario.getCorreoElectronico().equals(username) && usuario.getContrasena().equals(password)) {
                            // Save the login credentials and start the MainActivity
                            sessionManager.saveLoginCredentials(username, password);
                            startMainActivity();
                            return;
                        }
                    }

                    // Invalid username or password
                    Toast.makeText(LogInActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle API call failure
                    Toast.makeText(LogInActivity.this, "Failed to fetch users", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                // Handle API call failure
                Toast.makeText(LogInActivity.this, "Failed to fetch users: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

