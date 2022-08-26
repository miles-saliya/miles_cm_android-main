package com.milesforce.mwbewb.EngagementFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.MobileNumberModel;
import com.milesforce.mwbewb.R;

import java.util.ArrayList;

public class MobileIconAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<MobileNumberModel> mobileNumberModelArrayList;
    static onClickListners onClickListners;

    public void OnitecmClickListners(onClickListners onClickListners) {
        this.onClickListners = onClickListners;
    }

    public MobileIconAdapter(Context context, ArrayList<MobileNumberModel> mobileNumberModelArrayList) {
        this.context = context;
        this.mobileNumberModelArrayList = mobileNumberModelArrayList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.mobile_icon_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mobileNumberModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mobile_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mobile_icon = itemView.findViewById(R.id.mobile_icon);
            mobile_icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListners.getClickedItem(v, getAdapterPosition());
        }
    }


    public interface onClickListners {
        void getClickedItem(View view, int position);
    }
}
