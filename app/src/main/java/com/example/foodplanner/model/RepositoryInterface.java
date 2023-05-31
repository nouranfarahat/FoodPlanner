package com.example.foodplanner.model;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.network.NetworkDelegate;

import java.util.List;

public interface RepositoryInterface {
    public void getRandomMeal(NetworkDelegate networkDelegate);
    public void getMealCategory(NetworkDelegate networkDelegate);
    public void getCountry(NetworkDelegate networkDelegate);
    public void getCountryMeals(NetworkDelegate networkDelegate,String countryName);
    public void getMealDetails(NetworkDelegate networkDelegate,String mealName);

    public void insertMeal(Meal meal);
    public void removeMeal(Meal meal);
    public LiveData<List<Meal>> getFavMealsList();




}
