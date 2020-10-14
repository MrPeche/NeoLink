package com.example.neolink_app.clases.clasesparaformargraficos;

import com.example.neolink_app.clases.Dias;
import com.example.neolink_app.clases.SensorG.DiasG;
import com.example.neolink_app.clases.database_state.diasstate;

import java.util.Comparator;

public class sortpordias<A> implements Comparator<A> {

    @Override
    public int compare(Object o1, Object o2) {
        if(o1.getClass()==Dias.class){
            int[] valoreso1 = spliteldia(((Dias) o1).damedia(0));
            int[] valoreso2 = spliteldia(((Dias) o2).damedia(0));
            if(valoreso1[2]==valoreso2[2]){
                if(valoreso1[1]==valoreso2[1]){
                    return valoreso1[0] - valoreso2[0];
                } else return valoreso1[1] - valoreso2[1];
            } else return valoreso1[2] - valoreso2[2];
        } else if(o1.getClass()== DiasG.class){
            int[] valoreso1 = spliteldia(((DiasG) o1).damedia(0));
            int[] valoreso2 = spliteldia(((DiasG) o2).damedia(0));
            if(valoreso1[2]==valoreso2[2]){
                if(valoreso1[1]==valoreso2[1]){
                    return valoreso1[0] - valoreso2[0];
                } else return valoreso1[1] - valoreso2[1];
            } else return valoreso1[2] - valoreso2[2];

        } else if(o1.getClass()== diasstate.class){
            int[] valoreso1 = spliteldia(((diasstate) o1).damedia(0));
            int[] valoreso2 = spliteldia(((diasstate) o2).damedia(0));
            if(valoreso1[2]==valoreso2[2]){
                if(valoreso1[1]==valoreso2[1]){
                    return valoreso1[0] - valoreso2[0];
                } else return valoreso1[1] - valoreso2[1];
            } else return valoreso1[2] - valoreso2[2];
        } else return 0;
    }

    private int[] spliteldia(String dia){
        String[] valores = dia.split("/");
        int year = Integer.parseInt(valores[0]);
        int mes = translatenumber(valores[1]);
        int day =translatenumber(valores[2]);
        return new int[] {year,mes,day};
    }
    private int translatenumber(String obj){
        if(obj.charAt(0)=='0'){
            return Integer.parseInt(obj.substring(1,2));
        } else return Integer.parseInt(obj);
    }
}
