package com.example.neolink_app.clases;

import java.util.ArrayList;

public class OLDneolinksboleto {
    public String correo;
    public ArrayList<String> neonodos;

    public OLDneolinksboleto(){

    }
    public OLDneolinksboleto(String correo, ArrayList<String> nodos){
        this.correo = correo;
        this.neonodos = nodos;
    }
    public OLDneolinksboleto(String correo){
        this.correo = correo;
        ArrayList<String> nuevo = new ArrayList<>();
        nuevo.add("NaN");
        this.neonodos = nuevo;
    }
    public String neolinksuid(){
        return this.correo;
    }

    public ArrayList<String> dameneonodos(){
        if(this.neonodos.get(0).equals("NaN")){
            return null;
        } else return this.neonodos;
    }
    public int dametamano(){
        return this.neonodos.size();
    }
    public String damenodo(int posicion){
        return neonodos.get(posicion);
    }
    public String damecorreo(){ return this.correo;}
}
