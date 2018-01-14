package com.example.mona.admin;

/**
 * Created by Nur on 9/17/2016.
 *//**
 * Created by Nur on 9/17/2016.
 */
public class Recharge {
    private String u_id;
    private String cust_id;
    private String bal;


    public Recharge() {
        /**
         * Blank default constructor essential for Firebase
         * */
    }


    //Getters and setters



    public String getU_id() {

        return u_id;
    }

    public void setU_id(String u_id)
    {

        this.u_id = u_id;
    }
    public String getCust_id() {

        return cust_id;
    }

    public void setCust_id(String cust_id)
    {

        this.cust_id=cust_id;
    }

    public String getBal() {

        return bal;
    }

    public void setBal(String bal)
    {

        this.bal=bal;
    }


}
