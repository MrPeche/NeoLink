package com.example.neolink_app.clases.configuracion;

public class stateportsensork {
    public statesinglelimitvalues V1;
    public statesinglelimitvalues V2;

    public stateportsensork(){}
    public stateportsensork(statesinglelimitvalues V1,statesinglelimitvalues V2){
        this.V1 = V1;
        this.V2 = V2;
    }
    public statesinglelimitvalues dameV1(){return V1;}
    public statesinglelimitvalues dameV2(){return V2;}

}
