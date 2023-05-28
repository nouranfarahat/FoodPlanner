package com.example.foodplanner.network;

import com.example.foodplanner.model.Country;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
    public Call<MealResponse> getRandomMeals();

    @GET("filter.php?")
    public Call<MealResponse> getMealByIngredient(@Query("i") String ingredient );
    @GET("categories.php")
    public Call<CategoryResponse> getMealCategory();

    @GET("list.php?a=list")
    public Call<CountryResponse> getCountries();
    @GET("filter.php")
    public Call<MealResponse> getCountryMeals(@Query("a") String CountryName);
}
