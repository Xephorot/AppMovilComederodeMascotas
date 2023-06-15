package com.example.apliacacioncomederomascotas.Calendario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apliacacioncomederomascotas.R;

public class NotificationActivity extends AppCompatActivity {

    private Button stopAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        stopAlarmButton = findViewById(R.id.stopAlarmButton);
        stopAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop the alarm
                stopAlarm();
            }
        });
    }

    private void stopAlarm() {
        // Stop the alarm sound
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.stopAlarm();

        // Close the NotificationActivity
        finish();
    }
}
