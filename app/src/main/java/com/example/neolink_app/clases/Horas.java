package com.example.neolink_app.clases;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Horas implements Parcelable {
    private ArrayList<String> horas = new ArrayList<>();
    private ArrayList<Minutos> minutos = new ArrayList<>();
    public Horas(){}
    public Horas(ArrayList<String> horas, ArrayList<Minutos> minutos){
        this.horas=horas;
        this.minutos=minutos;
    }

    protected Horas(Parcel in) {
        horas = in.createStringArrayList();
        minutos = in.createTypedArrayList(Minutos.CREATOR);
    }

    public static final Creator<Horas> CREATOR = new Creator<Horas>() {
        @Override
        public Horas createFromParcel(Parcel in) {
            return new Horas(in);
        }

        @Override
        public Horas[] newArray(int size) {
            return new Horas[size];
        }
    };

    public void tomaHoras(String hora, Minutos minuto){
        this.horas.add(hora);
        this.minutos.add(minuto);
    }
    public String damehora(int posicion){
        return this.horas.get(posicion);
    }
    public Minutos dameminutos(int posicion){
        return this.minutos.get(posicion);
    }
    public int dametamano(){return this.horas.size();}

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
