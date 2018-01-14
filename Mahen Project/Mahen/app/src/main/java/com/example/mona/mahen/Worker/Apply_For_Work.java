package com.example.mona.mahen.Worker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mona.mahen.Class.Apply_For_Work_Adapter;
import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.Database.Notify_worker;
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

public class Apply_For_Work extends AppCompatActivity {
    @Bind(R.id.list)
    ListView list;
    ArrayList<Notify_worker> notifies;
    Firebase firebase;
    String w_id,uid;
    SharedPreferences preferences;
    String pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply__for__work);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        notifies=new ArrayList<Notify_worker>();
        final String active=preferences.getString("wrkr_id","");
        firebase=new Firebase(Config.FIREBASE_URL);

        firebase.child("notify").child("worker").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
                w_id= String.valueOf(newPost.get("worker_id"));
                if(active.equalsIgnoreCase(w_id))
                {
                    Notify_worker notify=new Notify_worker();
                    notify.setU_id(String.valueOf(newPost.get("u_id")));
                    notify.setWork_id(String.valueOf(newPost.get("work_id")));
                    notify.setWork_title(String.valueOf(newPost.get("work_title")));
                    notify.setCust_id(String.valueOf(newPost.get("cust_id")));
                    notify.setDistance(String.valueOf(newPost.get("distance")));
                    notify.setWorker_id(w_id);
                    notifies.add(notify);
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
        firebase.child("notify").child("worker").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                set_list_notify();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Notify_worker notify=(Notify_worker) parent.getItemAtPosition(position);
                Intent detail = new Intent(Apply_For_Work.this, Apply_For_Work_Details.class);
                detail.putExtra("work_id",notify.getWork_id());
                detail.putExtra("u_id",notify.getU_id());
                detail.putExtra("cust_id",notify.getCust_id());
                startActivity(detail);

            }
        });



    }




    public void set_list_notify(){
        Apply_For_Work_Adapter apply = new Apply_For_Work_Adapter(this, notifies);
        list.setAdapter(apply);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            Intent intent = new Intent(getApplicationContext(),Main.class);
            startActivity(intent);
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

}