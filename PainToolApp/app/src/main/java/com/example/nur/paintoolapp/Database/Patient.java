package com.example.nur.paintoolapp.Database;

import com.firebase.client.Firebase;

public class Patient {
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-14-06
     * This class is a database class which fills Firebse database form
     * This class also provide getter and setter methods of Patient object
     * */

    private String id;
    private String name;
    private String email;
    private String password;
    public Patient() {
        /**
         * Blank default constructor essential for Firebase
         * */
    }


    //Getters and setters

    public String getId() {
        /**
         * This is a getter methos of Doctor class
         * @param Nothing
         * @return String
         * Provides Patient ID
         */
        return id;
    }

    public void setId(String id) {
        /**
         * This is a setter methos of Doctor class
         * @param String
         * @return void
         * Sets Patient ID
         */
        this.id = id;
    }
    public String getName() {
        /**
         * This is a getter methos of Doctor class
         * @param Nothing
         * @return String
         * Provides Patient Name
         */
        return name;
    }

    public void setName(String name)
    {
        /**
         * This is a setter methos of Doctor class
         * @param String
         * @return void
         * Sets Patient Name
         */
        this.name = name;
    }

    public String getEmail() {
        /**
         * This is a getter methos of Doctor class
         * @param Nothing
         * @return String
         * Provides Patient Email
         */
        return email;
    }

    public void setEmail(String email)
    {
        /**
         * This is a setter methos of Doctor class
         * @param String
         * @return void
         * Sets Patient Email
         */
        this.email=email;
    }

    public String getPass() {
        /**
         * This is a getter methos of Doctor class
         * @param Nothing
         * @return String
         * Provides Patient Password
         */
        return password;
    }
    public void setPass(String password) {
        /**
         * This is a setter methos of Doctor class
         * @param String
         * @return void
         * Sets Patient Pass
         */
        this.password = password;
    }

    public void saveUser(Firebase myFirebaseRef) {
        /**
         * @param Firebase Object
         * @return void
         * Save patient data at specific location
         */
        myFirebaseRef=myFirebaseRef.child("patient").child(getId());
        myFirebaseRef.setValue(this);
    }
}
