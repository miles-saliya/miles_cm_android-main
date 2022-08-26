package com.milesforce.mwbewb.EngagementFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.R;

import java.util.ArrayList;

public class EducationTagsAdapter extends RecyclerView.Adapter<EducationTagsAdapter.ViewHolder> {
    Context context;
    static ArrayList<String> tags;
    static OnClickListeners onClickListeners;

    public void onItemClickListners(OnClickListeners onClickListeners) {
        this.onClickListeners = onClickListeners;

    }

    public EducationTagsAdapter(Context context, ArrayList<String> tags) {
        this.context = context;
        this.tags = tags;

    }

    @NonNull
    @Override
    public EducationTagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.tag_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EducationTagsAdapter.ViewHolder holder, int position) {
        holder.text_tag.setText(tags.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text_tag;
        ImageView close_tag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_tag = itemView.findViewById(R.id.text_tag);
            close_tag = itemView.findViewById(R.id.close_tag);
            close_tag.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.close_tag:
                    onClickListeners.itemSelected(v, getAdapterPosition(), "");
                    break;

            }

        }
    }


    public interface OnClickListeners {
        void itemSelected(View v, int position, String type);

    }
}
