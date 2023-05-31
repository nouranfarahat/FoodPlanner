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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.home.view.HomeFragmentDirections;
import com.example.foodplanner.model.Category;

import java.util.List;

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.ViewHolder>{
    private final Context context;
    private List<Category> categoryList;
    private OnSearchClickListener onSearchClickListener;


    public SearchCategoryAdapter(Context context, List<Category> categoryList, OnSearchClickListener onSearchClickListener) {
        this.context = context;
        this.categoryList = categoryList;
        this.onSearchClickListener = onSearchClickListener;
    }

    private static final String TAG="CategoryRecyclerView";

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
        holder.categoryTitleTextView.setText(categoryList.get(position).getStrCategory());
        Glide.with(context)
                .load(categoryList.get(position).getStrCategoryThumb()).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.categoryImage);
        holder.categoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchClickListener.categoryItemOnClick(categoryList.get(position).getStrCategory());

            }
        });

        Log.i(TAG,"onBindViewHolder");
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryImage;
        TextView categoryTitleTextView;
        CardView categoryCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage=itemView.findViewById(R.id.categoryImageView);
            categoryTitleTextView=itemView.findViewById(R.id.categoryTitleTextView);
            categoryCardView=itemView.findViewById(R.id.meal_card_content);
        }
    }
    public void setList(List<Category> updateList)
    {
        this.categoryList=updateList;
    }
}

