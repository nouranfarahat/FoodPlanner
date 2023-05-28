package com.example.foodplanner.home.view;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Meal;

import java.util.List;

public interface HomeViewInterface {

    public void viewRandomMeal(List<Meal> meals);
    public void viewCategories(List<Category> categoryList);
}
