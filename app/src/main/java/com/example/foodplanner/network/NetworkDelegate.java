package com.example.foodplanner.network;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Meal;

import java.util.List;

public interface NetworkDelegate {
    public void onSuccessMeal(List<Meal> mealsList);
    public void onSuccessCategories(List<Category> categoryList);
    public void onFailureResponse(String errorMsg);

}
