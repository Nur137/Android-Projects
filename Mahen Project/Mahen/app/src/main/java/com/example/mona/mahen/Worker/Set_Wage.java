package com.example.mona.mahen.Worker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.Database.Notify_cust;
import com.example.mona.mahen.R;
import com.firebase.client.Firebase;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Set_Wage extends AppCompatActivity {

    @Bind(R.id.p_from)
    TextView pFrom;
    @Bind(R.id.p_to)
    TextView pTo;
    @Bind(R.id.wage)
    EditText wage;
    @Bind(R.id.desc)
    EditText desc;
    @Bind(R.id.apply)
    Button apply;
    SharedPreferences preferences;
    String worker_id,cust_id,work_id,u_id;
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__wage);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        Firebase.setAndroidContext(this);
        worker_id = bundle.getString("worker_id");
        work_id = bundle.getString("work_id");
        cust_id=bundle.getString("cust_id");
        u_id=bundle.getString("uid");
        pFrom.setText(bundle.getString("pf"));
        pTo.setText(bundle.getString("pt"));
        firebase=new Firebase(Config.FIREBASE_URL);
       // Toast.makeText(Set_Wage.this,"Hello"+bundle.getString("pf"),Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.apply)
    public void onClick() {
        if(Integer.parseInt(wage.getText().toString())>Integer.parseInt(pTo.getText().toString()) ||
                Integer.parseInt(wage.getText().toString())<Integer.parseInt(pFrom.getText().toString()))
        {
            Toast.makeText(this,"Please maintain the price range",Toast.LENGTH_SHORT).show();
        }
        else{

            firebase.child("notify").child("worker").child(u_id).removeValue();

            Firebase notify=new Firebase(Config.FIREBASE_URL).child("notify").child("customer").push();
            u_id=notify.getKey();
            Notify_cust not_cust=new Notify_cust();
            not_cust.setWork_id(work_id);
            not_cust.setWorker_id(worker_id);
            not_cust.setCust_id(cust_id);
            not_cust.setU_id(u_id);
            not_cust.setWage(wage.getText().toString());
            not_cust.setDescription(desc.getText().toString());
            notify.setValue(not_cust);
            Intent in=new Intent(Set_Wage.this,Main.class);
            startActivity(in);
            Toast.makeText(getApplication()," This Customer will be informed ", Toast.LENGTH_SHORT).show();
        }

    }
}