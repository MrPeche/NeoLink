package com.example.neolink_app.clases.configuracion;

public class statesinglelimitvalues {
    public double Max;
    public double Min;
    public double bool;

    public statesinglelimitvalues(){}
    public statesinglelimitvalues(double Max, double Min, double bool){
        this.Max = Max;
        this.Min = Min;
        this.bool = bool;
    }
    public double dameMAX(){return Max;}
    public double dameMin(){return Min;}
    public double damebool(){return bool;}
}
