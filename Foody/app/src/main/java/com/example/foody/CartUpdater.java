package com.example.foody;

import android.content.SharedPreferences;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartUpdater {
    static MaterialButton counterView;
    static TextView orderPriceView;
    static OrdersRVAdapter ordersRVAdapter;
    static GridProductCardRVAdapter cartRVAdapter;
    static DefaultProductRVAdapter discountRVAdapter;
    static DefaultProductRVAdapter menuRVAdapter;
    static ImageCarouselRVAdapter specialOffersRVAdapter;

    public static void updateCart() {
        ArrayList<HashMap> data = new ArrayList<>();
        for (HashMap item : SavedData.menu) {
            int quantity = SavedData.cart.getOrDefault(item.get("id"), 0);
            if (quantity == 0) continue;
            HashMap map = new HashMap<>(item);
            map.put("quantity", quantity);
            data.add(map);
        }

        cartRVAdapter.data = data;
        cartRVAdapter.notifyDataSetChanged();
    }

    public static void updateCart(ArrayList<HashMap> data) {
        cartRVAdapter.data = new ArrayList<>();
        cartRVAdapter.notifyDataSetChanged();
    }

    public static void addItem(int id, int quantity) {
        // Check if action is valid
        if (!SavedData.cart.containsKey(id)) {
            if (quantity <= 0) return;
            SavedData.cart.put(id, 0);
        }
        // Update SavedData.cart
        SavedData.cart.replace(id, SavedData.cart.get(id) + quantity);
        if (SavedData.cart.get(id) <= 0) SavedData.cart.remove(id);
        // Update size counter
        SavedData.cartSize += quantity;
        counterView.setText(String.valueOf(SavedData.cartSize));
        // Update order cost
        for (HashMap item : SavedData.menu) {
            if ((int) item.get("id") == id) {
                SavedData.orderPrice += (int)item.get("price") * quantity;
            }
        }
        orderPriceView.setText(String.valueOf(SavedData.orderPrice));
        // Update saved cart data
        //cartPreferencesEditor.putInt(String.valueOf(id), SavedData.cart.get(id));
    }

    public static void eraseOfferData() {
        HashMap map = new HashMap();
        map.put("date", Calendar.getInstance().getTime());
        map.put("cost", SavedData.orderPrice);
        map.put("order", SavedData.cart);
        SavedData.orders.add(map);


        ordersRVAdapter.data = SavedData.orders;
        SavedData.cart = new HashMap<>();
        SavedData.cartSize = 0;
        SavedData.orderPrice = 0;
        orderPriceView.setText("0");
        counterView.setText("0");

        ordersRVAdapter.notifyDataSetChanged();
    }

    public static void updateMenu(ArrayList<HashMap> data) {
        SavedData.menu = data;
        discountRVAdapter.data = data;
        menuRVAdapter.data = data;
        discountRVAdapter.notifyDataSetChanged();
        menuRVAdapter.notifyDataSetChanged();
    }

    public static void updateSpecialOffers(ArrayList<HashMap> data) {
        SavedData.special_offers = data;
        specialOffersRVAdapter.data = data;
        specialOffersRVAdapter.notifyDataSetChanged();
    }
}
