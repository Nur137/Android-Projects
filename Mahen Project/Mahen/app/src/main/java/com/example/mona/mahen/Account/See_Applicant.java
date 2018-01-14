package com.example.mona.mahen.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.Database.Work_Under;
import com.example.mona.mahen.Manifest;
import com.example.mona.mahen.R;
import com.example.mona.mahen.Worker.Main;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class See_Applicant extends AppCompatActivity {

    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.wage)
    TextView wagemon;

    @Bind(R.id.cnt)
    TextView cnt;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.hire)
    Button hire;
    SharedPreferences preferences;
    String active,w_id,wrkr,u_id,wage,desc1,val,w_t,cst_del;
    Firebase firebase;
    String phoneNo,message;
    ArrayList<String> work_del,cust_del;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see__applicant);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        Bundle bundle = getIntent().getExtras();
        u_id=bundle.getString("u_id");
        w_id=bundle.getString("w_id");
        wrkr=bundle.getString("wrker_id");
        desc1=bundle.getString("desc");
        cst_del=new String();

         work_del=new ArrayList<String>();

        cust_del=new ArrayList<String>();



        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        active=preferences.getString("cust_id","");
        firebase=new Firebase(Config.FIREBASE_URL);


        firebase.child("Reg_users").child("worker").child(wrkr).child("contact").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                phoneNo=(String) dataSnapshot.getValue();
                cnt.setText(phoneNo);
                Toast.makeText(See_Applicant.this,phoneNo,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        desc.setText(desc1);


        firebase.child("notify").child("customer").child(u_id).child("worker_id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                val=(String) dataSnapshot.getValue();
                firebase.child("Reg_users").child("worker").child(val).child("un").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        name.setText((String) dataSnapshot.getValue());

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        firebase.child("posted_work").child(w_id).child("title").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                w_t=((String) dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        Firebase f2=new Firebase(Config.FIREBASE_URL).child("notify").child("customer").child(u_id);

        f2.child("wage").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                wage=(String) dataSnapshot.getValue();
                wagemon.setText(wage);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




    }

    @OnClick(R.id.hire)
    public void onClick() {

        Work_Under work_under=new Work_Under();
        Firebase firebase=new Firebase(Config.FIREBASE_URL).child("work_under").push();
        work_under.setU_id(firebase.getKey());
        work_under.setWorker_id(wrkr);
        work_under.setCust_id(active);
        work_under.setWork_title(w_t);
        work_under.setWage(wage);
        firebase.setValue(work_under);
        Firebase f2=new Firebase(Config.FIREBASE_URL).child("notify").child("customer").child(u_id);
        Firebase fadd=new Firebase(Config.FIREBASE_URL).child("posted_work").child(w_id);
        fadd.child("wage").setValue(wage);
        f2.removeValue();


        final Firebase f=new Firebase(Config.FIREBASE_URL);
        f.child("posted_work").child(w_id).removeValue();



        f.child("notify").child("worker").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
                String wrid = String.valueOf(newPost.get("work_id"));
                if(wrid.equalsIgnoreCase(w_id))
                {
                    //f.child("notify").child("worker").child(snapshot.getKey()).removeValue();
                    work_del.add(snapshot.getKey());
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
        f.child("notify").child("worker").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                ActivityCompat.requestPermissions(See_Applicant.this,new String[]{android.Manifest.permission.SEND_SMS},1);

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, "Congratulations, you have been selected for the work", null, null);
                    Toast.makeText(See_Applicant.this, "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(See_Applicant.this,
                            e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                for(int i=0;i<work_del.size();i++)
                {
                    f.child("notify").child("worker").child(work_del.get(i)).removeValue();
                }

                Intent in=new Intent(See_Applicant.this, MainActivity.class);
                startActivity(in);
                Toast.makeText(getApplication(),"Your worker has been hired", Toast.LENGTH_SHORT).show();



                f.child("notify").child("customer").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot snapshot1, String previousChildKey) {

                        Map<String,Object> newPost=(Map<String,Object>)snapshot1.getValue();
                        String wid = String.valueOf(newPost.get("work_id"));
                        if(wid.equalsIgnoreCase(w_id))
                        {
                            cust_del.add(snapshot1.getKey());
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
                f.child("notify").child("customer").addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(int i=0;i<cust_del.size();i++)
                        {
                            f.child("notify").child("customer").child(cust_del.get(i)).removeValue();
                        }

                    }
                    public void onCancelled(FirebaseError firebaseError) { }
                });




            }
            public void onCancelled(FirebaseError firebaseError) { }
        });




/*
        //Checking authorization when loading is done
        f.child("notify").child("customer").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                Intent in=new Intent(See_Applicant.this, Main.class);
                startActivity(in);
                Toast.makeText(getApplication(),"Your worker has been hired", Toast.LENGTH_SHORT).show();


            }
            public void onCancelled(FirebaseError firebaseError) { }
        });*/


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }


}