package com.example.nur.paintoolapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.nur.paintoolapp.Classes.Config;
import com.example.nur.paintoolapp.Database.Doctor_Patient_Relation;
import com.example.nur.paintoolapp.Database.Patient;
import com.example.nur.paintoolapp.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Doctor_search_patient extends AppCompatActivity {

    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class is a database class which fills Firebse database form
     * This class also provide getter and setter methods
     * It Establishes Doctor-patient relationship
     * */
    AutoCompleteTextView actv;
    Firebase myFirebaseRef,Relation;
    Button add_patient;
    ArrayList<String> patients,Listed_pats;
    String doc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_search_patient);
        Bundle bundle = getIntent().getExtras();
        doc = bundle.getString("doc_name");
        actv = (AutoCompleteTextView) findViewById(R.id.autoComplete_Patients);
        add_patient=(Button)findViewById(R.id.add_patient);
        patients=new ArrayList<String>();
        Listed_pats=new ArrayList<String>();

        myFirebaseRef = new Firebase(Config.FIREBASE_URL).child("patient");
        myFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                load();
            }
            public void onCancelled(FirebaseError firebaseError) { }
        });


        myFirebaseRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();

                String pat = String.valueOf(newPost.get("name"));
                patients.add(pat);

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



        Relation=new Firebase(Config.FIREBASE_URL).child("Rel");

        Relation.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();

                String pat = String.valueOf(newPost.get("patient"));
                Listed_pats.add(pat);

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



        add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Find()==true)
                { Toast.makeText(getApplicationContext(), "This Patient is already in your list", Toast.LENGTH_LONG).show();

                }

                else{
                    Doctor_Patient_Relation rel=new Doctor_Patient_Relation();
                    rel.setDoctor(doc);
                    rel.setPatient(actv.getText().toString());

                    int flag=0;

                    for(int i=0;i<patients.size();i++)
                    {
                        if(patients.get(i).equalsIgnoreCase(actv.getText().toString()))
                        {

                            Firebase myFirebaseRef=new Firebase(Config.FIREBASE_URL).child("Rel");

                            flag=1;
                            break;

                        }
                    }
                    if(flag==0)
                    {
                        Toast.makeText(getApplicationContext(), "Not found" , Toast.LENGTH_LONG).show();

                    }
                    else {
                        Firebase myFirebaseRef=new Firebase(Config.FIREBASE_URL).child("Rel");
                        myFirebaseRef.push().setValue(rel);
                        Toast.makeText(getApplicationContext(), "Patient has been added", Toast.LENGTH_LONG).show();

                    }

                }




            }
        });




    }

    public void load(){


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,patients);
        actv.setAdapter(adapter);
        actv.setThreshold(1);


    }

    public Boolean Find()
    {
        for(int i = 0; i< Listed_pats.size(); i++)
        {
            if(Listed_pats.get(i).equalsIgnoreCase(actv.getText().toString()))
                return true;
        }
        return false;
    }
}
