package com.example.mona.mahen.Worker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mona.mahen.Account.MapsActivity;
import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.R;
import com.example.mona.mahen.UI.Front;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class WSampleFragment  extends Fragment {

    private static final String ARG_POSITION = "position";

    SharedPreferences preferences;

    private int position;

    private String active;


    public static WSampleFragment newInstance(int position) {
        WSampleFragment f = new WSampleFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.page2, container, false);
        Firebase.setAndroidContext(this.getActivity());
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        active=preferences.getString("wrkr_id","");



        switch (position) {
            case 0:

                //do your custom view
                // Worker position and map
                rootView = inflater.inflate(R.layout.w_map, container, false);

                final Button loc = (Button) rootView.findViewById(R.id.loc);
                final Button logout=(Button)rootView.findViewById(R.id.logout);

                loc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            buildAlertMessageNoGps();

                        } else {
                            Intent intent = new Intent(getActivity(), MapsActivity.class);
                            startActivity(intent);
                        }


                    }
                });

                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear();
                        editor.commit();
                        Intent in=new Intent(getActivity(), Front.class);
                        startActivity(in);
                        Toast.makeText(getActivity(),"Logout Successfully",Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case 1:
                //work
                rootView = inflater.inflate(R.layout.w_work, container, false);
                final LinearLayout my_work=(LinearLayout)rootView.findViewById(R.id.work);
                final LinearLayout apply_for_work=(LinearLayout) rootView.findViewById(R.id.afw);

                my_work.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(getActivity(),My_Work.class);
                        startActivity(in);
                    }
                });

                apply_for_work.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(getActivity(),Apply_For_Work.class);
                        startActivity(in);
                    }
                });
                 break;
            case 2:
                //Worker Balance
                rootView = inflater.inflate(R.layout.w_balance, container, false);
                final Button withdraw=(Button)rootView.findViewById(R.id.withdraw);
                final TextView account=(TextView)rootView.findViewById(R.id.acc);
                final EditText edit_withdraw=(EditText) rootView.findViewById(R.id.edit_withdraw);
                final Firebase firebase2= new Firebase(Config.FIREBASE_URL);

                firebase2.child("Reg_users").child("worker").child(active).child("account").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        account.setText(value);
                        // do your stuff here with value

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                withdraw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit_withdraw.getText().toString().equalsIgnoreCase(""))
                        {
                            Toast.makeText(getActivity(),"Enter an amount to recharge",Toast.LENGTH_LONG).show();
                        }
                        else {

                            Firebase f2 = firebase2.child("Admin").child("Withdraw_req").push();
                            String idw = f2.getKey();
                            f2.child("id").setValue(idw);
                            f2.child("amount").setValue(edit_withdraw.getText().toString());
                            f2.child("worker_id").setValue(active);
                            Intent in = new Intent(getContext(), Main.class);
                            startActivity(in);
                            Toast.makeText(getActivity(), "Your Request is placed in queue", Toast.LENGTH_SHORT).show();
                        }

                    }
                });





                break;
            case 3:
                //Worker Profile
                rootView = inflater.inflate(R.layout.w_profile, container, false);

                final Button update = (Button) rootView.findViewById(R.id.update);
                final TextView id = (TextView) rootView.findViewById(R.id.id);
                final TextView pass = (TextView) rootView.findViewById(R.id.pass);
                final TextView name = (TextView) rootView.findViewById(R.id.name);
                final TextView sex = (TextView) rootView.findViewById(R.id.sex);
                final EditText email = (EditText) rootView.findViewById(R.id.email);
                final EditText contact = (EditText) rootView.findViewById(R.id.contact);
                final EditText address = (EditText) rootView.findViewById(R.id.address);



                final CircleImageView img = (CircleImageView) rootView.findViewById(R.id.pic);
                String pp;

                final CircleImageView ip = (CircleImageView) rootView.findViewById(R.id.idpic);
                String idp;


                Firebase firebase = new Firebase(Config.FIREBASE_URL);

                firebase.child("Reg_users").child("worker").child(active).child("id").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        id.setText(value);

                        // do your stuff here with value
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                firebase.child("Reg_users").child("worker").child(active).child("full_name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        name.setText(value);

                        // do your stuff here with value

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                firebase.child("Reg_users").child("worker").child(active).child("propic").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();

                        byte[] decodedString = Base64.decode(value, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        img.setImageBitmap(decodedByte);

                        // do your stuff here with value

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                firebase.child("Reg_users").child("worker").child(active).child("idpic").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();

                        byte[] decodedString = Base64.decode(value, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        ip.setImageBitmap(decodedByte);

                        // do your stuff here with value

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                firebase.child("Reg_users").child("worker").child(active).child("pass").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        pass.setText(value);

                        // do your stuff here with value

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                firebase.child("Reg_users").child("worker").child(active).child("sex").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        sex.setText(value);

                        // do your stuff here with value

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                firebase.child("Reg_users").child("worker").child(active).child("contact").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        contact.setText(value);

                        // do your stuff here with value

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                firebase.child("Reg_users").child("worker").child(active).child("address").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        address.setText(value);

                        // do your stuff here with value

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });





                firebase.child("Reg_users").child("worker").child(active).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        email.setText(value);

                        // do your stuff here with value

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                update.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Firebase firebase = new Firebase(Config.FIREBASE_URL);
                        firebase.child("Reg_users").child("worker").child(active).child("pass").setValue(pass.getText().toString());
                        firebase.child("Reg_users").child("worker").child(active).child("email").setValue(email.getText().toString());
                        firebase.child("Reg_users").child("worker").child(active).child("address").setValue(address.getText().toString());
                        firebase.child("Reg_users").child("worker").child(active).child("contact").setValue(contact.getText().toString());


                    }
                });


                break;


        }

        return rootView;
    }

    //for enable gps
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        buildAlertMessageNoGps();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }

}