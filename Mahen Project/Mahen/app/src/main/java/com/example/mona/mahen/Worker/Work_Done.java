package com.example.mona.mahen.Worker;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ListView;

import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.Database.RatWorker;
import com.example.mona.mahen.R;
import com.example.mona.mahen.UI.Invoice;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Work_Done extends AppCompatActivity {
    SharedPreferences preferences;
    Firebase firebase;
    String active;
    @Bind(R.id.done)
    Button done;
    String w_id,u_id,c_id,wage,w_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work__done);
        ButterKnife.bind(this);

        Firebase.setAndroidContext(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        active = preferences.getString("wrkr_id", "");
        Bundle bundle = getIntent().getExtras();
        w_id = bundle.getString("w_id");
        u_id = bundle.getString("u_id");
        c_id = bundle.getString("c_id");
        wage = bundle.getString("wage");

        firebase=new Firebase(Config.FIREBASE_URL);
    }

    @OnClick(R.id.done)
    public void onClick() {

        firebase.child("Reg_users").child("worker").child(active).child("account").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String bal=(String)dataSnapshot.getValue();
                int b=Integer.parseInt(bal);
                b+=Integer.parseInt(wage);
                firebase.child("Reg_users").child("worker").child(active).child("account").setValue(Integer.toString(b));

                firebase.child("posted_work").child(w_id).child("title").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        w_t=(String)dataSnapshot.getValue();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });




                firebase.child("Reg_users").child("customer").child(c_id).child("account").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String bal=(String)dataSnapshot.getValue();
                        int b=Integer.parseInt(bal);
                        b-=Integer.parseInt(wage);
                        firebase.child("Reg_users").child("customer").child(c_id).child("account").setValue(Integer.toString(b));

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });





                Firebase fr=new Firebase(Config.FIREBASE_URL).child("done").push();

                RatWorker ratWorker=new RatWorker();
                ratWorker.setU_id(fr.getKey());
                ratWorker.setCust_id(c_id);
                ratWorker.setWork_title(w_id);
                ratWorker.setWorker_id(active);
                fr.setValue(ratWorker);

                firebase.child("work_under").child(u_id).removeValue();
                firebase.child("posted_work").child(w_id).removeValue();
                Intent i=new Intent(getApplication(),Invoice.class);
                i.putExtra("wage",wage);
                startActivity(i);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            Intent intent = new Intent(getApplicationContext(),My_Work.class);
            startActivity(intent);
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }
}