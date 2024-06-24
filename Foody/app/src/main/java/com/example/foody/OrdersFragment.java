package com.example.foody;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        OrdersRVAdapter ordersRVAdapter = new OrdersRVAdapter(SavedData.orders, getContext(), inflater);
        RecyclerView ordersRV = view.findViewById(R.id.orders_rv);
        ordersRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ordersRV.setAdapter(ordersRVAdapter);

        CartUpdater.ordersRVAdapter = ordersRVAdapter;

        return view;
    }
}