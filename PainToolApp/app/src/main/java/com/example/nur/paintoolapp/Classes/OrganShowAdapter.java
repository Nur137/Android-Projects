package com.example.nur.paintoolapp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.nur.paintoolapp.R;
import java.util.ArrayList;

public class OrganShowAdapter extends ArrayAdapter<String> {
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-06-06
     * This class is a adapter class used to fill up the list of Organs visible from patient account
     * When doctor checks his patients attemp to give rating of a particular organ, this list
     * helps to select organ
     */

    public OrganShowAdapter(Context context, ArrayList<String> org) {
        /**
         * This is the constructor of DateShowAdapter Class
         * @param 3 - Context type Object,Arraylist of String type Object
         */
        super(context, 0, org);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * This method id used to generate data in listview
         * @param args integer,View and ViewGroup.
         * @return View Type Object.
         */

        // Get the data item for this position
        String org = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.organ_list, parent, false);
        }
        // Lookup view for data population
        TextView tvOrgan = (TextView) convertView.findViewById(R.id.organ);
        // Populate the data into the template view using the data object
        tvOrgan.setText(org);
        // Return the completed view to render on screen
        return convertView;
    }
}
