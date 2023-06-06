package com.example.apliacacioncomederomascotas.LogIn;
import android.content.Context;
import android.content.Intent;

import com.example.apliacacioncomederomascotas.LogIn.LogInActivity;
import com.example.apliacacioncomederomascotas.LogIn.SessionManager;

public class LogoutHelper {

    public static void logoutUser(Context context) {
        SessionManager sessionManager = SessionManager.getInstance(context);
        sessionManager.logoutUser();

        // Redirect back to LoginActivity
        Intent intent = new Intent(context, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}