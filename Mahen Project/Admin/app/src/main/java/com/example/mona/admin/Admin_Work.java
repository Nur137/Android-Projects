package com.example.mona.admin;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Admin_Work extends AppCompatActivity {


    LinearLayout au;
    LinearLayout wr;
    LinearLayout rr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__work);
        au = (LinearLayout) findViewById(R.id.au);
        wr = (LinearLayout) findViewById(R.id.wr);
        rr = (LinearLayout) findViewById(R.id.rr);



        au.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Admin_Work.this, MainActivity.class);
                startActivity(in);

            }
        });

        wr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(Admin_Work.this, Withdraw_Request.class);
                startActivity(in1);

            }
        });

        rr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in2 = new Intent(Admin_Work.this, Recharge_Request.class);
                startActivity(in2);

            }
        });

    }
}