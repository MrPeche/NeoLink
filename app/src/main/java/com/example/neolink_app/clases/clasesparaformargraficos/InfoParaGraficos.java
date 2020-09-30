package com.example.neolink_app.clases.clasesparaformargraficos;

import android.graphics.Color;
import android.util.Pair;

import com.example.neolink_app.clases.DepthPackage;
import com.example.neolink_app.clases.Dias;
import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.clases.Meses;
import com.example.neolink_app.clases.SensorG.DiasG;
import com.example.neolink_app.clases.database_state.diasstate;
import com.example.neolink_app.clases.database_state.horasstate;
import com.example.neolink_app.clases.liveclases.paquetededatacompleto;
import com.example.neolink_app.clases.paquetedatasetPuertos;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class InfoParaGraficos {
    private ArrayList<paquetededatacompleto<Dias,diasstate,DiasG>> dias = new ArrayList<>();
    float LINEWIDTH = 2.5f;
    private int alpha = 170;
    private int[] colores = {Color.argb(alpha,250,128,114),Color.argb(alpha,60,179,113),Color.argb(alpha,100,149,237),Color.argb(alpha,176,196,222)}; //salmon, medium sea green,corn flower blue, light steel blue https://www.rapidtables.com/web/color/RGB_Color.html

    public InfoParaGraficos(){}

    public void agregarinfodias(paquetededatacompleto<Dias,diasstate,DiasG> data){
        this.dias.add(data);
    }
    public boolean validarlosdias(){
        boolean validate = true;
        for(paquetededatacompleto<Dias,diasstate,DiasG> dia: dias){
            validate = dia.isitready()&&validate;
        }
        return validate;
    }
    private void managedias(){
        ArrayList<Dias> sensork = new ArrayList<>();
        ArrayList<diasstate> sensorstate = new ArrayList<>();
        ArrayList<DiasG> sensorg = new ArrayList<>();
        for(paquetededatacompleto<Dias,diasstate,DiasG> dia: dias){
            sensork.add(dia.damevalorA());
            sensorstate.add(dia.damevalorB());
            sensorg.add(dia.damevalorC());
        }
        managesensorkendias(sensork);
        managesensorstateendias(sensorstate);
        managesensorgendias(sensorg);
    }
    private void managesensorkendias(ArrayList<Dias> data){
        paquetedatasetPuertos PotencialMatricial = new paquetedatasetPuertos();
        paquetedatasetPuertos temperatura = new paquetedatasetPuertos();
        DepthPackage depth = new DepthPackage(); //******************************
        String sp = ":";
        String labeldia;
        String labelhoras;
        String labelminutos;
        ArrayList<String> datalabelsaxisX = new ArrayList<>(); //****************
        float Xindex = 0;
        for(Dias dia:data){
            for(int i =0;i<dia.dametamano();i++){
                labeldia = dia.damedia(i);
                for(int j = 0; j<dia.damehora(i).dametamano();j++){
                    labelhoras = dia.damehora(i).damehora(j);
                    for(int k = 0;k<dia.damehora(i).dameminutos(j).dametamano();k++){
                        labelminutos = dia.damehora(i).dameminutos(j).dameminuto(k);
                        datalabelsaxisX.add(labeldia + " "+labelhoras+sp+labelminutos);
                        for(int m = 0;m<dia.damehora(i).dameminutos(j).damepaquete(k).dametamano();m++){
                            String nombredelpuerto = dia.damehora(i).dameminutos(j).damepaquete(k).damePuerto(m);
                            switch (nombredelpuerto){
                                case "P1":
                                    PotencialMatricial.addP1(new Entry(Xindex,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                    temperatura.addP1(new Entry(Xindex,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                    depth.addP1(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                    break;
                                case "P2":
                                    PotencialMatricial.addP2(new Entry(Xindex,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                    temperatura.addP2(new Entry(Xindex,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                    depth.addP2(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                    break;
                                case "P3":
                                    PotencialMatricial.addP3(new Entry(Xindex,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                    temperatura.addP3(new Entry(Xindex,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                    depth.addP3(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                    break;
                                case "P4":
                                    PotencialMatricial.addP4(new Entry(Xindex,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                    temperatura.addP4(new Entry(Xindex,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                    depth.addP4(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                    break;
                            }
                            Xindex++;
                        }
                    }
                }
            }
        }
        Pair<ArrayList<String>,LineData> PM = extraerdatadelpaquete(PotencialMatricial); //****************
        Pair<ArrayList<String>,LineData> temp = extraerdatadelpaquete(temperatura); //*************

    }
    private Pair<ArrayList<String>,LineData> extraerdatadelpaquete(paquetedatasetPuertos entriesdata){
        ArrayList<String> orden = new ArrayList<>();
        LineData data = new LineData();
        if(entriesdata.getP1().size()!=0) {
            LineDataSet set1 = CreaDataLine(entriesdata.getP1(), "P1", colores[0]);
            data.addDataSet(set1);
            orden.add("P1");
        }
        if(entriesdata.getP2().size()!=0) {
            LineDataSet set1 = CreaDataLine(entriesdata.getP2(), "P2", colores[1]);
            data.addDataSet(set1);
            orden.add("P2");
        }
        if(entriesdata.getP3().size()!=0) {
            LineDataSet set1 = CreaDataLine(entriesdata.getP3(), "P3", colores[2]);
            data.addDataSet(set1);
            orden.add("P3");
        }
        if(entriesdata.getP4().size()!=0) {
            LineDataSet set1 = CreaDataLine(entriesdata.getP4(), "P4", colores[3]);
            data.addDataSet(set1);
            orden.add("P4");
        }
        return Pair.create(orden,data);
    }
    private LineDataSet CreaDataLine(ArrayList<Entry> data, String label,int color){

        LineDataSet pset = new LineDataSet(data,label);
        //set1.setHighLightColor(Color.argb(100,255,99,71));
        pset.setColor(color);
        pset.setDrawCircles(false);
        /*
        pset.setCircleColor(color);
        pset.setCircleRadius(5f);
        pset.setCircleHoleColor(Color.WHITE);
        */
        pset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        pset.setCubicIntensity(0.2f);
        pset.setDrawValues(false);
        pset.setLineWidth(LINEWIDTH);
        pset.setDrawHorizontalHighlightIndicator(false);
        pset.setDrawVerticalHighlightIndicator(false);
        return pset;
    }
    private void managesensorgendias(ArrayList<DiasG> data){
        paquetedatasetPuertos humS = new paquetedatasetPuertos();
        paquetedatasetPuertos tempeS = new paquetedatasetPuertos();
        paquetedatasetPuertos condE = new paquetedatasetPuertos();
        DepthPackage Depth = new DepthPackage();
        String sp = ":";
        String labeldia;
        String labelhora;
        String labelminuto;
        String nombrePuerto;
        ArrayList<String> XlabelsG = new ArrayList<>();
        float l = 0;
        for(DiasG dia:data){
            for(int i = 0; i<dia.dametamanoG();i++){
                labeldia = dia.damedia(i);
                for(int j = 0; j<dia.damehora(i).dametamanoG();j++){
                    labelhora = dia.damehora(i).damehora(j);
                    for(int k = 0; k<dia.damehora(i).dameminutos(j).dametamanoG();k++){
                        labelminuto = dia.damehora(i).dameminutos(j).dameminuto(k);
                        XlabelsG.add(labeldia+" "+labelhora+sp+labelminuto);
                        for(int m = 0;m<dia.damehora(i).dameminutos(j).damepaquete(k).dametamanoG();m++){
                            nombrePuerto = dia.damehora(i).dameminutos(j).damepaquete(k).damePuerto(m);
                            switch (nombrePuerto){
                                case "P1":
                                    humS.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                    tempeS.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                    condE.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3().floatValue()));
                                    Depth.addP1(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                    break;
                                case "P2":
                                    humS.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                    tempeS.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                    condE.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3().floatValue()));
                                    Depth.addP2(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                    break;
                                case "P3":
                                    humS.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                    tempeS.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                    condE.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3().floatValue()));
                                    Depth.addP3(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                    break;
                                case "P4":
                                    humS.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                    tempeS.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                    condE.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3().floatValue()));
                                    Depth.addP4(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                    break;
                            }
                        }
                    }
                    l++;
                }
            }
        }

    }
    private void managesensorstateendias(ArrayList<diasstate> data){
        final ArrayList<String> XlabelsSTATE = new ArrayList<>();
        ArrayList<Entry> bateria = new ArrayList<>();
        ArrayList<Entry> solar = new ArrayList<>();
        ArrayList<Entry> presionbaro = new ArrayList<>();
        ArrayList<Entry> humedadrelativa = new ArrayList<>();
        ArrayList<Entry> temperaturavulvoseco = new ArrayList<>();
        ArrayList<Entry> temperaturainterna = new ArrayList<>();
        String sp = ":";
        String labeldia;
        String labelhora;
        float l = 0;
        for(diasstate dia:data){
            for(int i = 0; i<dia.dametamano();i++){
                labeldia = dia.damedia(i);
                for(int k = 0;k<dia.damehoras(i).dametamano();k++){
                    labelhora = dia.damehoras(i).damehora(k);
                    for(int j = 0; j<dia.damehoras(i).dameminutos(k).dametamano();j++){
                        XlabelsSTATE.add(labeldia+" "+labelhora+sp+dia.damehoras(i).dameminutos(k).dameminutos(j));
                        bateria.add(new Entry(l,(float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveBV()));
                        solar.add(new Entry(l,(float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveSV()));
                        presionbaro.add(new Entry(l,(float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveBP()));
                        humedadrelativa.add(new Entry(l,(float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveRH()));
                        temperaturavulvoseco.add(new Entry(l,(float) dia.damehoras(i).dameminutos(k).damepaquete(j).givedT()));
                        temperaturainterna.add(new Entry(l,(float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveiT()));
                        l++;
                    }
                }
            }
        }

        LineDataSet LDS = CreaDataLine(bateria,"Bateria",colores[0]);
        LineDataSet LDSV = CreaDataLine(solar,"Voltaje Solar", colores[1]);
        LDSV.setAxisDependency(YAxis.AxisDependency.LEFT); //
        LDS.setAxisDependency(YAxis.AxisDependency.RIGHT); //
        LineDataSet LDhumedadrelativa= CreaDataLine(humedadrelativa,"Humedad Relativa",colores[0]);
        LineDataSet LDpresionbarometrica= CreaDataLine(presionbaro,"Presion Barométrica",colores[0]);
        LineDataSet LDtemperaturavulvoseco= CreaDataLine(temperaturavulvoseco,"Temperatura de bulbo seco",colores[0]);
        LineDataSet LDtemperaturainterna = CreaDataLine(temperaturainterna, "Temperatura interna",colores[1]);
        LDtemperaturainterna.enableDashedLine(30,10,10);
        LDtemperaturainterna.setLineWidth(0.8f);
    }

    public void agregarmes(Meses sensork, diasstate statesensor, DiasG sensorg){

    }
    public void agregarano(){

    }

}
