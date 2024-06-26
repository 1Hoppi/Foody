package com.example.foody;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainFragment extends Fragment {

    MainActivity mainActivity;
    HomeFragment homeFragment;
    MenuFragment menuFragment;
    OrdersFragment ordersFragment;
    ProfileFragment profileFragment;

    public MainFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        if (getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();

//        ExecutorService executor = Executors.newSingleThreadExecutor();
//
//        try {
//            String urlRequest;
//            Future<ArrayList> future;
//            Networking.URLRequestToArrayListHashmap request;
//
//            // Menu
//            urlRequest = String.format("http://%s/api/v1/menu", SavedData.ip);
//            request = new Networking.URLRequestToArrayListHashmap(urlRequest);
//            future = executor.submit(request);
//            SavedData.menu = future.get();
//            for (int i = 0; i < SavedData.menu.size(); i++)  {
//                byte[] imageByteArray = Base64.getDecoder().decode((String)SavedData.menu.get(i).get("image"));
//                SavedData.menu.get(i).put("image_bitmap", BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length));
//                SavedData.menu.get(i).remove("image");
//            }
//
//            // Special offers
//            urlRequest = String.format("http://%s/api/v1/special-offers", SavedData.ip);
//            request = new Networking.URLRequestToArrayListHashmap(urlRequest);
//            future = executor.submit(request);
//            SavedData.special_offers = future.get();
//            for (int i = 0; i < SavedData.special_offers.size(); i++)  {
//                byte[] imageByteArray = Base64.getDecoder().decode((String)SavedData.special_offers.get(i).get("image"));
//                SavedData.special_offers.get(i).put("image_bitmap", BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length));
//                SavedData.special_offers.get(i).remove("image");
//            }
//        } catch (Exception e) {
//            Log.e("MainActivityException", e.toString());
//        }
        //NetworkingThread thread = new NetworkingThread();
        //thread.start();

        CompletableFuture<String> asyncFunction = CompletableFuture.supplyAsync(() -> {
            try {
                ExecutorService executor = Executors.newSingleThreadExecutor();

                String urlRequest;
                Future<ArrayList> future;
                Networking.URLRequestToArrayListHashmap request;

                // Menu
                urlRequest = String.format("http://%s/api/v1/menu", SavedData.ip);
                request = new Networking.URLRequestToArrayListHashmap(urlRequest);
                future = executor.submit(request);
                ArrayList<HashMap> menu = future.get();
                for (int i = 0; i < menu.size(); i++) {
                    byte[] imageByteArray = Base64.getDecoder().decode((String) menu.get(i).get("image"));
                    menu.get(i).put("image_bitmap", BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length));
                    menu.get(i).remove("image");
                }
                mainActivity.runOnUiThread(() -> {
                    CartUpdater.updateMenu(menu);
                });

                // Special offers
                urlRequest = String.format("http://%s/api/v1/special-offers", SavedData.ip);
                request = new Networking.URLRequestToArrayListHashmap(urlRequest);
                future = executor.submit(request);
                ArrayList<HashMap> special_offers = future.get();
                for (int i = 0; i < special_offers.size(); i++)  {
                    byte[] imageByteArray = Base64.getDecoder().decode((String)special_offers.get(i).get("image"));
                    special_offers.get(i).put("image_bitmap", BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length));
                    special_offers.get(i).remove("image");
                }
                mainActivity.runOnUiThread(() -> {
                    CartUpdater.updateSpecialOffers(special_offers);
                    mainActivity.changeFragment(mainActivity.mainFragment);
                });
            } catch (Exception e) {
                Log.e("a", e.toString());
            }
            return "async result";
        });

        // UI logic
        ImageButton logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getContext(), LoginActivity.class);
                startActivity(newIntent);
            }
        });

        MaterialButton cartButton = view.findViewById(R.id.cart_button);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartUpdater.updateCart();
                mainActivity.changeFragment(mainActivity.cartFragment);
            }
        });
        CartUpdater.counterView = cartButton;

        // Fragments logic
        homeFragment = new HomeFragment();
        menuFragment = new MenuFragment();
        ordersFragment = new OrdersFragment();
        profileFragment = new ProfileFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.main_frame_layout, homeFragment);
        transaction.add(R.id.main_frame_layout, menuFragment);
        transaction.add(R.id.main_frame_layout, ordersFragment);
        transaction.add(R.id.main_frame_layout, profileFragment);
        transaction.commit();
        changeFragment(homeFragment);

        NavigationBarView navigationBarView = view.findViewById(R.id.navigation_bar);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home_gage) {
                    changeFragment(homeFragment);
                }
                else if (id == R.id.menu_page) {
                    changeFragment(menuFragment);
                }
                else if (id == R.id.orders_page) {
                    changeFragment(ordersFragment);
                }
                else {
                    changeFragment(profileFragment);
                }
                return true;
            }
        });

        return view;
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(homeFragment);
        transaction.hide(menuFragment);
        transaction.hide(ordersFragment);
        transaction.hide(profileFragment);
        transaction.show(fragment);
        transaction.commit();
    }
}