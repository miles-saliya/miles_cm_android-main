package com.milesforce.mwbewb.TabFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.UntappedModel;
import com.milesforce.mwbewb.R;

import java.util.ArrayList;

public class UntabbedAdapter extends RecyclerView.Adapter<UntabbedAdapter.ViewHolder> {

    Context context;
    ArrayList<DelaysModel> untappedModelArrayList;
    static onItemClickListner onItemClickListner;

    public void onItemSelectedListners(onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public UntabbedAdapter(Context context, ArrayList<DelaysModel> untappedModelArrayList) {
        this.untappedModelArrayList = untappedModelArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public UntabbedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.untapped_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UntabbedAdapter.ViewHolder holder, int position) {
        holder.untapped_client_name.setText(untappedModelArrayList.get(position).getPerson_name() + " - " + untappedModelArrayList.get(position).getLevel() + " - " + untappedModelArrayList.get(position).getCompany() + " - " + untappedModelArrayList.get(position).getSource());

    }

    @Override
    public int getItemCount() {
        return untappedModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card_untapped;
        ImageButton untapped_call;
        TextView untapped_client_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_untapped = itemView.findViewById(R.id.card_untapped);
            card_untapped.setOnClickListener(this);
            untapped_call = itemView.findViewById(R.id.untapped_call);
            untapped_call.setOnClickListener(this);
            untapped_client_name = itemView.findViewById(R.id.untapped_client_name);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_untapped:
                    onItemClickListner.onItemClickListner(v, getAdapterPosition(), "engagement");
                    break;
                case R.id.untapped_call:
                    onItemClickListner.onItemClickListner(v, getAdapterPosition(), "mobile");
                    break;
            }
        }
    }

    public interface onItemClickListner {
        void onItemClickListner(View view, int position, String type);
    }

}
