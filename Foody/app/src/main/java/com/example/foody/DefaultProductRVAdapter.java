package com.example.foody;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DefaultProductRVAdapter extends RecyclerView.Adapter<DefaultProductRVAdapter.Holder> {
    ArrayList<HashMap> data;
    Context context;
    ExecutorService executor;
    boolean discount;
    LayoutInflater inflater;

    private int layout;

    public DefaultProductRVAdapter(ArrayList<HashMap> data, Context context, boolean discount, LayoutInflater inflater) {
        this.data = data;
        this.context = context;
        this.discount = discount;
        this.inflater = inflater;
        executor = Executors.newSingleThreadExecutor();

        if (discount) {
            ArrayList<HashMap> discountData = new ArrayList<>();
            for (HashMap item : data) if ((Boolean) item.get("discount")) discountData.add(item);
            this.data = discountData;
        }

        if (discount) layout = R.layout.discount_rv_item;
        else layout = R.layout.default_product_rv_item;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new DefaultProductRVAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.previewImageView.setImageBitmap((Bitmap) data.get(position).get("image_bitmap"));
            holder.nameTextView.setText(data.get(position).get("name").toString());
            holder.weightTextView.setText(data.get(position).get("weight").toString());
            holder.priceTextView.setText(data.get(position).get("price").toString());

            if ((Boolean)data.get(position).get("discount"))
            {
                DrawableCompat.setTint(holder.priceTextView.getBackground(), ContextCompat.getColor(context, R.color.red));
                holder.priceTextView.setTextColor(ContextCompat.getColor(context, R.color.white));

                if (!discount)
                {
                    holder.discountTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
                    holder.currencyTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
                }
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alerts.newProductInfoAlert(context, inflater, data.get(position));
                }
            });
        } catch (Exception e) {
            Log.e("URLToImageBitmapException", e.toString());
        }
    }

    @Override
    public int getItemCount() {
        if (!discount) return data.size();

        int count = 0;
        for (HashMap item : data) if ((Boolean)item.get("discount")) count++;
        return count;
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView previewImageView;
        TextView nameTextView;
        TextView weightTextView;
        TextView priceTextView;

        // Discount only
        TextView discountTextView;
        TextView currencyTextView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            previewImageView = itemView.findViewById(R.id.default_product_rv_image);
            nameTextView = itemView.findViewById(R.id.default_product_rv_name);
            weightTextView = itemView.findViewById(R.id.default_product_rv_weight);
            priceTextView = itemView.findViewById(R.id.default_product_rv_price);
            if (!discount)
            {
                discountTextView = itemView.findViewById(R.id.default_product_rv_discount);
                currencyTextView = itemView.findViewById(R.id.default_product_rv_currency);
            }
        }
    }
}
