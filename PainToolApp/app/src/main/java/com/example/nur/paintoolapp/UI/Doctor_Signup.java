package com.example.nur.paintoolapp.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.example.nur.paintoolapp.Database.Doctor;
import com.example.nur.paintoolapp.Database.Doctor_info;
import com.example.nur.paintoolapp.Database.Patient;
import com.example.nur.paintoolapp.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Map;

public class Doctor_Signup extends AppCompatActivity {
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class is a database class which fills Firebse database form
     * This class also provide getter and setter methods
     * It Establishes Doctor-patient relationship
     * */
    private Doctor doctor;
    private EditText un, pass, email, confirm;
    private TextView tv;
    private ArrayList<String> doc_search;
    private Firebase myFirebaseRef;
    private RadioGroup sex;
    private ImageView doc_image;
    private String gender;

    // private FirebaseAuth.AuthStateListener mAuthListener;
    // FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__signup);
        doc_search = new ArrayList<String>();
        doc_image = (ImageView) findViewById(R.id.doc_image);

        tv = (TextView) findViewById(R.id.back_login_doc);

        myFirebaseRef = new Firebase(Config.FIREBASE_URL);



        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Doctor_Signup.this,Doctor_Login.class);
                startActivity(in);
            }
        });

        sex = (RadioGroup) findViewById(R.id.radioSex);
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = sex.findViewById(checkedId);
                int index = sex.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0: // first button
                        doc_image.setBackgroundResource(R.drawable.male_doc);
                        gender="male";
                        break;
                    case 1: // secondbutton
                        doc_image.setBackgroundResource(R.drawable.female_doc);
                        gender="female";
                        break;
                }
            }
        });

/*
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
                }
                // ...
            }
        };

*/
    }
    @Override
    protected void onStart() {
        super.onStart();
        un = (EditText) findViewById(R.id.un_doc);
        email = (EditText) findViewById(R.id.email_doc);
        pass = (EditText) findViewById(R.id.passsu_doc);
        confirm = (EditText) findViewById(R.id.cfpass_doc);
    }

    protected void setUpDoctor() {
        doctor = new Doctor();
        doctor.setName(un.getText().toString());
        doctor.setEmail(email.getText().toString());
        doctor.setPass(pass.getText().toString());

    }

    public void ondoctorSignUpClicked(View view) {

//        createAccount(email.getText().toString(), pass.getText().toString());

        setUpDoctor();

        if(pass.getText().toString().equalsIgnoreCase(confirm.getText().toString()))
        {
                myFirebaseRef.createUser(
                        doctor.getEmail(),
                        doctor.getPass(),
                        new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> stringObjectMap) {
                                doctor.setId(stringObjectMap.get("uid").toString());
                                doctor.saveUser(myFirebaseRef);
                                Doctor_info doc=new Doctor_info();
                                doc.setGender(gender);

                                myFirebaseRef.child("doctor_info").push().setValue(doc);

                                myFirebaseRef.unauth();
                                Toast.makeText(getApplicationContext(), "Your Account has been Created", Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), "Please Login With your Email and Password", Toast.LENGTH_LONG).show();

                                finish();
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                                Log.d("Error",firebaseError.getMessage());

                            }
                        }
                );


        }

        else
        {
            Toast.makeText(getApplicationContext(), "Password donot match", Toast.LENGTH_LONG).show();

        }


    }
/*
    private void createAccount(String email, String password) {

        // [START create_user_with_email]
         mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Doctor_Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }
*/
}
