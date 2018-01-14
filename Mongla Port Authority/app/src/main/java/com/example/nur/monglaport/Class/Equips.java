package com.example.nur.monglaport.Class;

/**
 * Created by nur on 11/18/16.
 */

public class Equips {
    private String slno;
    private String desc;
    private String capacity;
    private String quantity;
    private String year;


    public Equips() {
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

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc)
    {

        this.desc=desc;
    }


    public String getCapacity() {

        return capacity;
    }

    public void setCapacity(String capacity)
    {

        this.capacity=capacity;
    }

    public String getQuantity() {

        return quantity;
    }

    public void setQuantity(String quantity)
    {

        this.quantity=quantity;
    }

    public String getYear() {

        return year;
    }

    public void setYear(String year)
    {

        this.year=year;
    }
}
