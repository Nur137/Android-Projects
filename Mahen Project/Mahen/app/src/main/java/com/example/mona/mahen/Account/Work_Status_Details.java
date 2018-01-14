package com.example.mona.mahen.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.Class.WorkerArrayAdapter;
import com.example.mona.mahen.Database.Notify_cust;
import com.example.mona.mahen.R;
import com.example.mona.mahen.UI.Workermap;
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

public class Work_Status_Details extends AppCompatActivity {
    SharedPreferences preferences;

    @Bind(R.id.e_title)
    TextView title;
    @Bind(R.id.list)
    ListView list;

    Firebase firebase;
    String active, w_title, w_id, x, worker;
    boolean found = false;
    ArrayList<Notify_cust> workers;
    @Bind(R.id.smap)
    Button smap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work__status__details);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Firebase.setAndroidContext(this);
        firebase = new Firebase(Config.FIREBASE_URL);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        try{
            active = preferences.getString("cust_id", "");
            w_title = bundle.getString("title");
            w_id = bundle.getString("id");
            title.setText(w_title);
        }
        catch (Exception e)
        {
            e.printStackTrace();


        }

        firebase = new Firebase(Config.FIREBASE_URL);
        workers = new ArrayList<Notify_cust>();

        firebase.child("notify").child("customer").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                String wor_id = String.valueOf(newPost.get("work_id"));

                if (wor_id.equalsIgnoreCase(w_id)) {
                    Notify_cust notify_cust = new Notify_cust();
                    notify_cust.setU_id(String.valueOf(newPost.get("u_id")));
                    notify_cust.setWork_id(w_id);
                    notify_cust.setWorker_id(String.valueOf(newPost.get("worker_id")));
                    notify_cust.setCust_id(active);
                    notify_cust.setDescription(String.valueOf(newPost.get("description")));
                    workers.add(notify_cust);

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
        firebase.child("notify").child("customer").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                set_list_worker();
            }

            public void onCancelled(FirebaseError firebaseError) {
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(Work_Status_Details.this, See_Applicant.class);
                final Notify_cust notify = (Notify_cust) parent.getItemAtPosition(position);
                in.putExtra("w_id", notify.getWork_id());
                in.putExtra("u_id", notify.getU_id());
                in.putExtra("wrker_id", notify.getWorker_id());
                in.putExtra("desc", notify.getDescription());
                startActivity(in);
            }
        });


    }

    public void set_list_worker() {
        WorkerArrayAdapter wa = new WorkerArrayAdapter(this, workers);
        list.setAdapter(wa);
    }

    @OnClick(R.id.smap)
    public void onClick() {
        Intent in=new Intent(Work_Status_Details.this, Workermap.class);
        in.putExtra("work",w_id);
        startActivity(in);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            Intent intent = new Intent(getApplicationContext(),Work_Status.class);
            startActivity(intent);
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

}
