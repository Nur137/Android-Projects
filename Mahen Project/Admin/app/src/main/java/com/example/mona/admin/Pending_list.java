package com.example.mona.admin;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class Pending_list extends ArrayAdapter<User> {


    public Pending_list(Context context, ArrayList<User> user)
    {
        super(context, 0, user);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pending_user_list, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);

        name.setText(user.getFull_name());

        CircleImageView img=(CircleImageView) convertView.findViewById(R.id.pic);
        String pp=user.getPropic();
        byte[] decodedString = Base64.decode(pp, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        img.setImageBitmap(decodedByte);
        return convertView;
    }
}

