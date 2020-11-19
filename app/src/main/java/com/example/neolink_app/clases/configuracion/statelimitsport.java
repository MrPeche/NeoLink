package com.example.neolink_app.clases.configuracion;

public class statelimitsport {
    public stateport Port1;
    public stateport Port2;
    public stateport Port3;
    public stateport Port4;

    public statelimitsport(){}
    public statelimitsport(stateport Port1,stateport Port2,stateport Port3,stateport Port4){
        this.Port1 = Port1;
        this.Port2 = Port2;
        this.Port3 = Port3;
        this.Port4 = Port4;
    }

    public stateport dameP1(){return Port1;}
    public stateport dameP2(){return Port2;}
    public stateport dameP3(){return Port3;}
    public stateport dameP4(){return Port4;}
}
