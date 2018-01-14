package com.example.mona.admin;

/**
 * Created by Nur on 9/17/2016.
 */
public class Withdraw {
    private String u_id;
    private String worker_id;
    private String bal;


    public Withdraw() {
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
    public String getWorker_id() {

        return worker_id;
    }

    public void setWorker_id(String worker_id)
    {

        this.worker_id=worker_id;
    }

    public String getBal() {

        return bal;
    }

    public void setBal(String bal)
    {

        this.bal=bal;
    }





}
