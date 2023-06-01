package com.example.foodplanner.planner.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.RepositoryInterface;
import com.example.foodplanner.network.NetworkDelegate;
import com.example.foodplanner.planner.view.PlanViewInterface;

import java.util.List;

public class PlanPresenter {

    RepositoryInterface repositoryInterface;
    PlanViewInterface planViewInterface;


    public PlanPresenter(RepositoryInterface repositoryInterface, PlanViewInterface planViewInterface) {
        this.repositoryInterface = repositoryInterface;
        this.planViewInterface = planViewInterface;
    }


    public void addToFav(Meal meal)
    {
        repositoryInterface.insertMeal(meal);
    }
    public LiveData<List<Meal>> getAllPlanMeals(String day)
    {
        return repositoryInterface.getPlanMealsList(day);
    }

    public void deleteFromPlan(Meal meal)
    {
        repositoryInterface.removePlanMeal(meal);
    }



}
