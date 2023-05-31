package com.example.foodplanner.search.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.IngredientModel;

import java.util.List;

public class SearchIngredientAdapter extends RecyclerView.Adapter<SearchIngredientAdapter.ViewHolder>{
    private final Context context;
    private List<Ingredient> ingredientList;
    private OnSearchClickListener onSearchClickListener;


    public SearchIngredientAdapter(Context context, List<Ingredient> ingredientList, OnSearchClickListener onSearchClickListener) {
        this.context = context;
        this.ingredientList = ingredientList;
        this.onSearchClickListener = onSearchClickListener;
    }

    private static final String TAG="RecyclerView";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(recyclerView.getContext());
        View v=inflater.inflate(R.layout.category_card,recyclerView,false);
        ViewHolder vh=new ViewHolder(v);
        Log.i(TAG,"onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.ingredientNameTextView.setText(ingredientList.get(position).getStrIngredient());
        Glide.with(context)
                .load("https://www.themealdb.com/images/ingredients/"+ingredientList.get(position).getStrIngredient()+".png")
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ingredientImage);

        Log.i(TAG,"onBindViewHolder");

        holder.ingredientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredientName=ingredientList.get(position).getStrIngredient().toString();
                onSearchClickListener.ingredientItemOnclick(ingredientName);
            }
        });

        Log.i(TAG,"onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ingredientImage;
        TextView ingredientNameTextView;
        CardView ingredientCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientImage=itemView.findViewById(R.id.categoryImageView);
            ingredientNameTextView=itemView.findViewById(R.id.categoryTitleTextView);
            ingredientCard=itemView.findViewById(R.id.meal_card_content);
        }
    }
    public void setList(List<Ingredient> updateList)
    {
        this.ingredientList=updateList;
    }
}
