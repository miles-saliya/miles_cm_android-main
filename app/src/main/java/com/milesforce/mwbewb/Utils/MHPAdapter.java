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

import java.util.ArrayList;

public class MHPAdapter extends RecyclerView.Adapter<MHPAdapter.ViewHolder> {
    Context context;
    ArrayList<DelaysModel> FalgModelArrayList;
    static onItemClickListner onItemClickListner;

    public MHPAdapter(Context context, ArrayList<DelaysModel> FalgModelArrayList) {
        this.context = context;
        this.FalgModelArrayList = FalgModelArrayList;

    }

    public void onItemSelectedListners(MHPAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    @NonNull
    @Override
    public MHPAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.mhp_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MHPAdapter.ViewHolder holder, int position) {
        holder.mhp_person_name.setText(FalgModelArrayList.get(position).getPerson_name() + " - " + FalgModelArrayList.get(position).getLevel() + " - " + FalgModelArrayList.get(position).getCompany());
        holder.mhp_type.setText("MHP TYPE : "+FalgModelArrayList.get(position).getSource());
        holder.company.setText("Company : "+FalgModelArrayList.get(position).getCompany());
        holder.experiance.setText("Experience : "+FalgModelArrayList.get(position).getExperience());
        holder.designaton.setText("Designation : "+FalgModelArrayList.get(position).getDesignation());


    }

    @Override
    public int getItemCount() {
        return FalgModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card_mhp;
        TextView mhp_person_name,mhp_type,company,experiance,designaton;
        ImageButton call_Image_btn_flag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            call_Image_btn_flag = itemView.findViewById(R.id.call_Image_btn_flag);
            call_Image_btn_flag.setOnClickListener(this);
            card_mhp = itemView.findViewById(R.id.card_mhp);
            card_mhp.setOnClickListener(this);
            mhp_person_name = itemView.findViewById(R.id.mhp_person_name);
            mhp_type = itemView.findViewById(R.id.mhp_type);
            company = itemView.findViewById(R.id.company);
            experiance = itemView.findViewById(R.id.experiance);
            designaton = itemView.findViewById(R.id.designaton);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_mhp:
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
}
