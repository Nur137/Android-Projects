package com.example.mona.mahen.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import com.example.mona.mahen.R;
import com.example.mona.mahen.Worker.Main;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Invoice extends AppCompatActivity {
    String wage;
    @Bind(R.id.message)
    TextView message;
    @Bind(R.id.back)
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        wage=bundle.getString("wage");
        message.setText("You have been received $"+wage+" Congratulation");

        }

        @OnClick(R.id.back)
        public void onClick() {
        Intent in=new Intent(Invoice.this, Main.class);
        startActivity(in);
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