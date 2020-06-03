package com.example.neolink_app;

import java.util.ArrayList;

public class OLDneolinksboleto {
    private String correo;
    private ArrayList<String> neonodos;

    public OLDneolinksboleto(String correo, ArrayList<String> nodos){
        this.correo = correo;
        this.neonodos = nodos;
    }
    public OLDneolinksboleto(String correo){
        this.correo = correo;
        ArrayList<String> nuevo = new ArrayList<String>();
        nuevo.add("vacio");
        this.neonodos = nuevo;
    }
}
