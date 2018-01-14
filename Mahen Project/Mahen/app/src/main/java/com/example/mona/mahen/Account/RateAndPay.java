package com.example.mona.mahen.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.Class.RateAndPayAdapter;
import com.example.mona.mahen.Database.RatWorker;
import com.example.mona.mahen.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RateAndPay extends AppCompatActivity {

    @Bind(R.id.list)
    ListView list;
    Firebase firebase;
    SharedPreferences preferences;
    String active;
    ArrayList<RatWorker> ratWorkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_and_pay);
        ButterKnife.bind(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        active=preferences.getString("cust_id","");
        firebase=new Firebase(Config.FIREBASE_URL);
        ratWorkers =new ArrayList<RatWorker>();

        firebase.child("done").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
                String cst = String.valueOf(newPost.get("cust_id"));
                if(active.equalsIgnoreCase(cst))
                {
                    RatWorker rw=new RatWorker();
                    rw.setU_id(String.valueOf(newPost.get("u_id")));
                    rw.setCust_id(String.valueOf(newPost.get("cust_id")));
                    rw.setWork_title(String.valueOf(newPost.get("work_title")));
                    rw.setWorker_id(String.valueOf(newPost.get("worker_id")));
                    ratWorkers.add(rw);
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


        firebase.child("done").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {



                setlist();

            }
            public void onCancelled(FirebaseError firebaseError) { }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final RatWorker r=(RatWorker) parent.getItemAtPosition(position);
                Intent in = new Intent(RateAndPay.this,Give_Rating.class);
                in.putExtra("x",r.getWorker_id());
                in.putExtra("y",r.getU_id());

                startActivity(in);


            }
        });



    }

    public void setlist(){
        RateAndPayAdapter wor=new RateAndPayAdapter(this,ratWorkers);
        list.setAdapter(wor);
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

}