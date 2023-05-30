package com.example.foodplanner.favorite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.example.foodplanner.countries.view.CountryMealAdapter;
import com.example.foodplanner.database.ConcreteLocalSource;
import com.example.foodplanner.favorite.presenter.FavoritePresenter;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.MealClient;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements OnFavoriteClickListener,FavViewInterface {

    RecyclerView recyclerView;
    FavoriteAdapter adapter;
    FavoritePresenter favoritePresenter;
    LinearLayoutManager linearLayoutManager;
    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.favMealsRV);


        linearLayoutManager=new LinearLayoutManager(getContext());

        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        favoritePresenter=new FavoritePresenter(FavoriteFragment.this,Repository.getInstance(ConcreteLocalSource.getInstance(getContext()), MealClient.getInstance(getContext()),getContext()));

        adapter=new FavoriteAdapter(getContext(),new ArrayList<>(),this);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);

        favoritePresenter.getAllProducts();
    }

    @Override
    public void showFavMeals(LiveData<List<Meal>> meals) {

        meals.observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                adapter.setList(meals);
                //adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void deleteMeal(Meal meal) {
        favoritePresenter.deleteFromFav(meal);

    }

    @Override
    public void onFavClick(Meal meal) {

        deleteMeal(meal);
        adapter.notifyDataSetChanged();
    }
}