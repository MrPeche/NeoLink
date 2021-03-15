package com.example.neolink_app.clases.clasesparaformargraficos;

public class fulldatapack {
    kdatapack kdata;
    gdatapack gdata;
    statedatapack statedata;
    npkdatapack npkdata;
    phdatapack phdata;
    public fulldatapack(kdatapack kdata, gdatapack gdata, statedatapack statedata,npkdatapack npkdata,phdatapack phdata){
        this.kdata=kdata;
        this.gdata=gdata;
        this.statedata=statedata;
        this.npkdata=npkdata;
        this.phdata=phdata;
    }
    public kdatapack sacarkdatapack(){return this.kdata;}
    public gdatapack sacargdatapack(){return this.gdata;}
    public statedatapack sacarstatedatapack(){return this.statedata;}
    public npkdatapack sacarnpkdatapack(){return this.npkdata;}
    public phdatapack sacarphdatapack(){return this.phdata;}
}
