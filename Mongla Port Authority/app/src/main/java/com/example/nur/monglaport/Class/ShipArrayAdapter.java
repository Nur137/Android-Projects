package com.example.nur.monglaport.Class;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nur.monglaport.R;


import java.util.ArrayList;

/**
 * Created by nur on 11/29/16.
 */

public class ShipArrayAdapter extends ArrayAdapter<Shp> {

    public ShipArrayAdapter(Context context, ArrayList<Shp> equipsArrayList)
    {
        super(context, 0, equipsArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Shp shp = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_2, parent, false);
        }
        
        final TextView slno = (TextView) convertView.findViewById(R.id.text1);
        final TextView name = (TextView) convertView.findViewById(R.id.text2);
        final TextView location = (TextView) convertView.findViewById(R.id.text3);
        final TextView nature = (TextView) convertView.findViewById(R.id.text4);
        final TextView arrival = (TextView) convertView.findViewById(R.id.text5);
        final TextView etd = (TextView) convertView.findViewById(R.id.text6);
        final TextView agent = (TextView) convertView.findViewById(R.id.text7);

        slno.setText(shp.getSlno());
        name.setText(shp.getName());
        location.setText(shp.getLocation());
        nature.setText(shp.getNature());
        arrival.setText(shp.getArrival());
        etd.setText(shp.getEtd());
        agent.setText(shp.getAgent());


        return convertView;
    }
}
