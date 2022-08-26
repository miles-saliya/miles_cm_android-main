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
import com.milesforce.mwbewb.TabFragments.UntabbedAdapter;


import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FlaggedAdapter extends RecyclerView.Adapter<FlaggedAdapter.ViewHolder> {
    Context context;
    ArrayList<DelaysModel> FalgModelArrayList;
    static onItemClickListner onItemClickListner;

    public FlaggedAdapter(Context context,ArrayList<DelaysModel>flag_arrayList){
        this.context = context;
        this.FalgModelArrayList = flag_arrayList;

    }


    public void onItemSelectedListners(FlaggedAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    @NonNull
    @Override
    public FlaggedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.flag_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FlaggedAdapter.ViewHolder holder, int position) {
        holder.flag_person_name.setText(FalgModelArrayList.get(position).getPerson_name() + " - " + FalgModelArrayList.get(position).getLevel() + " - " + FalgModelArrayList.get(position).getCompany() + " - " + FalgModelArrayList.get(position).getSource());
        holder.flag_about.setText(FalgModelArrayList.get(position).getDetails());
        try {
            holder.flag_nextCall.setText("Schedule at : " + getStringTimeStamp(FalgModelArrayList.get(position).getNext_call()));
            holder.previous_next_call.setText("Previous  : " + getStringTimeStamp(FalgModelArrayList.get(position).getLast_call()));
        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return FalgModelArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card_flags;
        TextView flag_person_name,flag_nextCall,previous_next_call,flag_about;
        ImageButton call_Image_btn_flag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_flags = itemView.findViewById(R.id.card_flags);
            flag_person_name = itemView.findViewById(R.id.flag_person_name);
            flag_nextCall = itemView.findViewById(R.id.flag_nextCall);
            previous_next_call = itemView.findViewById(R.id.previous_next_call);
            flag_about = itemView.findViewById(R.id.flag_about);
            card_flags.setOnClickListener(this);
            call_Image_btn_flag = itemView.findViewById(R.id.call_Image_btn_flag);
            call_Image_btn_flag.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_flags:
                    onItemClickListner.onItemClickListner(v, getAdapterPosition(), "engagement");
                    break;
                case R.id.call_Image_btn_flag:
                    onItemClickListner.onItemClickListner(v, getAdapterPosition(), "mobile");
                    break;
            }
        }
    }

    public interface onItemClickListner {
        void onItemClickListner(View view, int position, String type);
    }
    private String getStringTimeStamp(String next_call) {
        Date date = new Date(Long.valueOf(next_call) * 1000L);
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String formatted = format.format(date);
        return formatted;
    }
}
