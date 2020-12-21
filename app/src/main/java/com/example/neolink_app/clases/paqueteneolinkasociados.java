package com.example.neolink_app.clases;

import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;

public class paqueteneolinkasociados {
    private ArrayList<String> neolinks;
    private ArrayList<ArrayList<String>> neonodos;
    public paqueteneolinkasociados(ArrayList<String> neolinks,ArrayList<ArrayList<String>>  neonodos){
        this.neolinks = neolinks;
        this.neonodos = neonodos;
    }
    public paqueteneolinkasociados(){
        this.neolinks = new ArrayList<>();
        this.neonodos = new ArrayList<>();
    }
    public void agregarneolinks(String neolink){
        neolinks.add(neolink);
    }
    public void agregarneonodos(ArrayList<String> neonodo){
        neonodos.add(neonodo);
    }
    public ArrayList<String> dameneolinks(){ return this.neolinks;}
    public ArrayList<ArrayList<String>> dameneonodos(){ return this.neonodos;}
}
