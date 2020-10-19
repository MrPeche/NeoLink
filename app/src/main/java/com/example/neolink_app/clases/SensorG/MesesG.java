package com.example.neolink_app.clases.SensorG;

import java.util.ArrayList;

public class MesesG {
    private ArrayList<String> meses = new ArrayList<>();
    private ArrayList<DiasG> dias = new ArrayList<>();
    public MesesG(){}
    public MesesG(ArrayList<String> meses, ArrayList<DiasG> dias){
        this.meses = meses;
        this.dias = dias;
    }
    public String damemes(int posicion){return meses.get(posicion);}
    public DiasG damedias(int posicion){return dias.get(posicion);}
    public ArrayList<DiasG> damearraydedias(){return this.dias;}
    public int dametamanoG(){return this.dias.size();}
    public void tomameses(String mes, DiasG dia){
        this.meses.add(mes);
        this.dias.add(dia);
    }
}
