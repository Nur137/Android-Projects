package com.example.nur.paintoolapp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.nur.paintoolapp.Database.Rating;
import com.example.nur.paintoolapp.R;
import java.util.ArrayList;


public class DateShowAdapter extends ArrayAdapter<Rating> {

    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-06-06
     * This class is a adapter class used to fill up the list of date and time visible from doctor account
     * When doctor checks his patients he will see the time and date of patient pain status update
     */

    public DateShowAdapter(Context context, ArrayList<Rating> rating)
    {
        /**
         * This is the constructor of DateShowAdapter Class
         * @param 3 - Context type Object,Arraylist of Rating type Object
         */
        super(context, 0, rating);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * This method id used to generate data in listview
         * @param args integer,View and ViewGroup.
         * @return View Type Object.
         */

        // Get the data item for this position
        Rating rating = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvDay = (TextView) convertView.findViewById(R.id.tvday);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvdate);
        TextView tvtime = (TextView) convertView.findViewById(R.id.tvtime);
        // Populate the data into the template view using the data object
       tvDay.setText(rating.getDay());
        tvDate.setText(rating.getdate());
        tvtime.setText(rating.getTime());
        // Return the completed view to render on screen
        return convertView;
    }
}
