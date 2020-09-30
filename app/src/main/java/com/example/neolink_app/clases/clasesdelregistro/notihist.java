package com.example.neolink_app.clases.clasesdelregistro;

import java.util.ArrayList;

public class notihist {
    ArrayList<String> years;
    ArrayList<registroyear> yearspack;

    public notihist(ArrayList<String> years, ArrayList<registroyear> yearspack){
        this.years = years;
        this.yearspack = yearspack;
    }
    public notihist(){
        this.years = new ArrayList<>();
        this.yearspack = new ArrayList<>();
    }
    public void agregarlosdos(String year,registroyear pack){
        this.years.add(year);
        this.yearspack.add(pack);
    }
    public ArrayList<String> damelosyears(){return this.years;}
    public ArrayList<registroyear> dameelpackdeyears(){return this.yearspack;}
}
