package com.example.neolink_app.clases.liveclases;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class paquetededatacompleto<A,B,C> extends MediatorLiveData<Pair<Pair<A,B>,C>> {
    private A dataa;
    private B datab;
    private C datac;
    public boolean validateA = false;
    public boolean validateB = false;
    public boolean validateC = false;
    public paquetededatacompleto(){}

    public paquetededatacompleto(LiveData<A> ld1, LiveData<B> ld2, LiveData<C> ld3) {
        setValue(Pair.create(Pair.create(dataa, datab),datac));
        validateA = false;
        validateB = false;
        validateC = false;
        addSource(ld1, new Observer<A>() {
            @Override
            public void onChanged(A a) {
                if(a!=null){
                    dataa = a;
                    validateA = true;
                }
                setValue(Pair.create(Pair.create(dataa, datab),datac));
            }
        });
        addSource(ld2, new Observer<B>() {
            @Override
            public void onChanged(B b) {
                if(b!=null){
                    datab = b;
                    validateB = true;
                }
                setValue(Pair.create(Pair.create(dataa, datab),datac));
            }
        });
        addSource(ld3, new Observer<C>() {
            @Override
            public void onChanged(C c) {
                if(c!=null){
                    datac=c;
                    validateC=true;
                }
                setValue(Pair.create(Pair.create(dataa, datab),datac));
            }
        });
    }
    public boolean isitready(){
        return this.validateA&&this.validateB&&this.validateC;
    }
    public A damevalorA(){return this.dataa;}
    public B damevalorB(){return this.datab;}
    public C damevalorC(){return this.datac;}

}
