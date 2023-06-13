package com.example.apliacacioncomederomascotas.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.example.apliacacioncomederomascotas.BotonDispensar.AguaDispension;
import com.example.apliacacioncomederomascotas.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationHelper {

    public static void setupBottomNavigation(BottomNavigationView bottomNavigationView, final Context context) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        // Acción al seleccionar "Home"
                        return true;
                    case R.id.navigation_calendar:
                        // Acción al seleccionar "Calendar"
                        return true;
                    case R.id.navigation_buttons:
                        // Acción al seleccionar "Botones"
                        if (!(context instanceof AguaDispension)) {
                            Intent intent = new Intent(context, AguaDispension.class);
                            context.startActivity(intent);
                            ((Activity) context).overridePendingTransition(0, 0); // Deshabilitar animación de transición
                            ((Activity) context).finish();
                        }
                        return true;
                    case R.id.navigation_profile:
                        // Acción al seleccionar "Profile"
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}

