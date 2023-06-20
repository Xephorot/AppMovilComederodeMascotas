package com.example.apliacacioncomederomascotas.Calendario;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apliacacioncomederomascotas.LogIn.DatabaseHelper;
import com.example.apliacacioncomederomascotas.Menu.BottomNavigationHelper;
import com.example.apliacacioncomederomascotas.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    private Button setAlarmButton;
    private ListView alarmListView;
    private List<String> alarmList;
    private ArrayAdapter<String> alarmAdapter;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setAlarmButton = findViewById(R.id.setAlarmButton);
        alarmListView = findViewById(R.id.alarmListView);

        alarmList = new ArrayList<>();
        alarmAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alarmList);
        alarmListView.setAdapter(alarmAdapter);

        databaseHelper = new DatabaseHelper(this);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la nueva actividad en miniatura
                Intent intent = new Intent(CalendarActivity.this, SetAlarmActivity.class);
                startActivity(intent);
            }
        });

        // Manejar el evento de hacer clic en un elemento de la lista de alarmas
        // Manejar el evento de hacer clic en un elemento de la lista de alarmas
        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el nombre de la alarma seleccionada
                String selectedAlarmName = alarmList.get(position);

                // Abrir la actividad de configuración de la alarma
                Intent intent = new Intent(CalendarActivity.this, SetAlarmActivity.class);
                intent.putExtra("alarm_name", selectedAlarmName);
                startActivity(intent);
                finish();
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_calendar); // Seleccionar el botón correspondiente

        BottomNavigationHelper.setupBottomNavigation(bottomNavigationView, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cargar las alarmas guardadas al volver a la actividad
        loadAlarmsFromDatabase();
    }

    private void loadAlarmsFromDatabase() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Recrear la tabla "calendar" si no existe
        //databaseHelper.recreateTable();

        // Verificar si existen registros en la tabla "calendar"
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_CALENDAR, null);
        cursor.moveToFirst();
        int rowCount = cursor.getInt(0);
        cursor.close();

        if (rowCount > 0) {
            // Si hay registros, cargar los datos de la base de datos
            Cursor alarmsCursor = db.query(DatabaseHelper.TABLE_CALENDAR, null, null, null, null, null, null);

            alarmList.clear(); // Limpiar la lista antes de cargar nuevos datos

            // Iterar sobre el cursor y agregar los nombres de las alarmas a la lista
            if (alarmsCursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String alarmName = alarmsCursor.getString(alarmsCursor.getColumnIndex(DatabaseHelper.COLUMN_CALENDAR_NAME));
                    alarmList.add(alarmName);
                } while (alarmsCursor.moveToNext());
            }

            alarmsCursor.close();

            // Notificar al adaptador de cambios en los datos
            alarmAdapter.notifyDataSetChanged();
        } else {
            // Si no hay registros, mostrar un mensaje o realizar alguna acción específica
            Toast.makeText(this, "No hay datos disponibles", Toast.LENGTH_SHORT).show();
        }
    }

}

