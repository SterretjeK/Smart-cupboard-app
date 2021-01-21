package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {

    private ArrayList<JsonObject> products;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDate;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textViewTitle = (TextView) view.findViewById(R.id.itemTitle);
            textViewDate = (TextView) view.findViewById(R.id.itemDate);
        }
    }

    public PantryAdapter(ArrayList<JsonObject> products) {
        this.products = products;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pantry_cell, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String timestamp_in_string = products.get(position).get("timestamp").getAsString();
        long dv = Long.parseLong(timestamp_in_string);// its need to be in milisecond
        Date df = new Date(dv);

        Calendar c = Calendar.getInstance();
        c.setTime(df);
        c.add(Calendar.DATE, products.get(position).get("expirationDays").getAsInt());

        String vv = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textViewTitle.setText(products.get(position).get("name").getAsString());
        viewHolder.textViewDate.setText(vv);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return products.size();
    }
}
