package com.example.neolink_app.clases.clasesdelregistro;

import java.util.ArrayList;

public class registrodia {
    ArrayList<registrocontenido> contenido;

    public registrodia(ArrayList<registrocontenido> contenido){
        this.contenido = contenido;
    }
    public registrodia(){
        this.contenido = new ArrayList<>();
    }

    public void agregarcontenido(registrocontenido contenido){ this.contenido.add(contenido);}

    public ArrayList<registrocontenido> dameelcontenido(){ return this.contenido;}
}
