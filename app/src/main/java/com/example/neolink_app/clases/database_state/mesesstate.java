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
    public String damemeses(int posicion){return this.meses.get(posicion);}
    public diasstate damedias(int posicion){return this.dias.get(posicion);}
    public ArrayList<diasstate> damearraydedias(){return this.dias;}
    public int tamanomes(){return this.dias.size();}
    public void tomameses(String mes, diasstate dia){
        this.meses.add(mes);
        this.dias.add(dia);
    }

}
