package com.example.mona.mahen.Account;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.Database.Notify_worker;
import com.example.mona.mahen.Database.Work;
import com.example.mona.mahen.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.vision.text.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Post_Work extends AppCompatActivity {
    SharedPreferences preferences;

    TextView account;
    @Bind(R.id.w_title)
    EditText wTitle;
    @Bind(R.id.w_description)
    EditText wDescription;
    @Bind(R.id.f_date)
    EditText fDate;
    @Bind(R.id.f_time)
    EditText fTime;
    @Bind(R.id.t_date)
    EditText tDate;
    @Bind(R.id.t_time)
    EditText tTime;
    @Bind(R.id.p_from)
    EditText pFrom;
    @Bind(R.id.p_to)
    EditText pTo;
    @Bind(R.id.range)
    EditText range;
    @Bind(R.id.submit)
    Button submit;
    Firebase firebase;

    ArrayList<Notify_worker> nw;

    Spinner nat;
    int mYear,mMonth,mDay;
    String acttp,lat,lon,uid,active,acc="100";

    String Cal_months[]={"JAN","FEB", "MAR", "APR", "MAY", "JUN", "JUL" , "AUG" , "SEP", "OCT", "NOV", "DEC"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__work);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Bundle bundle = getIntent().getExtras();
        acttp = bundle.getString("acttype");
        Firebase.setAndroidContext(this);
        active=preferences.getString("cust_id","");
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        nat=(Spinner)findViewById(R.id.nat);
        account=(TextView)findViewById(R.id.account);

        nw=new ArrayList<Notify_worker>() ;


        firebase=new Firebase(Config.FIREBASE_URL);

        firebase.child("Reg_users").child("customer").child(active).child("account").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acc = (String) dataSnapshot.getValue();
                account.setText("Your account "+acc);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @OnClick({R.id.f_date, R.id.f_time, R.id.t_date, R.id.t_time, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.f_date:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                fDate.setText(dayOfMonth + " " + Cal_months[monthOfYear] + " " + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.f_time:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Post_Work.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;

            case R.id.t_date:
                final Calendar c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tDate.setText(dayOfMonth + " " + Cal_months[monthOfYear] + " " + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog1.show();
                break;
            case R.id.t_time:
                Calendar mcurrentTime1 = Calendar.getInstance();
                int hour1 = mcurrentTime1.get(Calendar.HOUR_OF_DAY);
                int minute1 = mcurrentTime1.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker1;
                mTimePicker1 = new TimePickerDialog(Post_Work.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour1, minute1, true);//Yes 24 hour time
                mTimePicker1.setTitle("Select Time");
                mTimePicker1.show();
                break;

            case R.id.submit:
                if(wTitle.getText().toString().equalsIgnoreCase(""))
                {
                    dtitle();
                }
                else if(wDescription.getText().toString().equalsIgnoreCase(""))
                {
                    ddesc();
                }
                else if(Integer.parseInt(pFrom.getText().toString())>Integer.parseInt(acc) || Integer.parseInt(pTo.getText().toString())>Integer.parseInt(acc) )
                {
                    checkacc();
                }

                else {
                    Work work = new Work();
                    work.setPosted_by(active);
                    work.setTitle(wTitle.getText().toString());
                    work.setDescription(wDescription.getText().toString());
                    work.setFrom_date(fDate.getText().toString());
                    work.setFrom_time(fTime.getText().toString());
                    work.setTo_date(tDate.getText().toString());
                    work.setTo_time(tTime.getText().toString());
                    work.setRange(range.getText().toString());
                    work.setPrice_from(pFrom.getText().toString());
                    work.setPrice_to(pTo.getText().toString());
                    work.setWorker_type(acttp);
                    work.setNationality(nat.getSelectedItem().toString());

                    final Firebase firebase = new Firebase(Config.FIREBASE_URL).child("posted_work").push();
                    uid = firebase.getKey();
                    work.setUid(uid);
                    firebase.setValue(work);


                    final Firebase f2 = new Firebase(Config.FIREBASE_URL);

                    f2.child("Reg_users").child("customer").child(active).child("Latitude").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            lat = (String) dataSnapshot.getValue();

                            // do your stuff here with value

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                    f2.child("Reg_users").child("customer").child(active).child("Longitude").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            lon = (String) dataSnapshot.getValue();

                            // do your stuff here with value

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });


                    f2.child("Reg_users").child("worker").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                            Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                            String longi = String.valueOf(newPost.get("Longitude"));
                            String lati = String.valueOf(newPost.get("Latitude"));
                            String type = String.valueOf(newPost.get("type"));
                            String national = String.valueOf(newPost.get("nationality"));
                            if (range.getText().toString().equalsIgnoreCase("")) drange();
                            else if (nat.getSelectedItem().toString().equalsIgnoreCase(national) && Math.abs(distance(Double.parseDouble(lati), Double.parseDouble(longi), Double.parseDouble(lat), Double.parseDouble(lon))) <= Double.parseDouble(range.getText().toString()) && type.equalsIgnoreCase(acttp))
//if (nat.getSelectedItem().toString().equalsIgnoreCase(national)&& type.equalsIgnoreCase(acttp))
                            {
                                Notify_worker notify = new Notify_worker();
                                notify.setWork_id(uid);
                                notify.setWork_title(wTitle.getText().toString());
                                notify.setWorker_id(snapshot.getKey());
                                Firebase f3 = new Firebase(Config.FIREBASE_URL).child("notify").child("worker").push();
                                notify.setU_id(f3.getKey());
                                notify.setCust_id(active);
                                notify.setDistance(Double.toString(Math.abs(distance(Double.parseDouble(lati), Double.parseDouble(longi), Double.parseDouble(lat), Double.parseDouble(lon)))));
                                f3.setValue(notify);
                                nw.add(notify);

                            }
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

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplication(), " Your request have been sent to the workers maintaining required criteria", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344;

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }



    public void dtitle()
    {

        Toast.makeText(this,"Must provide a title",Toast.LENGTH_SHORT).show();

    }


    public void ddesc()
    {

        Toast.makeText(this,"Must provide a description",Toast.LENGTH_SHORT).show();

    }


    public void drange()
    {

        Toast.makeText(this,"Please provide a range in km",Toast.LENGTH_SHORT).show();

    }

    public void checkacc()
    {

        Toast.makeText(this,"You dont have sufficient balance",Toast.LENGTH_SHORT).show();
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
