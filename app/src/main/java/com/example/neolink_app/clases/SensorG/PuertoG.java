package com.example.neolink_app.clases.SensorG;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PuertoG implements Parcelable {
    private ArrayList<String> Puerto = new ArrayList<>();
    private ArrayList<dataPuertoG> data = new ArrayList<>();
    public PuertoG(){}
    public PuertoG(ArrayList<String> Puerto, ArrayList<dataPuertoG> data){
        this.Puerto = Puerto;
        this.data = data;
    }

    protected PuertoG(Parcel in) {
        Puerto = in.createStringArrayList();
        data = in.createTypedArrayList(dataPuertoG.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(Puerto);
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PuertoG> CREATOR = new Creator<PuertoG>() {
        @Override
        public PuertoG createFromParcel(Parcel in) {
            return new PuertoG(in);
        }

        @Override
        public PuertoG[] newArray(int size) {
            return new PuertoG[size];
        }
    };

    public void tomaPuerto(String Puerto, dataPuertoG data){
        this.Puerto.add(Puerto);
        this.data.add(data);
    }
    public String damePuerto(int posicion){
        return this.Puerto.get(posicion);
    }
    public dataPuertoG damedata(int posicion){
        return this.data.get(posicion);
    }
    public int dametamanoG(){return this.Puerto.size();}
}
