package com.example.nur.paintoolapp.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nur.paintoolapp.Classes.Config;
import com.example.nur.paintoolapp.Database.Doctor;
import com.example.nur.paintoolapp.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;



public class Doctor_Login extends AppCompatActivity {

    /**
     * @author  Nur Imtiazul Haque
     * @author Tasfia Mashiat
     * @version 1.0
     * @since   2016-06-05
     * Doctors having paintool account can get log in by this class's functionalities
     */

    private Doctor doctor;
    Firebase myFirebaseRef;
    private EditText email,password;
    private Button doc_login;
    private TextView not_a_mem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Type casting
        setContentView(R.layout.activity_doctor__login);
        myFirebaseRef=new Firebase(Config.FIREBASE_URL);
        not_a_mem=(TextView) findViewById(R.id.su_doc);
        doc_login=(Button)findViewById(R.id.doc_login);

        //Sends to signup page
        not_a_mem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Doctor_Login.this,Doctor_Signup.class);
                startActivity(in);
            }
        });


    }
    @Override
    protected void onStart() {
        /**
         * This method initializes and type casts Edittexts
         * @param void
         * @return Nothing.
         */

        super.onStart();
        email = (EditText) findViewById(R.id.em_doctor);
        password = (EditText) findViewById(R.id.pass_doctor);
    }

    protected void setUpUser() {
        /**
         * This method initiazes values of a member of Doctor class
         * @param Nothing
         * @return void.
         */

        doctor = new Doctor();
        doctor.setEmail(email.getText().toString());
        doctor.setPass(password.getText().toString());
    }


    public void ondocLoginClicked(View view) {
        /**
         * This method is called when doctor hits Login button
         * @param View type object
         * @return Nothing.
         * calls setUpUser and authenticateUserLogin methods
         */

        doc_login.setBackgroundColor(Color.parseColor("#379C04"));
        setUpUser();
        aunthenticateUserLogin();
    }



    private void aunthenticateUserLogin() {
        /**
         * This method authWithPassword method attempts to authenticate to Firebase with the given credentials.
         * @param Nothing
         * @return Nothing.
         */

         myFirebaseRef.authWithPassword(
                doctor.getEmail(),
                doctor.getPass(),
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Intent intent = new Intent(getApplicationContext(), Doctor_Home.class);
                        String uid = myFirebaseRef.getAuth().getUid();
                        intent.putExtra("doc_user_id", uid);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
        );
    }



}
