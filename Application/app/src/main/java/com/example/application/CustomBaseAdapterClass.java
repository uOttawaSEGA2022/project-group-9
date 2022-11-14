package com.example.application;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomBaseAdapterClass extends BaseAdapter {

    Context context;
    ArrayList<Complaint> listOfComplaints;
    int currentComplaintCounter;

    LayoutInflater inflater;
    //All this code is need for the admin to see a list of complaints on his screen
    public CustomBaseAdapterClass(Context ctx, ArrayList<Complaint> complaintArrayList) {
        inflater = LayoutInflater.from(ctx);
        this.context = ctx;
        this.listOfComplaints = complaintArrayList;
        currentComplaintCounter = 0;

    }

    @Override
    public int getCount() {
        return listOfComplaints.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setCurrentComplaintCounter() {
        this.currentComplaintCounter++;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= inflater.inflate(R.layout.activity_custom_list_view_complaints_class_complaint,null);

        TextView chefIDView = (TextView) convertView.findViewById(R.id.chefIDComplaint);
        TextView reasonView = (TextView) convertView.findViewById(R.id.reasonComplaint);

        //Increment works
        Log.i("MSG","Size:" + getCount());
        Log.i("MSG", String.valueOf((getCount() > currentComplaintCounter)));
        if (getCount() > currentComplaintCounter) {
            chefIDView.setText("ChefId: " + listOfComplaints.get(currentComplaintCounter).getChefID());
            reasonView.setText("Reason: " + listOfComplaints.get(currentComplaintCounter).getReason());
        }
        currentComplaintCounter++;

        return convertView;
    }
}