package com.example.foodplanner.model;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.network.NetworkDelegate;

import java.util.List;

public interface RepositoryInterface {
    public void getRandomMeal(NetworkDelegate networkDelegate);
    public void getMealCategory(NetworkDelegate networkDelegate);
    public void getCountry(NetworkDelegate networkDelegate);
    public void getIngredients(NetworkDelegate networkDelegate);

    public void getCountryMeals(NetworkDelegate networkDelegate,String countryName);
    public void getCategoryMeals(NetworkDelegate networkDelegate,String categoryName);
    public void getIngredientMeals(NetworkDelegate networkDelegate,String ingredientName);


    public void getMealDetails(NetworkDelegate networkDelegate,String mealName);

    public void insertMeal(Meal meal);
    public void removeFavMeal(Meal meal);
    public void removePlanMeal(Meal meal);

    public LiveData<List<Meal>> getFavMealsList();
    public LiveData<List<Meal>> getPlanMealsList(String day);





}
