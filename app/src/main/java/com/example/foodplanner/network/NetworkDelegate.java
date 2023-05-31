package com.example.foodplanner.network;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.IngredientModel;
import com.example.foodplanner.model.Meal;

import java.util.List;

public interface NetworkDelegate {
    public void onSuccessMeal(List<Meal> mealsList);
    public void onSuccessCategories(List<Category> categoryList);
    public void onSuccessCountries(List<Country> countryList);
    public void onSuccessIngredients(List<Ingredient> ingredientList);

    public void onFailureResponse(String errorMsg);

}
