package com.example.nur.paintoolapp.UI;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nur.paintoolapp.Classes.Config;
import com.example.nur.paintoolapp.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import com.google.android.gms.common.api.GoogleApiClient;


public class Patient_Home extends AppCompatActivity {

    /**
     * @author Nur Imtiazul Haque
     * @author Tasfia Mashiat
     * @version 1.0
     * @since 2016-16-06
     * This class will provide functionalities to authorized patients
     */


    TextView tv;
    LinearLayout my_prof, rating, my_doc, settings;
    String uid, name;
    Button logout;
    Firebase myFirebaseRef;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__home);

        // Initialization and type casting

        tv = (TextView) findViewById(R.id.user_name);
        Bundle bundle = getIntent().getExtras();
        uid = bundle.getString("pat_user_id");
        my_prof = (LinearLayout) findViewById(R.id.my_prof);
        rating = (LinearLayout) findViewById(R.id.pat_rating);
        my_doc = (LinearLayout) findViewById(R.id.patient_my_doc);
        settings = (LinearLayout) findViewById(R.id.pat_settings);

        myFirebaseRef = new Firebase(Config.FIREBASE_URL);
        logout = (Button) findViewById(R.id.logout_pat);


        myFirebaseRef.child("patient").child(uid).child("name").addValueEventListener(new ValueEventListener() {
            //onDataChange is called every time the name of the User changes in your Firebase Database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Inside onDataChange we can get the data as an Object from the dataSnapshot
                //getValue returns an Object. We can specify the type by passing the type expected as a parameter
                name = dataSnapshot.getValue(String.class);
                tv.setText(name);
            }

            //onCancelled is called in case of any error
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //Layout listeners

        my_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Patient_Home.this, Patient_profile.class);
                in.putExtra("pat_user_name", name);
                startActivity(in);
            }
        });


        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Patient_Home.this, Patient_Rating.class);
                in.putExtra("pat_user_name", uid);
                startActivity(in);
            }
        });


        my_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Patient_Home.this, Patient_Doctor.class);
                startActivity(in);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFirebaseRef.unauth();
                Intent in = new Intent(Patient_Home.this, Front.class);
                startActivity(in);

                finish();

            }
        });



    }


    @Override
    public void onStart() {
        super.onStart();
  }

    @Override
    public void onStop() {
        super.onStop();
    }
}
