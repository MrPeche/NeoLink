package com.example.neolink_app.clases.configuracion;

public class stateGPS {
    public double LAT;
    public double LONG;

    public stateGPS(){}
    public stateGPS(double LAT, double LONG){
        this.LAT = LAT;
        this.LONG = LONG;
    }
    public double darLAT(){return LAT;}
    public double darLONG(){return LONG;}
}
