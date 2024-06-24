package com.example.foody;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ImageCarouselRVAdapter extends RecyclerView.Adapter<ImageCarouselRVAdapter.Holder> {
    ArrayList<HashMap> data;
    Context context;
    ExecutorService executor;
    LayoutInflater inflater;

    public ImageCarouselRVAdapter(ArrayList data, Context context, LayoutInflater inflater) {
        this.data = data;
        this.context = context;
        this.inflater = inflater;
        executor = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_carousel_rv_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.previewImageView.setImageBitmap((Bitmap) data.get(position).get("image_bitmap"));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alerts.newEventInfoAlert(context, inflater, data.get(position));
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
        ImageView previewImageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            previewImageView = itemView.findViewById(R.id.image_carousel_image);
        }
    }
}