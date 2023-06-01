package com.example.foodplanner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.model.Meal;

import java.util.List;

@Dao
public interface MealDao {

    //@Query("SELECT * FROM meals")
    //LiveData<List<Meal>> getAllMealss();

    @Query("SELECT * FROM meals WHERE isFavorite='1'")
    LiveData<List<Meal>> getFavMeals();

    @Query("SELECT * FROM meals WHERE days=:day")
    LiveData<List<Meal>> getPlanMeals(String day);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);

    @Delete
    void deleteFavMeal(Meal meal);
    @Delete
    void deletePlanMeal(Meal meal);
}
