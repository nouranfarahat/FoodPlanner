package com.example.foodplanner.categories.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.categories.presenter.CategoryPresenter;
import com.example.foodplanner.countries.view.CountryMealsFragmentArgs;
import com.example.foodplanner.database.ConcreteLocalSource;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.MealClient;
import com.example.foodplanner.utilities.OnFavoriteClickListener;

import java.util.ArrayList;
import java.util.List;


public class CategoryMealsFragment extends Fragment implements CategoryViewInterface, OnFavoriteClickListener {

    TextView categoryName;
    RecyclerView mealListRecyclerView;
    CategoryMealAdapter categoryMealAdapter;
    GridLayoutManager mealGridLayoutManager;
    CategoryPresenter categoryPresenter;

    public CategoryMealsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryName=view.findViewById(R.id.categoryNameTV);
        mealListRecyclerView=view.findViewById(R.id.categoryMealsRV);
        String name= CategoryMealsFragmentArgs.fromBundle(getArguments()).getSelectedCategoryName();
        categoryName.setText(name);


        mealGridLayoutManager=new GridLayoutManager(getContext(),2);

        mealGridLayoutManager.setOrientation(RecyclerView.VERTICAL);



        categoryMealAdapter=new CategoryMealAdapter(getContext(),new ArrayList<>(),this);

        categoryPresenter=new CategoryPresenter(CategoryMealsFragment.this,Repository.getInstance(ConcreteLocalSource.getInstance(getContext()),MealClient.getInstance(getContext()),getContext()));

        mealListRecyclerView.setHasFixedSize(true);

        mealListRecyclerView.setLayoutManager(mealGridLayoutManager);

        mealListRecyclerView.setAdapter(categoryMealAdapter);

        categoryPresenter.getCategoryMealsByName(name);


    }


    @Override
    public void onFavClick(Meal meal) {

    }

    @Override
    public void viewCategoryMeals(List<Meal> meals) {
        categoryMealAdapter.setList(meals);
        categoryMealAdapter.notifyDataSetChanged();
    }
}