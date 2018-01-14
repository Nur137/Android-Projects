package com.example.nur.paintoolapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nur.paintoolapp.Classes.Config;
import com.example.nur.paintoolapp.Database.Patient_info;
import com.example.nur.paintoolapp.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

public class Patient_profile extends AppCompatActivity {
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class has been used to get extra information from patient
     * */

    private EditText country,province,zip_code,det_address,height,weight;
    private Button Update;
    private Firebase myFirebaseref,prof;
    private String pat_name;
    private TextView hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        Bundle bundle = getIntent().getExtras();
        pat_name = bundle.getString("pat_user_name");

        Update=(Button)findViewById(R.id.update);

        country=(EditText)findViewById(R.id.country);
        province=(EditText)findViewById(R.id.province);
        zip_code=(EditText)findViewById(R.id.zip_code);
        det_address=(EditText)findViewById(R.id.det_add);
        height=(EditText)findViewById(R.id.height);
        weight=(EditText)findViewById(R.id.weight);
        hello=(TextView)findViewById(R.id.hello);
        hello.setText(pat_name);

        myFirebaseref=new Firebase(Config.FIREBASE_URL).child("patient_info");


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patient_info pat=new Patient_info();
                pat.setName(pat_name);
                pat.setCountry(country.getText().toString());
                pat.setProvince(province.getText().toString());
                pat.setDet_address(det_address.getText().toString());
                pat.setZip_code(zip_code.getText().toString());
                pat.setHeight(height.getText().toString());
                pat.setWeight(weight.getText().toString());

                myFirebaseref.push().setValue(pat);
            }
        });

        prof=new Firebase(Config.FIREBASE_URL).child("patient_info");


        prof.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();

                String nm = String.valueOf(newPost.get("name"));
                if(nm.equalsIgnoreCase(pat_name))
                {
                    String cntry=String.valueOf(newPost.get("country"));
                    country.setText(cntry);
                    String prvnc=String.valueOf(newPost.get("province"));
                    province.setText(prvnc);
                    String zpcd=String.valueOf(newPost.get("zip_code"));
                    zip_code.setText(zpcd);
                    String dt_add=String.valueOf(newPost.get("det_addess"));
                    det_address.setText(dt_add);
                    String hgt=String.valueOf(newPost.get("height"));
                    height.setText(hgt);
                    String wgt=String.valueOf(newPost.get("weight"));
                    weight.setText(wgt);
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



    }


}
