package com.example.neolink_app.clases.clasesdelregistro;

import java.util.ArrayList;

public class registromes {
    ArrayList<String> dias;
    ArrayList<registrodia> diaspack;

    public registromes(ArrayList<String> dias, ArrayList<registrodia> diaspack){
        this.dias = dias;
        this.diaspack = diaspack;
    }
    public registromes(){
        this.dias = new ArrayList<>();
        this.diaspack = new ArrayList<>();
    }
    public void agregardias(String dia){ this.dias.add(dia);}
    public void agregardiaspack(registrodia pack){ this.diaspack.add(pack);}
    public void agregarboth(String dia, registrodia pack){
        this.dias.add(dia);
        this.diaspack.add(pack);
    }
    public ArrayList<String> damelosdias(){return this.dias;}
    public ArrayList<registrodia> dameelpackdedias(){return this.diaspack;}
}
