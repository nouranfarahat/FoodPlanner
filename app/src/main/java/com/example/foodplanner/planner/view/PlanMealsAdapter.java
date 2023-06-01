package com.example.foodplanner.planner.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.home.view.HomeFragmentDirections;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.utilities.OnFavoriteClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PlanMealsAdapter extends RecyclerView.Adapter<PlanMealsAdapter.ViewHolder>{
    private final Context context;
    private List<Meal> mealList;
    private OnRemoveClickListener listener;
    public static final String PREF_NAME="APPINFO";
    SharedPreferences pref;

    public PlanMealsAdapter(Context context, List<Meal> mealList, OnRemoveClickListener listener) {
        this.context = context;
        this.mealList = mealList;
        this.listener = listener;
    }

    private static final String TAG="RecyclerView";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(recyclerView.getContext());
        View v=inflater.inflate(R.layout.plan_meal_item,recyclerView,false);
        ViewHolder vh=new ViewHolder(v);
        Log.i(TAG,"onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.titleTextView.setText(mealList.get(position).getStrMeal());
        Glide.with(context)
                .load(mealList.get(position).getStrMealThumb()).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mealImage);
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRemoveClick(mealList.get(position));
            }
        });
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRemoveClick(mealList.get(position));
                SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

                String key = prefs.getString("ID","unknwon");
                deletePlanFire(mealList.get(position),key );

            }
        });

        Log.i(TAG,"onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mealImage;
        ToggleButton favButton;
        TextView titleTextView;
        Button removeBtn;
        CardView mealCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mealImage=itemView.findViewById(R.id.mealImageView);
            favButton=itemView.findViewById(R.id.favoriteButtonIcon);
            titleTextView=itemView.findViewById(R.id.mealTitleTextView);
            mealCard=itemView.findViewById(R.id.meal_card_content);
            removeBtn=itemView.findViewById(R.id.remove_btn);
        }
    }
    void deletePlanFire(Meal mealToDelete, String c){
        DatabaseReference userRef= FirebaseDatabase.getInstance().getReference().child("User");//= FirebaseDatabase.getInstance().getReference().child("User");
        final int[] counter = {0};

        userRef.child(c).child("Meals").child(mealToDelete.getIdMeal()).removeValue();
        Log.i("TAG", "deletePlanFire: deleted from plan fire base");
    }
    public void setList(List<Meal> updateList)
    {
        this.mealList=updateList;
    }
}
