package com.example.mona.admin;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Withdraw_Request extends AppCompatActivity {

    ListView list;
    ArrayList<Withdraw> withdraws;
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw__request);
        list=(ListView)findViewById(R.id.list);

        withdraws=new ArrayList<Withdraw>();
        firebase=new Firebase(Config.FIREBASE_URL);

        firebase.child("Admin").child("Withdraw_req").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();

                Withdraw r=new Withdraw();
                r.setU_id(String.valueOf(newPost.get("id")));
                r.setBal(String.valueOf(newPost.get("amount")));
                r.setWorker_id(String.valueOf(newPost.get("worker_id")));
                withdraws.add(r);

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
        firebase.child("Admin").child("Withdraw_req").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {


                try{

                    if(withdraws.size()==0) Toast.makeText(Withdraw_Request.this,"No Withdraw request to approve",Toast.LENGTH_SHORT).show();
                    else
                        set_list_withdraw();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            public void onCancelled(FirebaseError firebaseError) { }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Withdraw withdraw=(Withdraw) parent.getItemAtPosition(position);
                Intent in=new Intent(Withdraw_Request.this,Withdraw_Approve.class);
                in.putExtra("uid",withdraw.getU_id());
                in.putExtra("wid",withdraw.getWorker_id());
                in.putExtra("balance",withdraw.getBal());
                startActivity(in);


            }
        });
    }
    public void set_list_withdraw(){
        WithdrawAdapter wd=new WithdrawAdapter(this,withdraws);
        list.setAdapter(wd);
    }
}