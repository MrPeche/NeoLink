package com.example.neolink_app.clases.sensorNPK;

import java.util.ArrayList;

public class MesesNPK {
    private ArrayList<String> meses = new ArrayList<>();
    private ArrayList<DiasNPK> dias = new ArrayList<>();
    public MesesNPK(){}
    public MesesNPK(ArrayList<String> meses, ArrayList<DiasNPK> dias){
        this.meses = meses;
        this.dias = dias;
    }
    public String damemes(int posicion){return meses.get(posicion);}
    public DiasNPK damedias(int posicion){return dias.get(posicion);}
    public ArrayList<DiasNPK> damearraydedias(){return this.dias;}
    public int dametamanoNPK(){return this.dias.size();}
    public void tomameses(String mes, DiasNPK dia){
        this.meses.add(mes);
        this.dias.add(dia);
    }
}
