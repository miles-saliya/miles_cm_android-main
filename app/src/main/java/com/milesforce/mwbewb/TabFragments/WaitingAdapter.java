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
import com.milesforce.mwbewb.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class WaitingAdapter extends RecyclerView.Adapter<WaitingAdapter.ViewHolder> {
    Context context;
    static onClickListners onClickListners;
    ArrayList<DelaysModel> delaysModelArrayList;

    public void onItemSelectedListners(onClickListners onClickListners) {
        this.onClickListners = onClickListners;

    }

    public WaitingAdapter(Context context, ArrayList<DelaysModel> delaysModelArrayList) {
        this.context = context;
        this.delaysModelArrayList = delaysModelArrayList;

    }

    @NonNull
    @Override
    public WaitingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.waiting_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WaitingAdapter.ViewHolder holder, int position) {
        holder.person_name_delays.setText(delaysModelArrayList.get(position).getPerson_name() + " - " + delaysModelArrayList.get(position).getLevel() + " - " + delaysModelArrayList.get(position).getCompany() + " - " + delaysModelArrayList.get(position).getSource());

        holder.details_waiting.setText(delaysModelArrayList.get(position).getDetails());
        try {
            Date date = new Date(Long.valueOf(delaysModelArrayList.get(position).getNext_call()) * 1000L);
            DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String formatted = format.format(date);
            holder.schedule_at_waiting.setText(formatted);
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return delaysModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView person_name_delays, schedule_at_waiting, details_waiting;
        CardView card_waiting;
        ImageButton call_Image_btn_waiting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            person_name_delays = itemView.findViewById(R.id.person_name_delays);
            schedule_at_waiting = itemView.findViewById(R.id.schedule_at_waiting);
            details_waiting = itemView.findViewById(R.id.details_waiting);
            card_waiting = itemView.findViewById(R.id.card_waiting);
            card_waiting.setOnClickListener(this);
            call_Image_btn_waiting = itemView.findViewById(R.id.call_Image_btn_waiting);
            call_Image_btn_waiting.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_waiting:
                    onClickListners.onItemSelectedListner(v, getAdapterPosition(), "engagement");
                    break;
                case R.id.call_Image_btn_waiting:
                    onClickListners.onItemSelectedListner(v, getAdapterPosition(), "mobile");
                    break;
            }

        }
    }

    public interface onClickListners {
        void onItemSelectedListner(View view, int position, String type);
    }
}