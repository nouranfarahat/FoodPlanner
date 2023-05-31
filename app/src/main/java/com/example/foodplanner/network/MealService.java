package com.example.foodplanner.network;

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
    @GET("list.php?i=list")
    public Call<IngredientResponse> getngredients();
    @GET("filter.php")
    public Call<MealResponse> getCountryMeals(@Query("a") String CountryName);
    @GET("filter.php")
    public Call<MealResponse> getCategoryMeals(@Query("c") String categotyName);
    @GET("filter.php")
    public Call<MealResponse> getIngredientMeals(@Query("i") String ingredientName);
    @GET("search.php")
    public Call<MealResponse> getMealDetails(@Query("s") String mealName);
}
