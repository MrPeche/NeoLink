package com.example.neolink_app.clases.LoginFirebase;

public class PCUN {
    private String us;
    private String cn;
    private Boolean ck;
    public PCUN(String us,String cn){
        this.us = us;
        this.cn = cn;
        this.ck = true;
    }
    public String getus(){
        return us;
    }
    public String getcn(){
        return cn;
    }
    public Boolean chekck(){
        return ck;
    }
}
