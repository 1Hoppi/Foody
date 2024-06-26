package com.example.foody;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.DeleteGesture;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageCarouselRVAdapter specialOffersRVAdapter = new ImageCarouselRVAdapter(SavedData.special_offers, getContext(), inflater);
        RecyclerView eventsRV = view.findViewById(R.id.what_is_new_rv);
        eventsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        eventsRV.setAdapter(specialOffersRVAdapter);
        CartUpdater.specialOffersRVAdapter = specialOffersRVAdapter;

        DefaultProductRVAdapter discountRVAdapter = new DefaultProductRVAdapter(SavedData.menu, getContext(), true, inflater);
        RecyclerView discountsRV = view.findViewById(R.id.discountsRV);
        discountsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        discountsRV.setAdapter(discountRVAdapter);
        CartUpdater.discountRVAdapter = discountRVAdapter;

        return view;
    }
}