package com.example.foodplanner.home.presenter;

import com.example.foodplanner.home.view.HomeViewInterface;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.Repository;
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


    public void addToFav(Meal meal)
    {
        //repo.insertProduct(meal);
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


}
