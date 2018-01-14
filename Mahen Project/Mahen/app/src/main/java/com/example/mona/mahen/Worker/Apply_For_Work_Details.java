package com.example.mona.mahen.Worker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Apply_For_Work_Details extends AppCompatActivity {

    @Bind(R.id.w_title)
    TextView wTitle;
    @Bind(R.id.w_description)
    TextView wDescription;
    @Bind(R.id.cust)
    TextView cust;
    @Bind(R.id.sdt)
    TextView sdt;
    @Bind(R.id.edt)
    TextView edt;
    @Bind(R.id.p_from)
    TextView pFrom;
    @Bind(R.id.p_to)
    TextView pTo;
    @Bind(R.id.distance)
    TextView distance;
    SharedPreferences preferences;
    String w_id,s,e,u_id,cust_id,dist,active;
    Firebase firebase;

    Button Apply,Reject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_for_work_details);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        w_id = bundle.getString("work_id");
        u_id = bundle.getString("u_id");
        cust_id=bundle.getString("cust_id");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        active=preferences.getString("wrkr_id","");
        s=new String();
        e=new String();
        Apply=(Button)findViewById(R.id.Apply);
        Reject=(Button)findViewById(R.id.Reject);

        firebase=new Firebase(Config.FIREBASE_URL).child("posted_work").child(w_id);
        firebase.child("title").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                wTitle.setText((String) dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        firebase.child("description").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                wDescription.setText((String) dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        firebase.child("range").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                distance.setText((String) dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        firebase.child("from_date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                s=(String) dataSnapshot.getValue();

                s+=" ";


                firebase.child("from_time").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        s+=(String) dataSnapshot.getValue();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                sdt.setText(s);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        firebase.child("to_date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                e=(String) dataSnapshot.getValue();
                e+=" ";
                firebase.child("to_time").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        e+=(String) dataSnapshot.getValue();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                edt.setText(e);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        firebase.child("price_from").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                pFrom.setText((String) dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        firebase.child("price_to").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                pTo.setText((String) dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase f2=new Firebase(Config.FIREBASE_URL).child("Reg_users").child("customer").child(cust_id).child("full_name");
        f2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                cust.setText((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        Apply.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                firebase=new Firebase(Config.FIREBASE_URL);
                Intent in=new Intent(Apply_For_Work_Details.this,Set_Wage.class);
                in.putExtra("work_id",w_id);
                in.putExtra("cust_id",cust_id);
                in.putExtra("worker_id",active);
                in.putExtra("pf",pFrom.getText().toString());
                in.putExtra("pt",pTo.getText().toString());
                in.putExtra("uid",u_id);
                startActivity(in);
            }
        });

        Reject.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                firebase=new Firebase(Config.FIREBASE_URL);
                firebase.child("notify").child("worker").child(u_id).removeValue();

            }
        });

    }
}