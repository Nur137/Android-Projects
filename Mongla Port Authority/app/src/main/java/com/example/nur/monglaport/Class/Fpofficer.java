package com.example.nur.monglaport.Class;

/**
 * Created by nur on 11/30/16.
 */

public class Fpofficer {
    //private String slno;
    private String service;
    private String officer;
    private String tel;


    public Fpofficer() {
        /**
         * Blank default constructor essential for Firebase
         * */
    }

    //Getters and setters
//
//    public String getSlno() {
//
//        return slno;
//    }
//
//    public void setSlno(String slno)
//    {
//
//        this.slno=slno;
//    }

    public String getService() {

        return service;
    }

    public void setService(String service)
    {

        this.service=service;
    }

    public String getOfficer() {

        return officer;
    }

    public void setOfficer(String officer)
    {

        this.officer=officer;
    }
    public String getTel() {

        return tel;
    }

    public void setTel(String tel)
    {

        this.tel=tel;
    }
}
