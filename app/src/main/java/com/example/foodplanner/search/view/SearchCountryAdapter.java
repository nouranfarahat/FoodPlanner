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
import com.example.foodplanner.model.Country;

import java.util.List;

public class SearchCountryAdapter extends RecyclerView.Adapter<SearchCountryAdapter.ViewHolder>{
    private final Context context;
    private List<Country> countryList;
    private String[] flagList=null;
    private OnSearchClickListener onSearchClickListener;

    public SearchCountryAdapter(Context context, List<Country> countryList, OnSearchClickListener onSearchClickListener) {
        this.context = context;
        this.countryList = countryList;
        this.onSearchClickListener = onSearchClickListener;
        flagList=context.getResources().getStringArray(R.array.CountryFlag);

    }

    private static final String TAG="CategoryRecyclerView";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(recyclerView.getContext());
        View v=inflater.inflate(R.layout.country_card,recyclerView,false);
        ViewHolder vh=new ViewHolder(v);
        Log.i(TAG,"onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.countryTitleTextView.setText(countryList.get(position).getStrArea());
        Glide.with(context)
                .load(flagList[position])
                //.apply(new RequestOptions().override(150,130)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.countryImage);
       // holder.countryCardView.setMinimumWidth(170);
        //holder.countryCardView.setMinimumHeight(120);
        holder.countryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countryName=countryList.get(position).getStrArea().toString();
                onSearchClickListener.countryItemOnClick(countryName);

            }
        });

        Log.i(TAG,"onBindViewHolder");
    }


    @Override
    public int getItemCount() {
        if (countryList == null) {
            return 0;
        }
        return countryList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView countryImage;
        TextView countryTitleTextView;
        CardView countryCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            countryImage=itemView.findViewById(R.id.countryImageView);
            countryTitleTextView=itemView.findViewById(R.id.countryTitleTextView);
            countryCardView=itemView.findViewById(R.id.country_card_view);

        }
    }
    public void setList(List<Country> updateList)
    {
        this.countryList=updateList;
    }
}


