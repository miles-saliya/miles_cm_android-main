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
import com.milesforce.mwbewb.Model.NetEnquiry;
import com.milesforce.mwbewb.Model.NetEnquiryData;
import com.milesforce.mwbewb.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NetEnquiryAdapter extends RecyclerView.Adapter<NetEnquiryAdapter.NetEnquiresViewHolder> {
    static OnClickListner OnClickListner;
    ArrayList<NetEnquiryData> netEnquireModelArrayList;
    Context context;

    public void onItemClickListners(OnClickListner onClickListeners) {
        this.OnClickListner = onClickListeners;


    }

    public NetEnquiryAdapter(Context context, ArrayList<NetEnquiryData> netEnquireModelArrayList) {
        this.context = context;
        this.netEnquireModelArrayList = netEnquireModelArrayList;

    }

    @NonNull
    @Override
    public NetEnquiryAdapter.NetEnquiresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NetEnquiresViewHolder(LayoutInflater.from(context).inflate(R.layout.net_enquire, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NetEnquiryAdapter.NetEnquiresViewHolder holder, int position) {
        holder.enq_date_res.setText(netEnquireModelArrayList.get(position).getEnquiry_date());
        if (netEnquireModelArrayList.get(position).getDuplicateCount() > 1)
            holder.enquiry_person_name.setText(netEnquireModelArrayList.get(position).getName()+" - " +netEnquireModelArrayList.get(position).getCourse()+ " : " + netEnquireModelArrayList.get(position).getDuplicateCount());
        else
            holder.enquiry_person_name.setText(netEnquireModelArrayList.get(position).getName()+" - " +netEnquireModelArrayList.get(position).getCourse());
        holder.name_phone_res.setText(netEnquireModelArrayList.get(position).getMobile());
        holder.email_new_res.setText(netEnquireModelArrayList.get(position).getEmail());
        holder.details_new_res.setText(netEnquireModelArrayList.get(position).getDetails());
        holder.enq_form_res.setText(netEnquireModelArrayList.get(position).getNet_enquiry_type());
    }

    @Override
    public int getItemCount() {
        return netEnquireModelArrayList.size();
    }

    public static class NetEnquiresViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card_enquiry;
        ImageButton call_Image_btn_new_enq;
        TextView enquiry_person_name, name_phone_res, email_new_res, details_new_res, enq_date_res, enq_form_res;

        public NetEnquiresViewHolder(@NonNull View itemView) {
            super(itemView);
            enquiry_person_name = itemView.findViewById(R.id.enquiry_person_name);
            card_enquiry = itemView.findViewById(R.id.card_enquiry);
            call_Image_btn_new_enq = itemView.findViewById(R.id.call_Image_btn_new_enq);
            name_phone_res = itemView.findViewById(R.id.name_phone_res);
            email_new_res = itemView.findViewById(R.id.email_new_res);
            details_new_res = itemView.findViewById(R.id.details_new_res);
            enq_date_res = itemView.findViewById(R.id.enq_date_res);
            enq_form_res = itemView.findViewById(R.id.enq_form_res);
            card_enquiry.setOnClickListener(this);
            call_Image_btn_new_enq.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_enquiry:
                    OnClickListner.itemSelected(v, getAdapterPosition(), "engagement");
                    break;
                case R.id.call_Image_btn_new_enq:
                    OnClickListner.itemSelected(v, getAdapterPosition(), "mobile");
                    break;

            }

        }
    }

    public interface OnClickListner {
        void itemSelected(View v, int position, String type);

    }

    private String getStringTimeStamp(String next_call) {
        Date date = new Date(Long.valueOf(next_call) * 1000L);
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String formatted = format.format(date);
        return formatted;
    }
}
