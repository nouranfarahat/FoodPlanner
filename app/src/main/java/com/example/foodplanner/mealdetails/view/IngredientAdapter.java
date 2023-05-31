package com.example.foodplanner.mealdetails.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.home.view.CategoryMealsAdapter;
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{
    private final Context context;
    private List<Ingredient> ingredientList;

    public IngredientAdapter(Context context, List<Ingredient> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;

    }

    private static final String TAG="CategoryRecyclerView";

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(recyclerView.getContext());
        View v=inflater.inflate(R.layout.ingerdient_item,recyclerView,false);
        IngredientAdapter.ViewHolder vh=new IngredientAdapter.ViewHolder(v);
        Log.i(TAG,"onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ingredientNameTextView.setText(ingredientList.get(position).getIngredientName());
        holder.ingredientMeasure.setText(ingredientList.get(position).getIngredientMeasure());
        Glide.with(context)
                .load("https://www.themealdb.com/images/ingredients/"+ingredientList.get(position).getIngredientName()+".png")
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ingredientImage);

        Log.i(TAG,"onBindViewHolder");
    }


    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ingredientImage;
        TextView ingredientNameTextView;
        TextView ingredientMeasure;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientImage=itemView.findViewById(R.id.ingredientImageView);
            ingredientNameTextView=itemView.findViewById(R.id.ingerdientTextView);
            ingredientMeasure=itemView.findViewById(R.id.ingerdientAmountTextView);

        }
    }
    public void setList(List<Ingredient> updateList)
    {
        this.ingredientList=updateList;
    }
}

