package com.example.neolink_app.clases.database_state;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class minutosstate implements Parcelable {
    private ArrayList<String> minutos= new ArrayList<>();
    private ArrayList<statePK> paquete= new ArrayList<>();

    public minutosstate(){}
    public minutosstate(ArrayList<String> minutos, ArrayList<statePK> paquete){
        this.minutos = minutos;
        this.paquete = paquete;
    }

    protected minutosstate(Parcel in) {
        minutos = in.createStringArrayList();
        paquete = in.createTypedArrayList(statePK.CREATOR);
    }

    public static final Creator<minutosstate> CREATOR = new Creator<minutosstate>() {
        @Override
        public minutosstate createFromParcel(Parcel in) {
            return new minutosstate(in);
        }

        @Override
        public minutosstate[] newArray(int size) {
            return new minutosstate[size];
        }
    };

    public void tomaminutos(String minuto, statePK paquete){
        this.minutos.add(minuto);
        this.paquete.add(paquete);
    }
    public String dameminutos(int posicion){
        return this.minutos.get(posicion);
    }
    public statePK damepaquete(int posicion){
        return this.paquete.get(posicion);
    }
    public int tamano(){
        return this.minutos.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(minutos);
        dest.writeTypedList(paquete);
    }
}
