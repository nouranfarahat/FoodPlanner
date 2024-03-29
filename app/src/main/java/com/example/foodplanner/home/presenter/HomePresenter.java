package com.example.foodplanner.home.presenter;

import com.example.foodplanner.home.view.HomeViewInterface;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.IngredientModel;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.RepositoryInterface;
import com.example.foodplanner.network.NetworkDelegate;

import java.util.List;

public class HomePresenter implements NetworkDelegate {

    RepositoryInterface repositoryInterface;
    HomeViewInterface homeViewInterface;


    public HomePresenter(RepositoryInterface repositoryInterface, HomeViewInterface homeViewInterface) {
        this.repositoryInterface = repositoryInterface;
        this.homeViewInterface = homeViewInterface;
    }

    @Override
    public void onSuccessMeal(List<Meal> meals) {
        homeViewInterface.viewRandomMeal(meals);
    }

    @Override
    public void onSuccessCategories(List<Category> categoryList) {
        homeViewInterface.viewCategories(categoryList);

    }

    @Override
    public void onSuccessCountries(List<Country> countryList) {
        homeViewInterface.viewCountries(countryList);
    }

    @Override
    public void onSuccessIngredients(List<Ingredient> ingredientList) {

    }


    @Override
    public void onFailureResponse(String errorMsg) {
        System.out.println("Error in Connection");

    }
    public void getRandomMeals()
    {
        repositoryInterface.getRandomMeal(this);
    }
    public void getMealsCategory()
    {
        repositoryInterface.getMealCategory(this);
    }
    public void getCountry()
    {
        repositoryInterface.getCountry(this);
    }
    public void addToFav(Meal meal)
    {
        repositoryInterface.insertMeal(meal);
    }



}
