package com.example.foodplanner.home.view;

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
import android.widget.Button;
import android.widget.TextView;

import com.example.foodplanner.R;
import com.example.foodplanner.database.ConcreteLocalSource;
import com.example.foodplanner.home.presenter.HomePresenter;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.MealClient;
import com.example.foodplanner.network.NetworkDelegate;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements HomeViewInterface,OnFavoriteClickListener {
    /*TextView nameTextView;
    TextView emailTextView;
    Button logoutButton;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
*/
    RecyclerView randomRecyclerView,categoryRecyclerView,countryRecyclerView;
    RandomMealsAdapter randomAdapter;
    CategoryMealsAdapter categoryAdapter;
    CountryAdapter countryAdapter;
    HomePresenter homePresenter;
    LinearLayoutManager mealLayoutManager,categoryLayoutManager,countryLayoutManager;
    GridLayoutManager categoryGridLayoutManager;
    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        randomRecyclerView=view.findViewById(R.id.daily_inspirationRV);
        categoryRecyclerView=view.findViewById(R.id.categoriesRV);
        countryRecyclerView=view.findViewById(R.id.countriesRV);


        mealLayoutManager = new LinearLayoutManager(view.getContext());
        //categoryLayoutManager = new LinearLayoutManager(view.getContext());
        countryLayoutManager = new LinearLayoutManager(view.getContext());
        categoryGridLayoutManager=new GridLayoutManager(getContext(),2);


        mealLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        //categoryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        countryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryGridLayoutManager.setOrientation(RecyclerView.VERTICAL);



        randomAdapter=new RandomMealsAdapter(getContext(),new ArrayList<>(),this);
        categoryAdapter=new CategoryMealsAdapter(getContext(),new ArrayList<>());
        countryAdapter=new CountryAdapter(getContext(),new ArrayList<>());


        homePresenter=new HomePresenter( Repository.getInstance(ConcreteLocalSource.getInstance(getContext()),MealClient.getInstance(getContext()),getContext()), HomeFragment.this);

        randomRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setHasFixedSize(true);
        countryRecyclerView.setHasFixedSize(true);



        randomRecyclerView.setLayoutManager(mealLayoutManager);
        //categoryRecyclerView.setLayoutManager(categoryLayoutManager);
        countryRecyclerView.setLayoutManager(countryLayoutManager);
        categoryRecyclerView.setLayoutManager(categoryGridLayoutManager);

        randomRecyclerView.setAdapter(randomAdapter);
        categoryRecyclerView.setAdapter(categoryAdapter);
        countryRecyclerView.setAdapter(countryAdapter);


        homePresenter.getRandomMeals();
        homePresenter.getMealsCategory();
        homePresenter.getCountry();



        /*nameTextView=view.findViewById(R.id.nameTv);
        emailTextView=view.findViewById(R.id.emailTV);
        logoutButton=view.findViewById(R.id.logOutBtn);
        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(getContext(),googleSignInOptions);
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(getContext());
        if(account!=null)
        {
            nameTextView.setText(account.getDisplayName());
            emailTextView.setText(account.getEmail());
        }*/

        /*logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });*/
    }

    @Override
    public void viewRandomMeal(List<Meal> meals) {
        randomAdapter.setList(meals);


    }

    @Override
    public void viewCategories(List<Category> categoryList) {

        categoryAdapter.setList(categoryList);
        categoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewCountries(List<Country> countryList) {

        countryAdapter.setList(countryList);
        countryAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFavClick(Meal meal) {
        homePresenter.addToFav(meal);
    }

   /* private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }*/
}