package com.example.nur.paintoolapp.Database;

import com.firebase.client.Firebase;


public class Doctor {
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-15-06
     * This class is a database class which fills Firebse database form
     * This class also provide getter and setter methods of Doctor object
     * */

    private String id;
    private String name;
    private String email;
    private String password;

    public Doctor() {
        /**
         * Blank default constructor essential for Firebase
       * */
    }


    //Getters and setters
    public String getId()
    {
        /**
         * This is a getter methos of Doctor class
         * @param Nothing
         * @return String
         * Provides Doctor ID
         */
        return id;
    }

    public void setId(String id)
    {
        /**
         * This is a setter methos of Doctor class
         * @param String
         * @return void
         * Sets Doctor ID
         */
        this.id = id;
    }

    public String getName()
    {
        /**
         * This is a getter methos of Doctor class
         * @param Nothing
         * @return String
         * Provides Doctor Name
         */
        return name;
    }

    public void setName(String name)
    {
    /**
     * This is a setter methos of Doctor class
     * @param String
     * @return void
     * Sets Doctor Name
     */

        this.name = name;
    }

    public String getEmail()
    {
        /**
         * This is a getter methos of Doctor class
         * @param Nothing
         * @return String
         * Provides Doctor Email
         */
        return email;
    }

    public void setEmail(String email)
    {
        /**
         * This is a setter methos of Doctor class
         * @param String
         * @return void
         * Sets Doctor Email
         */

        this.email=email;
    }

    public String getPass(){
    /**
     * This is a getter methos of Doctor class
     * @param Nothing
     * @return String
     * Provides Doctor Password
     */
        return password;
    }
    public void setPass(String password)
    {
        /**
         * This is a setter methos of Doctor class
         * @param String
         * @return void
         * Sets Doctor password
         */

        this.password = password;
    }

    public void saveUser(Firebase myFirebaseRef) {
        /**
         * @param Firebase Object
         * @return void
         * Save doctor data at specific location
         */
        myFirebaseRef=myFirebaseRef.child("doctor").child(getId());
        myFirebaseRef.setValue(this);
    }
}
