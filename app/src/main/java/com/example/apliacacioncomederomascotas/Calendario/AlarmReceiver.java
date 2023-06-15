package com.example.apliacacioncomederomascotas.Calendario;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.apliacacioncomederomascotas.R;

public class AlarmReceiver extends BroadcastReceiver {

    private static MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm! Wake up!", Toast.LENGTH_LONG).show();

        // Play alarm sound
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.megalovania);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

        // Start NotificationActivity to stop the alarm
        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(notificationIntent);
    }

    public static void stopAlarm() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
