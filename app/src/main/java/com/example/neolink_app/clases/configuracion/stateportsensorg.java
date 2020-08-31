package com.example.neolink_app.clases.configuracion;

public class stateportsensorg {
    public statesinglelimitvalues V1;
    public statesinglelimitvalues V2;
    public statesinglelimitvalues V3;

    public stateportsensorg(){}
    public stateportsensorg(statesinglelimitvalues V1,statesinglelimitvalues V2,statesinglelimitvalues V3){
        this.V1 = V1;
        this.V2 = V2;
        this.V3 = V3;
    }
    public statesinglelimitvalues dameV1(){return V1;}
    public statesinglelimitvalues dameV2(){return V2;}
    public statesinglelimitvalues dameV3(){return V3;}

}
