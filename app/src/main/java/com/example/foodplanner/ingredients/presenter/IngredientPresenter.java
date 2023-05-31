package com.example.foodplanner.ingredients.presenter;

import com.example.foodplanner.ingredients.view.IngredientViewInterface;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.IngredientModel;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.RepositoryInterface;
import com.example.foodplanner.network.NetworkDelegate;

import java.util.List;

public class IngredientPresenter implements NetworkDelegate {

    IngredientViewInterface countryViewInterface;
    RepositoryInterface repositoryInterface;

    public IngredientPresenter(IngredientViewInterface countryViewInterface, RepositoryInterface repositoryInterface) {
        this.countryViewInterface = countryViewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public void onSuccessMeal(List<Meal> mealsList) {
        countryViewInterface.viewCountryMeals(mealsList);
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
    public void getCountryMealsByName(String countryName)
    {
        repositoryInterface.getCountryMeals(this,countryName);
    }

}
