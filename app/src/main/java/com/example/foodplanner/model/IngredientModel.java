package com.example.foodplanner.model;

public class IngredientModel {
    private String ingredientName;
    private String ingredientImg;
    private String ingredientMeasure;

    public IngredientModel(String ingredientName, String ingredientImg, String ingredientMeasure) {
        this.ingredientName = ingredientName;
        this.ingredientImg = ingredientImg;
        this.ingredientMeasure = ingredientMeasure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientImg() {
        return ingredientImg;
    }

    public void setIngredientImg(String ingredientImg) {
        this.ingredientImg = ingredientImg;
    }

    public String getIngredientMeasure() {
        return ingredientMeasure;
    }

    public void setIngredientMeasure(String ingredientMeasure) {
        this.ingredientMeasure = ingredientMeasure;
    }
}
