package com.igroove.igrooveapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.igroove.igrooveapp.R;
import com.igroove.igrooveapp.model.RecommendedItems;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {

    private final Context context;
    private final List<RecommendedItems> recommendedList;

    public RecommendedAdapter(Context context, List<RecommendedItems> recommendedList) {
        this.context = context;
        this.recommendedList = recommendedList;
    }

    @Override
    public RecommendedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommended_item, parent, false);
        return new RecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendedViewHolder holder, int position) {
        RecommendedItems item = recommendedList.get(position);
        holder.recommendedTitle.setText(item.getName());

        Glide.with(context)
                .load(item.getImageUrl())
                .placeholder(R.drawable.baseline_camera_24)
                .error(R.drawable.baseline_camera_24)
                .into(holder.recommendedImage);
    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    static class RecommendedViewHolder extends RecyclerView.ViewHolder {
        ImageView recommendedImage;
        TextView recommendedTitle;

        public RecommendedViewHolder(View itemView) {
            super(itemView);
            recommendedImage = itemView.findViewById(R.id.recommendedImage);
            recommendedTitle = itemView.findViewById(R.id.recommendedTitle);
        }
    }
}
