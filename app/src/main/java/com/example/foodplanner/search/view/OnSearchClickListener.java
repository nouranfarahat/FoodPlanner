package com.example.foodplanner.search.view;

import com.example.foodplanner.model.Meal;

public interface OnSearchClickListener {
    public void categoryItemOnClick(String category);
    public void countryItemOnClick(String country);
    public void ingredientItemOnclick(String ingredient);
    public void onFavClick(Meal meal);
}
