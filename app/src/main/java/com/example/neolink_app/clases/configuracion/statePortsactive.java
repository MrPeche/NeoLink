package com.example.neolink_app.clases.configuracion;

public class statePortsactive {
    public String Port1_Active;
    public String Port2_Active;
    public String Port3_Active;
    public String Port4_Active;
    public statePortsactive(){}
    public statePortsactive(String Port1_Active, String Port2_Active,String Port3_Active,String Port4_Active){
        this.Port1_Active = Port1_Active;
        this.Port2_Active = Port2_Active;
        this.Port3_Active = Port3_Active;
        this.Port4_Active = Port4_Active;
    }
    public String damePort1(){return Port1_Active;}
    public String damePort2(){return Port2_Active;}
    public String damePort3(){return Port3_Active;}
    public String damePort4(){return Port4_Active;}
}
