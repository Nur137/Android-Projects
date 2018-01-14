package com.example.nur.paintoolapp.Database;


public class Doctor_info {
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class is a database class which fills Firebse database form
     * This class also provide getter and setter methods of Doctor_info class
     * Doctor fill the form in Doctor profile page
     * */
    private String name;
    private String country;
    private String province;
    private String zip_code;
    private String det_address;
    private String qualification;
    private String gender;

    public Doctor_info() {
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
         * Provides Doctor Name
         */

        return name;
    }

    public void setName(String name) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Doctor name
         */
        this.name = name;
    }
    public String getCountry() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Doctor Country
         */
        return country;
    }

    public void setCountry(String country) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Doctor Country
         */
        this.country = country;
    }


    public String getProvince() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Doctor Province
         */
        return province;
    }

    public void setProvince(String province) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Doctor province
         */
        this.province = province;
    }



    public String getZip_code() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Doctor zip_code
         */
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Doctor zip_code
         */
        this.zip_code=zip_code;
    }

    public String getDet_address() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Doctor address
         */
        return det_address;
    }

    public void setDet_address(String det_address) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Doctor details_address
         */
        this.det_address = det_address;
    }


    public String getQualification() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Doctor qualification
         */
        return qualification;
    }

    public void setQualification(String qualification) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Doctor qualification
         */
        this.qualification = qualification;
    }

    public String getGender() {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Doctor gender
         */
        return gender;
    }

    public void setGender(String gender) {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Doctor gender
         */
        this.gender = gender;
    }


}
