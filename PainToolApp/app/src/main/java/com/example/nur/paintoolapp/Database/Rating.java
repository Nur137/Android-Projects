package com.example.nur.paintoolapp.Database;


import com.firebase.client.Firebase;

public class Rating {

    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-14-06
     * This class is a database class which fills Firebse database form
     * This class contains rating information of body parts along with date and time
     * */
    private String date;
    private String time;
    private String patient_ID;
    private String rating_code;
    private String day;
    public Rating() {
        /**
         * Blank default constructor essential for Firebase
         * */
    }


    //Getters and setters

    public String getdate() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides date of pain
         */
        return date;
    }
    public void setdate(String date) {
        /**
         * This is a setter methos of Doctor class
         * @param String
         * @return void
         * Sets date of pain
         */
        this.date =  date;
    }

    public String getTime() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides specific time of pain
         */
        return time;
    }
    public void setTime(String time)
    {
        /**
         * This is a setter methos of Doctor class
         * @param String
         * @return void
         * Sets specific time of pain
         */
        this.time=time;
    }

    public String getPatient_ID() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides patient ID of pain holder
         */
        return patient_ID;
    }
    public void setPatient_ID(String patient_ID) {
        /**
         * This is a setter methos of Doctor class
         * @param String
         * @return void
         * Sets patient ID of pain holder
         */
        this.patient_ID =  patient_ID;
    }

    public String getRating_code() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides 29 character pain code
         */
        return rating_code;
    }
    public void setRating_code(String rating_code) {
        /**
         * This is a setter methos of Doctor class
         * @param String
         * @return void
         * Sets 29 character pain code
         */
        this.rating_code=rating_code;
    }

    public String getDay()
    {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides day of pain
         */
        return day;
    }
    public void setDay(String day) {
        this.day =  day;
    }




    public void pushRating(Firebase myFirebaseRef) {
        /**
         * @param Firebase Object
         * @return void
         * Spushes patients pain rating data at specific location
         */
        myFirebaseRef.push().setValue(this);
    }

}
