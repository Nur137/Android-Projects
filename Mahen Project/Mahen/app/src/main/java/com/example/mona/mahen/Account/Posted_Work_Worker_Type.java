package com.example.mona.mahen.Account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mona.mahen.R;
import com.firebase.client.Firebase;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Posted_Work_Worker_Type extends AppCompatActivity {

    @Bind(R.id.driving)
    LinearLayout driving;
    @Bind(R.id.house_keeping)
    LinearLayout houseKeeping;
    @Bind(R.id.serving)
    LinearLayout serving;
    @Bind(R.id.cooking)
    LinearLayout cooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted__work__worker__type);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
    }

    @OnClick({R.id.driving, R.id.house_keeping, R.id.serving, R.id.cooking})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.driving:
                Intent dr=new Intent(Posted_Work_Worker_Type.this, Post_Work.class);
                dr.putExtra("acttype", "driver");
                startActivity(dr);
                break;

            case R.id.house_keeping:
                Intent hk=new Intent(Posted_Work_Worker_Type.this, Post_Work.class);
                hk.putExtra("acttype", "house_keeper");
                startActivity(hk);
                break;

            case R.id.serving:
                Intent sr=new Intent(Posted_Work_Worker_Type.this, Post_Work.class);
                sr.putExtra("acttype", "server");
                startActivity(sr);
                break;


            case R.id.cooking:
                Intent ck=new Intent(Posted_Work_Worker_Type.this, Post_Work.class);
                ck.putExtra("acttype", "cook");
                startActivity(ck);
                break;
        }
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