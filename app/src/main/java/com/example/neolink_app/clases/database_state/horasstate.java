package com.example.neolink_app.clases.database_state;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class horasstate implements Parcelable {
    private ArrayList<String> horas = new ArrayList<>();
    private ArrayList<minutosstate> minutos = new ArrayList<>();
    public horasstate(){}
    public horasstate(ArrayList<String> horas,ArrayList<minutosstate> minutos){
        this.horas = horas;
        this.minutos = minutos;
    }

    protected horasstate(Parcel in) {
        horas = in.createStringArrayList();
        minutos = in.createTypedArrayList(minutosstate.CREATOR);
    }

    public static final Creator<horasstate> CREATOR = new Creator<horasstate>() {
        @Override
        public horasstate createFromParcel(Parcel in) {
            return new horasstate(in);
        }

        @Override
        public horasstate[] newArray(int size) {
            return new horasstate[size];
        }
    };

    public void tomahoras(String hora, minutosstate minuto){
        this.horas.add(hora);
        this.minutos.add(minuto);
    }
    public String damehora(int posicion){ return this.horas.get(posicion);}
    public minutosstate dameminutos(int posicion){ return this.minutos.get(posicion);}
    public int dametamano(){return horas.size();}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(horas);
        dest.writeTypedList(minutos);
    }
}
