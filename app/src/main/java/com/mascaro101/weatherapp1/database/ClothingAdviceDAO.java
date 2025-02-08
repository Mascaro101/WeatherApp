package com.mascaro101.weatherapp1.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ClothingAdviceDAO {
    @Insert
    void insert(ClothingAdvice advice);

    @Query("SELECT * FROM clothing_advice")
    List<ClothingAdvice> getAllAdvice(); // Fetch all records
}