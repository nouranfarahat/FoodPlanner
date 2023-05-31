package com.example.foodplanner.categories.presenter;

import com.example.foodplanner.categories.view.CategoryViewInterface;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.IngredientModel;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.RepositoryInterface;
import com.example.foodplanner.network.NetworkDelegate;

import java.util.List;

public class CategoryPresenter implements NetworkDelegate {

    CategoryViewInterface categoryViewInterface;
    RepositoryInterface repositoryInterface;

    public CategoryPresenter(CategoryViewInterface categoryViewInterface, RepositoryInterface repositoryInterface) {
        this.categoryViewInterface = categoryViewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public void onSuccessMeal(List<Meal> mealsList) {
        categoryViewInterface.viewCategoryMeals(mealsList);
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
    public void getCategoryMealsByName(String categoryName)
    {
        repositoryInterface.getCategoryMeals(this,categoryName);
    }

}
