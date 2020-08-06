package com.example.neolink_app.clases.liveclases;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class livedaylydatapackagetwoday<A,B,C,D> extends MediatorLiveData<Pair<Pair<A,B>,Pair<C,D>>> {
    private A dataA;
    private B dataB;
    private C dataC;
    private D dataD;
    private Boolean validateA = false;
    private Boolean validateB = false;
    private Boolean validateC = false;
    private Boolean validateD = false;

    public livedaylydatapackagetwoday(){}
    public livedaylydatapackagetwoday(LiveData<A> ld1, LiveData<B> ld2, LiveData<C> ld3, LiveData<D> ld4){
        setValue(Pair.create(Pair.create(dataA,dataB),Pair.create(dataC,dataD)));
        validateA = false;
        validateB = false;
        addSource(ld1, new Observer<A>() {
            @Override
            public void onChanged(A a) {
                if(a!=null){
                    dataA = a;
                    validateA = true;
                }
                setValue(Pair.create(Pair.create(dataA,dataB),Pair.create(dataC,dataD)));
            }
        });
        addSource(ld2, new Observer<B>() {
            @Override
            public void onChanged(B b) {
                if(b!=null){
                    dataB = b;
                    validateB = true;
                }
                setValue(Pair.create(Pair.create(dataA,dataB),Pair.create(dataC,dataD)));
            }
        });
        addSource(ld3, new Observer<C>() {
            @Override
            public void onChanged(C c) {
                if(c!=null){
                    dataC = c;
                    validateC = true;
                }
                setValue(Pair.create(Pair.create(dataA,dataB),Pair.create(dataC,dataD)));
            }
        });
        addSource(ld4, new Observer<D>() {
            @Override
            public void onChanged(D d) {
                if(d!=null){
                    dataD = d;
                    validateD = true;
                }
                setValue(Pair.create(Pair.create(dataA,dataB),Pair.create(dataC,dataD)));
            }
        });
    }
    public Boolean isitready(){
        return validateA&&validateB&&validateC&&validateD;
    }

}
