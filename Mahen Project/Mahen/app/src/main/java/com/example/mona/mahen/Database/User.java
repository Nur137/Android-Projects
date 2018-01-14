package com.example.mona.mahen.Database;


import com.firebase.client.Firebase;



public class User {

    private String full_name;
    private String un;
    private String email;
    private String password;
    private String birth_date;
    private String sex;
    private String type;
    private String ID;
    private String propic;
    private String idpic;
    private String address;
    private String nationality;
    private String contact;

    public User() {
        /**
         * Blank default constructor essential for Firebase
         * */
    }


    //Getters and setters



    public String getFull_name() {

        return full_name;
    }

    public void setFull_name(String full_name)
    {

        this.full_name = full_name;
    }

    public String getUn() {

        return un;
    }

    public void setUn(String un)
    {

        this.un = un;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email)
    {

        this.email=email;
    }

    public String getPass() {

        return password;
    }
    public void setPass(String password) {

        this.password = password;
    }

    public String getBirth_date() {

        return birth_date;
    }
    public void setBirth_date(String birth_date) {

        this.birth_date= birth_date;
    }


    public String getSex() {

        return sex;
    }
    public void setSex(String sex) {

        this.sex= sex;
    }


    public String getID() {

        return ID;
    }
    public void setID(String ID) {

        this.ID= ID;
    }


    public String getPropic() {

        return propic;
    }
    public void setPropic(String propic) {

        this.propic= propic;
    }

    public String getType() {

        return type;
    }
    public void setType(String type) {

        this.type= type;
    }

    public String getIdpic() {

        return idpic;
    }
    public void setIdpic(String idpic) {

        this.idpic= idpic;
    }

    public String getNationality() {

        return nationality;
    }
    public void setNationality(String nationality) {

        this.nationality=nationality;
    }


    public String getContact() {

        return contact;
    }
    public void setContact(String contact) {

        this.contact=contact;
    }

    public String getAddress() {

        return address;
    }
    public void setAddress(String address) {

        this.address=address;
    }



    public void saveUser(Firebase firebase, String type) {
        firebase.child("Admin").child("Not Approved").child(type).push().setValue(this);
    }

}
