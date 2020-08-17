package com.example.neolink_app.clases.SensorG;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class HorasG implements Parcelable {
    private ArrayList<String> horas = new ArrayList<>();
    private ArrayList<MinutosG> minutos = new ArrayList<>();
    public HorasG(){}
    public HorasG(ArrayList<String> horas, ArrayList<MinutosG> minutos){
        this.horas=horas;
        this.minutos=minutos;
    }

    protected HorasG(Parcel in) {
        horas = in.createStringArrayList();
        minutos = in.createTypedArrayList(MinutosG.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(horas);
        dest.writeTypedList(minutos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HorasG> CREATOR = new Creator<HorasG>() {
        @Override
        public HorasG createFromParcel(Parcel in) {
            return new HorasG(in);
        }

        @Override
        public HorasG[] newArray(int size) {
            return new HorasG[size];
        }
    };

    public void tomaHoras(String hora, MinutosG minuto){
        this.horas.add(hora);
        this.minutos.add(minuto);
    }
    public String damehora(int posicion){
        return this.horas.get(posicion);
    }
    public MinutosG dameminutos(int posicion){
        return this.minutos.get(posicion);
    }
    public int dametamanoG(){return this.horas.size();}
}
