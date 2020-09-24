package com.example.neolink_app.clases.clasesdelregistro;

import java.util.ArrayList;

public class registroyear {
    ArrayList<String> meses;
    ArrayList<registromes> mesespack;
    public registroyear(ArrayList<String> meses, ArrayList<registromes> mesespack){
        this.meses = meses;
        this.mesespack = mesespack;
    }

    public ArrayList<String> damelosmes(){return this.meses;}
    public ArrayList<registromes> dameelpackdelosmes(){return this.mesespack;}
}
