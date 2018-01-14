package com.example.mona.admin;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Details extends AppCompatActivity {
    String  idn, name, uname, em, pas, typ, bday, gend,  acttp, prop, addr,cont,nat, uid;
    @Bind(R.id.pic)
    CircleImageView pic;
    @Bind(R.id.id)
    TextView id;
    @Bind(R.id.fname)
    TextView fname;
    @Bind(R.id.un)
    TextView un;
    @Bind(R.id.pass)
    TextView pass;
    @Bind(R.id.b_date)
    TextView bDate;
    @Bind(R.id.email)
    TextView email;
    @Bind(R.id.sex)
    TextView sex;
    @Bind(R.id.acc)
    EditText acc;
    @Bind(R.id.appr)
    Button appr;
    @Bind(R.id.rej)
    Button rej;

    Firebase firebase;
    @Bind(R.id.contact)
    TextView contact;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.nationality)
    TextView nationality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        firebase = new Firebase(Config.FIREBASE_URL);

        uid = bundle.getString("id");
        idn = bundle.getString("idno_user");
        name = bundle.getString("full_name_user");
        uname = bundle.getString("user_name_user");
        em = bundle.getString("email_user");
        pas = bundle.getString("pass_user");
        typ = bundle.getString("type_user");
        bday = bundle.getString("bday_user");
        gend = bundle.getString("sex_user");
        acttp = bundle.getString("acttype_user");
        addr = bundle.getString("address");
        cont = bundle.getString("contact");
        nat = bundle.getString("nationality");
        Firebase firebase = new Firebase(Config.FIREBASE_URL);
        id.setText(idn);
        fname.setText(name);
        un.setText(uname);
        email.setText(em);
        pass.setText(pas);
        bDate.setText(bday);
        sex.setText(gend);
        nationality.setText(nat);
        contact.setText(cont);
        address.setText(addr);
        appr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        appr.getBackground().setAlpha(180);
                        break;
                    case MotionEvent.ACTION_UP:
                        appr.getBackground().setAlpha(255);


                        break;
                }
                return false;
            }
        });

        firebase.child("Admin").child("Not Approved").child(typ).child(uid).child("propic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                prop = value;

                byte[] decodedString = Base64.decode(prop, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                pic.setImageBitmap(decodedByte);


                // do your stuff here with value
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    @OnClick({R.id.appr, R.id.rej})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appr:


                User user = new User();
                user.setFull_name(name);
                user.setUn(uname);
                user.setEmail(em);
                user.setPass(pas);
                user.setType(acttp);
                user.setBirth_date(bday);
                user.setSex(gend);
                user.setID(idn);
                user.setPropic(prop);
                user.setNationality(nat);
                user.setContact(cont);
                user.setAddress(addr);

                    firebase.child("Admin").child("Not Approved").child("customer").child(uid).removeValue();
                    firebase.child("Reg_users").child("customer").child(uid).setValue(user);
                    firebase.child("Reg_users").child("customer").child(uid).child("account").setValue(acc.getText().toString());
                    firebase.child("Reg_users").child("customer").child(uid).child("Latitude").setValue("0");
                    firebase.child("Reg_users").child("customer").child(uid).child("Longitude").setValue("0");


                sendEmail(em);

                break;

            case R.id.rej:
                firebase.child("Admin").child("Not Approved").child("customer").child(uid).removeValue();
                sendEmail2(em);

        }


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
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Congratulation. Now you are a registered user of our App MAHEN");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(Details.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
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
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Sorry you haven't provided all required criteria. Please try to register again. And read the terms and conditions perfectly");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(Details.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

}