package com.example.foodplanner.mealdetails.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.database.ConcreteLocalSource;
import com.example.foodplanner.mealdetails.presenter.MealDetailsPresenter;
import com.example.foodplanner.model.IngredientModel;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.MealClient;
import com.example.foodplanner.utilities.OnFavoriteClickListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealDetailsFragment extends Fragment implements MealViewInterface, OnFavoriteClickListener {
    TextView mealName;
    TextView countryName;
    YouTubePlayerView videoView ;
    ImageView mealImage;
    ToggleButton favButton;
    TextView mealSteps;
    FloatingActionButton planBtn;
    RecyclerView ingredientRecyclerView;
    IngredientAdapter ingredientAdapter;
    MealDetailsPresenter mealDetailsPresenter;
    LinearLayoutManager ingredientLayoutManager;
    List<IngredientModel> ingredientList;
    public static final String PREF_NAME="APPINFO";


    int selectedOptionIndex = -1;

    boolean[] daysItems = new boolean[]{false, false, false, false, false, false, false};

    // Create a list of the checkbox options
    final String[] items = new String[]{"Saturday","Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    public MealDetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingredientRecyclerView=view.findViewById(R.id.ingredientRV);
        mealName=view.findViewById(R.id.mealName);
        countryName=view.findViewById(R.id.countryName);
        mealImage=view.findViewById(R.id.mealImageView);
        mealSteps=view.findViewById(R.id.stepsTextView);
        videoView=view.findViewById(R.id.youtube_player_view);
        planBtn= view.findViewById(R.id.planFAB);
        favButton=view.findViewById(R.id.favoriteButtonIcon);
        String mealNameFromArgs= MealDetailsFragmentArgs.fromBundle(getArguments()).getMealName();
        mealName.setText(mealNameFromArgs);
        ingredientList=new ArrayList<>();
        //ingredientItemList=new ArrayList<>();
//        ingredientItemImageList=new ArrayList<>();
//        ingredientItemMeasureList=new ArrayList<>();

        ingredientLayoutManager = new LinearLayoutManager(view.getContext());

        ingredientLayoutManager.setOrientation(RecyclerView.VERTICAL);

        ingredientAdapter=new IngredientAdapter(getContext(),new ArrayList<>());


        mealDetailsPresenter=new MealDetailsPresenter( Repository.getInstance(ConcreteLocalSource.getInstance(getContext()),MealClient.getInstance(getContext()),getContext()), MealDetailsFragment.this);

        ingredientRecyclerView.setHasFixedSize(true);
        ingredientRecyclerView.setLayoutManager(ingredientLayoutManager);

        ingredientRecyclerView.setAdapter(ingredientAdapter);

        /*if (isNetworkAvailable(getContext())) {
            mealDetailsPresenter.getMealFromRepo(mealNameFromArgs);
            Log.i("TAG", "onCreate: " + mealNameFromArgs);

        } else if (typeTable.equals("favourite")) {
            Log.i("TAG", "onCreate: favourite table favourite meaal is presented");
            presenter.getOffMealDetail(mealName).observe(this, new Observer<MealDetails>() {
                @Override
                public void onChanged(MealDetails mealDetail) {
                    mealResponse = mealDetail;
                    MealName.setText(mealDetail.getMealName());
                    MealCountry.setText(mealDetail.getStrArea());
                    createIngredientList(mealDetail);
                    adapter = new IngredientsAdapter(getApplicationContext(), myIngredients);
                    recyclerView.setAdapter(adapter);
                    String thumb = "https://www.themealdb.com/images/ingredients/" + mealDetail.getStrIngredient1() + ".png";
                    myIngredients.add(new Recipe(mealDetail.getStrIngredient1(), mealDetail.getStrMeasure1(), thumb));
                    adapter = new IngredientsAdapter(getApplicationContext(), myIngredients);
                    recyclerView.setAdapter(adapter);
                    Glide.with(getApplicationContext())
                            .load(mealDetail.getStrMealThumb())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground)
                            .into(mealPic);
                    steps.setText(mealDetail.getStrInstructions());


                }
            });

        }*/
        mealDetailsPresenter.getMealFromRepo(mealNameFromArgs);

        /*planBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build the alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select Options");
                builder.setSingleChoiceItems(items, selectedOptionIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Update the selectedOptionIndex variable when aradio button is clicked
                        selectedOptionIndex = which;
                    }
                });

                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the OK button click
                        // Loop through the checkedItems array to get the selected options
                        for (int i = 0; i < daysItems.length; i++) {
                            if (daysItems[i]) {
                                // The ith option is selected
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the Cancel button click
                    }
                });

                // Show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });*/
    }


    @Override
    public void viewMealDetails(List<Meal> mealList) {
        Meal meal=mealList.get(0);
        countryName.setText(meal.getStrArea());
        mealSteps.setText(meal.getStrInstructions());
        Glide.with(this)
                .load(meal.getStrMealThumb()).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(mealImage);
        videoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                youTubePlayer.loadVideo(getYouTubeId(meal.getStrYoutube()), 0);
            }
        });
        getIngredientObjList(meal);
        ingredientAdapter.setList(ingredientList);
        ingredientAdapter.notifyDataSetChanged();

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavClick(meal);
            }
        });
        planBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build the alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select Options");
                builder.setSingleChoiceItems(items, selectedOptionIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Update the selectedOptionIndex variable when aradio button is clicked
                        selectedOptionIndex = which;
                    }
                });

                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the OK button click
                        // Loop through the checkedItems array to get the selected options
                        if (selectedOptionIndex != -1) {
                            System.out.println("I am in day: "+items[selectedOptionIndex]+" with meal: "+meal.getStrMeal());
                            Toast.makeText(getContext(),"Meal is added to "+items[selectedOptionIndex],Toast.LENGTH_LONG).show();
                            meal.setDaysList(items[selectedOptionIndex]);
                            mealDetailsPresenter.addMealToPlan(meal);
                            addMealsToFireBase(meal);

                        }
                       /* for (int i = 0; i < daysItems.length; i++) {
                            if (daysItems[i]) {
                                System.out.println("I am in day: "+daysItems[i]+" with meal: "+meal.getStrMeal());
                                Toast.makeText(getContext(),meal.getStrMeal(),Toast.LENGTH_LONG).show();
                            }
                        }*/
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the Cancel button click
                    }
                });

                // Show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public void addMealToPlan(Meal meal) {

    }

    private String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }

    private List<String> getIngredientItemList(Meal mealItem)
    {
        List<String> ingredientItemList=new ArrayList<>();;
        for(int i=1;i<=20;i++)
        {
            try {
                Method method=Meal.class.getMethod("getStrIngredient"+i);
                String ingredientResult = (String) method.invoke(mealItem);

                if(ingredientResult!=null&&!ingredientResult.isEmpty())
                {
                    System.out.println("ingredient "+i+" "+ingredientResult);
                    ingredientItemList.add(ingredientResult);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return ingredientItemList;
    }
    private List<String> getIngredientItemMeasureList(Meal mealItem)
    {
        List<String> ingredientItemMeasureList=new ArrayList<>();
        for(int i=1;i<=20;i++)
        {
            try {
                Method method=Meal.class.getMethod("getStrMeasure"+i);
                String ingredientMeasureResult = (String) method.invoke(mealItem);

                if(ingredientMeasureResult!=null&&!ingredientMeasureResult.isEmpty())
                {
                    System.out.println("measure "+i+" "+ingredientMeasureResult);
                    ingredientItemMeasureList.add(ingredientMeasureResult);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return  ingredientItemMeasureList;

    }
    private List<String> getIngredientItemImageList(List<String> ingredientItemList)
    {
        List<String> ingredientItemImageList=new ArrayList<>();
        for(String item:ingredientItemList)
        {
            ingredientItemImageList.add("https://www.themealdb.com/images/ingredients/"+item+".png");
            System.out.println("https://www.themealdb.com/images/ingredients/"+item+".png");
        }
        return  ingredientItemList;

    }

    private void getIngredientObjList(Meal meal)
    {
        List<String> ingredientItemList=getIngredientItemList(meal);
        List<String> ingredientItemMeasureList=getIngredientItemMeasureList(meal);
        List<String> ingredientItemImageList=getIngredientItemImageList(ingredientItemList);
        for(int i=0;i<ingredientItemList.size();i++)
        {
            ingredientList.add(new IngredientModel(ingredientItemList.get(i),ingredientItemImageList.get(i),ingredientItemMeasureList.get(i)));
        }
    }

    @Override
    public void onFavClick(Meal meal) {
        meal.setFavorite(true);
        mealDetailsPresenter.addToFav(meal);
    }
    public void addMealsToFireBase(Meal meal)
    {
        //DatabaseReference planRef=FirebaseDatabase.getInstance().getReference();//= FirebaseDatabase.getInstance().getReference().child("User");
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(getContext());
        SharedPreferences pref=getContext().getSharedPreferences(PREF_NAME,0);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference userRef=FirebaseDatabase.getInstance().getReference().child("User").child(uid);//= FirebaseDatabase.getInstance().getReference().child("User");
        DatabaseReference mealsRef = userRef.child("Meals");
        String newNodeKey = mealsRef.push().getKey();
        DatabaseReference newNodeRef = mealsRef.child(meal.getIdMeal());

        //String key=pref.getString("ID","unknown");
        //String key= account.getIdToken();
//        if(key.equals(account.getIdToken()))
//        {
//            planRef.push().setValue(meal);
//        }
        newNodeRef.setValue(meal)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Object added successfully
                        Log.d("MEALDETAILS", "Object added successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Object failed to be added
                        Log.w("MEALDETAILS", "Error adding object", e);
                    }
                });
        /*planRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(key)){
                    planRef.child(key).child("meals").child(meal.getIdMeal()).setValue(meal);
                    Log.i("TAG", "onDataChange: meal added to fire base");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG", "onDataChange: meal does not added to fire base");

            }
        });*/
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}