package com.example.nur.monglaport.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nur.monglaport.R;

import java.util.ArrayList;

/**
 * Created by nur on 11/18/16.
 */

public class EquipArrayAdapter extends ArrayAdapter<Equips> {

    public EquipArrayAdapter(Context context, ArrayList<Equips> equipsArrayList)
    {
        super(context, 0, equipsArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Equips equips = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_1, parent, false);
        }
        final TextView slno = (TextView) convertView.findViewById(R.id.text1);
        final TextView desc = (TextView) convertView.findViewById(R.id.text2);
        final TextView cap = (TextView) convertView.findViewById(R.id.text3);
        final TextView quan = (TextView) convertView.findViewById(R.id.text4);
        final TextView year = (TextView) convertView.findViewById(R.id.text5);

        slno.setText(equips.getSlno());
        desc.setText(equips.getDesc());
        cap.setText(equips.getCapacity());
        quan.setText(equips.getQuantity());
        year.setText(equips.getYear());

        return convertView;
    }
}
