package com.example.mona.mahen.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.Database.User;
import com.example.mona.mahen.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Registration extends AppCompatActivity {

    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.b_date)
    EditText bDate;
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.un)
    EditText un;
    @Bind(R.id.ssn)
    EditText ssn;
    @Bind(R.id.pass)
    EditText pass;
    @Bind(R.id.retype_pass)
    EditText retypePass;
    @Bind(R.id.male)
    RadioButton male;
    @Bind(R.id.female)
    RadioButton female;
    @Bind(R.id.MemberType)
    RadioGroup MemberType;
    @Bind(R.id.agree)
    CheckBox agree;
    ArrayList<String> users;
    String EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    boolean flag = false;

    String countrycode[]={"+1", "+20", "+212", "+213", "+216", "+218", "+220", "+221", "+222", "+223", "+224", "+225", "+226", "+227", "+228", "+229", "+230", "+231", "+232", "+233", "+234", "+235", "+236", "+237", "+238", "+239", "+240", "+241", "+242", "+243", "+244", "+245", "+246", "+247", "+248", "+249", "+250", "+251", "+252", "+253", "+254", "+255", "+256", "+257", "+258", "+260", "+261", "+262", "+263", "+264", "+265", "+266", "+267", "+268", "+269", "+27", "+290", "+291", "+297", "+298", "+299", "+30", "+31", "+32", "+33", "+34", "+350", "+351", "+352", "+353", "+354", "+355", "+356", "+357", "+358", "+359", "+36", "+370", "+371", "+372", "+373", "+374", "+375", "+376", "+377", "+378", "+379", "+380", "+381", "+385", "+386", "+387", "+389", "+39", "+40", "+41", "+420", "+421", "+423", "+43", "+44", "+45", "+46", "+47", "+48", "+49", "+500", "+501", "+502", "+503", "+504", "+505", "+506", "+507", "+508", "+509", "+51", "+52", "+53", "+54", "+55", "+56", "+57", "+58", "+590", "+591", "+592", "+593", "+594", "+595", "+596", "+597", "+598", "+599", "+60", "+61", "+62", "+63", "+64", "+65", "+66", "+670", "+672", "+673", "+674", "+675", "+676", "+677", "+678", "+679", "+680", "+681", "+682", "+683", "+684", "+685", "+686", "+687", "+688", "+689", "+690", "+691", "+692", "+7", "+800", "+81", "+82", "+84", "+850", "+852", "+853", "+855", "+856", "+86", "+870", "+871", "+872", "+873", "+874", "+880", "+881", "+886", "+90", "+91", "+92", "+93", "+94", "+95", "+960", "+961", "+962", "+963", "+964", "+965", "+966", "+967", "+968", "+970", "+971", "+972", "+973", "+974", "+975", "+976", "+977", "+98", "+992", "+993", "+994", "+995", "+996", "+997", "+998",};


    String Cal_months[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};


    Firebase firebase;
    String sex = "Male", tp, acttp;

    User user;
    @Bind(R.id.register)
    Button register;
    @Bind(R.id.login)
    TextView login;

    Calendar myCalendar;
    @Bind(R.id.address)
    EditText address;
    @Bind(R.id.contact)
    EditText contact;

    @Bind(R.id.code)
    AutoCompleteTextView code;

    @Bind(R.id.sp)
    Spinner sp;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Firebase.setAndroidContext(this);
        Bundle bundle = getIntent().getExtras();
        tp = bundle.getString("type");
        acttp = bundle.getString("acttype");
        ButterKnife.bind(this);
        users = new ArrayList<String>();

        firebase = new Firebase(Config.FIREBASE_URL);


        myCalendar = Calendar.getInstance();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,countrycode);
        code.setAdapter(adapter);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        bDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Registration.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        MemberType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = MemberType.findViewById(checkedId);
                int index = MemberType.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0: // first button
                        sex = "Male";
                        break;
                    case 1: // secondbutton
                        sex = "Female";
                        break;
                }
            }
        });
    }


    @OnClick({R.id.register, R.id.login, R.id.b_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                Firebase firebase = new Firebase(Config.FIREBASE_URL);


                firebase.child("Reg_users").child(tp).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                        Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                        String uname = String.valueOf(newPost.get("un"));
                        users.add(uname);

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


                //Checking authorization when loading is done
                firebase.child("Reg_users").child(tp).addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (check_code()==false)dt();
                        else if (name.getText().toString().equalsIgnoreCase("") ||
                                un.getText().toString().equalsIgnoreCase("") ||
                                email.getText().toString().equalsIgnoreCase("") ||
                                pass.getText().toString().equalsIgnoreCase("") ||
                                bDate.getText().toString().equalsIgnoreCase("") ||
                                ssn.getText().toString().equalsIgnoreCase("")) {
                            doToast();
                        } else if (email.getText().toString().matches(EMAIL_REGEX) == false) {
                            doToast4();
                        } else if (pass.getText().toString().length() < 10) {
                            doToast1();
                        } else if (!retypePass.getText().toString().equalsIgnoreCase(pass.getText().toString())) {
                            doToast5();
                        } else if (users.contains(un.getText().toString())) {
                            doToast2();
                        } else if (agree.isChecked() == false) {
                            doToast3();
                        } else {
                            if (tp.equalsIgnoreCase("customer")) {

                                Intent clnt = new Intent(Registration.this, Propic.class);
                                clnt.putExtra("full_name_user", name.getText().toString());
                                clnt.putExtra("user_name_user", un.getText().toString());
                                clnt.putExtra("email_user", email.getText().toString());
                                clnt.putExtra("pass_user", pass.getText().toString());
                                clnt.putExtra("type_user", tp);
                                clnt.putExtra("address",address.getText().toString());
                                String cnt= code.getText().toString()+contact.getText().toString();
                                clnt.putExtra("contact",cnt);
                                clnt.putExtra("country",sp.getSelectedItem().toString());
                                clnt.putExtra("bday_user", bDate.getText().toString());
                                clnt.putExtra("sex_user", sex);
                                clnt.putExtra("ssn_user", ssn.getText().toString());
                                clnt.putExtra("acttype_user", acttp);
                                startActivity(clnt);

                            } else {

                                Intent clnt = new Intent(Registration.this, Worker_Propic.class);
                                clnt.putExtra("full_name_user", name.getText().toString());
                                clnt.putExtra("user_name_user", un.getText().toString());
                                clnt.putExtra("email_user", email.getText().toString());
                                clnt.putExtra("pass_user", pass.getText().toString());
                                clnt.putExtra("type_user", tp);
                                clnt.putExtra("bday_user", bDate.getText().toString());
                                clnt.putExtra("sex_user", sex);
                                clnt.putExtra("ssn_user", ssn.getText().toString());
                                clnt.putExtra("acttype_user", acttp);
                                clnt.putExtra("address",address.getText().toString());
                                String cnt= code.getText().toString()+contact.getText().toString();
                                clnt.putExtra("contact",cnt);
                                clnt.putExtra("country",sp.getSelectedItem().toString());

                                startActivity(clnt);

                            }

                        }


                    }

                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });


                break;
            case R.id.login:
                Intent usr = new Intent(Registration.this, Login.class);
                usr.putExtra("type", tp);
                usr.putExtra("acttype", acttp);
                startActivity(usr);
                break;

        }

    }


    public void dt() {
        Toast.makeText(this, "Country code not matches", Toast.LENGTH_SHORT).show();

    }

    public void doToast() {
        Toast.makeText(this, "No field should be empty", Toast.LENGTH_SHORT).show();

    }

    public void doToast1() {
        Toast.makeText(this, "Password length should be minimum 10", Toast.LENGTH_SHORT).show();

    }


    public void doToast2() {
        Toast.makeText(this, "This Username is Already taken", Toast.LENGTH_SHORT).show();
    }

    public void doToast3() {
        Toast.makeText(this, "Must agree with all terms and conditions", Toast.LENGTH_SHORT).show();

    }

    public void doToast4() {

        Toast.makeText(this, "Not a valid email address", Toast.LENGTH_SHORT).show();
    }

    public void doToast5() {

        Toast.makeText(this, "Password didn't match", Toast.LENGTH_SHORT).show();
    }


    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        bDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            Intent in = new Intent(Registration.this, Front.class);
            startActivity(in);
        }

        return super.onKeyDown(keyCode, event);
    }

    public boolean check_code()
    {

        for(int i=0;i<countrycode.length;i++)
        {
            if(code.getText().toString().equalsIgnoreCase(countrycode[i]))return true;
        }
        return false;
    }

}



