package com.example.mona.mahen.Account;
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
import android.telephony.SmsManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.R;
import com.example.mona.mahen.UI.Front;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
public class SampleFragment extends Fragment{


    private static final String ARG_POSITION = "position";
    SharedPreferences preferences;
    private int position;

    Firebase firebase;

    public static SampleFragment newInstance(int position) {
        SampleFragment f = new SampleFragment();
        // The Bundle contains information passed between activities
        Bundle b = new Bundle();

        // Save the current position value
        b.putInt(ARG_POSITION, position);

        // Add the position value to the new Fragment
        f.setArguments(b);
        return f;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        Firebase.setAndroidContext(this.getActivity());
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        switch (position) {
            case 0:
                // Customer Map And position
                rootView = inflater.inflate(R.layout.c_map, container, false);

                final Button loc=(Button)rootView.findViewById(R.id.loc);
                final Button logout=(Button)rootView.findViewById(R.id.logout);
                loc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final LocationManager manager = (LocationManager) getActivity().getSystemService( Context.LOCATION_SERVICE );

                        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                            buildAlertMessageNoGps();

                        }else{
                            Intent intent = new Intent(getActivity(),MapsActivity.class);
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
                //Customer Work
                rootView = inflater.inflate(R.layout.work, container, false);


                final LinearLayout post_work=(LinearLayout) rootView.findViewById(R.id.pw);
                final LinearLayout work_status=(LinearLayout) rootView.findViewById(R.id.work);
                final LinearLayout rate_pay=(LinearLayout) rootView.findViewById(R.id.rp);

                post_work.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent in=new Intent(getActivity(),Posted_Work_Worker_Type.class);
                        startActivity(in);
                    }
                });


                work_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(getActivity(),Work_Status.class);
                        startActivity(in);
                    }
                });

                rate_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(getActivity(),RateAndPay.class);
                        startActivity(in);
                    }
                });



                break;
            case 2:
                //Customer Balance
                rootView = inflater.inflate(R.layout.balance, container, false);
                final Button recharge=(Button)rootView.findViewById(R.id.recharge);
                final TextView account=(TextView)rootView.findViewById(R.id.acc);
                final EditText edit_recharge=(EditText) rootView.findViewById(R.id.edit_recharge);
                final Firebase firebase= new Firebase(Config.FIREBASE_URL);
                final String c_active=preferences.getString("cust_id","");

                firebase.child("Reg_users").child("customer").child(c_active).child("account").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        account.setText(value);

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });


                recharge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit_recharge.getText().toString().equalsIgnoreCase(""))
                        {
                            Toast.makeText(getActivity(),"Enter amount to recharge",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Firebase f2=firebase.child("Admin").child("Recharge_req").push();
                            String idr=f2.getKey();
                            f2.child("id").setValue(idr);
                            f2.child("amount").setValue(edit_recharge.getText().toString());
                            f2.child("cust_id").setValue(c_active);
                            Intent in=new Intent(getContext(),MainActivity.class);
                            startActivity(in);
                            Toast.makeText(getActivity(),"Your Request is placed in queue",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                break;
            case 3:
                //Customer Profile


                rootView = inflater.inflate(R.layout.profile, container, false);

                final Button update=(Button)rootView.findViewById(R.id.update);
                final TextView id=(TextView)rootView.findViewById(R.id.id);
                final TextView name=(TextView) rootView.findViewById(R.id.name);
                final TextView sex=(TextView) rootView.findViewById(R.id.sex);

                final EditText pass=(EditText)rootView.findViewById(R.id.pass);
                final EditText email=(EditText) rootView.findViewById(R.id.email);
                final EditText contact = (EditText) rootView.findViewById(R.id.contact);
                final EditText address = (EditText) rootView.findViewById(R.id.address);

                final CircleImageView img=(CircleImageView) rootView.findViewById(R.id.pic);


                String c_active2=preferences.getString("cust_id","");

                Firebase firebase2= new Firebase(Config.FIREBASE_URL);

                firebase2.child("Reg_users").child("customer").child(c_active2).child("id").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        id.setText(value);

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                firebase2.child("Reg_users").child("customer").child(c_active2).child("full_name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        name.setText(value);


                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                firebase2.child("Reg_users").child("customer").child(c_active2).child("propic").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();

                        byte[] decodedString = Base64.decode(value, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        img.setImageBitmap(decodedByte);


                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                firebase2.child("Reg_users").child("customer").child(c_active2).child("pass").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        pass.setText(value);


                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                firebase2.child("Reg_users").child("customer").child(c_active2).child("sex").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        sex.setText(value);


                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                firebase2.child("Reg_users").child("customer").child(c_active2).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        email.setText(value);



                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                firebase2.child("Reg_users").child("customer").child(c_active2).child("contact").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        contact.setText(value);



                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                firebase2.child("Reg_users").child("customer").child(c_active2).child("address").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();
                        address.setText(value);


                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });



                update.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String c_active=preferences.getString("cust_id","");
                        Firebase firebase=new Firebase(Config.FIREBASE_URL);
                        firebase.child("Reg_users").child("customer").child(c_active).child("pass").setValue(pass.getText().toString());
                        firebase.child("Reg_users").child("customer").child(c_active).child("email").setValue(email.getText().toString());
                        firebase.child("Reg_users").child("customer").child(c_active).child("address").setValue(address.getText().toString());
                        firebase.child("Reg_users").child("customer").child(c_active).child("contact").setValue(contact.getText().toString());
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
                    public void onClick(final DialogInterface dialog,  final int id) {
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
    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

}