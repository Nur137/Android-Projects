package com.example.mona.mahen.Class;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mona.mahen.Database.Notify_cust;
import com.example.mona.mahen.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nur on 9/15/2016.
 */
public class WorkerArrayAdapter extends ArrayAdapter<Notify_cust> {


    public WorkerArrayAdapter(Context context, ArrayList<Notify_cust> notify)
    {
        super(context, 0, notify);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Notify_cust not = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.applied_worker_list, parent, false);
        }
        final TextView workr = (TextView) convertView.findViewById(R.id.worker);
        final CircleImageView im=(CircleImageView) convertView.findViewById(R.id.pic);

        Firebase firebase=new Firebase(Config.FIREBASE_URL);

        firebase.child("Reg_users").child("worker").child(not.getWorker_id()).child("un").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workr.setText(((String) dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        workr.setText(not.getWorker_id());

        final RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.Rating);


        Firebase firebase2=new Firebase(Config.FIREBASE_URL);
        firebase2.child("Reg_users").child("worker").child(not.getWorker_id()).child("rating").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ratingBar.setRating(Float.parseFloat((String) dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        firebase.child("Reg_users").child("worker").child(not.getWorker_id()).child("propic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();

                byte[] decodedString = Base64.decode(value, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                im.setImageBitmap(decodedByte);
                // do your stuff here with value
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        return convertView;
    }
}

