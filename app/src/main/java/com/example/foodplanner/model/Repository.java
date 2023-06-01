package com.example.foodplanner.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.database.LocalSource;
import com.example.foodplanner.network.NetworkDelegate;
import com.example.foodplanner.network.RemoteSource;

import java.util.List;

public class Repository implements RepositoryInterface{
    public static Repository repository=null;
    private Context context;
    RemoteSource remoteSource;
    LocalSource localSource;

    public Repository(Context context, RemoteSource remoteSource, LocalSource localSource) {
        this.context = context;
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    public static Repository getInstance(LocalSource lSource,RemoteSource rSource,Context cont)
    {
        if(repository==null)
            repository=new Repository(cont,rSource,lSource);
        return repository;
    }

    @Override
    public void getRandomMeal(NetworkDelegate networkDelegate) {
        remoteSource.getRandomMeal(networkDelegate);

    }

    @Override
    public void getMealCategory(NetworkDelegate networkDelegate) {
        remoteSource.getCategories(networkDelegate);
    }

    @Override
    public void getCountry(NetworkDelegate networkDelegate) {
        remoteSource.getCountries(networkDelegate);
    }

    @Override
    public void getIngredients(NetworkDelegate networkDelegate) {
        remoteSource.getIngredients(networkDelegate);
    }

    @Override
    public void getCountryMeals(NetworkDelegate networkDelegate, String countryName) {
        remoteSource.getMealsByCountry(networkDelegate,countryName);
    }

    @Override
    public void getCategoryMeals(NetworkDelegate networkDelegate, String categoryName) {
        remoteSource.getMealsByCategory(networkDelegate,categoryName);

    }

    @Override
    public void getIngredientMeals(NetworkDelegate networkDelegate, String ingredientName) {
remoteSource.getMealsByIngredient(networkDelegate,ingredientName);
    }

    @Override
    public void getMealDetails(NetworkDelegate networkDelegate, String mealName) {
        remoteSource.getMealDetails(networkDelegate,mealName);
    }

    @Override
    public void insertMeal(Meal meal) {
        localSource.insertMeal(meal);
    }

    @Override
    public void removeFavMeal(Meal meal) {
        localSource.removeFavMeal(meal);
    }

    @Override
    public void removePlanMeal(Meal meal) {
        localSource.removePlanMeal(meal);
    }

    @Override
    public LiveData<List<Meal>> getFavMealsList() {
        return localSource.getFavList();
    }

    @Override
    public LiveData<List<Meal>> getPlanMealsList(String day) {
        return localSource.getPlanList(day);
    }

    @Override
    public LiveData<List<Meal>> getAllMeal( ) {
        return localSource.getMealsList();
    }

    @Override
    public void deleteAllMeal() {
        localSource.deleteAllMeals();
    }


}
