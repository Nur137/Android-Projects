package com.example.nur.paintoolapp.Database;


public class Doctor_Patient_Relation {
    /**
     * @author  Nur Imtiazul Haque
     * @author  Tasfia Mashiat
     * @version 1.0
     * @since   2016-16-06
     * This class is a database class which fills Firebse database form
     * This class also provide getter and setter methods
     * It Establishes Doctor-patient relationship
     * */
    private String doctor;
    private String patient;

    public String getDoctor()
    {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides Doctor name of a specific patient
         */
        return doctor;
    }
    public void setDoctor(String doctor)
    {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets doctor name of a patient
         */
        this.doctor=doctor;
    }

    public String getPatient()
    {
        /**
         * This is a getter methos of Doctor_Info class
         * @param Nothing
         * @return String
         * Provides patient name of a specific doctor
         */
        return patient;
    }
    public void setPatient(String patient)
    {
        /**
         * This is a setter methos of Doctor_info class
         * @param String
         * @return void
         * Sets Patient name of a doctor
         */
        this.patient=patient;
    }

}
