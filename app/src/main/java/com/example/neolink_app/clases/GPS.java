package com.example.neolink_app.clases;

public class GPS {
    private Double LAT;
    private Double LONG;
    public GPS(){}
    public GPS(Double LAT, Double LONG){
        this.LAT = LAT;
        this.LONG = LONG;
    }
    public Double getLat(){
        return LAT;
    }
    public Double getLong(){
        return LONG;
    }
}
