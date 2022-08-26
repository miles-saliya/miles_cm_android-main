package com.milesforce.mwbewb.Utils;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

import android.content.Context;

import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.milesforce.mwbewb.Activities.MainActivity;
import com.milesforce.mwbewb.Model.SuggestModel;
import com.milesforce.mwbewb.R;

import java.util.ArrayList;
import java.util.List;

public class AutoSuggestAdapter extends ArrayAdapter<SuggestModel> implements Filterable {
    private ArrayList<SuggestModel> mlistData;
    Context context;
    int layoutResourceId;

    public AutoSuggestAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mlistData = new ArrayList<>();
       this.context=context;
       this.layoutResourceId=resource;
    }
    public void setData(ArrayList<SuggestModel> list) {
        mlistData.clear();
        mlistData.addAll(list);
    }

    @Override
    public int getCount() {
        return mlistData.size();
    }

    @Nullable
    @Override
    public SuggestModel getItem(int position) {
        return mlistData.get(position);
    }

    /**
     * Used to Return the full object directly from adapter.
     *
     * @param position
     * @return
     */
    public SuggestModel getObject(int position) {
        return mlistData.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String s =mlistData.get(position).getPerson_name();
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.referral_layout, parent, false);
        }
        TextView textViewItem =convertView.findViewById(R.id.referel_suggest);
        textViewItem.setText(mlistData.get(position).getIdentity()+" - "+mlistData.get(position).getPerson_name()+" - "+mlistData.get(position).getLevel());
         return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter dataFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = mlistData;
                    filterResults.count = mlistData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && (results.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return dataFilter;
    }
}
