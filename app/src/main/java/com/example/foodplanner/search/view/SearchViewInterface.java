package com.example.foodplanner.search.view;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.IngredientModel;
import com.example.foodplanner.model.Meal;

import java.util.List;

public interface SearchViewInterface {

    public void viewIngredients(List<Ingredient> ingredientList);
    public void viewCategories(List<Category> categoryList);
    public void viewCountries(List<Country> countryList);
    public void viewMeals(List<Meal> mealsList);


}
