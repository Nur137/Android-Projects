package com.example.mona.admin;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Recharge_Approve extends AppCompatActivity {

    @Bind(R.id.message)
    TextView message;
    @Bind(R.id.email)
    TextView email;
    @Bind(R.id.approve)
    Button approve,reject;
    Firebase firebase;
    String uid,custid,balance,em;
    int b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge__approve);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        uid=bundle.getString("uid");
        custid=bundle.getString("custid");
        balance=bundle.getString("balance");

        reject=(Button)findViewById(R.id.rej);

        firebase=new Firebase(Config.FIREBASE_URL);
        firebase.child("Reg_users").child("customer").child(custid).child("un").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                String m=value + " is requesting $"+balance +" to recharge";
                message.setText(m);

                // do your stuff here with value
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        firebase.child("Reg_users").child("customer").child(custid).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                em = (String) dataSnapshot.getValue();
                email.setText(em);

                // do your stuff here with value
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        approve.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        approve.getBackground().setAlpha(180);
                        break;
                    case MotionEvent.ACTION_UP:
                        approve.getBackground().setAlpha(255);


                        break;
                }
                return false;
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase.child("Admin").child("Recharge_req").child(uid).removeValue();
                Toast.makeText(Recharge_Approve.this,"Request rejected",Toast.LENGTH_SHORT).show();
                sendEmail2(em);
            }
        });
    }

    @OnClick(R.id.approve)
    public void onClick() {

        firebase.child("Admin").child("Recharge_req").child(uid).removeValue();


        firebase.child("Reg_users").child("customer").child(custid).child("account").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                b=Integer.parseInt(value);
                int n=Integer.parseInt(balance);
                b+=n;
                firebase.child("Reg_users").child("customer").child(custid).child("account").setValue(Integer.toString(b));
                Intent in= new Intent(Recharge_Approve.this,Recharge_Request.class);
                startActivity(in);

                // do your stuff here with value
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    sendEmail(em);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            Intent intent = new Intent(getApplicationContext(),Recharge_Request.class);
            startActivity(intent);
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    protected void sendEmail(String em) {
        Log.i("Send email", "");
        String[] TO = {em};
        String[] CC = {em};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "You have been approved");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Congratulation. Your account has been recharged");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(Recharge_Approve.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void sendEmail2(String em) {
        Log.i("Send email", "");
        String[] TO = {em};
        String[] CC = {em};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your request have been rejected");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Sorry you haven't provided all required criteria. Please read the terms and conditions perfectly and send recharge request again.");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(Recharge_Approve.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}