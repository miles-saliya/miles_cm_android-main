package com.milesforce.mwbewb.EngagementFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.EmailModel;
import com.milesforce.mwbewb.R;

import java.util.ArrayList;

class EMailInfoAdapter extends RecyclerView.Adapter<EMailInfoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<EmailModel> emailModelArrayList;
    static OnClickListeners onClickListeners;


    public void OnItemSelectedListners(OnClickListeners onClickListeners) {
        this.onClickListeners = onClickListeners;

    }

    public EMailInfoAdapter(Context context, ArrayList<EmailModel> emailModelArrayList) {
        this.context = context;
        this.emailModelArrayList = emailModelArrayList;

    }

    @NonNull
    @Override
    public EMailInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.email_infos_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EMailInfoAdapter.ViewHolder holder, int position) {
        holder.client_emails_number.setText(emailModelArrayList.get(position).getMasked_email());

    }

    @Override
    public int getItemCount() {
        return emailModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView client_emails_number;
        CardView card_email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            client_emails_number = itemView.findViewById(R.id.client_emails_number);
            card_email = itemView.findViewById(R.id.card_email);
            card_email.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListeners.itemSelected(v, getAdapterPosition(), "");

        }
    }

    public interface OnClickListeners {
        void itemSelected(View v, int position, String type);

    }
}
