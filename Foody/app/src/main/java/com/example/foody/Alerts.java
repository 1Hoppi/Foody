package com.example.foody;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.HashMap;

public class Alerts {
    static void newEventInfoAlert(Context context, LayoutInflater inflater, HashMap data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alert = builder.create();

        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.alert_event_info, null);
        alert.setView(layout);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ((Button)layout.findViewById(R.id.alert_event_info_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        ((TextView)layout.findViewById(R.id.alert_event_info_text)).setText((String)data.get("description"));

        alert.show();
    }

    static void newProductInfoAlert(Context context, LayoutInflater inflater, HashMap data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alert = builder.create();

        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.alert_product_info, null);
        alert.setView(layout);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView itemQuantityTextView = layout.findViewById(R.id.alert_event_info_quantity);
        int id = (int)data.get("id");
        if (SavedData.cart.containsKey(id)) itemQuantityTextView.setText(SavedData.cart.get(id).toString());

        ((ImageButton)layout.findViewById(R.id.alert_product_info_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        ((MaterialButton)layout.findViewById(R.id.alert_event_info_remove)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartUpdater.addItem(id, -1);
                itemQuantityTextView.setText(SavedData.cart.getOrDefault(id, 0).toString());
            }
        });
        ((MaterialButton)layout.findViewById(R.id.alert_event_info_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartUpdater.addItem(id, 1);
                itemQuantityTextView.setText(SavedData.cart.getOrDefault(id, 0).toString());
            }
        });

        ((ImageView)layout.findViewById(R.id.alert_product_info_image)).setImageBitmap((Bitmap) data.get("image_bitmap"));
        ((TextView)layout.findViewById(R.id.alert_product_info_name)).setText((String)data.get("name"));
        ((TextView)layout.findViewById(R.id.alert_product_info_description)).setText((String)data.get("description"));
        ((TextView)layout.findViewById(R.id.alert_product_info_weight)).setText(data.get("weight").toString());
        ((TextView)layout.findViewById(R.id.alert_product_info_price)).setText(data.get("price").toString());

        alert.show();
    }
}
