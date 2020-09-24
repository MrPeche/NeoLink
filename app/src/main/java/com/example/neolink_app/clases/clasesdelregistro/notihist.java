package com.example.neolink_app.clases.clasesdelregistro;

import java.util.ArrayList;

public class notihist {
    ArrayList<String> years;
    ArrayList<registroyear> yearspack;

    public notihist(ArrayList<String> years, ArrayList<registroyear> yearspack){
        this.years = years;
        this.yearspack = yearspack;
    }
    public ArrayList<String> damelosyears(){return this.years;}
    public ArrayList<registroyear> dameelpackdeyears(){return this.yearspack;}
}
