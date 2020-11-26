package com.example.neolink_app.clases.configuracion;

public class depthconf {
    public int P1;
    public int P2;
    public int P3;
    public int P4;
    public depthconf(){ }
    public depthconf(int P1,int P2,int P3,int P4){
        this.P1=P1;
        this.P2=P2;
        this.P3=P3;
        this.P4=P4;
    }
    public int damep1(){return P1;}
    public int damep2(){return P2;}
    public int damep3(){return P3;}
    public int damep4(){return P4;}

}
