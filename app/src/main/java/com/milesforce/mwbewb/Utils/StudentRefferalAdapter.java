package com.milesforce.mwbewb.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.StudentReferenceData;
import com.milesforce.mwbewb.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StudentRefferalAdapter extends RecyclerView.Adapter<StudentRefferalAdapter.studentViewHolder> {
    Context context;
    ArrayList<StudentReferenceData>studentReferenceDataArrayList;
    public StudentRefferalAdapter(Context context, ArrayList<StudentReferenceData>studentReferenceDataArrayList){
        this.context=context;
        this.studentReferenceDataArrayList=studentReferenceDataArrayList;

    }

    @NonNull
    @Override
    public StudentRefferalAdapter.studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new studentViewHolder(LayoutInflater.from(context).inflate(R.layout.student_relation_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentRefferalAdapter.studentViewHolder holder, int position) {
        try {
            holder.referal_name.setText(studentReferenceDataArrayList.get(position).getReferred_person_name());
            holder.referel_date.setText(epochToIso8601(studentReferenceDataArrayList.get(position).getReference_given_date()));
            holder.current_status.setText("Bonus status    "+studentReferenceDataArrayList.get(position).getBonus_status());
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return studentReferenceDataArrayList.size();
    }

    public static class studentViewHolder extends RecyclerView.ViewHolder {
        TextView referal_name, referel_date, current_status;

        public studentViewHolder(@NonNull View itemView) {
            super(itemView);
            referal_name = itemView.findViewById(R.id.referal_name);
            referel_date = itemView.findViewById(R.id.referel_date);
            current_status = itemView.findViewById(R.id.current_status);

        }
    }

    String epochToIso8601(String time) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        long time_long=Long.valueOf(time);
        return sdf.format(new Date(time_long * 1000));
    }

}
