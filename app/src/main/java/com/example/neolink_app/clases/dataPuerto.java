package com.example.neolink_app.clases;

public class dataPuerto {
    private String Depth;
    private String V1;
    private String V2;

    public dataPuerto(){}
    public dataPuerto(String Depth, String V1, String V2){
        this.Depth = Depth;
        this.V1 = V1;
        this.V2 = V2;
    }
    public String dameDepth(){
        return Depth;
    }
    public String dameV1(){
        return V1;
    }
    public String dameV2(){
        return V2;
    }
}
