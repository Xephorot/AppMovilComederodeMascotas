package com.example.apliacacioncomederomascotas.BotonDispensar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.apliacacioncomederomascotas.Menu.BottomNavigationHelper;
import com.example.apliacacioncomederomascotas.R;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class BotonDispensarActivity extends AppCompatActivity {

    private Button botonDispensar;
    private Button btnCambiarAgua;
    private TextView dispensarComidaTxt;
    private CountDownTimer countDownTimer;

    private static final long MAX_DURATION = 15000; // 15 segundos

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton_dispensar);

        //Ponemos la funcion para que funcione el menu de la parte inferior
        BottomNavigationHelper.setupBottomNavigation(this, R.id.bottom_navigation_water);

        btnCambiarAgua = findViewById(R.id.ButtonCambioAgua);
        botonDispensar = findViewById(R.id.BotonDispensar);
        dispensarComidaTxt = findViewById(R.id.DispensarComidatxt);

        btnCambiarAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BotonDispensarActivity.this, AguaDispension.class);
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
        //Cuando se presione alguno mostrara un mensaje solo prueba para ver si funciona
        BottomNavigationHelper.handleBottomNavigationItemSelected(this, R.id.navigation_buttons);
    }
}