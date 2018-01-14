package com.example.mona.mahen.Database;

/**
 * Created by Nur on 9/13/2016.
 */
public class Work {
    private String uid;
    private String posted_by;
    private String title;
    private String description;
    private String from_date;
    private String from_time;
    private String to_date;
    private String to_time;
    private String range;
    private String nationality;
    private String worker_type;
    private String price_from;
    private String price_to;



    public Work() {
        /**
         * Blank default constructor essential for Firebase
         * */
    }


    //Getters and setters



    public String getUid() {

        return uid;
    }

    public void setUid(String uid)
    {

        this.uid=uid;
    }
    public String getPosted_by() {

        return posted_by;
    }

    public void setPosted_by(String posted_by)
    {

        this.posted_by = posted_by;
    }
    public String getTitle() {

        return title;
    }

    public void setTitle(String title)
    {

        this.title = title;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description)
    {

        this.description = description;
    }


    public String getFrom_date() {

        return from_date;
    }

    public void setFrom_date(String from_date)
    {

        this.from_date= from_date;
    }

    public String getFrom_time() {

        return from_time;
    }

    public void setFrom_time(String from_time)
    {

        this.from_time = from_time;
    }


    public String getTo_date() {

        return to_date;
    }

    public void setTo_date(String to_date)
    {

        this.to_date= to_date;
    }

    public String getTo_time() {

        return to_time;
    }

    public void setTo_time(String to_time)
    {

        this.to_time = to_time;
    }


    public String getRange() {

        return range;
    }

    public void setRange(String range)
    {

        this.range = range;
    }
    public String getWorker_type() {

        return worker_type;
    }

    public void setWorker_type(String worker_type)
    {

        this.worker_type = worker_type;
    }

    public String getPrice_from() {

        return price_from;
    }

    public void setPrice_from(String price_from)
    {

        this.price_from=price_from;
    }
    public String getPrice_to() {

        return price_to;
    }

    public void setPrice_to(String price_to)
    {

        this.price_to = price_to;
    }
    public String getNationality() {

        return nationality;
    }
    public void setNationality(String nationality) {

        this.nationality=nationality;
    }

}
