package com.example.nur.monglaport.Class;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nur.monglaport.R;
import com.example.nur.monglaport.UI.Containers;


import java.util.ArrayList;

/**
 * Created by nur on 11/18/16.
 */

public class FpArrayAdapter extends ArrayAdapter<Fpofficer> {

    public FpArrayAdapter(Context context, ArrayList<Fpofficer> fpofficers)
    {
        super(context, 0, fpofficers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fpofficer fpofficer = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_6, parent, false);
        }

        final TextView  service= (TextView) convertView.findViewById(R.id.serv);
        final TextView  off= (TextView) convertView.findViewById(R.id.off);
        final TextView  tel= (TextView) convertView.findViewById(R.id.tel);

        service.setText(fpofficer.getService());
        off.setText(fpofficer.getOfficer());
        tel.setText(fpofficer.getTel());


        return convertView;
    }
}
