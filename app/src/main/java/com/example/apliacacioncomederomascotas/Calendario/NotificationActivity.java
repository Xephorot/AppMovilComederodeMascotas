package com.example.apliacacioncomederomascotas.Calendario;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.apliacacioncomederomascotas.BotonDispensar.BotonDispensarActivity;
import com.example.apliacacioncomederomascotas.BotonDispensar.MqttManager;
import com.example.apliacacioncomederomascotas.MQTT.MqttHandler;
import com.example.apliacacioncomederomascotas.R;

public class NotificationActivity extends Activity {
    private MediaPlayer mediaPlayer;
    private Button stopAlarmButton;
    private Handler handler;
    private MqttManager mqttManager;
    private final int totalSeconds = 5;
    private final int delayMillis = 1000; // Retardo de 1 segundo
    private final int[] elapsedSeconds = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TextView timeTextView = findViewById(R.id.timeTextView);
        stopAlarmButton = findViewById(R.id.stopAlarmButton);

        // Obtener la hora de la alarma del intent y mostrarla en el TextView
        String alarmTime = getIntent().getStringExtra("alarm_time");
        timeTextView.setText(alarmTime);

        // Reproducir música de alarma
        mediaPlayer = MediaPlayer.create(this, R.raw.megalovania);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        // Inicializar el Handler
        handler = new Handler();

        // Crear una instancia del MqttManager
        mqttManager = new MqttManager(NotificationActivity.this);

        // Conectar al servidor MQTT
        mqttManager.connect();

        // Programar la ejecución del código cada segundo durante 5 segundos
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Publicar el mensaje correspondiente
                mqttManager.publishMessage("boton_bool", String.valueOf(elapsedSeconds[0] % 2));

                // Incrementar el contador de segundos transcurridos
                elapsedSeconds[0]++;

                // Verificar si se alcanzó el número total de segundos
                if (elapsedSeconds[0] < totalSeconds) {
                    // Volver a programar la ejecución después de 1 segundo
                    handler.postDelayed(this, delayMillis);
                } else {
                    // Finalizar el ciclo y detener la música de alarma
                    stopAlarm();
                    finish();
                }
            }
        }, delayMillis);

        // Detener la música de alarma y finalizar la actividad al hacer clic en el botón "Stop Alarm"
        stopAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();
                finish();
            }
        });
    }

    private void stopAlarm() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAlarm();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        // Eliminar cualquier mensaje pendiente en el Handler para evitar fugas de memoria
        handler.removeCallbacksAndMessages(null);
    }
}
