package com.example.foodplanner.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;

import java.util.List;

public class ConcreteLocalSource implements LocalSource{

    private MealDao mealDAO;
    public static ConcreteLocalSource concreteLocalSourceInstance=null;
    private LiveData<List<Meal>> mealsList;

    public ConcreteLocalSource(Context context) {
        AppDatabase appDataBase=AppDatabase.getInstance(context.getApplicationContext());
        mealDAO=appDataBase.productDAO();
        mealsList=mealDAO.getAllMealss();

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
    public void removeMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDAO.deleteMeal(meal);
            }
        }).start();
    }

    @Override
    public LiveData<List<Meal>> getMealsList() {
        return mealsList;
    }
}
