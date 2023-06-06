package com.example.apliacacioncomederomascotas.BotonDispensar;

import android.os.CountDownTimer;
import android.widget.TextView;

public class DispensionHelper {

    private static final long MAX_DURATION = 5000; // 5 segundos

    public static void startDispensing(TextView dispensarComidaTxt) {
        dispensarComidaTxt.setText("Depositando");
        CountDownTimer countDownTimer = new CountDownTimer(MAX_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // No se necesita hacer nada durante cada tick
            }

            @Override
            public void onFinish() {
                stopDispensing(dispensarComidaTxt);
                dispensarComidaTxt.setText("Deteniendo Dispensación");
            }
        }.start();
    }

    public static void stopDispensing(TextView dispensarComidaTxt) {
        dispensarComidaTxt.setText("Dispensar al toque");
    }
}

