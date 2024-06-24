package com.example.foody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    CartFragment cartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();

        mainFragment = new MainFragment(this);
        cartFragment = new CartFragment(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.global_frame_layout, mainFragment);
        transaction.add(R.id.global_frame_layout, cartFragment);
        transaction.commit();
        changeFragment(mainFragment);
    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mainFragment);
        transaction.hide(cartFragment);
        transaction.show(fragment);
        transaction.commit();
    }
}