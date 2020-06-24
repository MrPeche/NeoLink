package com.example.neolink_app.clases;

import java.util.ArrayList;

public class Años {
    private ArrayList<String> años = new ArrayList<>();
    private ArrayList<Meses> meses = new ArrayList<>();
    public Años(){}
    public Años(String año,Meses mes){
        this.años.add(año);
        this.meses.add(mes);
    }
    public String dameaño(int posicion){
        return this.años.get(posicion);
    }
    public Meses damemes(int posicion){
        return this.meses.get(posicion);
    }
}
