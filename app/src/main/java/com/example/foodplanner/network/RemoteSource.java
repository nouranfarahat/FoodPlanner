package com.example.foodplanner.network;

public interface RemoteSource {
    public void getRandomMeal(NetworkDelegate networkDelegate);
    public void getCategories(NetworkDelegate networkDelegate);



}
