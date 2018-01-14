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

import com.example.mona.mahen.Database.RatWorker;
import com.example.mona.mahen.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nur on 9/18/2016.
 */
public class RateAndPayAdapter extends ArrayAdapter<RatWorker> {

    public RateAndPayAdapter(Context context, ArrayList<RatWorker> ratWorkers)
    {
        super(context, 0, ratWorkers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RatWorker ratWorker= getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rating_list, parent, false);
        }
        final TextView work = (TextView) convertView.findViewById(R.id.work);
        final TextView worker = (TextView) convertView.findViewById(R.id.worker);

        Firebase firebase=new Firebase(Config.FIREBASE_URL);

        firebase.child("Reg_users").child("worker").child(ratWorker.getWorker_id()).child("full_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                worker.setText(((String) dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        work.setText(ratWorker.getWork_title());
        return convertView;
    }
}
