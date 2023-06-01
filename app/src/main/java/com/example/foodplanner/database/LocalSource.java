package com.example.foodplanner.database;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;

import java.util.List;

public interface LocalSource {
    public void insertMeal(Meal meal);
    public void removeFavMeal(Meal meal);
    public void removePlanMeal(Meal meal);

    //public LiveData<List<Meal>> getMealsList();
    public LiveData<List<Meal>> getFavList();
    public LiveData<List<Meal>> getPlanList(String day);
}
