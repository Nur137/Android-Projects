package com.example.mona.mahen.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.mona.mahen.Database.Work;
import com.example.mona.mahen.R;

import java.util.ArrayList;

/**
 * Created by Nur on 9/14/2016.
 */
public class WorkStatusAdapter extends ArrayAdapter<Work> {


public WorkStatusAdapter(Context context, ArrayList<Work> work)
        {
        super(context, 0, work);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {

        Work wor = getItem(position);

        if (convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.work_status_list, parent, false);
        }
        TextView work = (TextView) convertView.findViewById(R.id.work);
        work.setText(wor.getTitle());


        return convertView;
        }
}

