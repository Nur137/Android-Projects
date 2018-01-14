package com.example.nur.paintoolapp.UI;

import android.content.Intent;
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

public class Doctor_Home extends AppCompatActivity {
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class will provide functionalities to authorized doctors
     * */

    TextView tv;
    LinearLayout doc_profile, doc_patients,search_patient,doc_settings,doc_qualification;
    String name;
    Button logout;
    Firebase myFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__home);
        // Initialization and type casting

        tv = (TextView) findViewById(R.id.doc_Welcome);
        Bundle bundle = getIntent().getExtras();
        String uid = bundle.getString("doc_user_id");
        doc_profile=(LinearLayout)findViewById(R.id.doc_my_prof);
        doc_patients =(LinearLayout)findViewById(R.id.doc_my_patient);
        search_patient=(LinearLayout)findViewById(R.id.doc_search_patient);
        doc_settings=(LinearLayout)findViewById(R.id.doc_settings);
        doc_qualification=(LinearLayout)findViewById(R.id.doc_qualification);


        myFirebaseRef = new Firebase(Config.FIREBASE_URL);
        logout=(Button)findViewById(R.id.logout_doc);

        myFirebaseRef.child("doctor").child(uid).child("name").addValueEventListener(new ValueEventListener() {
            //onDataChange is called every time the name of the User changes in your Firebase Database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Inside onDataChange we can get the data as an Object from the dataSnapshot
                //getValue returns an Object. We can specify the type by passing the type expected as a parameter
                name = dataSnapshot.getValue(String.class);
                tv.setText("Hello "+name+", ");
            }
            //onCancelled is called in case of any error
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //Layout listeners
        doc_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Doctor_Home.this,Doctor_profile.class);
                in.putExtra("doc_name", name);
                startActivity(in);

            }
        });


        doc_patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Doctor_Home.this,Doctor_patients.class);
                in.putExtra("doc_name", name);
                startActivity(in);
            }
        });

        search_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Doctor_Home.this,Doctor_search_patient.class);
                in.putExtra("doc_name", name);
                startActivity(in);


            }
        });

        doc_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Doctor_Home.this,Doctor_settings.class);
                startActivity(in);

            }
        });

        doc_qualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Doctor_Home.this,Doctor_Qualification.class);
                startActivity(in);

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFirebaseRef.unauth();
                Intent in=new Intent(Doctor_Home.this, Front.class);
                startActivity(in);
                finish();

            }
        });

    }


}
