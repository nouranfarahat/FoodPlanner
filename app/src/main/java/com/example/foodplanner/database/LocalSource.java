package com.example.foodplanner.database;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;

import java.util.List;

public interface LocalSource {
    public void insertMeal(Meal meal);
    public void removeMeal(Meal meal);
    public LiveData<List<Meal>> getMealsList();
}
