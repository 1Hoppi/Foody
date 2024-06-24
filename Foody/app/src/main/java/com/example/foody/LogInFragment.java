package com.example.foody;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LogInFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        TextInputEditText emailField = view.findViewById(R.id.email_edit_text);
        TextInputEditText passwordField = view.findViewById(R.id.password_edit_text);
        TextView errorText = view.findViewById(R.id.error_text);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Button button = view.findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                if (email.equals("") || password.equals("")) {
                    errorText.setText(R.string.error_empty_fields);
                    return;
                }

                try {
                    String urlRequest = String.format("http://%s/api/v1/login?email=%s&password=%s", SavedData.ip, email, password);
                    Networking.URLRequestToHashmap request = new Networking.URLRequestToHashmap(urlRequest);
                    Future<HashMap> future = executor.submit(request);
                    HashMap result = future.get();
                    if (result == null) {
                        errorText.setText(R.string.error_no_server_connection);
                        return;
                    }
                    if (!(boolean)result.get("email")) {
                        errorText.setText(R.string.error_unknown_email);
                        return;
                    }
                    if (!(boolean)result.get("password")) {
                        errorText.setText(R.string.error_wrong_password);
                        return;
                    }

                    Intent newIntent = new Intent(getContext(), MainActivity.class);
                    startActivity(newIntent);
                } catch (Exception e) {
                    Log.e("FutureTaskException", e.toString());
                }
            }
        });

        return view;
    }
}