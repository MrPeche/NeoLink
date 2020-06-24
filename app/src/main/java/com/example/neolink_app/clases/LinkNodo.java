package com.example.neolink_app.clases;

public class LinkNodo {
    private String neolink;
    private OLDneolinksboleto nodo;
    public LinkNodo(String neolink, OLDneolinksboleto nodo){
        this.neolink = neolink;
        this.nodo = nodo;
    }
    public String getneolink(){
        return this.neolink;
    }
    public OLDneolinksboleto getoldnodo(){
        return this.nodo;
    }
}
