package com.example.foodplanner.favorite.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.utilities.OnFavoriteClickListener;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private final Context context;
    private List<Meal> favMealList;
    private OnFavoriteClickListener listener;


    public FavoriteAdapter(Context context, List<Meal> favMealList, OnFavoriteClickListener listener) {
        this.context = context;
        this.favMealList = favMealList;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView mealImg;
        public TextView mealTitle;
        public ImageButton deleteButton;

        //public ConstraintLayout favConstraintLayout;
        public View favLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favLayout=itemView;

            mealImg=itemView.findViewById(R.id.favMealImageView);
            mealTitle=itemView.findViewById(R.id.favMealTextView);
            deleteButton=itemView.findViewById(R.id.favoriteButtonIcon);
            //favConstraintLayout=itemView.findViewById(R.id.favRow);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(recyclerView.getContext());
        View v=inflater.inflate(R.layout.fav_card_content,recyclerView,false);
        ViewHolder vh=new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Meal meal=favMealList.get(position);
        holder.mealTitle.setText(favMealList.get(position).getStrMeal());
        Glide.with(context)
                .load(favMealList.get(position).getStrMealThumb()).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mealImg);
       /* holder.favConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, values.get(position).getTitle(), Toast.LENGTH_LONG).show();

            }
        });*/
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFavClick(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favMealList.size();
    }
    public void setList(List<Meal> updateList)
    {
        this.favMealList=updateList;
    }
}
