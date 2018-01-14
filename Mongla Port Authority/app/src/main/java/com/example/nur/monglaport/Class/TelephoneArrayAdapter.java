package com.example.nur.monglaport.Class;

import android.content.Context;
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

public class TelephoneArrayAdapter extends ArrayAdapter<Teleph> {

    public TelephoneArrayAdapter(Context context, ArrayList<Teleph> telephs)
    {
        super(context, 0, telephs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Teleph teleph = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_5, parent, false);
        }

        final TextView  sl= (TextView) convertView.findViewById(R.id.date);
        final TextView  desig= (TextView) convertView.findViewById(R.id.river);
        final TextView  tel= (TextView) convertView.findViewById(R.id.time);
        final TextView  email= (TextView) convertView.findViewById(R.id.height);

        sl.setText(teleph.getSlno());
        desig.setText(teleph.getDesignation());
        tel.setText(teleph.getTel());
        email.setText(teleph.getEmail());

        return convertView;
    }
}
