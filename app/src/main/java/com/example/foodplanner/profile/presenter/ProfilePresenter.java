package com.example.foodplanner.profile.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.favorite.view.FavViewInterface;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.RepositoryInterface;
import com.example.foodplanner.planner.view.PlanViewInterface;
import com.example.foodplanner.profile.view.ProfileViewInterface;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ProfilePresenter {
    RepositoryInterface repositoryInterface;
    ProfileViewInterface profileViewInterface;


    public ProfilePresenter(ProfileViewInterface profileViewInterface, RepositoryInterface repositoryInterface) {
        this.profileViewInterface = profileViewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public LiveData<List<Meal>> getAllMeals()
    {
        return repositoryInterface.getAllMeal();
    }

    public void deleteAllMeals()

    {
        repositoryInterface.deleteAllMeal();
    }


//    public void addMealsToFireBase()
//    {
//        DatabaseReference planRef= FirebaseDatabase.getInstance().getReference().child("User");
//        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(getContext());
//    }
}
