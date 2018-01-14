package com.example.mona.mahen.Account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Give_Rating extends AppCompatActivity {

    @Bind(R.id.cust)
    TextView cust;
    @Bind(R.id.rating)
    RatingBar rating;
    @Bind(R.id.back)
    Button back;
    Firebase firebase;
    String id,worker;
    float b,c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give__rating);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("y");
        worker = bundle.getString("x");


        firebase=new Firebase(Config.FIREBASE_URL);
        firebase.child("Reg_users").child("worker").child(worker).child("un").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cust.setText((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @OnClick(R.id.back)
    public void onClick() {
        firebase.child("Reg_users").child("worker").child(worker).child("rating").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                b=Float.parseFloat((String) dataSnapshot.getValue());

                firebase.child("Reg_users").child("worker").child(worker).child("customer").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        c=Float.parseFloat((String) dataSnapshot.getValue());
                        b*= c;
                        b+=rating.getRating();
                        b/=(c+1);
                        firebase.child("Reg_users").child("worker").child(worker).child("rating").setValue(Float.toString(b));
                        firebase.child("Reg_users").child("worker").child(worker).child("customer").setValue(Float.toString(c+1));

                        firebase.child("done").child(id).removeValue();

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

        Intent in=new Intent(Give_Rating.this, MainActivity.class);
        startActivity(in);

        Toast.makeText(getApplication(),"Thanks for your rating",Toast.LENGTH_SHORT).show();

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