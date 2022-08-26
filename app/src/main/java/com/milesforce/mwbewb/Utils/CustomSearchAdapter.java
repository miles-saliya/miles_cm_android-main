package com.milesforce.mwbewb.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.R;

import java.util.ArrayList;

public class CustomSearchAdapter extends BaseAdapter {
    Context context;
    int flags[];
    String[] countryNames;
    LayoutInflater inflter;
    ArrayList<DelaysModel> delaysModelArrayList;

    public CustomSearchAdapter(Context applicationContext, ArrayList<DelaysModel> delaysModelArrayList) {
        this.context = applicationContext;
        this.delaysModelArrayList = delaysModelArrayList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return delaysModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.customadapter, null);
        TextView names = (TextView) view.findViewById(R.id.search_result_list);
        names.setText(delaysModelArrayList.get(i).getIdentity()+" -  "+delaysModelArrayList.get(i).getPerson_name()+" -  "+delaysModelArrayList.get(i).getLevel());
        return view;
    }
}
