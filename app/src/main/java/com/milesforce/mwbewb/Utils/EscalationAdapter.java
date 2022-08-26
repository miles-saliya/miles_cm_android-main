package com.milesforce.mwbewb.Utils;

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


public class EscalationAdapter extends RecyclerView.Adapter<EscalationAdapter.EscalationViewHolder> {
    static OnClickListner OnClickListner;
    ArrayList<DelaysModel> delaysModelArrayList;
    Context context;

    public void onItemClickListners(OnClickListner onClickListeners) {
        this.OnClickListner = onClickListeners;

    }

    public EscalationAdapter(Context context, ArrayList<DelaysModel> delaysModelArrayList){
        this.context = context;
        this.delaysModelArrayList = delaysModelArrayList;

    }


    @NonNull
    @Override
    public EscalationAdapter.EscalationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EscalationViewHolder(LayoutInflater.from(context).inflate(R.layout.delays_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EscalationAdapter.EscalationViewHolder holder, int position) {
        holder.delays_person_name.setText(delaysModelArrayList.get(position).getPerson_name() + " - " + delaysModelArrayList.get(position).getLevel() + " - " + delaysModelArrayList.get(position).getCompany() + " - " + delaysModelArrayList.get(position).getSource());
        holder.delays_about.setText(delaysModelArrayList.get(position).getDetails());
        try {
            holder.delays_nextCall.setText("Schedule at : " + getStringTimeStamp(delaysModelArrayList.get(position).getNext_call()));
            holder.previous_next_call.setText("Previous  : " + getStringTimeStamp(delaysModelArrayList.get(position).getLast_call()));
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return delaysModelArrayList.size();
    }

    public static class EscalationViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        CardView card_delays;
        ImageButton call_Image_btn_delays;
        TextView delays_person_name, delays_nextCall, previous_next_call, delays_about;
        public EscalationViewHolder(@NonNull View itemView) {
            super(itemView);
            card_delays = itemView.findViewById(R.id.card_delays);
            call_Image_btn_delays = itemView.findViewById(R.id.call_Image_btn_delays);
            card_delays.setOnClickListener(this);
            delays_person_name = itemView.findViewById(R.id.delays_person_name);
            delays_nextCall = itemView.findViewById(R.id.delays_nextCall);
            previous_next_call = itemView.findViewById(R.id.previous_next_call);
            delays_about = itemView.findViewById(R.id.delays_about);
            call_Image_btn_delays.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_delays:
                    OnClickListner.itemSelected(v, getAdapterPosition(), "engagement");
                    break;
                case R.id.call_Image_btn_delays:
                    OnClickListner.itemSelected(v, getAdapterPosition(), "mobile");
                    break;

            }
        }
    }

    public interface OnClickListner{
        void itemSelected(View v, int position, String type);

    }
    private String getStringTimeStamp(String next_call) {
        Date date = new Date(Long.valueOf(next_call) * 1000L);
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String formatted = format.format(date);
        return formatted;
    }
}
