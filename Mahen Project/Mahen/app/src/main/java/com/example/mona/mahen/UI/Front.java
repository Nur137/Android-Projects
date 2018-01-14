package com.example.mona.mahen.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.mona.mahen.Account.MainActivity;
import com.example.mona.mahen.R;
import com.example.mona.mahen.Worker.Main;
import com.firebase.client.Firebase;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Front extends AppCompatActivity {
    @Bind(R.id.main)
    LinearLayout main;
    @Bind(R.id.worker)
    LinearLayout worker;
    @Bind(R.id.customer)
    LinearLayout client;
    @Bind(R.id.pb)
    ProgressBar pb;
    SharedPreferences preferences;
    String aw,ac,exit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_front);
        exit="1";

        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        exit=prefs1.getString("sp1","");

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();


        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        ac=preferences.getString("cust_id","");
        aw=preferences.getString("wrkr_id","");


        if(exit.equalsIgnoreCase("done"))
        {

            editor.putString("sp1", "1");
            editor.commit();
            finish();
        }

        else{
        if(ac.equalsIgnoreCase("") && aw.equalsIgnoreCase(""))
        {
            main.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
        }

        else if(ac.equalsIgnoreCase("") )
        {
            pb.setVisibility(View.INVISIBLE);
            Intent in=new Intent(Front.this,Main.class);
             startActivity(in);
        }

        else if(aw.equalsIgnoreCase(""))
        {
            pb.setVisibility(View.INVISIBLE);
            Intent in=new Intent(Front.this,MainActivity.class);
            startActivity(in);
        }
        else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            editor.clear();
            editor.commit();
            main.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
        }
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {

        super.onPause();

    }
    @Override
    protected void onStop() {

        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @OnClick({R.id.worker, R.id.customer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.worker:
                Intent wor=new Intent(Front.this, Selection_worker.class);
                wor.putExtra("type", "worker");
                startActivity(wor);
                break;
            case R.id.customer:
                Intent clnt = new Intent(Front.this, Login.class);
                clnt.putExtra("type", "customer");
                clnt.putExtra("acttype","customer");
                startActivity(clnt);
                break;

        }
    }



}
