package com.milesforce.mwbewb.EngagementFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.HistoryModel;
import com.milesforce.mwbewb.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    onClickListners onClickListners;
    Context context;
    ArrayList<HistoryModel> historyModelArrayList;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> historyModelArrayList) {
        this.context = context;
        this.historyModelArrayList = historyModelArrayList;
    }

    public void onItemSelectedListeners(onClickListners onClickListners) {
        this.onClickListners = onClickListners;

    }


    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.history_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        try {
            //   holder.date_engage.setText(getStringTimeStamp(historyModelArrayList.get(position).getLast_call()));
            holder.date_engage.setText(historyModelArrayList.get(position).getLast_call());
            holder.about_engagement.setText(historyModelArrayList.get(position).getDetails());
            holder.person_engage.setText(" - " + historyModelArrayList.get(position).getAdded_by_name() + "");
        } catch (Exception e) {

        }


    }

    @Override
    public int getItemCount() {
        return historyModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date_engage, about_engagement, person_engage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date_engage = itemView.findViewById(R.id.date_engage);
            about_engagement = itemView.findViewById(R.id.about_engagement);
            person_engage = itemView.findViewById(R.id.person_engage);
        }
    }

    public interface onClickListners {
        void onItemClickListner(View view, int position);
    }

    private String getStringTimeStamp(String next_call) {
        Date date = new Date(Long.valueOf(next_call) * 1000L);
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String formatted = format.format(date);
        return formatted;
    }
}
