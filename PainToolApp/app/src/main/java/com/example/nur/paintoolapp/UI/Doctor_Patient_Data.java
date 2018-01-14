package com.example.nur.paintoolapp.UI;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nur.paintoolapp.Classes.Config;
import com.example.nur.paintoolapp.Classes.DateShowAdapter;
import com.example.nur.paintoolapp.Database.Patient;
import com.example.nur.paintoolapp.Database.Rating;
import com.example.nur.paintoolapp.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Map;

public class Doctor_Patient_Data extends AppCompatActivity {
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     *
     * */
    private String patient_name,patient_id;
    private  Firebase myFirebaseRef;
    private ArrayList<Rating> rating;
    private ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__patient__data);
        Bundle bundle = getIntent().getExtras();
        patient_name= bundle.getString("patient_name");
        myFirebaseRef=new Firebase(Config.FIREBASE_URL);
        list=(ListView)findViewById(R.id.list);

        rating=new ArrayList<Rating>();
        myFirebaseRef.child("patient").addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
                String pat=String.valueOf(newPost.get("name"));
                if(pat.equalsIgnoreCase(patient_name))
                {
                    patient_id = String.valueOf(newPost.get("id"));
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

        myFirebaseRef.child("Rating").addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
                String patient= String.valueOf(newPost.get("patient_ID"));
                if(patient.equalsIgnoreCase(patient_id)) {

                    String date = String.valueOf(newPost.get("date"));
                    String time=String.valueOf(newPost.get("time"));
                    String rating_code=String.valueOf(newPost.get("rating_code"));
                    String day=String.valueOf(newPost.get("day"));
                    Rating rat=new Rating();
                    rat.setPatient_ID(patient_id);
                    rat.setdate(date);
                    rat.setTime(time);
                    rat.setDay(day);
                    rat.setRating_code(rating_code);
                    rating.add(rat);
                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });

        DateShowAdapter adapter = new DateShowAdapter(this, rating);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rating rat=(Rating)parent.getItemAtPosition(position);

                //Toast.makeText(getApplicationContext(), "List Item Value: "+patient, Toast.LENGTH_LONG).show();
                Intent in=new Intent(Doctor_Patient_Data.this,Doctor_patient_status.class);
                in.putExtra("id", rat.getPatient_ID());
                in.putExtra("time", rat.getTime());
                in.putExtra("date", rat.getdate());
                startActivity(in);
            }
        });

    }
}
