package com.milesforce.mwbewb.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.milesforce.mwbewb.EngagementFragments.LevelsCustomAdapter;
import com.milesforce.mwbewb.Model.ClassData;
import com.milesforce.mwbewb.Model.LevelsModel;
import com.milesforce.mwbewb.R;

import java.util.List;

public class CustomCallAdapter extends ArrayAdapter<ClassData> {
    LayoutInflater flater;

    public CustomCallAdapter(Context context, int resouceId, int textviewId, List<ClassData> list){

        super(context,resouceId,textviewId, list);
//        flater = context.getLayoutInflater();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        ClassData rowItem = getItem(position);

        CustomCallAdapter.viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new CustomCallAdapter.viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.class_layout, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.class_items);
            rowview.setTag(holder);
        }else{
            holder = (CustomCallAdapter.viewHolder) rowview.getTag();
        }
        holder.txtTitle.setText(rowItem.getClass_name());

        return rowview;
    }

    private class viewHolder{
        TextView txtTitle;
    }

}
