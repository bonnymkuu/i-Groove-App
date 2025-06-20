package com.igroove.igrooveapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.igroove.igrooveapp.GenreListActivity;
import com.igroove.igrooveapp.R;
import com.igroove.igrooveapp.model.Genre;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenreViewHolder> {

    private final Context context;
    private final List<Genre> genreList;

    public GenresAdapter(Context context, List<Genre> genreList) {
        this.context = context;
        this.genreList = genreList;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.genre_item, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        Genre genre = genreList.get(position);
        holder.genreTitle.setText(genre.getTitle());
        Glide.with(context)
                .load(genre.getImageUrl()) // assuming it's a drawable resource ID
                .into(holder.genreImage);

        holder.itemView.setOnClickListener(v -> openGenreListActivity(genre));
    }

    private void openGenreListActivity(Genre genre) {
        Intent intent = new Intent(context, GenreListActivity.class);
        intent.putExtra("genre", genre.getTitle());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {
        ImageView genreImage;
        TextView genreTitle;

        public GenreViewHolder(View itemView) {
            super(itemView);
            genreImage = itemView.findViewById(R.id.genreImage);
            genreTitle = itemView.findViewById(R.id.genreTitle);
        }
    }
}
