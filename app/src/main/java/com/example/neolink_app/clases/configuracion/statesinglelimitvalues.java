package com.example.neolink_app.clases.configuracion;

public class statesinglelimitvalues {
    public double MAX;
    public double Min;
    public double bool;

    public statesinglelimitvalues(){}
    public statesinglelimitvalues(double MAX, double Min, double bool){
        this.MAX = MAX;
        this.Min = Min;
        this.bool = bool;
    }
    public double dameMAX(){return MAX;}
    public double dameMin(){return Min;}
    public double damebool(){return bool;}
}
