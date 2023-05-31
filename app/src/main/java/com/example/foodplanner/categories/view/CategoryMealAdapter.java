package com.example.foodplanner.categories.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

public class CategoryMealAdapter extends RecyclerView.Adapter<CategoryMealAdapter.ViewHolder>{
    private final Context context;
    private List<Meal> mealList;
    private OnFavoriteClickListener listener;

    public CategoryMealAdapter(Context context, List<Meal> mealList, OnFavoriteClickListener listener) {
        this.context = context;
        this.mealList = mealList;
        this.listener = listener;
    }

    private static final String TAG="RecyclerView";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(recyclerView.getContext());
        View v=inflater.inflate(R.layout.meal_card,recyclerView,false);
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
                listener.onFavClick(mealList.get(position));
            }
        });
        holder.mealCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealName=mealList.get(position).getStrMeal().toString();
                CategoryMealsFragmentDirections.ActionCategoryMealsFragmentToMealDetailsFragment action=CategoryMealsFragmentDirections.actionCategoryMealsFragmentToMealDetailsFragment(mealName);
                Navigation.findNavController(v).navigate(action);
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
        CardView mealCardView;
        View layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView;

            mealImage=itemView.findViewById(R.id.mealImageView);
            favButton=itemView.findViewById(R.id.favoriteButtonIcon);
            titleTextView=itemView.findViewById(R.id.mealTitleTextView);
            mealCardView=itemView.findViewById(R.id.meal_card_content);

        }
    }
    public void setList(List<Meal> updateList)
    {
        this.mealList=updateList;
    }
}

