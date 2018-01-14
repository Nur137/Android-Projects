package com.example.nur.monglaport.Class;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nur.monglaport.R;
import com.example.nur.monglaport.UI.Containers;

import java.util.ArrayList;

/**
 * Created by nur on 11/18/16.
 */

public class TideArrayAdapter extends ArrayAdapter<Tide> {

    public TideArrayAdapter(Context context, ArrayList<Tide> tides)
    {
        super(context, 0, tides);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tide tide = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_7, parent, false);
        }

        final TextView  sl= (TextView) convertView.findViewById(R.id.sl);
        final TextView  data= (TextView) convertView.findViewById(R.id.data);
        final ImageView pdf= (ImageView) convertView.findViewById(R.id.pdf);



        sl.setText(tide.getSlno());
        data.setText(tide.getInfo());

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(tide.getLink());

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        });


        return convertView;
    }
}
