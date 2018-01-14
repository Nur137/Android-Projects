package com.example.ne.bmail.arraylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ne.bmail.R;
import com.example.ne.bmail.classes.ReceiveMail;

import java.util.ArrayList;

/**
 * Created by ICe_Cube on 12/11/2017.
 */

public class ReceiveMailArrayList extends ArrayAdapter<ReceiveMail> {

    public ReceiveMailArrayList(Context context, ArrayList<ReceiveMail> containers)
    {
        super(context, 0, containers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReceiveMail receiveMail = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list1, parent, false);
        }

        final TextView subject = (TextView) convertView.findViewById(R.id.subject);
        final TextView message = (TextView) convertView.findViewById(R.id.message);
        final TextView from = (TextView) convertView.findViewById(R.id.from);

        subject.setText(receiveMail.getSubject());
        message.setText(receiveMail.getMail());
        from.setText(receiveMail.getFrom());
        return convertView;
    }
}