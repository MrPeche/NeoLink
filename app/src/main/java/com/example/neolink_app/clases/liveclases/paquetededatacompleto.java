package com.example.neolink_app.clases.liveclases;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class paquetededatacompleto<A,B,C,D,E> extends MediatorLiveData<objetoconjuntoparagraficos<A,B,C,D,E>> {
    private A dataa;
    private B datab;
    private C datac;
    private D datad;
    private E datae;
    public boolean validateA = false;
    public boolean validateB = false;
    public boolean validateC = false;
    public boolean validateD = false;
    public boolean validateE = false;
    public paquetededatacompleto(){}

    public paquetededatacompleto(LiveData<A> ld1, LiveData<B> ld2, LiveData<C> ld3, LiveData<D> ld4, LiveData<E> ld5) {
        setValue(new objetoconjuntoparagraficos<>(dataa,datab,datac,datad,datae));
        validateA = false;
        validateB = false;
        validateC = false;
        validateD = false;
        validateE = false;
        addSource(ld1, new Observer<A>() {
            @Override
            public void onChanged(A a) {
                if(a!=null){
                    dataa = a;
                    validateA = true;
                }
                setValue(new objetoconjuntoparagraficos<>(dataa,datab,datac,datad,datae));
            }
        });
        addSource(ld2, new Observer<B>() {
            @Override
            public void onChanged(B b) {
                if(b!=null){
                    datab = b;
                    validateB = true;
                }
                setValue(new objetoconjuntoparagraficos<>(dataa,datab,datac,datad,datae));
            }
        });
        addSource(ld3, new Observer<C>() {
            @Override
            public void onChanged(C c) {
                if(c!=null){
                    datac=c;
                    validateC=true;
                }
                setValue(new objetoconjuntoparagraficos<>(dataa,datab,datac,datad,datae));
            }
        });
        addSource(ld4, new Observer<D>() {
            @Override
            public void onChanged(D d) {
                if(d!=null){
                    datad=d;
                    validateD=true;
                }
                setValue(new objetoconjuntoparagraficos<>(dataa,datab,datac,datad,datae));
            }
        });
        addSource(ld5, new Observer<E>() {
            @Override
            public void onChanged(E e) {
                if(e!=null){
                    datae=e;
                    validateE=true;
                }
                setValue(new objetoconjuntoparagraficos<>(dataa,datab,datac,datad,datae));
            }
        });

    }
    public boolean isitready(){
        return this.validateA&&this.validateB&&this.validateC&&this.validateD&&validateE;
    }
    public A damevalorA(){return this.dataa;}
    public B damevalorB(){return this.datab;}
    public C damevalorC(){return this.datac;}
    public D damevalorD(){return this.datad;}
    public E damevalorE(){return this.datae;}

}
