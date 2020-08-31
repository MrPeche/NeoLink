package com.example.neolink_app.clases.configuracion;

import java.util.ArrayList;

public class stateport {
    public stateportsensorg g; //stateportsensorg g
    public stateportsensork k; //stateportsensork k

    public stateport(){}
    public stateport(stateportsensorg g,stateportsensork k){
        this.g=g;
        this.k=k;
    }
    public stateportsensorg dameG(){return g;}
    public stateportsensork damek(){return k;}
}
