package com.example.neolink_app.clases.configuracion;

public class statesinglelimitspecialvalues {
    public double CC;
    public double PMP;
    public double AU;
    public int bool;

    public statesinglelimitspecialvalues(){ }
    public statesinglelimitspecialvalues(double CC, double PMP,double AU, int bool){
        this.CC = CC;
        this.PMP = PMP;
        this.AU = AU;
        this.bool = bool;
    }
    public double dameCC(){return CC;}
    public double damePMP(){return PMP;}
    public double dameAU(){return AU;}
    public int damebool(){return bool;}
}
