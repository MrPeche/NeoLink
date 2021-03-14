package com.example.neolink_app.clases.sensorNPK;

public class dataPuertoNPK {
    public Double Nitrogen;
    public Double Nitrate;
    public Double Phosphorus;
    public Double Phosphate;
    public Double Ptassium;
    public Double Depth;

    public dataPuertoNPK(){}
    public dataPuertoNPK(Double Nitrogen,Double Nitrate,Double Phosphorus,Double Phosphate,Double Ptassium,Double Depth){
        this.Nitrogen=Nitrogen;
        this.Nitrate=Nitrate;
        this.Phosphorus=Phosphorus;
        this.Phosphate=Phosphate;
        this.Ptassium=Ptassium;
        this.Depth=Depth;
    }
    public Double dameNitrogen(){return this.Nitrogen;}
    public Double dameNitrate(){return this.Nitrate;}
    public Double damePhosphorus(){return this.Phosphorus;}
    public Double dameDepth(){return this.Depth;}
    public Double damePhosphate(){return this.Phosphate;}
    public Double damePtassium(){return this.Ptassium;}
}
