package com.example.neolink_app.clases;

import java.util.ArrayList;

public class Puerto {
    private ArrayList<String> Puerto = new ArrayList<>();
    private ArrayList<dataPuerto> data = new ArrayList<>();

    public Puerto(){}
    public Puerto(ArrayList<String> Puerto, ArrayList<dataPuerto> data){
        this.Puerto=Puerto;
        this.data=data;
    }
    public void tomaPuerto(String Puerto,dataPuerto data){
        this.Puerto.add(Puerto);
        this.data.add(data);
    }
    public String damePuerto(int posicion){
        return this.Puerto.get(posicion);
    }
    public dataPuerto damedata(int posicion){
        return this.data.get(posicion);
    }

}
