package com.milesforce.mwbewb.TabFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.DashBoardModel;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.MissedCallModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Utils.AlertForUntrackedEngagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MissedCallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static Context context;
    static OnItemClickOnListner onItemClickOnListner;
    static ArrayList<DashBoardModel> missedCallModelArrayList;
    private int MISSEDCALL = 0;
    private int UNTRACKED = 1;
    static String accessToken;


    public void setOnItemClickListener(OnItemClickOnListner onItemClickOnListner) {
        this.onItemClickOnListner = onItemClickOnListner;

    }

    public MissedCallAdapter(Context context, ArrayList<DashBoardModel> missedCallModels, String AccessToken) {
        this.context = context;
        this.missedCallModelArrayList = missedCallModels;
        this.accessToken = AccessToken;
    }

    @Override
    public int getItemViewType(int position) {
        if (missedCallModelArrayList.get(position).getMwb().getPerson_name() != null && missedCallModelArrayList.get(position).getMwb().getLevel() != null) {
            return MISSEDCALL;
        } else {

            return UNTRACKED;

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MISSEDCALL) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.missed_call_layout, parent, false));
        } else {
            return new UntrackedView(LayoutInflater.from(context).inflate(R.layout.untracked_layout, parent, false));
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            onBindMissedCallView((ViewHolder) holder, position);
        } else {
            onBindUntrackedView((UntrackedView) holder, position);
        }


    }

    private void onBindUntrackedView(UntrackedView holder, int position) {
        try {
            holder.untrack_mobile_number.setText(missedCallModelArrayList.get(position).getCall_data().getPhone_number());
            holder.untrack_type.setText(missedCallModelArrayList.get(position).getCall_data().getDirectory() + " a call at - " + getStringTimeStamp(missedCallModelArrayList.get(position).getCall_data().getTime()));
            holder.duration_untrack.setText("Call Duration : " + String.valueOf(missedCallModelArrayList.get(position).getCall_data().getCall_duration()));
        } catch (Exception e) {

        }

    }

    private void onBindMissedCallView(ViewHolder holder, int position) {
        try {
            holder.missed_call_person_name.setText(missedCallModelArrayList.get(position).getMwb().getPerson_name());
            holder.missedCallCompany.setText(missedCallModelArrayList.get(position).getMwb().getCompany());
            holder.misse_call_date.setText("MISSED call at  :" + getStringTimeStamp(missedCallModelArrayList.get(position).getCall_data().getTime()));
        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return missedCallModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card_missed;
        ImageButton call_Image_btn;
        TextView missed_call_person_name, missedCallCompany, misse_call_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_missed = itemView.findViewById(R.id.card_missed);
            call_Image_btn = itemView.findViewById(R.id.call_Image_btn);
            missed_call_person_name = itemView.findViewById(R.id.missed_call_person_name);
            missedCallCompany = itemView.findViewById(R.id.missedCallCompany);
            misse_call_date = itemView.findViewById(R.id.misse_call_date);
            call_Image_btn.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemClickOnListner.onItemClick(v, missedCallModelArrayList.get(getAdapterPosition()));
        }
    }

    public interface OnItemClickOnListner {
        void onItemClick(View view, DashBoardModel dashBoardModel);

    }

    private String getStringTimeStamp(String next_call) {
        Date date = new Date(Long.valueOf(next_call));
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String formatted = format.format(date);
        return formatted;
    }

    public static class UntrackedView extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card_untrack;
        TextView untrack_mobile_number, untrack_type, duration_untrack;
        ImageView call_Image_btn;

        public UntrackedView(@NonNull View itemView) {
            super(itemView);
            call_Image_btn = itemView.findViewById(R.id.call_Image_btn);
            call_Image_btn.setOnClickListener(this);
            untrack_mobile_number = itemView.findViewById(R.id.untrack_mobile_number);
            untrack_type = itemView.findViewById(R.id.untrack_type);
            duration_untrack = itemView.findViewById(R.id.duration_untrack);
            card_untrack = itemView.findViewById(R.id.card_untrack);
            card_untrack.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.call_Image_btn:
                    onItemClickOnListner.onItemClick(v, missedCallModelArrayList.get(getAdapterPosition()));
                    break;
                case R.id.card_untrack:
                    new AlertForUntrackedEngagement().UntrackedAdd(context, missedCallModelArrayList.get(getAdapterPosition()).getCall_data().getPhone_number(), accessToken);
                    break;
            }

        }
    }


}
