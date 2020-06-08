package com.example.neolink_app.clases;

import java.util.ArrayList;

public class OWNERitems {
    public ArrayList<String> neolinks;

    public OWNERitems(){

    }
    public OWNERitems( ArrayList<String> neolinks){
        this.neolinks = neolinks;
    }

    public ArrayList<String> getlista(){
        return neolinks;
    }
}
