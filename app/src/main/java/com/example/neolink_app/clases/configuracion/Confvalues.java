package com.example.neolink_app.clases.configuracion;

public class Confvalues {
    public double BAT_H;
    public double BAT_L;
    public double BEEP_EN;
    public double GPS_RQ;
    public double NO_WIFI;
    public double N_END_HOUR;
    public double N_SLEEP_TIME;
    public double N_START_HOUR;
    public double PORT_RQ;
    public double SLEEP_TIME;
    public String WIFI_PSSWD_DEFAULT;
    public String WIFI_SSID_DEFAULT;
    public double aux;

    public void confvalues(){}
    public void confvalues(double BAT_H, double BAT_L, double BEEP_EN, double GPS_RQ, double NO_WIFI, double N_END_HOUR, double N_SLEEP_TIME, double N_START_HOUR, double PORT_RQ, double SLEEP_TIME, String WIFI_PSSWD_DEFAULT, String WIFI_SSID_DEFAULT,double aux){
        this.BAT_H = BAT_H;
        this.BAT_L = BAT_L;
        this.BEEP_EN = BEEP_EN;
        this.GPS_RQ = GPS_RQ;
        this.NO_WIFI = NO_WIFI;
        this.N_END_HOUR = N_END_HOUR;
        this.N_SLEEP_TIME = N_SLEEP_TIME;
        this.N_START_HOUR = N_START_HOUR;
        this.PORT_RQ = PORT_RQ;
        this.SLEEP_TIME = SLEEP_TIME;
        this.WIFI_PSSWD_DEFAULT = WIFI_PSSWD_DEFAULT;
        this.WIFI_SSID_DEFAULT = WIFI_SSID_DEFAULT;
        this.aux = aux;
    }


}
