package com.example.mona.mahen.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mona.mahen.Account.MainActivity;
import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.Database.User;
import com.example.mona.mahen.R;
import com.example.mona.mahen.Worker.Main;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {
    Firebase firebase;
    User user;
    @Bind(R.id.un)
    EditText username;
    @Bind(R.id.pass)
    EditText password;

    String un, pass;
    Boolean flag = false;
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.Register)
    TextView Register;
    @Bind(R.id.pb)
    ProgressBar pb;
    @Bind(R.id.main)
    LinearLayout main;


    String tp,acttp,id_no;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Firebase.setAndroidContext(this);
             Bundle bundle = getIntent().getExtras();
        tp = bundle.getString("type");
        acttp = bundle.getString("acttype");

        ButterKnife.bind(this);
        firebase = new Firebase(Config.FIREBASE_URL);
        flag=false;

        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        login.getBackground().setAlpha(100);
                        break;
                    case MotionEvent.ACTION_UP:
                        login.getBackground().setAlpha(255);
                        break;
                }
                return false;
            }
        });
    }


    @OnClick({R.id.login, R.id.Register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                flag=false;
                un=username.getText().toString();
                pass=password.getText().toString();
                pb.setVisibility(View.VISIBLE);
                authen_user();
                break;
            case R.id.Register:
                Intent clnt = new Intent(Login.this, Registration.class);
                clnt.putExtra("type", tp);
                clnt.putExtra("acttype",acttp);
                startActivity(clnt);
                break;
        }
    }


    public void authen_user() {

        firebase.child("Reg_users").child(tp).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                String usern = String.valueOf(newPost.get("un"));
                String passwor = String.valueOf(newPost.get("pass"));
                if (usern.equalsIgnoreCase(un) && passwor.equalsIgnoreCase(pass)) {
                     flag = true;
                     id_no=snapshot.getKey();
                }

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
        firebase.child("Reg_users").child(tp).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                pb.setVisibility(View.INVISIBLE);

                if (flag == true) {
                    if (tp.equalsIgnoreCase("customer")) {
                        Intent cstmer = new Intent(Login.this, MainActivity.class);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("cust_id", id_no);
                        editor.putString("wrkr_id", "");
                        editor.putString("gps","1");
                        editor.commit();
                        startActivity(cstmer);

                    } else if (tp.equalsIgnoreCase("worker")) {
                        Intent wrkr = new Intent(Login.this, Main.class);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("cust_id", "");
                        editor.putString("wrkr_id", id_no);
                        editor.putString("gps","1");
                        editor.commit();
                        startActivity(wrkr);
                    }

                } else
                    Toast.makeText(getApplicationContext(), "Invalid Username or password", Toast.LENGTH_LONG).show();


            }

            public void onCancelled(FirebaseError firebaseError) {
            }
        });


    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,  final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        buildAlertMessageNoGps();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }
    @Override
    protected void onPause() {

        super.onPause();

        finish();

    }
}