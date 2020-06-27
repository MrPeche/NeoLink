package com.example.neolink_app.clases;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Puerto implements Parcelable {
    private ArrayList<String> Puerto = new ArrayList<>();
    private ArrayList<dataPuerto> data = new ArrayList<>();

    public Puerto(){}
    public Puerto(ArrayList<String> Puerto, ArrayList<dataPuerto> data){
        this.Puerto=Puerto;
        this.data=data;
    }

    protected Puerto(Parcel in) {
        Puerto = in.createStringArrayList();
    }

    public static final Creator<Puerto> CREATOR = new Creator<Puerto>() {
        @Override
        public Puerto createFromParcel(Parcel in) {
            return new Puerto(in);
        }

        @Override
        public Puerto[] newArray(int size) {
            return new Puerto[size];
        }
    };

    public void tomaPuerto(String Puerto, dataPuerto data){
        this.Puerto.add(Puerto);
        this.data.add(data);
    }
    public String damePuerto(int posicion){
        return this.Puerto.get(posicion);
    }
    public dataPuerto damedata(int posicion){
        return this.data.get(posicion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(Puerto);
    }
}
