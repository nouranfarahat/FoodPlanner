package com.example.foodplanner.network;

import com.example.foodplanner.model.Meal;

import java.util.List;

public interface NetworkDelegate {
    public void onSuccessResponse(List<Meal> products);
    public void onFailureResponse(String errorMsg);

}
