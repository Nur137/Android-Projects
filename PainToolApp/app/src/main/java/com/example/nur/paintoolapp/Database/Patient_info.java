package com.example.nur.paintoolapp.Database;

import com.firebase.client.Firebase;

/**
 * Created by user on 6/20/2016.
 */

public class Patient_info {

    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class is a database class which fills Firebse database form
     * This class also provide getter and setter methods of Patient_info class
     * Patient fill the form in Doctor profile page
     * */
    private String name;
    private String country;
    private String province;
    private String zip_code;
    private String det_address;
    private String height;
    private String weight;
    private String gender;


    public Patient_info() {
        /**
         * Blank default constructor essential for Firebase
         * */
    }


    //Getters and setters
    public String getName()
    {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Patient Name
         */

        return name;
    }

    public void setName(String name) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Patient name
         */
        this.name = name;
    }

    public String getCountry()
    {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Patient Country
         */

        return country;
    }

    public void setCountry(String country) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Patient country
         */
        this.country = country;
    }


    public String getProvince()
    {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Patient Provice
         */

        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


    public String getZip_code() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Patient zip_code
         */

        return zip_code;
    }

    public void setZip_code(String zip_code) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Patient zip_code
         */
        this.zip_code=zip_code;
    }

    public String getDet_address() {

        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Patient details-address
         */
        return det_address;
    }

    public void setDet_address(String det_address) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Patient details_address
         */
        this.det_address = det_address;
    }


    public String getHeight() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Patient height
         */
        return height;
    }

    public void setHeight(String height) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Patient height
         */
        this.height = height;
    }

    public String getWeight() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Patient weight
         */
        return weight;
    }

    public void setWeight(String weight) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Patient weight
         */
        this.weight = weight;
    }

    public String getGender() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Patient gender
         */
        return gender;
    }

    public void setGender(String gender) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Patient gender
         */
        this.gender = gender;
    }



}
