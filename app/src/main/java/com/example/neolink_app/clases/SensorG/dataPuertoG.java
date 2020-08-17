package com.example.neolink_app.clases.SensorG;

import android.os.Parcel;
import android.os.Parcelable;

public class dataPuertoG implements Parcelable {
    public Double Depth;
    public Double V1;
    public Double V2;
    public Double V3;
    public dataPuertoG(){}
    public dataPuertoG(Double Depth, Double V1,Double V2,Double V3){
        this.Depth = Depth;
        this.V1 = V1;
        this.V2 = V2;
        this.V3 = V3;
    }


    protected dataPuertoG(Parcel in) {
        if (in.readByte() == 0) {
            Depth = null;
        } else {
            Depth = in.readDouble();
        }
        if (in.readByte() == 0) {
            V1 = null;
        } else {
            V1 = in.readDouble();
        }
        if (in.readByte() == 0) {
            V2 = null;
        } else {
            V2 = in.readDouble();
        }
        if (in.readByte() == 0) {
            V3 = null;
        } else {
            V3 = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Depth == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Depth);
        }
        if (V1 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(V1);
        }
        if (V2 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(V2);
        }
        if (V3 == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(V3);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<dataPuertoG> CREATOR = new Creator<dataPuertoG>() {
        @Override
        public dataPuertoG createFromParcel(Parcel in) {
            return new dataPuertoG(in);
        }

        @Override
        public dataPuertoG[] newArray(int size) {
            return new dataPuertoG[size];
        }
    };

    public Double dameV1(){return this.V1;}
    public Double dameV2(){return this.V2;}
    public Double dameV3(){return this.V3;}
    public Double dameDepth(){return this.Depth;}
}
