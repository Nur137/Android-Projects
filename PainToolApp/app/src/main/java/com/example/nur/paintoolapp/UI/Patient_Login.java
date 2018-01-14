package com.example.nur.paintoolapp.UI;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import com.example.nur.paintoolapp.Database.Patient;
import com.example.nur.paintoolapp.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class Patient_Login extends AppCompatActivity {

    /**
     * @author  Nur Imtiazul Haque
     * @author Tasfia Mashiat
     * @version 1.0
     * @since   2016-06-05
     * Patients having paintool account can get log in by this class's functionalities
     */


    private Patient patient;
    private Firebase myFirebaseRef;
    private EditText email,password;
    private TextView not_a_mem;
    private Button pat_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__login);
        //Type casting
        pat_login=(Button)findViewById(R.id.pat_login);
        not_a_mem=(TextView) findViewById(R.id.su);

        //Sends to signup page
        not_a_mem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Patient_Login.this,Patient_Signup.class);
                startActivity(in);
            }
        });

        myFirebaseRef = new Firebase(Config.FIREBASE_URL);

    }

    @Override
    protected void onStart() {
        /**
         * This method initializes and type casts Edittexts
         * @param void
         * @return Nothing.
         */

        super.onStart();
        email = (EditText) findViewById(R.id.email_log_patient);
        password = (EditText) findViewById(R.id.pass_log_patient);
        //checkUserLogin();
    }

    protected void setUpUser() {
        /**
         * This method initiazes values of a member of Patient class
         * @param Nothing
         * @return void.
         */
        patient = new Patient();
        patient.setEmail(email.getText().toString());
        patient.setPass(password.getText().toString());
    }



    public void onLoginClicked(View view) {
        /**
         * This method is called when patient hits login button
         * @param View type object
         * @return Nothing.
         * calls setUpUser and authenticateUserLogin methods
         */
        pat_login.setBackgroundColor(Color.parseColor("#379C04"));
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
                patient.getEmail(),
                patient.getPass(),
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Log.i("TOKEN",authData.getToken());
                        Log.i("PROVIDER",authData.getProvider());
                        Log.i("UID",authData.getUid());
                        Log.i("AUTH_MAP",authData.getAuth().toString());

                        Intent intent = new Intent(getApplicationContext(), Patient_Home.class);
                        String uid = myFirebaseRef.getAuth().getUid();
                        intent.putExtra("pat_user_id", uid);
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
