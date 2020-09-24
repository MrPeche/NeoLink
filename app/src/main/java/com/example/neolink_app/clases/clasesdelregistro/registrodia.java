package com.example.neolink_app.clases.clasesdelregistro;

import java.util.ArrayList;

public class registrodia {
    ArrayList<String> hora;
    ArrayList<registrocontenido> contenido;

    public registrodia(ArrayList<String> hora, ArrayList<registrocontenido> contenido){
        this.hora = hora;
        this.contenido = contenido;
    }
    public ArrayList<String> damelashoras(){ return this.hora;}
    public ArrayList<registrocontenido> dameelcontenido(){ return this.contenido;}
}
