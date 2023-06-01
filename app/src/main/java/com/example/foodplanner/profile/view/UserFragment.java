package com.example.foodplanner.profile.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodplanner.R;
import com.example.foodplanner.database.ConcreteLocalSource;
import com.example.foodplanner.favorite.presenter.FavoritePresenter;
import com.example.foodplanner.favorite.view.FavoriteFragment;
import com.example.foodplanner.mainscreen.MainActivity;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.MealClient;
import com.example.foodplanner.profile.presenter.ProfilePresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserFragment extends Fragment implements ProfileViewInterface{
    TextView nameTextView;
    TextView emailTextView;
    Button logoutButton;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    ProfilePresenter profilePresenter;
    public static final String PREF_NAME="APPINFO";


    public UserFragment() {
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
        return inflater.inflate(R.layout.fragment_user, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameTextView=view.findViewById(R.id.userNameTV);
        emailTextView=view.findViewById(R.id.emailTV);
        logoutButton=view.findViewById(R.id.logOutBtn);
        profilePresenter=new ProfilePresenter(UserFragment.this, Repository.getInstance(ConcreteLocalSource.getInstance(getContext()), MealClient.getInstance(getContext()),getContext()));

        SharedPreferences pref=getContext().getSharedPreferences(PREF_NAME,0);

            String user=pref.getString("USERNAME","unknown");
            String email=pref.getString("EMAIL","email");
            System.out.println("I am "+user+" email "+email);


        googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(getContext(),googleSignInOptions);
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(getContext());
        if(account!=null)
        {
            nameTextView.setText(account.getDisplayName());
            emailTextView.setText(account.getEmail());
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveData<List<Meal>> mealsList=profilePresenter.getAllMeals();
                //addMealsToFireBase(mealsList);
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("LOGIN","NO");
                editor.commit();
                profilePresenter.deleteAllMeals();
                signOut();
            }
        });


    }
    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                UserFragment myFragment = new UserFragment();
                //FragmentManager fragmentManager = getFragmentManager();

                if (myFragment != null) {
                    assert getFragmentManager() != null;
                    getFragmentManager().beginTransaction().remove(myFragment).commit();
                }
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
    }
//    public void addMealsToFireBase(LiveData<List<Meal>> meals)
//    {
//        //DatabaseReference planRef=FirebaseDatabase.getInstance().getReference();//= FirebaseDatabase.getInstance().getReference().child("User");
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        DatabaseReference userRef=FirebaseDatabase.getInstance().getReference().child("User").child(uid);//= FirebaseDatabase.getInstance().getReference().child("User");
//        meals.observe(this, new Observer<List<Meal>>() {
//            @Override
//            public void onChanged(List<Meal> meal) {
//
//                for(Meal mealItem:meal){
//                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();//= FirebaseDatabase.getInstance().getReference().child("User");
//
//
//                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.hasChild(uid)){
//                            databaseReference.child(uid).child("meals").child(mealItem.getIdMeal()).setValue(mealItem);
//                            Log.i("TAG", "onDataChange: meal added to fire base");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Log.i("TAG", "onDataChange: meal does not added to fire base");
//
//                    }
//                });}
//                /*userRef.child("Meals").setValue(meal)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                // Object added successfully
//                                Log.d("MEALDETAILS", "Object added successfully");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Object failed to be added
//                                Log.w("MEALDETAILS", "Error adding object", e);
//                            }
//                        });*/
//            }
//        });
//        /*DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();//= FirebaseDatabase.getInstance().getReference().child("User");
//
//
//        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.hasChild(uid)){
//                    databaseReference.child(uid).child("meals").child(meal.getIdMeal()).setValue(meal);
//                    Log.i("TAG", "onDataChange: meal added to fire base");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.i("TAG", "onDataChange: meal does not added to fire base");
//
//            }
//        });*/
//
//
//    }


}