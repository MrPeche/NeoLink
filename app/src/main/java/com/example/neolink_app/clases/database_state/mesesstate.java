package com.example.neolink_app.clases.database_state;


import java.util.ArrayList;

public class mesesstate {
    private ArrayList<String> meses = new ArrayList<>();
    private ArrayList<diasstate> dias = new ArrayList<>();
    public mesesstate(){}
    public mesesstate(String mes,diasstate dia){
        this.meses.add(mes);
        this.dias.add(dia);
    }

}
