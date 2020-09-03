package com.example.neolink_app.clases.configuracion;

public class Confvalues {
    public double BAT_H;
    public double BAT_L;
    public int BEEP_EN;
    public int GPS_RQ;
    public double NO_WIFI;
    public double N_END_HOUR;
    public double N_SLEEP_TIME;
    public double N_START_HOUR;
    public int PORT_RQ;
    public int SLEEP_TIME;
    public String WIFI_PSSWD;
    public String WIFI_SSID;

    public void confvalues(){}
    public void confvalues(double BAT_H, double BAT_L, int BEEP_EN, int GPS_RQ, double NO_WIFI, double N_END_HOUR, double N_SLEEP_TIME, double N_START_HOUR, int PORT_RQ, int SLEEP_TIME, String WIFI_PSSWD, String WIFI_SSID,double aux){
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
        this.WIFI_PSSWD = WIFI_PSSWD;
        this.WIFI_SSID = WIFI_SSID;
    }


}
