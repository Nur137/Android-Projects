package com.example.nur.paintoolapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.nur.paintoolapp.Classes.Config;
import com.example.nur.paintoolapp.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;




public class Front extends AppCompatActivity {
    /**
     * @author  Nur Imtiazul Haque
     * @version 1.0
     * @since   2016-06-05
     * The function of this class is to check if the user is already authorized or not
     * Authorized members will be directly taken to their homepage
     * Unauthorized members will be able to choose whether he is a doctor or patient and will be allowed to login or signup further
     */

    private LinearLayout patient,doctor,back;

    private Firebase myFirebaseRef;

    //Arraylist for searching to figure out whether the account is of doc or patient
    private ArrayList<String> doc_search,patient_search;

    private ProgressBar spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        //Initializing and type casting components

        //Linear Layouts
        patient=(LinearLayout)findViewById(R.id.patient);
        doctor=(LinearLayout)findViewById(R.id.doctor);
        back=(LinearLayout)findViewById(R.id.main);

        //Arraylist Initialization
        patient_search=new ArrayList<String>();
        doc_search=new ArrayList<String>();

        //Progressbar
        spinner = (ProgressBar)findViewById(R.id.loading);

        //  Waiting for authorization
       // spinner.setVisibility(View.VISIBLE);
        //back.setVisibility(View.INVISIBLE);

        //Initializing firebase object
        myFirebaseRef = new Firebase(Config.FIREBASE_URL);


        //layout listeners
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent goto_patient_login = new Intent(Front.this, Patient_Login.class);
                    startActivity(goto_patient_login);
            }
        });

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent goto_doc_login = new Intent(Front.this, Doctor_Login.class);
                    startActivity(goto_doc_login);
            }
        });






        //Filling patient arraylist with all patients id
        myFirebaseRef.child("patient").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
                String patient = String.valueOf(newPost.get("id"));
                patient_search.add(patient);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //Checking authorization when loading is done
        myFirebaseRef.child("patient").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    for (int i = 0; i < patient_search.size(); i++) {
                        Log.d("ptnt",patient_search.get(i));
                        if (patient_search.get(i).equalsIgnoreCase(myFirebaseRef.getAuth().getUid())) {

                            Intent intent = new Intent(getApplicationContext(), Patient_Home.class);
                            String uid = myFirebaseRef.getAuth().getUid();
                            intent.putExtra("pat_user_id", uid);
                            spinner.setVisibility(View.GONE);
                            startActivity(intent);
                            finish();

                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }



            }
            public void onCancelled(FirebaseError firebaseError) { }
        });


        //Filling doctor arraylist with all doctors id
        myFirebaseRef.child("doctor").addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();

                String dctr = String.valueOf(newPost.get("id"));

                doc_search.add(dctr);

                // Log.d("Gotname",Patient_Array[x]);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //Checking authorization when loading is done
        myFirebaseRef.child("doctor").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (int i = 0; i < doc_search.size(); i++) {
                        Log.d("dctr",doc_search.get(i));
                        if (doc_search.get(i).equalsIgnoreCase(myFirebaseRef.getAuth().getUid())) {

                            Intent intent = new Intent(getApplicationContext(), Doctor_Home.class);
                            String uid = myFirebaseRef.getAuth().getUid();
                            intent.putExtra("doc_user_id", uid);
                            intent.putExtra("doc_list", doc_search);
                            spinner.setVisibility(View.GONE);
                            startActivity(intent);
                            finish();
                        }
                    }
                    spinner.setVisibility(View.GONE);
                    back.setVisibility(View.VISIBLE);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                spinner.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);

            }
            public void onCancelled(FirebaseError firebaseError) { }
        });



    }


}
