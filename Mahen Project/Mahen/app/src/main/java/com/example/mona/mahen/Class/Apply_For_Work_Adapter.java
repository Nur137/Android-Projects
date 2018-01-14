package com.example.mona.mahen.Class;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mona.mahen.Database.Notify_worker;
import com.example.mona.mahen.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Apply_For_Work_Adapter  extends ArrayAdapter<Notify_worker> {


    public Apply_For_Work_Adapter(Context context, ArrayList<Notify_worker> notify)
    {
        super(context, 0, notify);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Notify_worker not = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.apply_for_work_list, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(not.getWork_title());

        final TextView provider = (TextView) convertView.findViewById(R.id.provider);

        final CircleImageView pic= (CircleImageView) convertView.findViewById(R.id.pic);


        Firebase firebase=new Firebase(Config.FIREBASE_URL);
        firebase.child("Reg_users").child("customer").child(not.getCust_id()).child("un").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                provider.setText((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        firebase.child("Reg_users").child("customer").child(not.getCust_id()).child("propic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();

                byte[] decodedString = Base64.decode(value, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                pic.setImageBitmap(decodedByte);



                // do your stuff here with value
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });





        return convertView;
    }
}


