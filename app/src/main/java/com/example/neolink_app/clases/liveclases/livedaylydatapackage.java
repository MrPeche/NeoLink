package com.example.neolink_app.clases.liveclases;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class livedaylydatapackage<A,B> extends MediatorLiveData<Pair<A,B>> {
    private A dataa;
    private B datab;
    public boolean validateA = false;
    public boolean validateB = false;

    public livedaylydatapackage(){}

    public livedaylydatapackage(LiveData<A> ld1, LiveData<B> ld2) {
        setValue(Pair.create(dataa, datab));
        validateA = false;
        validateB = false;
        addSource(ld1, new Observer<A>() {
            @Override
            public void onChanged(A a) {
                if(a!=null){
                    dataa = a;
                    validateA = true;
                }
                setValue(Pair.create(dataa, datab));
            }
        });
        addSource(ld2, new Observer<B>() {
            @Override
            public void onChanged(B b) {
                if(b!=null){
                    datab = b;
                    validateB = true;
                }
                setValue(Pair.create(dataa, datab));
            }
        });
    }
    public boolean isitready(){
        return this.validateA&&this.validateB;}
}
