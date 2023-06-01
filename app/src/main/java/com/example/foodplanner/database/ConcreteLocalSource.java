package com.example.foodplanner.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;

import java.util.List;

public class ConcreteLocalSource implements LocalSource{

    private MealDao mealDAO;
    public static ConcreteLocalSource concreteLocalSourceInstance=null;
    private LiveData<List<Meal>> mealsList;
    private LiveData<List<Meal>> favMealsList;
    private LiveData<List<Meal>> planMealsList;



    public ConcreteLocalSource(Context context) {
        AppDatabase appDataBase=AppDatabase.getInstance(context.getApplicationContext());
        mealDAO=appDataBase.productDAO();
        //mealsList=mealDAO.getAllMealss();
        favMealsList=mealDAO.getFavMeals();
    }

    public static ConcreteLocalSource getInstance(Context context)
    {
        if(concreteLocalSourceInstance==null)
            concreteLocalSourceInstance=new ConcreteLocalSource(context);
        return concreteLocalSourceInstance;
    }

    @Override
    public void insertMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.insertMeal(meal);
            }
        }).start();
    }

    @Override
    public void removeFavMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deleteFavMeal(meal);
            }
        }).start();
    }

    @Override
    public void removePlanMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deletePlanMeal(meal);
            }
        }).start();
    }

    @Override
    public LiveData<List<Meal>> getMealsList() {
        return mealDAO.getAllMeals();
    }

    @Override
    public LiveData<List<Meal>> getFavList() {
        return mealDAO.getFavMeals();
    }

    @Override
    public LiveData<List<Meal>> getPlanList(String day) {
        LiveData<List<Meal>> planMeals =mealDAO.getPlanMeals(day);
        return planMeals;

    }

    @Override
    public void deleteAllMeals() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deleteAllMeals();
            }
        }).start();

    }

//    @Override
//    public LiveData<List<Meal>> getMealsList() {
//        return mealsList;
//    }
}
