package com.example.nur.monglaport.Class;

/**
 * Created by nur on 11/28/16.
 */

public class Teleph {
    private String slno;
    private String designation;
    private String tel;
    private String email;




    public Teleph() {
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
    public String getDesignation() {

        return designation;
    }

    public void setDesignation(String designation)
    {

        this.designation=designation;
    }

    public String getTel() {

        return tel;
    }

    public void setTel(String tel)
    {

        this.tel=tel;
    }


    public String getEmail() {

        return email;
    }

    public void setEmail(String email)
    {

        this.email=email;
    }
}
