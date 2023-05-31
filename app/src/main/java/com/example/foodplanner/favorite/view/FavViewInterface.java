package com.example.foodplanner.favorite.view;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.model.Meal;

import java.util.List;

public interface FavViewInterface {
    public void showFavMeals(LiveData<List<Meal>> meals);
    public void deleteMeal(Meal meal);
}
