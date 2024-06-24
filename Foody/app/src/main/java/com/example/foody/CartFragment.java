package com.example.foody;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    MainActivity mainActivity;
    RecyclerView cartRV;

    public CartFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        GridLayoutManager menuGridLM = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        GridProductCardRVAdapter cartRVAdapter = new GridProductCardRVAdapter(SavedData.menu, getContext(), getLayoutInflater());
        cartRV = view.findViewById(R.id.cart_rv);
        cartRV.setLayoutManager(menuGridLM);
        cartRV.setAdapter(cartRVAdapter);

        CartUpdater.orderPriceView = view.findViewById(R.id.order_cost);
        CartUpdater.cartRVAdapter = cartRVAdapter;

        view.findViewById(R.id.cart_exit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFragment(mainActivity.mainFragment);
            }
        });

        view.findViewById(R.id.cart_rv_place_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SavedData.cart.isEmpty()) return;
                cartRVAdapter.data = new ArrayList<>();
                cartRVAdapter.notifyDataSetChanged();
                CartUpdater.eraseOfferData();
            }
        });

        //SharedPreferences cartPreferences = mainActivity.getSharedPreferences("cart", Context.MODE_PRIVATE);
        //ArrayList<HashMap> cartAdapterData = new ArrayList<>();
        //for (HashMap item : SavedData.menu) {
        //    int quantity = cartPreferences.getInt(item.get("id").toString(), 0);
        //    if (quantity == 0) continue;
        //    SavedData.cart.put((int)item.get("id"), quantity);
        //    SavedData.cartSize += quantity;
        //    SavedData.orderPrice += (int)item.get("price") * quantity;
        //    HashMap cartAdapterDataItem = new HashMap(item);
        //    cartAdapterDataItem.put("quantity", quantity);
        //    cartAdapterData.add(cartAdapterDataItem);
        //}
        //CartUpdater.cartPreferencesEditor = cartPreferences.edit();
        //CartUpdater.updateCart(cartAdapterData);

        return view;
    }
}