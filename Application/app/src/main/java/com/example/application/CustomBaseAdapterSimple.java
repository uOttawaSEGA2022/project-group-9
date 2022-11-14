package com.example.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBaseAdapterSimple extends BaseAdapter {

    Context context;
    String tempListOfChefIDs[];
    String tempListOfReasons[];
    LayoutInflater inflater;
    //All this code is need for the admin to see a list of complaints on his screen
    public CustomBaseAdapterSimple(Context ctx, String[] chefIdList, String[] reasonList) {
        inflater = LayoutInflater.from(ctx);
        this.context = ctx;
        this.tempListOfChefIDs = chefIdList;
        this.tempListOfReasons = reasonList;

    }

    @Override
    public int getCount() {
        return tempListOfChefIDs.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= inflater.inflate(R.layout.activity_custom_list_view_complaints_simple,null);
        TextView chefIDView = (TextView) convertView.findViewById(R.id.chefIDComplaint);
        TextView reasonView = (TextView) convertView.findViewById(R.id.reasonComplaint);
        chefIDView.setText(tempListOfChefIDs[position]);
        reasonView.setText(tempListOfReasons[position]);

        return convertView;
    }
}