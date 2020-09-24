package com.example.neolink_app.clases.clasesdelregistro;

import java.util.ArrayList;

public class registrocontenido {
    String hora;
    String contenido;
    public registrocontenido(String hora, String contenido){
        this.hora = hora;
        this.contenido = contenido;
    }
    public String damelahora(){ return this.hora;}
    public String dameelcontenido(){ return this.contenido;}
}
