package com.example.foodplanner.favorite.presenter;

import com.example.foodplanner.favorite.view.FavViewInterface;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.RepositoryInterface;

public class FavoritePresenter {
    FavViewInterface favViewInterface;
    RepositoryInterface repositoryInterface;

    public FavoritePresenter(FavViewInterface favViewInterface, RepositoryInterface repositoryInterface) {
        this.favViewInterface = favViewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public void getAllProducts()
    {
        favViewInterface.showFavMeals(repositoryInterface.getFavMealsList());
    }

    public void deleteFromFav(Meal meal)
    {
        repositoryInterface.removeMeal(meal);
    }
}
