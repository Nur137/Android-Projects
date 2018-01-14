package com.example.nur.paintoolapp.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nur.paintoolapp.Classes.Config;
import com.example.nur.paintoolapp.Classes.DateShowAdapter;
import com.example.nur.paintoolapp.Classes.OrganShowAdapter;
import com.example.nur.paintoolapp.Database.Rating;
import com.example.nur.paintoolapp.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Patient_Rating extends AppCompatActivity {
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class is controls and stores information in databse of patient rating
     * */

    private Button update,rat0,rat1,rat2,rat3,rat4,rat5,rat6,rat7,rat8,rat9,rat10,close,no_pain,mild,moderate,severe,very_severe,worst_possible;
    private LinearLayout popup,background;
    private Button [] buttons=new Button[30];
    private int val=-1;
    private String id;
    private Firebase myFirebaseRef ;
    private String formattedDate,formattedTime,formattedDay;
    private ListView org_list;
    private ArrayList<String> org;
    private String Rat_Code;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__rating);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("pat_user_name");
        update=(Button)findViewById(R.id.update);
        String Organs[]={
                "front_forehead",
                "front_cheek_right",
                "front_cheek_left",
                "front_chin",
                "front_shoulder_right",
                "front_shoulder_left",
                "front_arm_right",
                "front_arm_left",
                "front_forearm_right",
                "front_forearm_left",
                "front_elbow_right",
                "front_elbow_left",
                "front_wrist_right",
                "front_wrist_left",
                "front_chest_right",
                "front_chest_left",
                "front_upper_abdomen",
                "front_lower_abdomen",
                "front_surgical",
                "front_thigh_right",
                "front_thigh_left",
                "front_knee_right",
                "front_knee_left",
                "front_leg_right",
                "front_leg_left",
                "front_ankle_right",
                "front_ankle_left",
                "front_foot_left",
                "front_foot_right"
        };

        //Initializing Ratcode String, which will contain 29 character signifying corresposdin pain code of Organ[i] 0<=i<=28
        //0-9 character for 0-9 rating and A for 10 rating
        // N means no rating
        Rat_Code=new String();

        // first set N to all organ;
        for(int i=0;i<29;i++)
        {
            Rat_Code+='N';
        }



        List<String> lst_orgs= Arrays.<String>asList(Organs);
        org=new ArrayList<String>();
        org.addAll(lst_orgs);

        //Getting date

        Calendar c = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd MMM,yyyy");
        SimpleDateFormat time =new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat day=new SimpleDateFormat("E", Locale.US);
        formattedDate = date.format(c.getTime());
        formattedTime=time.format(c.getTime());
        formattedDay=day.format(c.getTime());



        myFirebaseRef = new Firebase(Config.FIREBASE_URL).child("Rating");
        org_list=(ListView)findViewById(R.id.org_list);
        popup=(LinearLayout)findViewById(R.id.popup);
        background=(LinearLayout)findViewById(R.id.background);
        View view_popup=(View)findViewById(R.id.popup);
        view_popup.bringToFront();

        final View view_image=(View)findViewById(R.id.image);
        view_image.bringToFront();


        //Calling Organshowadapter class and filling pop up list
        OrganShowAdapter adapter = new OrganShowAdapter(this, org);
        org_list.setAdapter(adapter);

        //Pop up list item listener
        org_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                val=position;
            }
        });




        //When pop up window is on , clicking on the backgroung will close that pop up
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popup.getVisibility()==View.VISIBLE)
                {
                    popup.setVisibility(View.INVISIBLE);
                }
            }
        });

        //button listeners

        buttons[0]=(Button)findViewById(R.id.button_front_forehead);
        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(popup.getVisibility()==View.VISIBLE)
                {
                    popup.setVisibility(View.INVISIBLE);
                }
                else {
                    val=0;
                    popup.setVisibility(View.VISIBLE);
                    org_list.setSelection(0);
                }
            }
        });





        buttons[1]=(Button)findViewById(R.id.button_front_cheek_right);
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (popup.getVisibility() == View.VISIBLE) {
                    popup.setVisibility(View.INVISIBLE);
                } else {
                    val = 1;
                    popup.setVisibility(View.VISIBLE);
                    org_list.setSelection(1);
                }
            }
            });


        buttons[2]=(Button)findViewById(R.id.button_front_cheek_left);
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(popup.getVisibility()==View.VISIBLE)
                {
                    popup.setVisibility(View.INVISIBLE);
                }
                else {
                    val = 2;
                    popup.setVisibility(View.VISIBLE);
                    org_list.setSelection(1);
                }
            }
        });


        buttons[3]=(Button)findViewById(R.id.button_front_chin);
        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(popup.getVisibility()==View.VISIBLE)
                {
                    popup.setVisibility(View.INVISIBLE);
                }
                else {
                    val = 3;
                    popup.setVisibility(View.VISIBLE);
                    org_list.setSelection(3);
                }
            }
        });



        buttons[4]=(Button)findViewById(R.id.button_front_shoulder_right);
        buttons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(popup.getVisibility()==View.VISIBLE)
                {
                    popup.setVisibility(View.INVISIBLE);
                }
                else {
                    val = 4;
                    popup.setVisibility(View.VISIBLE);
                    org_list.setSelection(4);
                }
            }
        });



        buttons[5]=(Button)findViewById(R.id.button_front_shoulder_left);
        buttons[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(popup.getVisibility()==View.VISIBLE)
                {
                    popup.setVisibility(View.INVISIBLE);
                }
                else {
                    val = 5;
                    popup.setVisibility(View.VISIBLE);
                    org_list.setSelection(5);
                }
            }
        });



        buttons[6]=(Button)findViewById(R.id.button_front_arm_right);
        buttons[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(popup.getVisibility()==View.VISIBLE)
                {
                    popup.setVisibility(View.INVISIBLE);
                }
                else {
                    val = 6;
                    popup.setVisibility(View.VISIBLE);
                    org_list.setSelection(6);
                }
            }
        });


        buttons[7]=(Button)findViewById(R.id.button_front_arm_left);
        buttons[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=7;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(7);
            }
        });


        buttons[8]=(Button)findViewById(R.id.button_front_forearm_right);
        buttons[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=8;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(8);
            }
        });

        buttons[9]=(Button)findViewById(R.id.button_front_forearm_left);
        buttons[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=9;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(9);
            }
        });


        buttons[10]=(Button)findViewById(R.id.button_front_elbow_right);
        buttons[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=10;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(10);
            }
        });

        buttons[11]=(Button)findViewById(R.id.button_front_elbow_left);
        buttons[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=11;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(11);
            }
        });


        buttons[12]=(Button)findViewById(R.id.button_front_wrist_right);
        buttons[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=12;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(12);
            }
        });




        buttons[13]=(Button)findViewById(R.id.button_front_wrist_left);
        buttons[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=13;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(13);
            }
        });


        buttons[14]=(Button)findViewById(R.id.button_front_chest_right);
        buttons[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=14;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(14);
            }
        });



        buttons[15]=(Button)findViewById(R.id.button_front_chest_left);
        buttons[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=15;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(15);
            }
        });


        buttons[16]=(Button)findViewById(R.id.button_front_upper_abdomen);
        buttons[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=16;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(16);
            }
        });


        buttons[17]=(Button)findViewById(R.id.button_front_lower_abdomen);
        buttons[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=17;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(17);
            }
        });


        buttons[18]=(Button)findViewById(R.id.button_front_surgical);
        buttons[18].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=18;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(18);
            }
        });





        buttons[19]=(Button)findViewById(R.id.button_front_thigh_right);
        buttons[19].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=19;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(19);
            }
        });




        buttons[20]=(Button)findViewById(R.id.button_front_thigh_left);
        buttons[20].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=20;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(20);
            }
        });





        buttons[21]=(Button)findViewById(R.id.button_front_knee_right);
        buttons[21].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=21;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(21);
            }
        });



        buttons[22]=(Button)findViewById(R.id.button_front_knee_left);
        buttons[22].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=22;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(22);
            }
        });



        buttons[23]=(Button)findViewById(R.id.button_front_leg_right);
        buttons[23].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=23;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(23);
            }
        });


        buttons[24]=(Button)findViewById(R.id.button_front_leg_left);
        buttons[24].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=24;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(24);
            }
        });

        buttons[25]=(Button)findViewById(R.id.button_front_ankle_right);
        buttons[25].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=25;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(25);
            }
        });


        buttons[26]=(Button)findViewById(R.id.button_front_ankle_left);
        buttons[26].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=26;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(26);
            }
        });





        buttons[27]=(Button)findViewById(R.id.button_front_foot_left);
        buttons[27].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=27;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(27);
            }
        });




    

        buttons[28]=(Button)findViewById(R.id.button_front_foot_right);
        buttons[28].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val=28;
                popup.setVisibility(View.VISIBLE);
                org_list.setSelection(28);
            }
        });

        rat0=(Button)findViewById(R.id.rat0);
        rat1=(Button)findViewById(R.id.rat1);
        rat2=(Button)findViewById(R.id.rat2);
        rat3=(Button)findViewById(R.id.rat3);
        rat4=(Button)findViewById(R.id.rat4);
        rat5=(Button)findViewById(R.id.rat5);
        rat6=(Button)findViewById(R.id.rat6);
        rat7=(Button)findViewById(R.id.rat7);
        rat8=(Button)findViewById(R.id.rat8);
        rat9=(Button)findViewById(R.id.rat9);
        rat10=(Button)findViewById(R.id.rat10);
        close=(Button)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.setVisibility(View.INVISIBLE);
            }
        });

        no_pain=(Button)findViewById(R.id.no_pain);
        mild=(Button)findViewById(R.id.mild);
        moderate=(Button)findViewById(R.id.moderate);
        severe=(Button)findViewById(R.id.severe);
        very_severe=(Button)findViewById(R.id.very_severe);
        worst_possible=(Button)findViewById(R.id.worst_possible);


        //Getting data and showing it from database
        myFirebaseRef.addChildEventListener(new ChildEventListener() {
                    // Retrieve new posts as they are added to Firebase
                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                        Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();

                        String date = String.valueOf(newPost.get("date"));
                        String patint = String.valueOf(newPost.get("patient_ID"));
                        if (date.equalsIgnoreCase(formattedDate) && patint.equalsIgnoreCase(id)) {
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

        //Pop up buttons listener

        rat0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#FDFED7"));

                Rat_Code=Rat_Code.substring(0, val) + "0" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);



            }
        });

        no_pain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#FDFED7"));

                Rat_Code=Rat_Code.substring(0, val) + "0" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);

            }
        });

        rat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                buttons[val].setBackgroundColor(Color.parseColor("#FDFED7"));

                Rat_Code=Rat_Code.substring(0, val) + "1" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);

            }
        });


        rat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#FDF95A"));
                Rat_Code=Rat_Code.substring(0, val) + "2" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);

            }
        });

        mild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#FDF95A"));
                Rat_Code=Rat_Code.substring(0, val) + "2" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);

            }
        });

        rat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#FDF95A"));
                Rat_Code=Rat_Code.substring(0, val) + "3" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);
            }
        });

        rat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#FCE327"));
                Rat_Code=Rat_Code.substring(0, val) + "4" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);
            }
        });

        moderate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#FCE327"));
                Rat_Code=Rat_Code.substring(0, val) + "4" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);
            }
        });

        rat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#FCE327"));
                Rat_Code=Rat_Code.substring(0, val) + "5" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);
            }
        });

        rat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#F8A73A"));
                Rat_Code=Rat_Code.substring(0, val) + "6" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);
            }
        });


        severe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#F8A73A"));
                Rat_Code=Rat_Code.substring(0, val) + "6" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);
            }
        });
        rat7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#F8A73A"));
                Rat_Code=Rat_Code.substring(0, val) + "7" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);
            }
        });

        rat8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#F7B9CE"));
                Rat_Code=Rat_Code.substring(0, val) + "8" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);
            }
        });

        very_severe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#F7B9CE"));
                Rat_Code=Rat_Code.substring(0, val) + "8" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);
            }
        });

        rat9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#F7B9CE"));
                Rat_Code=Rat_Code.substring(0, val) + "9" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);
            }
        });

        rat10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#990342"));
                Rat_Code=Rat_Code.substring(0, val) + "A" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);
            }
        });

        worst_possible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[val].setBackgroundColor(Color.parseColor("#990342"));
                Rat_Code=Rat_Code.substring(0, val) + "A" + Rat_Code.substring(val+1, Rat_Code.length());
                popup.setVisibility(View.INVISIBLE);
            }
        });



        //Database gets values after this button is clicked
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rating rat=new Rating();
                rat.setPatient_ID(id);
                rat.setdate(formattedDate);
                rat.setTime(formattedTime);
                rat.setDay(formattedDay);
                rat.setRating_code(Rat_Code);

                rat.pushRating(myFirebaseRef);
            }
        });

    }

}
