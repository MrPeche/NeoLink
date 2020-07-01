package com.example.neolink_app.clases;

public class GPS {
    private Double Lat;
    private Double Long;
    public GPS(){}
    public GPS(Double Lat, Double Long){
        this.Lat = Lat;
        this.Long = Long;
    }
    public Double getLat(){
        return Lat;
    }
    public Double getLong(){
        return Long;
    }
}
