package com.example.mona.mahen.Class;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mona.mahen.Database.Work_Under;
import com.example.mona.mahen.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Nur on 9/15/2016.
 */
public class WorkUnderAdapter extends ArrayAdapter<Work_Under> {


    public WorkUnderAdapter(Context context, ArrayList<Work_Under> work_under)
    {
        super(context, 0, work_under);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Work_Under work_under = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_work_list, parent, false);
        }
        final TextView title = (TextView) convertView.findViewById(R.id.title);

        final TextView customer = (TextView) convertView.findViewById(R.id.customer);

        final Firebase firebase=new Firebase(Config.FIREBASE_URL);

        title.setText(work_under.getWork_title());

        firebase.child("Reg_users").child("customer").child(work_under.getCust_id()).child("full_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                customer.setText(value);
                // do your stuff here with value
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        return convertView;
    }
}