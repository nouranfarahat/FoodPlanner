package com.example.foodplanner.network;

import com.example.foodplanner.model.Meal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealClient implements RemoteSource {
    private static final String BASE_URL = "www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "MEAL_CLIENT";
    private static MealClient instance=null;
    ProductService productService;

    public MealClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productService = retrofit.create(ProductService.class);
    }
    public  static MealClient getInstance()
    {
        if(instance==null)
            instance=new MealClient();

        return instance;
    }


    @Override
    public void enqueueCall(NetworkDelegate networkDelegate)
    {
        Call<MealResponse> call = productService.getAllMeals();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful()&&response.body()!=null)
                    networkDelegate.onSuccessResponse(response.body().getMeals());

            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkDelegate.onFailureResponse(t.getMessage());

            }
        });
    }
}
