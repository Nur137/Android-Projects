package com.example.nur.monglaport.Class;

/**
 * Created by nur on 11/29/16.
 */

public class Container {
    private String slno;
    private String a_name;
    private String twd;
    private String twr;
    private String ford;
    private String forr;




    public Container() {
        /**
         * Blank default constructor essential for Firebase
         * */
    }

    //Getters and setters

    public String getSlno() {

        return slno;
    }

    public void setSlno(String slno)
    {

        this.slno=slno;
    }


    public String getA_name() {

        return a_name;
    }

    public void setA_name(String a_name)
    {

        this.a_name=a_name;
    }

    public String getTwd() {

        return twd;
    }

    public void setTwd(String twd)
    {

        this.twd=twd;
    }


    public String getTwr() {

        return twr;
    }

    public void setTwr(String twr)
    {

        this.twr=twr;
    }

    public String getFord() {

        return ford;
    }

    public void setFord(String ford)
    {

        this.ford=ford;
    }

    public String getForr() {

        return forr;
    }

    public void setForr(String forr)
    {

        this.forr=forr;
    }




}
