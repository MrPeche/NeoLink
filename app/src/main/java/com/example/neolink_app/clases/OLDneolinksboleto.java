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
        nuevo.add("nulo");
        this.neonodos = nuevo;
    }
    public String neolinksuid(){
        return this.correo;
    }
    public ArrayList<String> dameneonodos(){
        return this.neonodos;
    }
}
