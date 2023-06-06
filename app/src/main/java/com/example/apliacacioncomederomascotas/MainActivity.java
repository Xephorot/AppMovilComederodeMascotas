package com.example.apliacacioncomederomascotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apliacacioncomederomascotas.LogIn.LogInActivity;
import com.example.apliacacioncomederomascotas.LogIn.LogoutHelper;
import com.example.apliacacioncomederomascotas.LogIn.SessionManager;
import com.example.apliacacioncomederomascotas.Menu.BottomNavigationHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ponemos la funcion para que funcione el menu de la parte inferior
        BottomNavigationHelper.setupBottomNavigation(this, R.id.bottom_navigation);

        Button logoutButton = findViewById(R.id.buttonLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutHelper.logoutUser(MainActivity.this);
            }
        });
        //Cuando se presione alguno mostrara un mensaje solo prueba para ver si funciona
        BottomNavigationHelper.handleBottomNavigationItemSelected(this, R.id.navigation_home);
    }
}

