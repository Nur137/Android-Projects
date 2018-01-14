package com.example.ne.bmail.classes;

/**
 * Created by ICe_Cube on 12/11/2017.
 */

public class ReceiveMail {
    String subject;
    String mail;
    String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
