package com.milesforce.mwbewb.EngagementFragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.milesforce.mwbewb.Model.LevelsModel;
import com.milesforce.mwbewb.R;

import java.util.List;

public class LevelsCustomAdapter extends ArrayAdapter<LevelsModel> {
    LayoutInflater flater;

    public LevelsCustomAdapter(Context context, int resouceId, int textviewId, List<LevelsModel> list){

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

        LevelsModel rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.listitems_layout, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.levels_items);
            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }
        holder.txtTitle.setText(rowItem.getDefinationLevel());

        return rowview;
    }

    private class viewHolder{
        TextView txtTitle;
    }
}
