package com.example.foodplanner.mealdetails.presenter;

import com.example.foodplanner.mealdetails.view.MealViewInterface;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.IngredientModel;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.RepositoryInterface;
import com.example.foodplanner.network.NetworkDelegate;

import java.util.List;

public class MealDetailsPresenter implements NetworkDelegate {

    RepositoryInterface repositoryInterface;
    MealViewInterface mealViewInterface;

    public MealDetailsPresenter(RepositoryInterface repositoryInterface, MealViewInterface mealViewInterface) {
        this.repositoryInterface = repositoryInterface;
        this.mealViewInterface = mealViewInterface;
    }

    @Override
    public void onSuccessMeal(List<Meal> mealsList) {
        mealViewInterface.viewMealDetails(mealsList);
    }

    @Override
    public void onSuccessCategories(List<Category> categoryList) {

    }

    @Override
    public void onSuccessCountries(List<Country> countryList) {

    }

    @Override
    public void onSuccessIngredients(List<Ingredient> ingredientList) {

    }


    @Override
    public void onFailureResponse(String errorMsg) {

    }
    public void getMealFromRepo(String mealName)
    {
        repositoryInterface.getMealDetails(this,mealName);
    }

    public void addMealToPlan(Meal meal)
    {
        repositoryInterface.insertMeal(meal);
    }
    public void addToFav(Meal meal)
    {
        repositoryInterface.insertMeal(meal);
    }

}
