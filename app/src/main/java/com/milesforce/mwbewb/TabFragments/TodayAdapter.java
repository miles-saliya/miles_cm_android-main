package com.milesforce.mwbewb.TabFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.WorkLogModel;
import com.milesforce.mwbewb.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.ViewHolder> {
    Context context;
    static OnItemClickListeners onItemClickListeners;
    ArrayList<DelaysModel> delaysModelArrayList;

    public TodayAdapter(Context context, ArrayList<DelaysModel> delaysModelArrayList) {
        this.context = context;
        this.delaysModelArrayList = delaysModelArrayList;
    }

    public void onItemselectedListners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public TodayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.todays_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodayAdapter.ViewHolder holder, int position) {
        holder.todays_peron_name.setText(delaysModelArrayList.get(position).getPerson_name() + " - " + delaysModelArrayList.get(position).getLevel() + " - " + delaysModelArrayList.get(position).getCompany() + " - " + delaysModelArrayList.get(position).getSource());
        holder.details_about.setText(delaysModelArrayList.get(position).getDetails());
        try {
            holder.nextcall_at.setText("Schedule at : " + getStringTimeStamp(delaysModelArrayList.get(position).getNext_call()));
            holder.lastCall_at.setText("Previous  :  " + getStringTimeStamp(delaysModelArrayList.get(position).getLast_call()));
        } catch (Exception e) {

        }


    }

    @Override
    public int getItemCount() {
        return delaysModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card_today;
        TextView todays_peron_name, nextcall_at, lastCall_at, details_about;
        ImageButton call_Image_btn_today;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_today = itemView.findViewById(R.id.card_today);
            todays_peron_name = itemView.findViewById(R.id.todays_peron_name);
            card_today.setOnClickListener(this);
            nextcall_at = itemView.findViewById(R.id.nextcall_at);
            lastCall_at = itemView.findViewById(R.id.lastCall_at);
            details_about = itemView.findViewById(R.id.details_about);
            call_Image_btn_today = itemView.findViewById(R.id.call_Image_btn_today);
            call_Image_btn_today.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.call_Image_btn_today:
                    onItemClickListeners.onItemSelected(v, getAdapterPosition(), "mobile");
                    break;
                case R.id.card_today:
                    onItemClickListeners.onItemSelected(v, getAdapterPosition(), "engagement");
                    break;
            }


        }
    }

    public interface OnItemClickListeners {
        void onItemSelected(View view, int position, String type);
    }

    private String getStringTimeStamp(String next_call) {
        Date date = new Date(Long.valueOf(next_call) * 1000L);
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String formatted = format.format(date);
        return formatted;
    }

}
