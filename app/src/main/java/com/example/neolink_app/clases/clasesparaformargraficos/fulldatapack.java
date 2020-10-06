package com.example.neolink_app.clases.clasesparaformargraficos;

public class fulldatapack {
    kdatapack kdata;
    gdatapack gdata;
    statedatapack statedata;
    public fulldatapack(kdatapack kdata, gdatapack gdata, statedatapack statedata){
        this.kdata=kdata;
        this.gdata=gdata;
        this.statedata=statedata;
    }
    public kdatapack sacarkdatapack(){return this.kdata;}
    public gdatapack sacargdatapack(){return this.gdata;}
    public statedatapack sacarstatedatapack(){return this.statedata;}
}
