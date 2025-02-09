package com.mascaro101.weatherapp1.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clothing_advice")
public class ClothingAdvice {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String date;
    public double temperature;
    public double windSpeed;
    public String advice;
    public String cityName; // Add city name field
}