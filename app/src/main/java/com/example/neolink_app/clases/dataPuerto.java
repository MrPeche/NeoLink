package com.example.neolink_app.clases;

import android.os.Parcel;
import android.os.Parcelable;

public class dataPuerto implements Parcelable {
    public Double Depth;
    public Double V1;
    public Double V2;

    public dataPuerto(){}
    public dataPuerto(Double Depth, Double V1, Double V2){
        this.Depth = Depth;
        this.V1 = V1;
        this.V2 = V2;
    }

    protected dataPuerto(Parcel in) {
        Depth = in.readDouble();
        V1 = in.readDouble();
        V2 = in.readDouble();
    }

    public static final Creator<dataPuerto> CREATOR = new Creator<dataPuerto>() {
        @Override
        public dataPuerto createFromParcel(Parcel in) {
            return new dataPuerto(in);
        }

        @Override
        public dataPuerto[] newArray(int size) {
            return new dataPuerto[size];
        }
    };

    public Double dameDepth(){
        return Depth;
    }
    public Double dameV1(){
        return V1;
    }
    public Double dameV2(){
        return V2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(Depth);
        dest.writeDouble(V1);
        dest.writeDouble(V2);
    }
}
