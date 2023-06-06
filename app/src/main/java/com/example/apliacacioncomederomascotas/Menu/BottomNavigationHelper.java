package com.example.apliacacioncomederomascotas.Menu;
import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.apliacacioncomederomascotas.BotonDispensar.AguaDispension;
import com.example.apliacacioncomederomascotas.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationHelper {

    public static void setupBottomNavigation(Activity activity, int bottomNavigationViewId) {
        BottomNavigationView bottomNavigationView = activity.findViewById(bottomNavigationViewId);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home); // Seleccionar "Home" por defecto
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(activity, "Home selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation_calendar:
                        Toast.makeText(activity, "Calendar selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation_profile:
                        Toast.makeText(activity, "Profile selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation_buttons:
                        Toast.makeText(activity, "Buttons selected", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, AguaDispension.class);
                        activity.startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

    public static void handleBottomNavigationItemSelected(Activity activity, int itemId) {
        switch (itemId) {
            case R.id.navigation_home:
                // Acción al seleccionar "Home"
                break;
            case R.id.navigation_calendar:
                // Acción al seleccionar "Search"
                break;
            case R.id.navigation_profile:
                // Acción al seleccionar "Profile"
            case R.id.navigation_buttons:
                // Acción al seleccionar "Buttons"
                break;
        }
    }
}
