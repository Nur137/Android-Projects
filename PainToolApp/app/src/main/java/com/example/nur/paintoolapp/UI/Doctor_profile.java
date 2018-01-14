package com.example.nur.paintoolapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nur.paintoolapp.Classes.Config;
import com.example.nur.paintoolapp.Database.Doctor;
import com.example.nur.paintoolapp.Database.Doctor_info;
import com.example.nur.paintoolapp.Database.Patient_info;
import com.example.nur.paintoolapp.R;
import com.firebase.client.Firebase;

public class Doctor_profile extends AppCompatActivity {

    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class is a database class which fills Firebse database form
     * This class also provide getter and setter methods
     * It Establishes Doctor-patient relationship
     * */

    private EditText country,province,zip_code,det_address,qualification;
    private Button Update;
    private Firebase myFirebaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        Update=(Button)findViewById(R.id.update);

        country=(EditText)findViewById(R.id.country);
        province=(EditText)findViewById(R.id.province);
        zip_code=(EditText)findViewById(R.id.zip_code);
        det_address=(EditText)findViewById(R.id.det_add);
        qualification=(EditText)findViewById(R.id.qualification);

        myFirebaseref=new Firebase(Config.FIREBASE_URL).child("doctor_info");


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doctor_info doc=new Doctor_info();
                doc.setCountry(country.getText().toString());
                doc.setProvince(province.getText().toString());
                doc.setDet_address(det_address.getText().toString());
                doc.setZip_code(zip_code.getText().toString());
                doc.setQualification(qualification.getText().toString());

                myFirebaseref.push().setValue(doc);
            }
        });
    }


}
