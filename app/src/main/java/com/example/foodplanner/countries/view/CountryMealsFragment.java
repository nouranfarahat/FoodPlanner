package com.example.foodplanner.countries.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodplanner.R;
import com.example.foodplanner.countries.presenter.CountryPresenter;
import com.example.foodplanner.database.ConcreteLocalSource;
import com.example.foodplanner.home.presenter.HomePresenter;
import com.example.foodplanner.home.view.CategoryMealsAdapter;
import com.example.foodplanner.home.view.CountryAdapter;
import com.example.foodplanner.home.view.HomeFragment;
import com.example.foodplanner.home.view.OnFavoriteClickListener;
import com.example.foodplanner.home.view.RandomMealsAdapter;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.MealClient;

import java.util.ArrayList;
import java.util.List;


public class CountryMealsFragment extends Fragment implements CountryViewInterface, OnFavoriteClickListener {

    TextView countryName;
    RecyclerView mealListRecyclerView;
    CountryMealAdapter countryMealAdapter;
    GridLayoutManager mealGridLayoutManager;
    CountryPresenter countryPresenter;

    public CountryMealsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countryName=view.findViewById(R.id.countryNameTV);
        mealListRecyclerView=view.findViewById(R.id.countryMealsRV);
        String name=CountryMealsFragmentArgs.fromBundle(getArguments()).getSelectedCountryName();
        countryName.setText(name);


        mealGridLayoutManager=new GridLayoutManager(getContext(),2);

        mealGridLayoutManager.setOrientation(RecyclerView.VERTICAL);



        countryMealAdapter=new CountryMealAdapter(getContext(),new ArrayList<>(),this);

        countryPresenter=new CountryPresenter(CountryMealsFragment.this,Repository.getInstance(ConcreteLocalSource.getInstance(getContext()),MealClient.getInstance(getContext()),getContext()));

        mealListRecyclerView.setHasFixedSize(true);

        mealListRecyclerView.setLayoutManager(mealGridLayoutManager);

        mealListRecyclerView.setAdapter(countryMealAdapter);

        countryPresenter.getCountryMealsByName(name);


    }

    @Override
    public void viewCountryMeals(List<Meal> meals) {
        countryMealAdapter.setList(meals);
        countryMealAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFavClick(Meal meal) {

    }
}