package com.example.apliacacioncomederomascotas.Calendario;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apliacacioncomederomascotas.LogIn.DatabaseHelper;

public class ReadAlarmData {
    private DatabaseHelper databaseHelper;

    public ReadAlarmData(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void readAlarmData(String alarmName) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] columns = {
                DatabaseHelper.COLUMN_CALENDAR_NAME,
                DatabaseHelper.COLUMN_CALENDAR_REPETITIONS,
                DatabaseHelper.COLUMN_CALENDAR_HOUR
        };
        String selection = DatabaseHelper.COLUMN_CALENDAR_NAME + "=?";
        String[] selectionArgs = {alarmName};
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_CALENDAR,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CALENDAR_NAME));
            @SuppressLint("Range") int repetitions = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CALENDAR_REPETITIONS));
            @SuppressLint("Range") String hour = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CALENDAR_HOUR));

            // Utiliza los valores leídos de la base de datos según sea necesario
            System.out.println("Alarm Name: " + name);
            System.out.println("Repetitions: " + repetitions);
            System.out.println("Hour: " + hour);
        }

        cursor.close();
    }
}
