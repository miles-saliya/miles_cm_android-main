package com.milesforce.mwbewb.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.viewHolder> {

    Context context;
    ArrayList<DelaysModel> delaysModelArrayList;
    static OnItemClickListners onItemClickListners;

    public void onItemSelectedListners(OnItemClickListners onItemClickListners) {
        this.onItemClickListners = onItemClickListners;
    }

    public SearchAdapter(Context context, ArrayList<DelaysModel> delaysModelArrayList) {
        this.context = context;
        this.delaysModelArrayList = delaysModelArrayList;
    }

    @NonNull
    @Override
    public SearchAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.search_client_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.viewHolder holder, int position) {
        try {
            holder.untapped_client_name.setText(delaysModelArrayList.get(position).getPerson_name() + " - " + delaysModelArrayList.get(position).getLevel() + " - " + delaysModelArrayList.get(position).getSource());
            if (delaysModelArrayList.get(position).getCompany().equals("") || delaysModelArrayList.get(position).getCompany().equals(null)) {
                holder.company_name_search.setText(" - ");
            } else {
                holder.company_name_search.setText(delaysModelArrayList.get(position).getCompany());
            }
            if (delaysModelArrayList.get(position).getDesignation().equals("") || delaysModelArrayList.get(position).getDesignation().equals(null)) {
                holder.designation_search.setText(" - ");
            } else {
                holder.designation_search.setText(String.valueOf(delaysModelArrayList.get(position).getDesignation()));
            }
        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return delaysModelArrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView untapped_client_name, company_name_search, designation_search;
        CardView card_search;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            untapped_client_name = itemView.findViewById(R.id.untapped_client_name);
            company_name_search = itemView.findViewById(R.id.company_name_search);
            designation_search = itemView.findViewById(R.id.designation_search);
            card_search = itemView.findViewById(R.id.card_search);
            card_search.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListners.onItemClickedListners(v, getAdapterPosition());
        }
    }

    public interface OnItemClickListners {
        void onItemClickedListners(View view, int posi);

    }
}
