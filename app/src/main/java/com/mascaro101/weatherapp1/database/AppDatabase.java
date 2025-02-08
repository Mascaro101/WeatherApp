package com.mascaro101.weatherapp1.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ClothingAdvice.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClothingAdviceDAO clothingAdviceDAO();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "weather_app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}