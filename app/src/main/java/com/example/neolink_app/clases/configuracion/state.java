package com.example.neolink_app.clases.configuracion;

public class state {
    public int Firmware;
    public stateGPS GPS;
    public String Hardware_ver;
    public String IP;
    public double LastOP_TIME;
    public String LastUpload;
    public statelimitsport Limits;
    public boolean MODE_PRG;
    public double NewConf;
    public statenotification Notification;
    public statePortsactive Port;
    public String WiFi_rssi;
    public state(){}
    public state(int Firmware, stateGPS GPS, String Hardware_ver, String IP, double LastOP_TIME, String LastUpload, statelimitsport Limits, boolean MODE_PRG, double NewConf, statenotification Notification, statePortsactive Port, String WiFi_rssi){
        this.Firmware = Firmware;
        this.GPS = GPS;
        this.Hardware_ver = Hardware_ver;
        this.IP = IP;
        this.LastOP_TIME = LastOP_TIME;
        this.LastUpload = LastUpload;
        this.Limits = Limits;
        this.MODE_PRG = MODE_PRG;
        this.NewConf = NewConf;
        this.Notification = Notification;
        this.Port = Port;
        this.WiFi_rssi = WiFi_rssi;
    }
    public double dameFirmware(){return Firmware;}
    public stateGPS dameGPS(){return GPS;}
    public String dameHardware_ver(){return Hardware_ver;}
    public String dameIP(){return IP;}
    public double dameLastOP_TIME(){return LastOP_TIME;}
    public String dameLastUpload(){return LastUpload;}
    //public statelimitsport dameLimits(){return Limits;}
    public boolean dameMODE_PRG(){return MODE_PRG;}
    public double dameNewConf(){return NewConf;}
    public statenotification dameNotification(){return Notification;}
    public statePortsactive damePort(){return Port;}
    public String dameWiFirssi(){return WiFi_rssi;}
}
