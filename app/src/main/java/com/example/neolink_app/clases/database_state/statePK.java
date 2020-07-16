package com.example.neolink_app.clases.database_state;

import android.os.Parcel;
import android.os.Parcelable;

public class statePK implements Parcelable {
    public double AL;
    public double BP;
    public double BV;
    public double RH;
    public double dT;
    public double SV;
    public double iT;

    public statePK(){}
    public statePK(double AL,double BP,double BV,double RH,double dT,double SV,double iT){
        this.AL = AL;
        this.BP = BP;
        this.BV = BV;
        this.RH = RH;
        this.dT = dT;
        this.SV = SV;
        this.iT = iT;
    }

    protected statePK(Parcel in) {
        AL = in.readDouble();
        BP = in.readDouble();
        BV = in.readDouble();
        RH = in.readDouble();
        dT = in.readDouble();
        SV = in.readDouble();
        iT = in.readDouble();
    }

    public static final Creator<statePK> CREATOR = new Creator<statePK>() {
        @Override
        public statePK createFromParcel(Parcel in) {
            return new statePK(in);
        }

        @Override
        public statePK[] newArray(int size) {
            return new statePK[size];
        }
    };

    public double giveAL(){
        return this.AL;
    }
    public double giveBP(){
        return this.BP;
    }
    public double giveBV(){
        return this.BV;
    }
    public double giveRH(){
        return this.RH;
    }
    public double givedT(){
        return this.dT;
    }
    public double giveSV(){
        return this.SV;
    }
    public double giveiT(){
        return this.iT;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(AL);
        dest.writeDouble(BP);
        dest.writeDouble(BV);
        dest.writeDouble(RH);
        dest.writeDouble(dT);
        dest.writeDouble(SV);
        dest.writeDouble(iT);
    }
}
