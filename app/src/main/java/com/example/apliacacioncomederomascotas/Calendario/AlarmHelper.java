package com.example.apliacacioncomederomascotas.Calendario;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.apliacacioncomederomascotas.Calendario.AlarmManagerHelper;
import com.example.apliacacioncomederomascotas.LogIn.DatabaseHelper;

import java.util.List;
import java.util.Locale;

public class AlarmHelper {
    private static AlarmHelper instance;
    private DatabaseHelper databaseHelper;

    private AlarmHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static synchronized AlarmHelper getInstance(Context context) {
        if (instance == null) {
            instance = new AlarmHelper(context.getApplicationContext());
        }
        return instance;
    }

    public void setAlarm(String alarmName, int hour, int minute) {
        Context context = databaseHelper.getContext();
        AlarmManagerHelper.setAlarm(context, hour, minute);
        saveAlarm(alarmName, hour, minute);
    }

    public void cancelAlarm() {
        Context context = databaseHelper.getContext();
        AlarmManagerHelper.cancelAlarm(context);
    }

    public List<String> getAlarmNames() {
        return databaseHelper.getAlarmNames();
    }

    private void saveAlarm(String alarmName, int hour, int minute) {
        // Obtener el contexto de la base de datos
        Context context = databaseHelper.getContext();
        if (context == null) {
            // Manejar el caso en el que el contexto sea nulo
            return;
        }
        // Crear una instancia de DatabaseHelper para acceder a la base de datos
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        // Guardar la alarma en la base de datos
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CALENDAR_NAME, alarmName);
        values.put(DatabaseHelper.COLUMN_CALENDAR_HOUR, String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

        long newRowId = db.insert(DatabaseHelper.TABLE_CALENDAR, null, values);
        if (newRowId != -1) {
            // La alarma se guardó correctamente
            Toast.makeText(context, "Alarma guardada", Toast.LENGTH_SHORT).show();
        } else {
            // Ocurrió un error al guardar la alarma
            Toast.makeText(context, "Error al guardar la alarma", Toast.LENGTH_SHORT).show();
        }
    }

}
