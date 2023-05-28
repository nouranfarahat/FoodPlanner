package com.example.foodplanner.home.view;

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
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{
    private final Context context;
    private List<Country> countryList;
    private String[] flagList=null;

    public CountryAdapter(Context context, List<Country> countryList) {
        this.context = context;
        this.countryList = countryList;
        flagList=context.getResources().getStringArray(R.array.CountryFlag);

    }

    private static final String TAG="CategoryRecyclerView";

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(recyclerView.getContext());
        View v=inflater.inflate(R.layout.country_card,recyclerView,false);
        CountryAdapter.ViewHolder vh=new CountryAdapter.ViewHolder(v);
        Log.i(TAG,"onCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.countryTitleTextView.setText(countryList.get(position).getStrArea());
        Glide.with(context)
                .load(flagList[position]).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.countryImage);
        holder.countryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countryName=countryList.get(position).getStrArea().toString();
               HomeFragmentDirections.ActionHomeFragmentToCountryMealsFragment action=HomeFragmentDirections.actionHomeFragmentToCountryMealsFragment(countryName);
               Navigation.findNavController(v).navigate(action);

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


