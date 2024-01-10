package com.milesforce.mwbewb.TabFragments;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.CallLogs;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.SrModelInfo;
import com.milesforce.mwbewb.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ToUpdateAdapter extends RecyclerView.Adapter<ToUpdateAdapter.ViewHolder> {
    static Context context;
    static private OnItemClickListener onItemClickListener;
    static ArrayList<DelaysModel> srModelInfoArrayList = new ArrayList<>();
    static private long mLastClickTime = 0;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ToUpdateAdapter(Context context, ArrayList<DelaysModel> srModelInfoArrayList) {
        this.context = context;
        this.srModelInfoArrayList = srModelInfoArrayList;
    }

    @NonNull
    @Override
    public ToUpdateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.toupdate_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ToUpdateAdapter.ViewHolder holder, final int position) {
       /* if (srModelInfoArrayList.get(position).getCategory().equals("mwb")) {
            holder.student_name.setText(srModelInfoArrayList.get(position).getName() + " - " + srModelInfoArrayList.get(position).getLevel());
        } else {
            holder.student_name.setText(srModelInfoArrayList.get(position).getName() + " - " + srModelInfoArrayList.get(position).getBatch());
        }*/

        try {
            holder.call_at.setText(srModelInfoArrayList.get(position).getDirectory() + "a call at -" + getStringTimeStamp(srModelInfoArrayList.get(position).getTime()));
            Long totalSecs = Long.valueOf(srModelInfoArrayList.get(position).getCall_duration());
            Long hours = totalSecs / 3600;
            Long minutes = (totalSecs % 3600) / 60;
            Long seconds = totalSecs % 60;
            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            holder.call_duration.setText("Duration : " + timeString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.student_name.setText(srModelInfoArrayList.get(position).getPerson_name() + " - " + srModelInfoArrayList.get(position).getLevel() + " - " +srModelInfoArrayList.get(position).getIiml_level()+" - " + srModelInfoArrayList.get(position).getCompany() + " - " + srModelInfoArrayList.get(position).getSource());

        //  holder.student_name.setText(srModelInfoArrayList.get(position).getPerson_name() + " - " + srModelInfoArrayList.get(position).getContact_type());
        holder.company_name_client.setText(srModelInfoArrayList.get(position).getCompany() + " - " + srModelInfoArrayList.get(position).getDesignation());

    }

    @Override
    public int getItemCount() {
        return srModelInfoArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card_update;
        TextView student_name, companyName, call_at, call_duration, company_name_client;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_update = itemView.findViewById(R.id.card_update);
            student_name = itemView.findViewById(R.id.student_name);
            call_at = itemView.findViewById(R.id.call_at);
            company_name_client = itemView.findViewById(R.id.company_name_client);
            card_update.setOnClickListener(this);
            call_duration = itemView.findViewById(R.id.call_duration);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.card_update:
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, getAdapterPosition(), "engagement");
                    }
                    break;
            }


        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int pos, String type);
    }

    private String getStringTimeStamp(String next_call) {
        Date date = new Date(Long.valueOf(next_call));
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String formatted = format.format(date);
        return formatted;
    }
}
