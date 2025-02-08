package com.mascaro101.weatherapp1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mascaro101.weatherapp1.R;
import com.mascaro101.weatherapp1.database.ClothingAdvice;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<ClothingAdvice> adviceList;

    public HistoryAdapter(List<ClothingAdvice> adviceList) {
        this.adviceList = adviceList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        ClothingAdvice advice = adviceList.get(position);
        holder.tvDate.setText(advice.date);
        holder.tvTemperature.setText("Temperature: " + advice.temperature);
        holder.tvWindSpeed.setText("Wind Speed: " + advice.windSpeed);
        holder.tvAdvice.setText(advice.advice);
    }

    @Override
    public int getItemCount() {
        return adviceList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTemperature, tvWindSpeed, tvAdvice;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            tvWindSpeed = itemView.findViewById(R.id.tvWindSpeed);
            tvAdvice = itemView.findViewById(R.id.tvAdvice);
        }
    }
}