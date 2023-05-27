package com.example.foodplanner.network;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealClient implements RemoteSource {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "MEAL_CLIENT";
    private static MealClient instance=null;
    MealService mealService;

    public MealClient(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);
    }
    public  static MealClient getInstance(Context context)
    {
        if(instance==null)
            instance=new MealClient(context);

        return instance;
    }


    @Override
    public void getRandomMeal(NetworkDelegate networkDelegate) {

        Call<MealResponse> call = mealService.getRandomMeals();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful()&&response.body()!=null)
                    networkDelegate.onSuccessMeal(response.body().getMeals());
                Log.i(TAG,"getRandom success");
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onFailureResponse("Error in Failure "+t.getMessage());

            }
        });
    }

    @Override
    public void getCategories(NetworkDelegate networkDelegate) {

        Call<CategoryResponse> call = mealService.getMealCategory();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful()&&response.body()!=null)
                    networkDelegate.onSuccessCategories(response.body().getCategories());
                Log.i(TAG,"getCategories success");
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                networkDelegate.onFailureResponse(t.getMessage());

            }
        });
    }
}
