package com.example.neolink_app.clases.clasesdelregistro;

import java.util.ArrayList;

public class registroyear {
    ArrayList<String> meses;
    ArrayList<registromes> mesespack;
    public registroyear(ArrayList<String> meses, ArrayList<registromes> mesespack){
        this.meses = meses;
        this.mesespack = mesespack;
    }
    public registroyear(){
        this.meses = new ArrayList<>();
        this.mesespack = new ArrayList<>();
    }
    public void agregarlosdos(String mes, registromes pack){
        this.meses.add(mes);
        this.mesespack.add(pack);
    }
    public ArrayList<String> damelosmes(){return this.meses;}
    public ArrayList<registromes> dameelpackdelosmes(){return this.mesespack;}
}
