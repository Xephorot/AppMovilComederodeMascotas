package com.example.apliacacioncomederomascotas.Calendario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.apliacacioncomederomascotas.LogIn.DatabaseHelper;
import com.example.apliacacioncomederomascotas.R;

public class SetAlarmActivity extends AppCompatActivity {
    private ToggleButton toggleButtonMonday;
    private ToggleButton toggleButtonTuesday;
    private ToggleButton toggleButtonWednesday;
    private ToggleButton toggleButtonThursday;
    private ToggleButton toggleButtonFriday;
    private ToggleButton toggleButtonSaturday;
    private ToggleButton toggleButtonSunday;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        // Obtener referencias a los ToggleButton
        toggleButtonMonday = findViewById(R.id.toggleButtonMonday);
        toggleButtonTuesday = findViewById(R.id.toggleButtonTuesday);
        toggleButtonWednesday = findViewById(R.id.toggleButtonWednesday);
        toggleButtonThursday = findViewById(R.id.toggleButtonThursday);
        toggleButtonFriday = findViewById(R.id.toggleButtonFriday);
        toggleButtonSaturday = findViewById(R.id.toggleButtonSaturday);
        toggleButtonSunday = findViewById(R.id.toggleButtonSunday);

        // Inicializar el objeto DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Manejar el evento del botón "Guardar"
        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los estados de los ToggleButton
                boolean mondayChecked = toggleButtonMonday.isChecked();
                boolean tuesdayChecked = toggleButtonTuesday.isChecked();
                boolean wednesdayChecked = toggleButtonWednesday.isChecked();
                boolean thursdayChecked = toggleButtonThursday.isChecked();
                boolean fridayChecked = toggleButtonFriday.isChecked();
                boolean saturdayChecked = toggleButtonSaturday.isChecked();
                boolean sundayChecked = toggleButtonSunday.isChecked();

                // Guardar los estados en la base de datos
                saveAlarmStates(mondayChecked, tuesdayChecked, wednesdayChecked, thursdayChecked,
                        fridayChecked, saturdayChecked, sundayChecked);

                // Mostrar un mensaje de éxito
                Toast.makeText(SetAlarmActivity.this, "Alarma guardada", Toast.LENGTH_SHORT).show();

                // Regresar a la actividad anterior
                finish();
            }
        });
    }

    private void saveAlarmStates(boolean mondayChecked, boolean tuesdayChecked, boolean wednesdayChecked,
                                 boolean thursdayChecked, boolean fridayChecked, boolean saturdayChecked,
                                 boolean sundayChecked) {
        // Obtener una instancia de la base de datos en modo escritura
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Crear un objeto ContentValues para guardar los valores
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CALENDAR_NAME, "Nombre de la alarma"); // Reemplaza con el nombre real de la alarma
        values.put(DatabaseHelper.COLUMN_CALENDAR_REPETITIONS, getRepetitionsValue(mondayChecked, tuesdayChecked,
                wednesdayChecked, thursdayChecked, fridayChecked, saturdayChecked, sundayChecked));
        values.put(DatabaseHelper.COLUMN_CALENDAR_HOUR, "00:00"); // Reemplaza con la hora real de la alarma
        values.put(DatabaseHelper.COLUMN_CREDENTIALS_USERNAME, "Nombre de usuario"); // Reemplaza con el nombre real de usuario

        // Insertar los valores en la tabla "calendar"
        long newRowId = db.insert(DatabaseHelper.TABLE_CALENDAR, null, values);
    }

    private int getRepetitionsValue(boolean mondayChecked, boolean tuesdayChecked, boolean wednesdayChecked,
                                    boolean thursdayChecked, boolean fridayChecked, boolean saturdayChecked,
                                    boolean sundayChecked) {
        int repetitions = 0;

        if (mondayChecked) repetitions |= 1; // Representa el valor binario 00000001
        if (tuesdayChecked) repetitions |= 2; // Representa el valor binario 00000010
        if (wednesdayChecked) repetitions |= 4; // Representa el valor binario 00000100
        if (thursdayChecked) repetitions |= 8; // Representa el valor binario 00001000
        if (fridayChecked) repetitions |= 16; // Representa el valor binario 00010000
        if (saturdayChecked) repetitions |= 32; // Representa el valor binario 00100000
        if (sundayChecked) repetitions |= 64; // Representa el valor binario 01000000

        return repetitions;
    }
}