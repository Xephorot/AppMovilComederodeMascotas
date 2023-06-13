package com.example.apliacacioncomederomascotas.BotonDispensar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.apliacacioncomederomascotas.Menu.BottomNavigationHelper;
import com.example.apliacacioncomederomascotas.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AguaDispension extends AppCompatActivity {
    private Button botonDispensar;
    private Button btnCambiar;
    private TextView dispensarComidaTxt;
    private CountDownTimer countDownTimer;

    private static final long MAX_DURATION = 5000; // 5 segundos

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.apliacacioncomederomascotas.R.layout.activity_agua_dispension);
        //Ponemos la funcion para que funcione el menu de la parte inferior
        btnCambiar = findViewById(R.id.ButtonCambio);
        botonDispensar = findViewById(R.id.BotonDispensarAgua);
        dispensarComidaTxt = findViewById(R.id.DispensarAguatxt);

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AguaDispension.this, BotonDispensarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        botonDispensar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    DispensionHelper.startDispensing(dispensarComidaTxt);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    DispensionHelper.stopDispensing(dispensarComidaTxt);
                }
                return true;
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_buttons); // Seleccionar el botón correspondiente

        BottomNavigationHelper.setupBottomNavigation(bottomNavigationView, this);
    }
}