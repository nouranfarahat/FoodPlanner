package com.example.foodplanner.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.example.foodplanner.R;
import com.example.foodplanner.database.ConcreteLocalSource;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.IngredientModel;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.MealClient;
import com.example.foodplanner.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchViewInterface,OnSearchClickListener{
    RecyclerView searchRecyclerView;
    SearchIngredientAdapter ingredientAdapter;
    SearchCategoryAdapter categoryAdapter;
    SearchCountryAdapter countryAdapter;
    SearchMealAdapter searchMealAdapter;
    SearchPresenter searchPresenter;
    ToggleButton countryBtn,ingredientBtn,categoryBtn;
    RadioGroup radioGroup;

    GridLayoutManager searchGridLayoutManager;
    public SearchFragment() {
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchRecyclerView=view.findViewById(R.id.searchRV);
        radioGroup=view.findViewById(R.id.toggle_group);
        countryBtn=view.findViewById(R.id.countriesBtn);
        ingredientBtn=view.findViewById(R.id.ingredientsBtn);
        categoryBtn=view.findViewById(R.id.categoriesBtn);

        searchGridLayoutManager = new GridLayoutManager(getContext(),2);

        searchGridLayoutManager.setOrientation(RecyclerView.VERTICAL);



        ingredientAdapter=new SearchIngredientAdapter(getContext(),new ArrayList<>(),this);
        categoryAdapter=new SearchCategoryAdapter(getContext(),new ArrayList<>(),this);
        countryAdapter=new SearchCountryAdapter(getContext(),new ArrayList<>(),this);
        searchMealAdapter=new SearchMealAdapter(getContext(),new ArrayList<>(),this);

        searchPresenter=new SearchPresenter( Repository.getInstance(ConcreteLocalSource.getInstance(getContext()), MealClient.getInstance(getContext()),getContext()), SearchFragment.this);

        searchRecyclerView.setHasFixedSize(true);

        searchRecyclerView.setLayoutManager(searchGridLayoutManager);

        searchRecyclerView.setVisibility(View.INVISIBLE);

        /*radioGroup.check(R.id.ingredientsBtn);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                ToggleButton toggleButton=group.findViewById(checkedId);
                if (checkedId == R.id.ingredientsBtn) {
                    searchPresenter.getIngredientMeals();
                } else if (checkedId == R.id.countriesBtn) {
                    searchPresenter.getCountry();
                } else if (checkedId == R.id.categoriesBtn) {
                    searchPresenter.getMealsCategory();
                }
            }
        });*/
        ingredientBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.toggle_style));
        countryBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.toggle_style));
        categoryBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.toggle_style));

        ingredientBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    searchPresenter.getIngredientMeals();
                    categoryBtn.setChecked(false);
                    countryBtn.setChecked(false);

                    // Do something when the toggle button is checked
                } else {
                    // Do something when the toggle button is unchecked
                }
            }
        });
        countryBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    searchPresenter.getCountry();
                    ingredientBtn.setChecked(false);
                    categoryBtn.setChecked(false);


                    // Do something when the toggle button is checked
                } else {
                    // Do something when the toggle button is unchecked
                }
            }
        });
        categoryBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    searchPresenter.getMealsCategory();
                    ingredientBtn.setChecked(false);
                    countryBtn.setChecked(false);
                    // Do something when the toggle button is checked
                } else {
                    // Do something when the toggle button is unchecked
                }
            }
        });
    }

    @Override
    public void viewIngredients(List<Ingredient> ingredientList) {

        searchRecyclerView.setVisibility(View.VISIBLE);

        searchRecyclerView.setAdapter(ingredientAdapter);
        ingredientAdapter.setList(ingredientList);
        categoryAdapter.notifyDataSetChanged();


    }

    @Override
    public void viewCategories(List<Category> categoryList) {

        searchRecyclerView.setVisibility(View.VISIBLE);

        searchRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.setList(categoryList);
        categoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewCountries(List<Country> countryList) {
        searchRecyclerView.setVisibility(View.VISIBLE);

        searchRecyclerView.setAdapter(countryAdapter);
        countryAdapter.setList(countryList);
        countryAdapter.notifyDataSetChanged();


    }

    @Override
    public void viewMeals(List<Meal> mealsList) {
        searchRecyclerView.setVisibility(View.VISIBLE);

        searchRecyclerView.setAdapter(searchMealAdapter);
        searchMealAdapter.setList(mealsList);
        searchMealAdapter.notifyDataSetChanged();
    }

    @Override
    public void categoryItemOnClick(String category) {
        searchPresenter.getCategoryMealsByName(category);


    }

    @Override
    public void countryItemOnClick(String country) {
        searchPresenter.getCountryMealsByName(country);

    }

    @Override
    public void ingredientItemOnclick(String ingredient) {
        searchPresenter.getMealsByIngredient(ingredient);

    }

    @Override
    public void onFavClick(Meal meal) {
        searchPresenter.addToFav(meal);
    }
}