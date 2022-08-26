package com.milesforce.mwbewb.TabFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.CallLogs;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Utils.TriggerCallWIthTelephoneManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UntrackedAdapter extends RecyclerView.Adapter<UntrackedAdapter.ViewHolder> {
    static Context context;
    static OnClickListner onClickListner;
    static ArrayList<CallLogs> untrackedModelArrayList;

    public void setOnItemClickListner(OnClickListner onClickListner) {
        this.onClickListner = onClickListner;

    }

    public UntrackedAdapter(Context context, ArrayList<CallLogs> untrackedModelArrayList) {
        this.context = context;
        this.untrackedModelArrayList = untrackedModelArrayList;

    }

    @NonNull
    @Override
    public UntrackedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.untracked_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UntrackedAdapter.ViewHolder holder, final int position) {
        try {
            holder.untrack_mobile_number.setText(untrackedModelArrayList.get(position).getPhone_number());
            holder.untrack_type.setText(untrackedModelArrayList.get(position).getDirectory() + " a call at - " + getStringTimeStamp(untrackedModelArrayList.get(position).getTime()));


            Long totalSecs = Long.valueOf(untrackedModelArrayList.get(position).getCall_duration());
            Long hours = totalSecs / 3600;
            Long minutes = (totalSecs % 3600) / 60;
            Long seconds = totalSecs % 60;
            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            holder.duration_untrack.setText("Duration : " + timeString);

        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return untrackedModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card_untrack;
        TextView untrack_mobile_number, untrack_type, duration_untrack;
        ImageView call_Image_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_untrack = itemView.findViewById(R.id.card_untrack);
            card_untrack.setOnClickListener(this);
            untrack_mobile_number = itemView.findViewById(R.id.untrack_mobile_number);
            untrack_type = itemView.findViewById(R.id.untrack_type);
            duration_untrack = itemView.findViewById(R.id.duration_untrack);
            call_Image_btn = itemView.findViewById(R.id.call_Image_btn);
            call_Image_btn.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_untrack:
                    onClickListner.OnItemCLicked(v, getAdapterPosition());
                    break;
                case R.id.call_Image_btn:
                    TriggerCallWIthTelephoneManager.TriggerCall(String.valueOf(untrackedModelArrayList.get(getAdapterPosition()).getPhone_number()), context);
                    break;
            }

        }
    }

    public interface OnClickListner {
        void OnItemCLicked(View view, int position);
    }

    private String getStringTimeStamp(String next_call) {
        Date date = new Date(Long.valueOf(next_call));
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String formatted = format.format(date);
        return formatted;
    }
}
