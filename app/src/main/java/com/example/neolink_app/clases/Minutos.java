package com.example.neolink_app.clases;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Minutos implements Parcelable {
    private ArrayList<String> minutos= new ArrayList<>();
    private ArrayList<Puerto> paquetes= new ArrayList<>();

    public Minutos(){}
    public Minutos(ArrayList<String> minutos, ArrayList<Puerto> paquetes){
        this.minutos=minutos;
        this.paquetes=paquetes;
    }

    protected Minutos(Parcel in) {
        minutos = in.createStringArrayList();
        paquetes = in.createTypedArrayList(Puerto.CREATOR);
    }

    public static final Creator<Minutos> CREATOR = new Creator<Minutos>() {
        @Override
        public Minutos createFromParcel(Parcel in) {
            return new Minutos(in);
        }

        @Override
        public Minutos[] newArray(int size) {
            return new Minutos[size];
        }
    };

    public void tomaMinutos(String minuto, Puerto paquete){
        this.minutos.add(minuto);
        this.paquetes.add(paquete);
    }
    public String dameminuto(int posicion){
        return this.minutos.get(posicion);
    }
    public Puerto damepaquete(int posicion){
        return this.paquetes.get(posicion);
    }
    public int dametamano(){return minutos.size();}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(minutos);
        dest.writeTypedList(paquetes);
    }
}
