package com.example.foody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Boolean logInMode = true;
    SignUpFragment signUpFragment;
    LogInFragment logInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TEST
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);

        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        signUpFragment = new SignUpFragment();
        logInFragment = new LogInFragment();
        transaction.add(R.id.frame_layout, signUpFragment);
        transaction.add(R.id.frame_layout, logInFragment);
        transaction.hide(signUpFragment);
        transaction.show(logInFragment);
        transaction.commit();

        Button changeModeButton = findViewById(R.id.change_mode_button);
        changeModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logInMode) {
                    changeFragment(signUpFragment);
                    changeModeButton.setText("Log In");
                }
                else {
                    changeFragment(logInFragment);
                    changeModeButton.setText("Sign Up");
                }
                logInMode = !logInMode;
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(signUpFragment);
        transaction.hide(logInFragment);
        transaction.show(fragment);
        transaction.commit();
    }
}