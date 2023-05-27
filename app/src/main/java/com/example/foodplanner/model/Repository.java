package com.example.foodplanner.model;

import android.content.Context;

import com.example.foodplanner.network.NetworkDelegate;
import com.example.foodplanner.network.RemoteSource;

public class Repository implements RepositoryInterface{
    public static Repository repository=null;
    private Context context;
    RemoteSource remoteSource;

    public Repository(Context context, RemoteSource remoteSource) {
        this.context = context;
        this.remoteSource = remoteSource;
    }
    public static Repository getInstance(RemoteSource rSource,Context cont)
    {
        if(repository==null)
            repository=new Repository(cont,rSource);
        return repository;
    }

    @Override
    public void getRandomMeal(NetworkDelegate networkDelegate) {
        remoteSource.getRandomMeal(networkDelegate);

    }

    @Override
    public void getMealCategory(NetworkDelegate networkDelegate) {
        remoteSource.getCategories(networkDelegate);
    }
}
