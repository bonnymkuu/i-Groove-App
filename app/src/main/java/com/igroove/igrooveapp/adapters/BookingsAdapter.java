package com.igroove.igrooveapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.igroove.igrooveapp.R;
import com.igroove.igrooveapp.model.Bookings;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookingsViewHolder> {

    private final List<Bookings> bookingsList;
    private final Context context;

    public BookingsAdapter(Context context, List<Bookings> bookingsList) {
        this.context = context;
        this.bookingsList = bookingsList;
    }

    @Override
    public BookingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booked_items, parent, false);
        return new BookingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookingsViewHolder holder, int position) {
        Bookings bookings = bookingsList.get(position);
        holder.location.setText("Location: " + bookings.getLocation());
        holder.time.setText("Time: " + bookings.getTime());
        holder.date.setText("Date: " + bookings.getDate());
    }

    @Override
    public int getItemCount() {
        return bookingsList.size(); // fixed bug here
    }

    public static class BookingsViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView location;
        TextView time;

        public BookingsViewHolder(View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
        }
    }
}
