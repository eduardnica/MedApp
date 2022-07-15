package csie.aplicatielicenta.Models;

public class Consultation {
    public String  city, hospital, specialization, dateAndTime;


    public Consultation(){

    }

    public Consultation( String city, String hospital, String specialization, String dateAndTime) {
        this.city = city;
        this.hospital = hospital;
        this.specialization = specialization;
        this.dateAndTime = dateAndTime;
    }

    public String getHospital() {
        return hospital;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }
}
