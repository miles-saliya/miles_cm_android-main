package com.milesforce.mwbewb.EngagementFragments;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.EmailModel;
import com.milesforce.mwbewb.R;

import java.util.ArrayList;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewCreated> {
    Context context;
    ArrayList<EmailModel> emailModelArrayList;
    static onItemClickListners onItemClickListners;

    public void onItemSelectedListners(onItemClickListners onItemClickListners) {
        this.onItemClickListners = onItemClickListners;

    }


    public EmailAdapter(Context context, ArrayList<EmailModel> emailModelArrayList) {
        this.context = context;
        this.emailModelArrayList = emailModelArrayList;

    }

    @NonNull
    @Override
    public EmailAdapter.ViewCreated onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewCreated(LayoutInflater.from(context).inflate(R.layout.email_info_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmailAdapter.ViewCreated holder, int position) {

    }

    @Override
    public int getItemCount() {
        return emailModelArrayList.size();
    }

    public static class ViewCreated extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView email_icon;

        public ViewCreated(@NonNull View itemView) {
            super(itemView);
            email_icon = itemView.findViewById(R.id.email_icon);
            email_icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListners.onItemSelectedListners(v, getAdapterPosition());

        }
    }

    public interface onItemClickListners {
        void onItemSelectedListners(View view, int position);
    }

}
