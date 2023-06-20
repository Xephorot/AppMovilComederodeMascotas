package com.example.apliacacioncomederomascotas.Calendario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private ReadAlarmData readAlarmData;

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

        // Leer los datos de la alarma desde la base de datos

        // Crear una instancia de ReadAlarmData
        ReadAlarmData readAlarmData = new ReadAlarmData(this);

        // Obtener el nombre de la alarma del intent o de donde sea que lo obtengas
        String alarmName = getIntent().getStringExtra("alarm_name");

        try {
            // Leer los datos de la alarma desde la base de datos
            readAlarmData.readAlarmData(alarmName);
            // Obtener el nombre de la alarma del elemento seleccionado
            // Configurar el valor del nombre de la alarma en el campo de texto
            EditText editTextAlarmName = findViewById(R.id.editTextName);
            editTextAlarmName.setText(alarmName);
            // Configurar los valores en la interfaz de usuario según los datos leídos
            // ...
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(SetAlarmActivity.this, "Error al Cargar", Toast.LENGTH_SHORT).show();
            // Manejar la excepción de acuerdo a tus necesidades
        }



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

                // Obtener el nombre de la alarma desde el campo de entrada de texto
                EditText editTextAlarmName = findViewById(R.id.editTextName);
                String alarmName = editTextAlarmName.getText().toString();

                // Verificar si se ha proporcionado un nombre de alarma válido
                if (alarmName.isEmpty()) {
                    // Mostrar un mensaje de error o tomar alguna acción adecuada
                    Toast.makeText(SetAlarmActivity.this, "Por favor, ingresa un nombre para la alarma", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Guardar los estados en la base de datos
                saveAlarmStates(mondayChecked, tuesdayChecked, wednesdayChecked, thursdayChecked,
                        fridayChecked, saturdayChecked, sundayChecked, alarmName);

                // Mostrar un mensaje de éxito
                Toast.makeText(SetAlarmActivity.this, "Alarma guardada", Toast.LENGTH_SHORT).show();

                // Establecer la alarma
                setAlarm(mondayChecked, tuesdayChecked, wednesdayChecked, thursdayChecked,
                        fridayChecked, saturdayChecked, sundayChecked);

                // Regresar a la actividad anterior
                Intent intent = new Intent(SetAlarmActivity.this, CalendarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Manejar el evento del botón "Eliminar"
        Button buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el nombre de la alarma desde el campo de entrada de texto
                EditText editTextAlarmName = findViewById(R.id.editTextName);
                String alarmName = editTextAlarmName.getText().toString();

                // Verificar si se ha proporcionado un nombre de alarma válido
                if (alarmName.isEmpty()) {
                    // Mostrar un mensaje de error o tomar alguna acción adecuada
                    Toast.makeText(SetAlarmActivity.this, "Por favor, ingresa un nombre para la alarma", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Eliminar la alarma de la base de datos
                deleteAlarm(alarmName);

                // Mostrar un mensaje de éxito
                Toast.makeText(SetAlarmActivity.this, "Alarma eliminada", Toast.LENGTH_SHORT).show();

                // Regresar a la actividad anterior
                Intent intent = new Intent(SetAlarmActivity.this, CalendarActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveAlarmStates(boolean mondayChecked, boolean tuesdayChecked, boolean wednesdayChecked,
                                 boolean thursdayChecked, boolean fridayChecked, boolean saturdayChecked,
                                 boolean sundayChecked, String alarmName) {
        // Obtener una instancia de la base de datos en modo escritura
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Crear un objeto ContentValues para guardar los valores
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CALENDAR_NAME, alarmName);
        values.put(DatabaseHelper.COLUMN_CALENDAR_REPETITIONS, getRepetitionsValue(mondayChecked, tuesdayChecked,
                wednesdayChecked, thursdayChecked, fridayChecked, saturdayChecked, sundayChecked));
        values.put(DatabaseHelper.COLUMN_CALENDAR_HOUR, "00:00"); // Reemplaza con la hora real de la alarma
        values.put(DatabaseHelper.COLUMN_CREDENTIALS_USERNAME, "Nombre de usuario"); // Reemplaza con el nombre real de usuario

        // Insertar o actualizar los valores en la tabla "calendar"
        db.insertWithOnConflict(DatabaseHelper.TABLE_CALENDAR, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    private void deleteAlarm(String alarmName) {
        // Obtener una instancia de la base de datos en modo escritura
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Eliminar la alarma de la tabla "calendar" si existe
        db.delete(DatabaseHelper.TABLE_CALENDAR, DatabaseHelper.COLUMN_CALENDAR_NAME + " = ?", new String[]{alarmName});
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

    private void setAlarm(boolean mondayChecked, boolean tuesdayChecked, boolean wednesdayChecked,
                          boolean thursdayChecked, boolean fridayChecked, boolean saturdayChecked,
                          boolean sundayChecked) {
        // Lógica para establecer la alarma según los días seleccionados

        if (mondayChecked) {
            // Establecer alarma para los lunes a las 8:00 AM
            AlarmManagerHelper.setAlarm(this, 8, 0);
        }

        if (tuesdayChecked) {
            // Establecer alarma para los martes a las 9:00 AM
            AlarmManagerHelper.setAlarm(this, 9, 0);
        }

        if (wednesdayChecked) {
            // Establecer alarma para los miércoles a las 10:00 AM
            AlarmManagerHelper.setAlarm(this, 10, 0);
        }

        // Agrega condiciones similares para los demás días de la semana
    }
}
