package com.example.foodplanner.network;

public interface RemoteSource {
    public void getRandomMeal(NetworkDelegate networkDelegate);
    public void getCategories(NetworkDelegate networkDelegate);
    public void getCountries(NetworkDelegate networkDelegate);
    public void getMealsByCountry(NetworkDelegate networkDelegate,String countryName);
    public void getMealDetails(NetworkDelegate networkDelegate,String mealName);



}
