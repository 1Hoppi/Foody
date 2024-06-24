package com.example.foody;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GridProductCardRVAdapter extends RecyclerView.Adapter<GridProductCardRVAdapter.Holder> {
    ArrayList<HashMap> data;
    Context context;
    ExecutorService executor;
    LayoutInflater inflater;

    public GridProductCardRVAdapter(ArrayList<HashMap> data, Context context, LayoutInflater inflater) {
        this.data = data;
        this.context = context;
        this.inflater = inflater;
        executor = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_product_card_rv_item, parent, false);
        return new GridProductCardRVAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        try {
            HashMap item = data.get(position);
            holder.nameTextView.setText((String)item.get("name"));
            holder.priceTextView.setText(item.get("price").toString());
            holder.quantityTextView.setText(item.get("quantity").toString());
            holder.previewShapeableImaveView.setImageBitmap((Bitmap)item.get("image_bitmap"));

            int id = (int)item.get("id");

            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartUpdater.addItem(id, 1);
                    item.replace("quantity", SavedData.cart.getOrDefault(id, 0).toString());
                    holder.quantityTextView.setText(SavedData.cart.getOrDefault(id, 0).toString());
                }
            });
            holder.removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartUpdater.addItem(id, -1);
                    item.replace("quantity", SavedData.cart.getOrDefault(id, 0).toString());
                    if (!SavedData.cart.containsKey(id)) {
                        data.remove(position);
                        notifyDataSetChanged();
                        return;
                    }
                    holder.quantityTextView.setText(SavedData.cart.getOrDefault(id, 0).toString());
                }
            });
        } catch (Exception e) {
            Log.e("URLToImageBitmapException", e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ShapeableImageView previewShapeableImaveView;
        TextView nameTextView;
        TextView priceTextView;
        TextView quantityTextView;
        MaterialButton addButton;
        MaterialButton removeButton;

        public Holder(@NonNull View itemView) {
            super(itemView);
            previewShapeableImaveView = itemView.findViewById(R.id.grid_product_card_rv_image);
            nameTextView = itemView.findViewById(R.id.grid_product_card_rv_name);
            priceTextView = itemView.findViewById(R.id.grid_product_card_rv_price);
            quantityTextView = itemView.findViewById(R.id.grid_product_card_rv_quantity);
            addButton = itemView.findViewById(R.id.grid_product_card_rv_add);
            removeButton = itemView.findViewById(R.id.grid_product_card_rv_remove);
        }
    }
}