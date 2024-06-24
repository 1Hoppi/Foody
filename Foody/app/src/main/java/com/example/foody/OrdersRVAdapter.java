package com.example.foody;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrdersRVAdapter extends RecyclerView.Adapter<OrdersRVAdapter.Holder> {
    ArrayList<HashMap> data;
    Context context;
    ExecutorService executor;
    LayoutInflater inflater;

    public OrdersRVAdapter(ArrayList data, Context context, LayoutInflater inflater) {
        this.data = data;
        this.context = context;
        this.inflater = inflater;
        executor = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_rv_item, parent, false);
        return new OrdersRVAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        try {
            holder.priceTextView.setText(data.get(position).get("cost").toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date)data.get(position).get("date"));
            holder.dateTextView.setText(calendar.get(Calendar.DAY_OF_MONTH) + "."
                    + (calendar.get(Calendar.MONTH) + 1)
                    + "." + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR_OF_DAY)
                    + ":" + calendar.get(Calendar.MINUTE));
        } catch (Exception e) {
            Log.e("URLToImageBitmapException", e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView priceTextView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.orders_rv_date);
            priceTextView = itemView.findViewById(R.id.orders_rv_price);
        }
    }
}
