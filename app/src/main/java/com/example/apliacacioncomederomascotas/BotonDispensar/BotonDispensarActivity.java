package com.example.apliacacioncomederomascotas.BotonDispensar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.apliacacioncomederomascotas.MQTT.MqttHandler;
import com.example.apliacacioncomederomascotas.Menu.BottomNavigationHelper;
import com.example.apliacacioncomederomascotas.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class BotonDispensarActivity extends AppCompatActivity {

    private ImageButton botonDispensar;
    private Button btnCambiarAgua;
    private TextView dispensarComidaTxt;
    private CountDownTimer countDownTimer;

    private MqttManager mqttManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton_dispensar);

        btnCambiarAgua = findViewById(R.id.ButtonCambioAgua);
        botonDispensar = findViewById(R.id.BotonDispensar);
        dispensarComidaTxt = findViewById(R.id.DispensarComidatxt);

        // Crear una instancia del MqttManager
        mqttManager = new MqttManager(BotonDispensarActivity.this);

        // Conectar al servidor MQTT
        mqttManager.connect();

        //Prueba
        //mqttManager.subscribeToTopic("boton_bool");
        //mqttManager.publishMessage("boton_bool", "hola");

        //Definir posición Servo
        mqttManager.publishMessage("boton_bool", "0");

        btnCambiarAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BotonDispensarActivity.this, AguaDispension.class);
                startActivity(intent);
                overridePendingTransition(0, 0); // Deshabilitar animación de transición
                finish();
            }
        });

        botonDispensar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    DispensionHelper.startDispensing(dispensarComidaTxt, mqttManager);
                    mqttManager.publishMessage("boton_bool", "1");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    DispensionHelper.stopDispensing(dispensarComidaTxt);
                    mqttManager.publishMessage("boton_bool", "0");
                }
                return true;
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_buttons); // Seleccionar el botón correspondiente

        BottomNavigationHelper.setupBottomNavigation(bottomNavigationView, this);
    }
}
