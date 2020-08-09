package com.example.neolink_app.clases;

import java.util.ArrayList;

public class OWNERitems {
    public ArrayList<String> neolinks;

    public OWNERitems(){ }
    public OWNERitems( ArrayList<String> neolinks){
        this.neolinks = neolinks;
    }

    public ArrayList<String> damelista(){
        return neolinks;
    }
    public int dametamanolista(){ return neolinks.size(); }
    public String getitem(int position){ return neolinks.get(position);}

}
