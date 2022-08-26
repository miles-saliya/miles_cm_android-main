package com.milesforce.mwbewb.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.MobileNumberModel;
import com.milesforce.mwbewb.R;

import java.util.ArrayList;

public class MobileAdapter extends RecyclerView.Adapter<MobileAdapter.viewHolder> {
    static Context context;
    static ArrayList<MobileNumberModel> mobileNumberModelArrayList;

    public MobileAdapter(Context context, ArrayList<MobileNumberModel> mobileNumberModelArrayList) {
        this.context = context;
        this.mobileNumberModelArrayList = mobileNumberModelArrayList;
    }

    @NonNull
    @Override
    public MobileAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.mobilelayout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MobileAdapter.viewHolder holder, int position) {
        holder.client_mobile_number.setText(mobileNumberModelArrayList.get(position).getMasked_number());

    }

    @Override
    public int getItemCount() {
        return mobileNumberModelArrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView client_mobile_number;
        CardView card_mobile;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            client_mobile_number = itemView.findViewById(R.id.client_mobile_number);
            card_mobile = itemView.findViewById(R.id.card_mobile);
            card_mobile.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MobileNumberModel mobileNumberModel = mobileNumberModelArrayList.get(getAdapterPosition());
            TriggerCallWIthTelephoneManager.TriggerCall(mobileNumberModel.getPhone_number(), context);
        }
    }
}
