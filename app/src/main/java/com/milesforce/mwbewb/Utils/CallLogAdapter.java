package com.milesforce.mwbewb.Utils;

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

import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.TabFragments.ToUpdateAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.ViewHolder> {
    static Context context;
    static private OnItemClickListener onItemClickListener;
    static ArrayList<DelaysModel> srModelInfoArrayList = new ArrayList<>();
    static private long mLastClickTime = 0;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CallLogAdapter(Context context, ArrayList<DelaysModel> srModelInfoArrayList) {
        this.context = context;
        this.srModelInfoArrayList = srModelInfoArrayList;
    }

    @NonNull
    @Override
    public CallLogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.call_log_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CallLogAdapter.ViewHolder holder, final int position) {
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
        holder.student_name.setText(srModelInfoArrayList.get(position).getPerson_name() + " - " + srModelInfoArrayList.get(position).getLevel());
        holder.company_name_client.setText(srModelInfoArrayList.get(position).getCompany() + " - " + srModelInfoArrayList.get(position).getDesignation());
        if (srModelInfoArrayList.get(position).getDirectory().equals("INCOMING")) {
            holder.call_Image_btn.setImageResource(R.drawable.callback);
        }
        if (srModelInfoArrayList.get(position).getDirectory().equals("OUTGOING")) {
            holder.call_Image_btn.setImageResource(R.drawable.outgoing);
        }
        if (srModelInfoArrayList.get(position).getDirectory().equals("REJECTED")) {
            holder.call_Image_btn.setImageResource(R.drawable.reject);
        }
        if (srModelInfoArrayList.get(position).getDirectory().equals("MISSED")) {
            holder.call_Image_btn.setImageResource(R.drawable.missed);
        }


    }

    @Override
    public int getItemCount() {
        return srModelInfoArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card_update;
        TextView student_name, companyName, call_at, call_duration, company_name_client;
        ImageButton call_Image_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_update = itemView.findViewById(R.id.card_update);
            student_name = itemView.findViewById(R.id.student_name);
            call_at = itemView.findViewById(R.id.call_at);
            company_name_client = itemView.findViewById(R.id.company_name_client);
            card_update.setOnClickListener(this);
            call_duration = itemView.findViewById(R.id.call_duration);
            call_Image_btn = itemView.findViewById(R.id.call_Image_btn);
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
