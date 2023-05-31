package com.example.foodplanner.search.presenter;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.IngredientModel;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.RepositoryInterface;
import com.example.foodplanner.network.NetworkDelegate;
import com.example.foodplanner.search.view.SearchViewInterface;

import java.util.List;

public class SearchPresenter implements NetworkDelegate {

    RepositoryInterface repositoryInterface;
    SearchViewInterface searchViewInterface;


    public SearchPresenter(RepositoryInterface repositoryInterface, SearchViewInterface searchViewInterface) {
        this.repositoryInterface = repositoryInterface;
        this.searchViewInterface = searchViewInterface;
    }

    @Override
    public void onSuccessMeal(List<Meal> meals) {
        searchViewInterface.viewMeals(meals);

    }

    @Override
    public void onSuccessCategories(List<Category> categoryList) {
        searchViewInterface.viewCategories(categoryList);

    }

    @Override
    public void onSuccessCountries(List<Country> countryList) {
        searchViewInterface.viewCountries(countryList);
    }

    @Override
    public void onSuccessIngredients(List<Ingredient> ingredientList) {
        searchViewInterface.viewIngredients(ingredientList);
    }


    @Override
    public void onFailureResponse(String errorMsg) {
        System.out.println("Error in Connection");

    }
    public void getIngredientMeals()
    {
        repositoryInterface.getIngredients(this);
    }
    public void getMealsCategory()
    {
        repositoryInterface.getMealCategory(this);
    }
    public void getCountry()
    {
        repositoryInterface.getCountry(this);
    }
    public void getCategoryMealsByName(String categoryName)
    {
        repositoryInterface.getCategoryMeals(this,categoryName);
    }
    public void getCountryMealsByName(String countryName)
    {
        repositoryInterface.getCountryMeals(this,countryName);
    }
    public void getMealsByIngredient(String ingredientName)
    {
        repositoryInterface.getIngredientMeals(this,ingredientName);
    }
    public void addToFav(Meal meal)
    {
        repositoryInterface.insertMeal(meal);
    }




}
