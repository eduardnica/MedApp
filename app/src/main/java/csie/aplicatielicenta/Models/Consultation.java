package csie.aplicatielicenta.Models;

public class Consultation {
    public String patient, city, hospital, specialization, dateAndTime;


    public Consultation(){

    }

    public Consultation(String patient, String city, String hospital, String specialization, String dateAndTime) {
        this.patient = patient;
        this.city = city;
        this.hospital = hospital;
        this.specialization = specialization;
        this.dateAndTime = dateAndTime;
    }
}
