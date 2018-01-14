package com.example.mona.mahen.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mona.mahen.R;
import com.firebase.client.Firebase;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Selection_worker extends AppCompatActivity {

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
        setContentView(R.layout.activity_selection_worker);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
    }

    @OnClick({R.id.driving, R.id.house_keeping, R.id.serving, R.id.cooking})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.driving:
                Intent dr=new Intent(Selection_worker.this, Login.class);
                dr.putExtra("type", "worker");
                dr.putExtra("acttype", "driver");
                startActivity(dr);
                break;

            case R.id.house_keeping:
                Intent hk=new Intent(Selection_worker.this, Login.class);
                hk.putExtra("type", "worker");
                hk.putExtra("acttype", "house_keeper");
                startActivity(hk);
                break;

            case R.id.serving:
                Intent sr=new Intent(Selection_worker.this, Login.class);
                sr.putExtra("type", "worker");
                sr.putExtra("acttype", "server");
                startActivity(sr);
                break;


            case R.id.cooking:
                Intent ck=new Intent(Selection_worker.this, Login.class);
                ck.putExtra("type", "worker");
                ck.putExtra("acttype", "cook");
                startActivity(ck);
                break;
        }
    }
    @Override
    protected void onPause() {

        super.onPause();
        // startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        finish();

    }
}
