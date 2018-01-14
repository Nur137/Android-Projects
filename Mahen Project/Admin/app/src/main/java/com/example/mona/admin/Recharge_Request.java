package com.example.mona.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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

public class Recharge_Request extends AppCompatActivity {

    @Bind(R.id.list)
    ListView list;
    ArrayList<Recharge> recharges;
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge__request);
        ButterKnife.bind(this);
        recharges=new ArrayList<Recharge>();
        firebase=new Firebase(Config.FIREBASE_URL);

        firebase.child("Admin").child("Recharge_req").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();

                Recharge r=new Recharge();
                r.setU_id(String.valueOf(newPost.get("id")));
                r.setBal(String.valueOf(newPost.get("amount")));
                r.setCust_id(String.valueOf(newPost.get("cust_id")));
                recharges.add(r);

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
        firebase.child("Admin").child("Recharge_req").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{

                    if(recharges.size()==0) Toast.makeText(Recharge_Request.this,"No Recharge request to approve",Toast.LENGTH_SHORT).show();
                    else
                        set_list_recharge();
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

                final Recharge recharge=(Recharge) parent.getItemAtPosition(position);
                Intent in =new Intent(Recharge_Request.this,Recharge_Approve.class);
                in.putExtra("uid",recharge.getU_id());
                in.putExtra("custid",recharge.getCust_id());
                in.putExtra("balance",recharge.getBal());
                startActivity(in);

            }
        });
    }
    public void set_list_recharge(){
        RechargeAdapter re=new RechargeAdapter(this,recharges);
        list.setAdapter(re);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            Intent intent = new Intent(getApplicationContext(),Admin_Work.class);
            startActivity(intent);
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }
}