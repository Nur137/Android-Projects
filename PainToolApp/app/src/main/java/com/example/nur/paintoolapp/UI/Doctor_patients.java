package com.example.nur.paintoolapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;
import java.util.Map;

public class Doctor_patients extends AppCompatActivity {

    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class is a database class which fills Firebse database form
     * This class also provide getter and setter methods
     * It Establishes Doctor-patient relationship
     * */
    Firebase myFirebaseRef;
    ListView lst;
    ArrayList<String> patients;
    String doc_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patients);
        Bundle bundle = getIntent().getExtras();
        doc_name = bundle.getString("doc_name");
        patients=new ArrayList<String >();


        lst=(ListView)findViewById(R.id.list);
        myFirebaseRef=new Firebase(Config.FIREBASE_URL).child("Rel");




        myFirebaseRef.child("patient").addListenerForSingleValueEvent(new ValueEventListener() {
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
                String doc=String.valueOf(newPost.get("doctor"));
                if(doc.equalsIgnoreCase(doc_name))
                {
                    String pat = String.valueOf(newPost.get("patient"));
                    patients.add(pat);


                }


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






/*
        ListAdapter adapter = new FirebaseListAdapter<Doctor_Patient_Relation>(this, Doctor_Patient_Relation.class,R.layout.list_item, myFirebaseRef)
        {
            @Override
            protected void populateView(View view, Doctor_Patient_Relation rel, int i) {


                ((TextView)view.findViewById(R.id.tvName)).setText(rel.getPatient());
                //((TextView)view.findViewById(R.id.tvEmail)).setText(rel.getDoctor());

            }
        };


            lst.setAdapter(adapter);
*/


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String patient = (String) parent.getItemAtPosition(position);


                Toast.makeText(getApplicationContext(), "List Item Value: "+patient, Toast.LENGTH_LONG).show();
                Intent in=new Intent(Doctor_patients.this,Doctor_Patient_Data.class);
                in.putExtra("patient_name", patient);
                startActivity(in);

            }
        });



    }


    public void load()
    {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, patients);
        lst.setAdapter(adapter);
    }
}