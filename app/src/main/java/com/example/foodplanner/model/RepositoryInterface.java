package com.example.foodplanner.model;

import com.example.foodplanner.network.NetworkDelegate;

public interface RepositoryInterface {
    public void getRandomMeal(NetworkDelegate networkDelegate);
    public void getMealCategory(NetworkDelegate networkDelegate);
    public void getCountry(NetworkDelegate networkDelegate);
    public void getCountryMeals(NetworkDelegate networkDelegate,String countryName);



}
