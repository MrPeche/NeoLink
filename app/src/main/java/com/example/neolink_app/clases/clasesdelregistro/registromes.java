package com.example.neolink_app.clases.clasesdelregistro;

import java.util.ArrayList;

public class registromes {
    ArrayList<String> dias;
    ArrayList<registrodia> diaspack;

    public registromes(ArrayList<String> dias, ArrayList<registrodia> diaspack){
        this.dias = dias;
        this.diaspack = diaspack;
    }

    public ArrayList<String> damelosdias(){return this.dias;}
    public ArrayList<registrodia> dameelpackdedias(){return this.diaspack;}
}
