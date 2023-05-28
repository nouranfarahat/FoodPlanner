package com.example.foodplanner.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Meal;

import java.util.List;

public class RandomMealsAdapter extends RecyclerView.Adapter<RandomMealsAdapter.ViewHolder>{
    private final Context context;
    private List<Meal> mealList;
    private OnFavoriteClickListener listener;

    public RandomMealsAdapter(Context context, List<Meal> mealList, OnFavoriteClickListener listener) {
        this.context = context;
        this.mealList = mealList;
        this.listener = listener;
    }

    private static final String TAG="RecyclerView";

    @NonNull
    @Override
    public RandomMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(recyclerView.getContext());
        View v=inflater.inflate(R.layout.meal_card,recyclerView,false);
        ViewHolder vh=new ViewHolder(v);
        Log.i(TAG,"onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RandomMealsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.titleTextView.setText(mealList.get(position).getIdMeal());
        Glide.with(context)
                .load(mealList.get(position).getStrMealThumb()).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mealImage);
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFavClick(mealList.get(position));
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
        ImageButton favButton;
        TextView titleTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mealImage=itemView.findViewById(R.id.mealImageView);
            favButton=itemView.findViewById(R.id.favoriteButtonIcon);
            titleTextView=itemView.findViewById(R.id.mealTitleTextView);
        }
    }
    public void setList(List<Meal> updateList)
    {
        this.mealList=updateList;
    }
}