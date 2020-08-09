package com.example.neolink_app.clases.clasesparaformargraficos;

import com.example.neolink_app.clases.Dias;
import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.clases.database_state.diasstate;
import com.example.neolink_app.clases.database_state.horasstate;

public class InfoParaGraficos {
    private Horas paquete;
    private horasstate state;

    private Dias hoyDias;
    private diasstate hoystateDias;
    private Dias ayer;
    private diasstate ayerstate;

    public InfoParaGraficos(){}
    public void agregarinfodedias(Dias hoy,diasstate hoystate, Dias ayer,diasstate ayerstate){
        this.hoyDias = hoy;
        this.hoystateDias = hoystate;
        this.ayer = ayer;
        this.ayerstate= ayerstate;

    }
    private void setmedias(Dias hoy,diasstate hoystate, Dias ayer,diasstate ayerstate){

    }

}
