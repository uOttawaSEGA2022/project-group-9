package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String tempListOfChefIDs[];
    String tempListOfReasons[];
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, String[] chefIdList, String[] reasonList) {
        inflater = LayoutInflater.from(ctx);
        this.context = ctx;
        this.tempListOfChefIDs =chefIdList;
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
        convertView= inflater.inflate(R.layout.activity_custom_list_view_complaints,null);
        TextView chefIDView = (TextView) convertView.findViewById(R.id.chefIDComplaint);
        TextView reasonView = (TextView) convertView.findViewById(R.id.reasonComplaint);
        chefIDView.setText(tempListOfChefIDs[position]);
        reasonView.setText(tempListOfChefIDs[position]);

        return convertView;
    }
}