package com.example.nur.monglaport.Class;

/**
 * Created by nur on 11/29/16.
 */

public class Shp {
    private String slno;
    private String name;
    private String location;
    private String nature;
    private String arrival;
    private String etd;
    private String agent;

    public Shp() {
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


    public String getName() {

        return name;
    }

    public void setName(String name)
    {

        this.name=name;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location)
    {

        this.location=location;
    }


    public String getNature() {

        return nature;
    }

    public void setNature(String nature)
    {

        this.nature=nature;
    }


    public String getArrival() {

        return arrival;
    }

    public void setArrival(String arrival)
    {

        this.arrival=arrival;
    }


    public String getEtd() {

        return etd;
    }

    public void setEtd(String etd)
    {

        this.etd=etd;
    }

    public String getAgent() {

        return agent;
    }

    public void setAgent(String agent)
    {

        this.agent=agent;
    }

}
