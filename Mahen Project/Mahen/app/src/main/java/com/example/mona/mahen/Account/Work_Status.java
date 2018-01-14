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
import com.example.mona.mahen.Class.WorkStatusAdapter;
import com.example.mona.mahen.Database.Work;
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

public class Work_Status extends AppCompatActivity {
    @Bind(R.id.list)
    ListView list;
    String active;
    SharedPreferences preferences;
    ArrayList<Work> works;
    Firebase firebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work__status);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        active=preferences.getString("cust_id","");
        firebase=new Firebase(Config.FIREBASE_URL);
        works=new ArrayList<Work>();

        firebase.child("posted_work").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
                String id = String.valueOf(newPost.get("posted_by"));
                if(id.equalsIgnoreCase(active))
                {
                    Work w=new Work();
                    w.setUid(String.valueOf(newPost.get("uid")));
                    w.setFrom_date(String.valueOf(newPost.get("from_date")));
                    w.setFrom_time(String.valueOf(newPost.get("from_time")));
                    w.setTo_date(String.valueOf(newPost.get("to_date")));
                    w.setTo_time(String.valueOf(newPost.get("to_time")));
                    w.setRange(String.valueOf(newPost.get("range")));
                    w.setTitle(String.valueOf(newPost.get("title")));
                    w.setDescription(String.valueOf(newPost.get("description")));
                    w.setPosted_by(String.valueOf(newPost.get("posted_by")));
                    w.setPrice_from(String.valueOf(newPost.get("price_from")));
                    w.setPrice_to(String.valueOf(newPost.get("price_to")));
                    w.setWorker_type(String.valueOf(newPost.get("worker_type")));
                    works.add(w);
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
        firebase.child("posted_work").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                set_list_work();
            }
            public void onCancelled(FirebaseError firebaseError) { }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Work work=(Work) parent.getItemAtPosition(position);
                Intent in=new Intent(Work_Status.this,Work_Status_Details.class);
                in.putExtra("title",work.getTitle());
                in.putExtra("id",work.getUid());
                startActivity(in);

            }
        });






    }
    public void set_list_work(){
        WorkStatusAdapter wor=new WorkStatusAdapter(this,works);
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