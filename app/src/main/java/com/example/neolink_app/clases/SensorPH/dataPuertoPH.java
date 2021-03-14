package com.example.neolink_app.clases.SensorPH;

public class dataPuertoPH {
    public Double PH;
    public Double Depth;

    public dataPuertoPH(){}
    public dataPuertoPH(Double PH,Double Depth){
        this.PH=PH;
        this.Depth=Depth;
    }
    public Double damePH(){return this.PH;}
    public Double dameDepth(){return this.Depth;}
}
