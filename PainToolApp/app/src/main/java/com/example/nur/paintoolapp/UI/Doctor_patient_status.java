package com.example.nur.paintoolapp.UI;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.nur.paintoolapp.Classes.Config;
import com.example.nur.paintoolapp.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class Doctor_patient_status extends AppCompatActivity {
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class is a database class which fills Firebse database form
     * This class also provide getter and setter methods
     * It Establishes Doctor-patient relationship
     * */
    Button[] buttons = new Button[30];
    int val = -1;
    Firebase myFirebaseRef,patient_db;
    private String organ,rating,Rat_Code,ID,TIME,DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_status);
        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("id");
        TIME=bundle.getString("time");
        DATE=bundle.getString("date");


        View view_image=(View)findViewById(R.id.image);
        view_image.bringToFront();

        myFirebaseRef = new Firebase(Config.FIREBASE_URL).child("Rating");
        buttons[0] = (Button) findViewById(R.id.button_front_forehead);
        buttons[1] = (Button) findViewById(R.id.button_front_cheek_right);
        buttons[2] = (Button) findViewById(R.id.button_front_cheek_left);
        buttons[3] = (Button) findViewById(R.id.button_front_chin);
        buttons[4] = (Button) findViewById(R.id.button_front_shoulder_right);
        buttons[5] = (Button) findViewById(R.id.button_front_shoulder_left);
        buttons[6] = (Button) findViewById(R.id.button_front_arm_right);
        buttons[7] = (Button) findViewById(R.id.button_front_arm_left);
        buttons[8] = (Button) findViewById(R.id.button_front_forearm_right);
        buttons[9] = (Button) findViewById(R.id.button_front_forearm_left);
        buttons[10] = (Button) findViewById(R.id.button_front_elbow_right);
        buttons[11] = (Button) findViewById(R.id.button_front_elbow_left);
        buttons[12] = (Button) findViewById(R.id.button_front_wrist_right);
        buttons[13] = (Button) findViewById(R.id.button_front_wrist_left);
        buttons[14] = (Button) findViewById(R.id.button_front_chest_right);
        buttons[15] = (Button) findViewById(R.id.button_front_chest_left);
        buttons[16] = (Button) findViewById(R.id.button_front_upper_abdomen);
        buttons[17] = (Button) findViewById(R.id.button_front_lower_abdomen);
        buttons[18] = (Button) findViewById(R.id.button_front_surgical);
        buttons[19] = (Button) findViewById(R.id.button_front_thigh_right);
        buttons[20] = (Button) findViewById(R.id.button_front_thigh_left);
        buttons[21] = (Button) findViewById(R.id.button_front_knee_right);
        buttons[22] = (Button) findViewById(R.id.button_front_knee_left);
        buttons[23] = (Button) findViewById(R.id.button_front_leg_right);
        buttons[24] = (Button) findViewById(R.id.button_front_leg_left);
        buttons[25] = (Button) findViewById(R.id.button_front_ankle_right);
        buttons[26] = (Button) findViewById(R.id.button_front_ankle_left);
        buttons[27] = (Button) findViewById(R.id.button_front_foot_left);
        buttons[28] = (Button) findViewById(R.id.button_front_foot_right);





        myFirebaseRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();

                String time = String.valueOf(newPost.get("time"));
                String date = String.valueOf(newPost.get("date"));
                String patid = String.valueOf(newPost.get("patient_ID"));
                if (date.equalsIgnoreCase(DATE) && patid.equalsIgnoreCase(ID)&& time.equalsIgnoreCase(TIME)) {
                    String organ_code = String.valueOf(newPost.get("rating_code"));
                    Rat_Code = organ_code;
                    buttons[0].setText(organ_code);
                    for (int i=0;i<29;i++)
                    {
                        if(Rat_Code.charAt(i)=='0' || Rat_Code.charAt(i)=='1')
                        {
                            buttons[i].setBackgroundColor(Color.parseColor("#FDFED7"));
                        }
                        else if(Rat_Code.charAt(i)=='2'|| Rat_Code.charAt(i)=='3')
                        {
                            buttons[i].setBackgroundColor(Color.parseColor("#FDF95A"));

                        }
                        else if(Rat_Code.charAt(i)=='4'|| Rat_Code.charAt(i)=='5')
                        {
                            buttons[i].setBackgroundColor(Color.parseColor("#FCE327"));

                        }
                        else if(Rat_Code.charAt(i)=='6'|| Rat_Code.charAt(i)=='7')
                        {
                            buttons[i].setBackgroundColor(Color.parseColor("#F8A73A"));

                        }

                        else if(Rat_Code.charAt(i)=='8'|| Rat_Code.charAt(i)=='9')
                        {
                            buttons[i].setBackgroundColor(Color.parseColor("#F7B9CE"));

                        }

                        else if(Rat_Code.charAt(i)=='A')
                        {
                            buttons[i].setBackgroundColor(Color.parseColor("#990342"));

                        }
                    }


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







    }
}
