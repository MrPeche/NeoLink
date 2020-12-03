package com.example.neolink_app.clases.clasesparaformargraficos;

import com.example.neolink_app.clases.Dias;
import com.example.neolink_app.clases.Meses;
import com.example.neolink_app.clases.SensorG.DiasG;
import com.example.neolink_app.clases.SensorG.MesesG;
import com.example.neolink_app.clases.database_state.diasstate;
import com.example.neolink_app.clases.database_state.mesesstate;

import java.util.Comparator;

public class sortpormes<A> implements Comparator<A> {
    @Override
    public int compare(A o1, A o2) {

        if(o1.getClass()== Meses.class){
            int[] valoreso1 = spliteldia(((Meses) o1).damemes(0));
            int[] valoreso2 = spliteldia(((Meses) o2).damemes(0));
            if(valoreso1[1]==valoreso2[1]){
                return valoreso1[0] - valoreso2[0];
            } else return valoreso1[1] - valoreso2[1];
        } else if(o1.getClass()== MesesG.class){
            int[] valoreso1 = spliteldia(((MesesG) o1).damemes(0));
            int[] valoreso2 = spliteldia(((MesesG) o2).damemes(0));
            if(valoreso1[1]==valoreso2[1]){
                return valoreso1[0] - valoreso2[0];
            } else return valoreso1[1] - valoreso2[1];

        } else if(o1.getClass()== mesesstate.class){
            int[] valoreso1 = spliteldia(((mesesstate) o1).damemeses(0));
            int[] valoreso2 = spliteldia(((mesesstate) o2).damemeses(0));
            if(valoreso1[1]==valoreso2[1]){
                return valoreso1[0] - valoreso2[0];
            } else return valoreso1[1] - valoreso2[1];
        } else return 0;
    }
    private int[] spliteldia(String dia){
        String[] valores = dia.split("/");
        int year = Integer.parseInt(valores[0]);
        int mes = translatenumber(valores[1]);
        return new int[] {year,mes};
    }
    private int translatenumber(String obj){
        if(obj.charAt(0)=='0'){
            return Integer.parseInt(obj.substring(1,2));
        } else return Integer.parseInt(obj);
    }
}
