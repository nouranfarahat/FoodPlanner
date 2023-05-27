package com.example.foodplanner.network;

import com.example.foodplanner.model.Meal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {
    @GET("random.php")
    public Call<MealResponse> getRandomMeals();

    @GET("filter.php?")
    public Call<MealResponse> getMealByIngredient(@Query("i") String ingredient );
}
