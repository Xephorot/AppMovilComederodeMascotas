package com.example.apliacacioncomederomascotas.BotonDispensar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.apliacacioncomederomascotas.R;

public class AguaDispension extends AppCompatActivity {
    private Button botonDispensar;
    private TextView dispensarComidaTxt;
    private CountDownTimer countDownTimer;

    private static final long MAX_DURATION = 5000; // 5 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.apliacacioncomederomascotas.R.layout.activity_agua_dispension);
        botonDispensar = findViewById(R.id.BotonDispensarAgua);
        dispensarComidaTxt = findViewById(R.id.DispensarAguatxt);

        botonDispensar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startDispensing();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    stopDispensing();
                }
                return true;
            }
        });
    }

    private void startDispensing() {
        dispensarComidaTxt.setText("Depositando");
        countDownTimer = new CountDownTimer(MAX_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // No se necesita hacer nada durante cada tick
            }

            @Override
            public void onFinish() {
                stopDispensing();
                dispensarComidaTxt.setText("Deteniendo Dispensación");
            }
        }.start();
    }

    private void stopDispensing() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        dispensarComidaTxt.setText("Dispensar Agua al toque");
    }
}