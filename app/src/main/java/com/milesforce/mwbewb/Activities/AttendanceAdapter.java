package com.milesforce.mwbewb.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.R;

import java.util.ArrayList;

class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {
    Context context;
    ArrayList<DelaysModel> delaysModelArrayList;

    public AttendanceAdapter(Context context, ArrayList<DelaysModel> delaysModels){
        this.context=context;
        this.delaysModelArrayList=delaysModels;

    }
    @NonNull
    @Override
    public AttendanceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.attendance_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.MyViewHolder holder, int position) {
        holder. person_Info_id_attendance.setText(delaysModelArrayList.get(position).getIdentity());
        holder.person_Info_name_attendance.setText(delaysModelArrayList.get(position).getPerson_name());
        holder. person_Info_city_attendance.setText(delaysModelArrayList.get(position).getCity());

    }

    @Override
    public int getItemCount() {
        return delaysModelArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView person_Info_id_attendance,
                 person_Info_name_attendance,
                 person_Info_city_attendance;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            person_Info_id_attendance=itemView.findViewById(R.id.person_Info_id);
            person_Info_name_attendance=itemView.findViewById(R.id.person_Info_name);
            person_Info_city_attendance=itemView.findViewById(R.id.person_Info_city);
        }
    }
}
