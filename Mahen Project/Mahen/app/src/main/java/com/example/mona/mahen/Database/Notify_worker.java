package com.example.mona.mahen.Database;

/**
 * Created by Nur on 9/13/2016.
 */
public class Notify_worker {
    private String u_id;
    private String work_id;
    private String work_title;
    private String worker_id;
    private String cust_id;
    private String distance;

    public Notify_worker() {
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
    public String getWork_id() {

        return work_id;
    }

    public void setWork_id(String work_id)
    {

        this.work_id = work_id;
    }
    public String getWork_title() {

        return work_title;
    }

    public void setWork_title(String work_title)
    {

        this.work_title = work_title;
    }

    public String getWorker_id() {

        return worker_id;
    }

    public void setWorker_id(String worker_id)
    {

        this.worker_id = worker_id;
    }


    public String getCust_id() {

        return cust_id;
    }

    public void setCust_id(String cust_id)
    {

        this.cust_id=cust_id;
    }


    public String getDistance() {

        return distance;
    }

    public void setDistance(String distance)
    {

        this.distance=distance;
    }




}
