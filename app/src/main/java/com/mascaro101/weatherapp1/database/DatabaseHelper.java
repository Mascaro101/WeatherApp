package com.mascaro101.weatherapp1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WeatherApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CLOTHING_ADVICE = "clothing_advice";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TEMPERATURE = "temperature";
    private static final String COLUMN_WIND_SPEED = "wind_speed";
    private static final String COLUMN_ADVICE = "advice";

    private static final String CREATE_TABLE_CLOTHING_ADVICE = "CREATE TABLE " + TABLE_CLOTHING_ADVICE + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DATE + " TEXT NOT NULL, "
            + COLUMN_TEMPERATURE + " REAL NOT NULL, "
            + COLUMN_WIND_SPEED + " REAL NOT NULL, "
            + COLUMN_ADVICE + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CLOTHING_ADVICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHING_ADVICE);
        onCreate(db);
    }

    public boolean insertClothingAdvice(String date, double temperature, double windSpeed, String advice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TEMPERATURE, temperature);
        values.put(COLUMN_WIND_SPEED, windSpeed);
        values.put(COLUMN_ADVICE, advice);
        long result = db.insert(TABLE_CLOTHING_ADVICE, null, values);
        return result != -1;
    }

    public Cursor getAdviceByDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CLOTHING_ADVICE + " WHERE " + COLUMN_DATE + " = ?", new String[]{date});
    }
}
