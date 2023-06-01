package com.example.foodplanner.planner.view;

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
import com.example.foodplanner.database.ConcreteLocalSource;
import com.example.foodplanner.favorite.presenter.FavoritePresenter;
import com.example.foodplanner.favorite.view.FavoriteFragment;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.MealClient;
import com.example.foodplanner.planner.presenter.PlanPresenter;

import java.util.ArrayList;
import java.util.List;

public class PlannerFragment extends Fragment implements OnRemoveClickListener, PlanViewInterface {

    RecyclerView[] daysRecyclerViews=new RecyclerView[7];
    PlanMealsAdapter[] planMealsAdapter=new PlanMealsAdapter[7];
    LinearLayoutManager[] daysLinearLayoutManagers=new LinearLayoutManager[7];
    PlanPresenter planPresenter;
    final String[] days = new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    public PlannerFragment() {
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
        return inflater.inflate(R.layout.fragment_planner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);

        planPresenter=new PlanPresenter(Repository.getInstance(ConcreteLocalSource.getInstance(getContext()),MealClient.getInstance(getContext()),getContext()),PlannerFragment.this);

        for (int i = 0; i < 7; i++) {
            setMealsToAdapter(i);
        }
    }

    private void initUI(View view) {
        daysRecyclerViews[0] = view.findViewById(R.id.saturdayRV);
        daysRecyclerViews[1] = view.findViewById(R.id.sundayRV);
        daysRecyclerViews[2] = view.findViewById(R.id.mondayRV);
        daysRecyclerViews[3] = view.findViewById(R.id.tuesdayRV);
        daysRecyclerViews[4] = view.findViewById(R.id.wednesdayRV);
        daysRecyclerViews[5] = view.findViewById(R.id.thursdayRV);
        daysRecyclerViews[6] = view.findViewById(R.id.fridayRV);

        daysLinearLayoutManagers[0] = new LinearLayoutManager(view.getContext());
        daysLinearLayoutManagers[1] = new LinearLayoutManager(view.getContext());
        daysLinearLayoutManagers[2] = new LinearLayoutManager(view.getContext());
        daysLinearLayoutManagers[3] = new LinearLayoutManager(view.getContext());
        daysLinearLayoutManagers[4] = new LinearLayoutManager(view.getContext());
        daysLinearLayoutManagers[5] = new LinearLayoutManager(view.getContext());
        daysLinearLayoutManagers[6] = new LinearLayoutManager(view.getContext());

        planMealsAdapter[0] = new PlanMealsAdapter(getContext(), new ArrayList<>(), this);
        planMealsAdapter[1] = new PlanMealsAdapter(getContext(), new ArrayList<>(), this);
        planMealsAdapter[2] = new PlanMealsAdapter(getContext(), new ArrayList<>(), this);
        planMealsAdapter[3] = new PlanMealsAdapter(getContext(), new ArrayList<>(), this);
        planMealsAdapter[4] = new PlanMealsAdapter(getContext(), new ArrayList<>(), this);
        planMealsAdapter[5] = new PlanMealsAdapter(getContext(), new ArrayList<>(), this);
        planMealsAdapter[6] = new PlanMealsAdapter(getContext(), new ArrayList<>(), this);

    }

    @Override
    public void onRemoveClick(Meal meal) {
        deletePlanMeal(meal);
        for(int j=0;j<7;j++)
        {
            planMealsAdapter[j].notifyDataSetChanged();
        }


    }


    @Override
    public void deletePlanMeal(Meal meal) {
        planPresenter.deleteFromPlan(meal);

    }

    public void setMealsToAdapter(int i) {
        LiveData<List<Meal>> mealList=planPresenter.getAllPlanMeals(days[i]);
        mealList.observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {

                    daysLinearLayoutManagers[i].setOrientation(RecyclerView.HORIZONTAL);

                    daysRecyclerViews[i].setHasFixedSize(true);

                    daysRecyclerViews[i].setLayoutManager(daysLinearLayoutManagers[i]);
                    planMealsAdapter[i].setList(meals);
                    //adapter.notifyDataSetChanged();
                    daysRecyclerViews[i].setAdapter(planMealsAdapter[i]);

            }
        });

    }
}