package com.example.apliacacioncomederomascotas.Calendario;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.apliacacioncomederomascotas.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm! Wake up!", Toast.LENGTH_LONG).show();

        // Play alarm sound
        mediaPlayer = MediaPlayer.create(context, R.raw.megalovania);
        mediaPlayer.start();
    }
}


