package com.example.mona.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Nur on 9/17/2016.
 */
public class WithdrawAdapter extends ArrayAdapter<Withdraw> {


    public WithdrawAdapter(Context context, ArrayList<Withdraw> withdraws)
    {
        super(context, 0, withdraws);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Withdraw withdraw = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
        }
        final TextView name = (TextView) convertView.findViewById(R.id.name);
        final Firebase firebase=new Firebase(Config.FIREBASE_URL);
        firebase.child("Reg_users").child("worker").child(withdraw.getWorker_id()).child("un").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.setText((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        final TextView account = (TextView) convertView.findViewById(R.id.amount);
        String b= "$";
        b+=withdraw.getBal().toString();
        account.setText(b);



        return convertView;
    }

}
