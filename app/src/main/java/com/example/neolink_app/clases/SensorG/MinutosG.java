package com.example.neolink_app.clases.SensorG;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MinutosG implements Parcelable {
    private ArrayList<String> minutos= new ArrayList<>();
    private ArrayList<PuertoG> paquetes= new ArrayList<>();
    public MinutosG(){}
    public MinutosG(ArrayList<String> minutos, ArrayList<PuertoG> paquetes){
        this.minutos=minutos;
        this.paquetes=paquetes;
    }

    protected MinutosG(Parcel in) {
        minutos = in.createStringArrayList();
        paquetes = in.createTypedArrayList(PuertoG.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(minutos);
        dest.writeTypedList(paquetes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MinutosG> CREATOR = new Creator<MinutosG>() {
        @Override
        public MinutosG createFromParcel(Parcel in) {
            return new MinutosG(in);
        }

        @Override
        public MinutosG[] newArray(int size) {
            return new MinutosG[size];
        }
    };

    public void tomaMinutos(String minuto, PuertoG paquete){
        this.minutos.add(minuto);
        this.paquetes.add(paquete);
    }
    public String dameminuto(int posicion){
        return this.minutos.get(posicion);
    }
    public PuertoG damepaquete(int posicion){
        return this.paquetes.get(posicion);
    }
    public int dametamanoG(){return minutos.size();}
}
