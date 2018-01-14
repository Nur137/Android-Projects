package com.example.nur.paintoolapp.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nur.paintoolapp.Classes.Config;
import com.example.nur.paintoolapp.Database.Doctor_info;
import com.example.nur.paintoolapp.Database.Patient;
import com.example.nur.paintoolapp.Database.Patient_info;
import com.example.nur.paintoolapp.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Patient_Signup extends AppCompatActivity {
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class provides signup facility for patient
     * */

    private Patient patient;
    private EditText un, pass, email, confirm;
    private Button signup;
    private TextView tv;
    private Firebase myFirebaseRef;
    private ArrayList<String> patient_search;
    private RadioGroup sex;
    private ImageView pat_image;
    private String gender;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_signup);

        //Initialization and type casting
        patient_search=new ArrayList<String>();

        tv=(TextView)findViewById(R.id.back_login_patient);
        pat_image=(ImageView)findViewById(R.id.pat_image);


        myFirebaseRef = new Firebase(Config.FIREBASE_URL);

        sex = (RadioGroup) findViewById(R.id.radioSex);
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = sex.findViewById(checkedId);
                int index = sex.indexOfChild(radioButton);

                switch (index) {
                    case 0: // first button
                        pat_image.setBackgroundResource(R.drawable.patient_male);
                        gender="male";
                        break;
                    case 1: // secondbutton
                        pat_image.setBackgroundResource(R.drawable.female_pat);
                        gender="female";

                        break;
                }
            }
        });



    }

    @Override
    protected void onStart() {
        /**
         * This is a called when activity starts
         * @param Nothing
         * @return void
         *
         */

        super.onStart();
        un = (EditText) findViewById(R.id.editUN_patient);
        email = (EditText) findViewById(R.id.editEmail_patient);
        pass = (EditText) findViewById(R.id.editPass_patient);
        confirm = (EditText) findViewById(R.id.editConfirmpass_patient);
    }

    protected void setUpPatient() {
        /**
         * this method fills up database taking values from edittext
         * @param Nothing
         * @return void
         *
         */

        patient = new Patient();
        patient.setName(un.getText().toString());
        patient.setEmail(email.getText().toString());
        patient.setPass(pass.getText().toString());

    }

    public void onSignUpClicked(View view) {
        /**
         * This method is called on Register button click
         * @param Nothing
         * @return void
         *
         */
        setUpPatient();
        if(pass.getText().toString().equalsIgnoreCase(confirm.getText().toString()))
        {
                myFirebaseRef.createUser(
                        patient.getEmail(),
                        patient.getPass(),
                        new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> stringObjectMap) {
                                patient.setId(stringObjectMap.get("uid").toString());

                                patient.saveUser(myFirebaseRef);

                                Patient_info pat=new Patient_info();
                                pat.setGender(gender);

                                myFirebaseRef.child("patient_info").push().setValue(pat);


                                myFirebaseRef.unauth();
                                Toast.makeText(getApplicationContext(), "Your Account has been Created", Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), "Please Login With your Email and Password", Toast.LENGTH_LONG).show();
                                finish();
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                );


            }


        else
        {
            Toast.makeText(getApplicationContext(), "Password donot match", Toast.LENGTH_LONG).show();
        }


        }

}

