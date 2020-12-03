package com.example.neolink_app.clases.clasesparaformargraficos;

import android.graphics.Color;
import android.util.Pair;

import com.example.neolink_app.clases.DepthPackage;
import com.example.neolink_app.clases.Dias;
import com.example.neolink_app.clases.Meses;
import com.example.neolink_app.clases.SensorG.DiasG;
import com.example.neolink_app.clases.SensorG.MesesG;
import com.example.neolink_app.clases.database_state.diasstate;
import com.example.neolink_app.clases.database_state.mesesstate;
import com.example.neolink_app.clases.liveclases.paquetededatacompleto;
import com.example.neolink_app.clases.paquetedatasetPuertos;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;

public class InfoParaGraficos {
    private ArrayList<paquetededatacompleto<Dias,diasstate,DiasG>> dias = new ArrayList<>();
    private ArrayList<paquetededatacompleto<Meses, mesesstate, MesesG>> meses = new ArrayList<>();
    float LINEWIDTH = 2.5f;
    private int alpha = 250;
    private int[] colores = {Color.argb(alpha,250,128,114),Color.argb(alpha,60,179,113),Color.argb(alpha,100,149,237),Color.argb(alpha,147,112,219)}; //salmon, medium sea green,corn flower blue,  medium purple https://www.rapidtables.com/web/color/RGB_Color.html

    public InfoParaGraficos(){}

    public void agregarinfodias(paquetededatacompleto<Dias,diasstate,DiasG> data){
        this.meses.clear();
        this.dias.add(data);
    }
    public void agregarmesinfomes(paquetededatacompleto<Meses, mesesstate, MesesG> mes){
        this.dias.clear();
        this.meses.add(mes);
    }
    public boolean validarlosdias(){
        boolean validate = true;
        if(dias.size()!=0){
            for(paquetededatacompleto<Dias,diasstate,DiasG> dia: dias){
                validate = dia.isitready()&&validate;
            }
            return validate;
        } else if(meses.size()!=0){
            for(paquetededatacompleto<Meses,mesesstate,MesesG> mes: meses){
                validate = mes.isitready()&&validate;
            }
            return validate;
        } else
            return false;

        /*
        if((dias.size()==0)){
            return false;
        } else{
            for(paquetededatacompleto<Dias,diasstate,DiasG> dia: dias){
                validate = dia.isitready()&&validate;
            }
            return validate;
        }
         */
    }
    public Pair<Integer,Boolean> buscarpordiadentro(String dia){
        boolean hay = false;
        int posicion = 0;
        for(int i=0;i<dias.size();i++){
            if(dias.get(i).damevalorB().damedia(0).equals(dia)){
                hay=true;
                posicion=i;
                break;
            }
        }
        return Pair.create(posicion,hay);
    }
    public void actualizardatoespecifico(paquetededatacompleto<Dias,diasstate,DiasG> data, int pos){
        this.dias.set(pos,data);
    }
    public fulldatapack managedias(){
        if(dias.size()!=0){
            ArrayList<Dias> sensork = new ArrayList<>();
            ArrayList<diasstate> sensorstate = new ArrayList<>();
            ArrayList<DiasG> sensorg = new ArrayList<>();
            for(paquetededatacompleto<Dias,diasstate,DiasG> dia: dias){
                sensork.add(dia.damevalorA());
                sensorstate.add(dia.damevalorB());
                sensorg.add(dia.damevalorC());
            }
            return new fulldatapack(managesensorkendias(sensork),managesensorgendias(sensorg),managesensorstateendias(sensorstate));
        } else {
            ArrayList<Meses> sensorkmes = new ArrayList<>();
            ArrayList<mesesstate> sensorstatemes = new ArrayList<>();
            ArrayList<MesesG> sensorgmes = new ArrayList<>();
            for(paquetededatacompleto<Meses,mesesstate,MesesG> mes: meses){
                sensorkmes.add(mes.damevalorA());
                sensorstatemes.add(mes.damevalorB());
                sensorgmes.add(mes.damevalorC());
            }
            return new fulldatapack(managesensorkmes(sensorkmes),managesensorgmes(sensorgmes),managesensorstatemes(sensorstatemes));
        }

        /*
        ArrayList<Dias> sensork = new ArrayList<>();
        ArrayList<diasstate> sensorstate = new ArrayList<>();
        ArrayList<DiasG> sensorg = new ArrayList<>();
        for(paquetededatacompleto<Dias,diasstate,DiasG> dia: dias){
            sensork.add(dia.damevalorA());
            sensorstate.add(dia.damevalorB());
            sensorg.add(dia.damevalorC());
        }
        return new fulldatapack(managesensorkendias(sensork),managesensorgendias(sensorg),managesensorstateendias(sensorstate));
         */
    }
    private kdatapack managesensorkendias(ArrayList<Dias> data){
        Collections.sort(data,new sortpordias<>());
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
                        datalabelsaxisX.add(labeldia +"\n"+labelhoras+sp+labelminutos);
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
        Pair<paquetedatasetPuertos,paquetedatasetPuertos> Raiz = new Pair<>(PotencialMatricial,temperatura);
        return new kdatapack(depth,datalabelsaxisX,PM,temp,Raiz);
    }
    private Pair<ArrayList<String>,LineData> extraerdatadelpaquete(paquetedatasetPuertos entriesdata){
        ArrayList<String> orden = new ArrayList<>();
        LineData data = new LineData();
        if(entriesdata.getP1().size()!=0) {
            LineDataSet set1 = CreaDataLine(entriesdata.getP1(), "Puerto1", colores[0]);
            data.addDataSet(set1);
            orden.add("P1");
        }
        if(entriesdata.getP2().size()!=0) {
            LineDataSet set1 = CreaDataLine(entriesdata.getP2(), "Puerto2", colores[1]);
            data.addDataSet(set1);
            orden.add("P2");
        }
        if(entriesdata.getP3().size()!=0) {
            LineDataSet set1 = CreaDataLine(entriesdata.getP3(), "Puerto3", colores[2]);
            data.addDataSet(set1);
            orden.add("P3");
        }
        if(entriesdata.getP4().size()!=0) {
            LineDataSet set1 = CreaDataLine(entriesdata.getP4(), "Puerto4", colores[3]);
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
    private gdatapack managesensorgendias(ArrayList<DiasG> data){
        Collections.sort(data,new sortpordias<>());
        paquetedatasetPuertos humS = new paquetedatasetPuertos();
        paquetedatasetPuertos tempeS = new paquetedatasetPuertos();
        paquetedatasetPuertos condE = new paquetedatasetPuertos();
        paquetedatasetPuertos conddelporo = new paquetedatasetPuertos();
        paquetedatasetPuertos contentvolumagua = new paquetedatasetPuertos();
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
                        XlabelsG.add(labeldia+"\n"+labelhora+sp+labelminuto);
                        for(int m = 0;m<dia.damehora(i).dameminutos(j).damepaquete(k).dametamanoG();m++){
                            nombrePuerto = dia.damehora(i).dameminutos(j).damepaquete(k).damePuerto(m);
                            switch (nombrePuerto){
                                case "P1":
                                    humS.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                    tempeS.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                    condE.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3().floatValue()));
                                    conddelporo.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameporece().floatValue()));
                                    contentvolumagua.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).damevwc().floatValue()));
                                    Depth.addP1(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                    break;
                                case "P2":
                                    humS.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                    tempeS.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                    condE.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3().floatValue()));
                                    conddelporo.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameporece().floatValue()));
                                    contentvolumagua.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).damevwc().floatValue()));
                                    Depth.addP2(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                    break;
                                case "P3":
                                    humS.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                    tempeS.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                    condE.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3().floatValue()));
                                    conddelporo.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameporece().floatValue()));
                                    contentvolumagua.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).damevwc().floatValue()));
                                    Depth.addP3(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                    break;
                                case "P4":
                                    humS.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                    tempeS.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                    condE.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3().floatValue()));
                                    conddelporo.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameporece().floatValue()));
                                    contentvolumagua.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).damevwc().floatValue()));
                                    Depth.addP4(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                    break;
                            }
                        }
                        l++;
                    }
                    //l++;
                }
            }
        }
        Pair<ArrayList<String>,LineData> humedadG = extraerdatadelpaquete(humS);
        Pair<ArrayList<String>,LineData> temperaturaG = extraerdatadelpaquete(tempeS);
        Pair<ArrayList<String>,LineData> conductividadG = extraerdatadelpaquete(condE);
        Pair<ArrayList<String>,LineData> conductividaddelporo = extraerdatadelpaquete(conddelporo);
        Pair<ArrayList<String>,LineData> contenidovolumendelagua = extraerdatadelpaquete(contentvolumagua);
        return new gdatapack(Depth,XlabelsG,humedadG,temperaturaG,conductividadG,conductividaddelporo,contenidovolumendelagua,humS,tempeS,condE,conddelporo,contentvolumagua);
    }
    private statedatapack managesensorstateendias(ArrayList<diasstate> data){
        Collections.sort(data,new sortpordias<>());
        ArrayList<String> XlabelsSTATE = new ArrayList<>();
        ArrayList<Entry> bateria = new ArrayList<>();
        ArrayList<Entry> solar = new ArrayList<>();
        ArrayList<Entry> presionbaro = new ArrayList<>();
        ArrayList<Entry> humedadrelativa = new ArrayList<>();
        ArrayList<Entry> temperaturavulvoseco = new ArrayList<>();
        ArrayList<Entry> temperaturainterna = new ArrayList<>();
        ArrayList<Entry> windspeed = new ArrayList<>();
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
                        XlabelsSTATE.add(labeldia+"\n"+labelhora+sp+dia.damehoras(i).dameminutos(k).dameminutos(j));
                        bateria.add(new Entry(l,(float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveBV()));
                        solar.add(new Entry(l,(float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveSV()));
                        presionbaro.add(new Entry(l,(float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveBP()));
                        humedadrelativa.add(new Entry(l,(float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveRH()));
                        temperaturavulvoseco.add(new Entry(l,(float) dia.damehoras(i).dameminutos(k).damepaquete(j).givedT()));
                        temperaturainterna.add(new Entry(l,(float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveiT()));
                        windspeed.add(new Entry(l,(float)dia.damehoras(i).dameminutos(k).damepaquete(j).giveWS()));
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
        LineDataSet LDwindspeed = CreaDataLine(windspeed,"Velocidad del viento",colores[0]);
        LDtemperaturainterna.enableDashedLine(30,10,10);
        LDtemperaturainterna.setLineWidth(0.8f);
        return new statedatapack(dataseparada(LDhumedadrelativa),dataseparada(LDpresionbarometrica),juntardata(LDS,LDSV),juntardata(LDtemperaturavulvoseco,LDtemperaturainterna),dataseparada(LDwindspeed),XlabelsSTATE);
    }
    private LineData juntardata(LineDataSet A, LineDataSet B){
        LineData data = new LineData();
        data.addDataSet(A);
        data.addDataSet(B);
        return data;
    }
    private LineData dataseparada(LineDataSet A){
        LineData data = new LineData();
        data.addDataSet(A);
        return  data;
    }

    private kdatapack managesensorkmes(ArrayList<Meses> data){
        Collections.sort(data, new sortpormes<>());
        paquetedatasetPuertos PotencialMatricial = new paquetedatasetPuertos();
        paquetedatasetPuertos temperatura = new paquetedatasetPuertos();
        DepthPackage depth = new DepthPackage(); //******************************
        String sp = ":";
        String labeldia;
        String labelhoras;
        String labelminutos;
        ArrayList<String> datalabelsaxisX = new ArrayList<>(); //****************
        float Xindex = 0;
        for(Meses mes:data){
            for(Dias dia:mes.dameelarraycompleto()){
                for(int i =0;i<dia.dametamano();i++){
                    labeldia = mes.damemes(0)+"/"+dia.damedia(i);
                    for(int j = 0; j<dia.damehora(i).dametamano();j++){
                        labelhoras = dia.damehora(i).damehora(j);
                        for(int k = 0;k<dia.damehora(i).dameminutos(j).dametamano();k++){
                            labelminutos = dia.damehora(i).dameminutos(j).dameminuto(k);
                            datalabelsaxisX.add(labeldia +"\n"+labelhoras+sp+labelminutos);
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
        }

        Pair<ArrayList<String>,LineData> PM = extraerdatadelpaquete(PotencialMatricial); //****************
        Pair<ArrayList<String>,LineData> temp = extraerdatadelpaquete(temperatura); //*************
        Pair<paquetedatasetPuertos,paquetedatasetPuertos> Raiz = new Pair<>(PotencialMatricial,temperatura);
        return new kdatapack(depth,datalabelsaxisX,PM,temp,Raiz);
    }
    private gdatapack managesensorgmes(ArrayList<MesesG> data){
        Collections.sort(data, new sortpormes<>());
        paquetedatasetPuertos humS = new paquetedatasetPuertos();
        paquetedatasetPuertos tempeS = new paquetedatasetPuertos();
        paquetedatasetPuertos condE = new paquetedatasetPuertos();
        paquetedatasetPuertos conddelporo = new paquetedatasetPuertos();
        paquetedatasetPuertos contentvolumagua = new paquetedatasetPuertos();
        DepthPackage Depth = new DepthPackage();
        String sp = ":";
        String labeldia;
        String labelhora;
        String labelminuto;
        String nombrePuerto;
        ArrayList<String> XlabelsG = new ArrayList<>();
        float l = 0;
        for(MesesG mes:data){
            for(DiasG dia:mes.damearraydedias()){
                for(int i = 0; i<dia.dametamanoG();i++){
                    labeldia = mes.damemes(0)+"/"+dia.damedia(i);
                    for(int j = 0; j<dia.damehora(i).dametamanoG();j++){
                        labelhora = dia.damehora(i).damehora(j);
                        for(int k = 0; k<dia.damehora(i).dameminutos(j).dametamanoG();k++){
                            labelminuto = dia.damehora(i).dameminutos(j).dameminuto(k);
                            XlabelsG.add(labeldia+"\n"+labelhora+sp+labelminuto);
                            for(int m = 0;m<dia.damehora(i).dameminutos(j).damepaquete(k).dametamanoG();m++){
                                nombrePuerto = dia.damehora(i).dameminutos(j).damepaquete(k).damePuerto(m);
                                switch (nombrePuerto){
                                    case "P1":
                                        humS.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                        tempeS.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                        condE.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3().floatValue()));
                                        conddelporo.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameporece().floatValue()));
                                        contentvolumagua.addP1(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).damevwc().floatValue()));
                                        Depth.addP1(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                        break;
                                    case "P2":
                                        humS.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                        tempeS.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                        condE.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3().floatValue()));
                                        conddelporo.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameporece().floatValue()));
                                        contentvolumagua.addP2(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).damevwc().floatValue()));
                                        Depth.addP2(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                        break;
                                    case "P3":
                                        humS.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                        tempeS.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                        condE.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3().floatValue()));
                                        conddelporo.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameporece().floatValue()));
                                        contentvolumagua.addP3(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).damevwc().floatValue()));
                                        Depth.addP3(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                        break;
                                    case "P4":
                                        humS.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1().floatValue()));
                                        tempeS.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2().floatValue()));
                                        condE.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3().floatValue()));
                                        conddelporo.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameporece().floatValue()));
                                        contentvolumagua.addP4(new Entry(l,dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).damevwc().floatValue()));
                                        Depth.addP4(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth());
                                        break;
                                }
                            }
                            l++;
                        }
                        //l++;
                    }
                }
            }
        }

        Pair<ArrayList<String>,LineData> humedadG = extraerdatadelpaquete(humS);
        Pair<ArrayList<String>,LineData> temperaturaG = extraerdatadelpaquete(tempeS);
        Pair<ArrayList<String>,LineData> conductividadG = extraerdatadelpaquete(condE);
        Pair<ArrayList<String>,LineData> conductividaddelporo = extraerdatadelpaquete(conddelporo);
        Pair<ArrayList<String>,LineData> contenidovolumendelagua = extraerdatadelpaquete(contentvolumagua);
        return new gdatapack(Depth,XlabelsG,humedadG,temperaturaG,conductividadG,conductividaddelporo,contenidovolumendelagua,humS,tempeS,condE,conddelporo,contentvolumagua);
    }
    private statedatapack managesensorstatemes(ArrayList<mesesstate> data){
        Collections.sort(data, new sortpormes<>());
        ArrayList<String> XlabelsSTATE = new ArrayList<>();
        ArrayList<Entry> bateria = new ArrayList<>();
        ArrayList<Entry> solar = new ArrayList<>();
        ArrayList<Entry> presionbaro = new ArrayList<>();
        ArrayList<Entry> humedadrelativa = new ArrayList<>();
        ArrayList<Entry> temperaturavulvoseco = new ArrayList<>();
        ArrayList<Entry> temperaturainterna = new ArrayList<>();
        ArrayList<Entry> windspeed = new ArrayList<>();
        String sp = ":";
        String labeldia;
        String labelhora;
        float l = 0;
        for(mesesstate mes: data) {
            for (diasstate dia : mes.damearraydedias()) {
                for (int i = 0; i < dia.dametamano(); i++) {
                    labeldia = mes.damemeses(0) + "/" + dia.damedia(i);
                    for (int k = 0; k < dia.damehoras(i).dametamano(); k++) {
                        labelhora = dia.damehoras(i).damehora(k);
                        for (int j = 0; j < dia.damehoras(i).dameminutos(k).dametamano(); j++) {
                            XlabelsSTATE.add(labeldia + "\n" + labelhora + sp + dia.damehoras(i).dameminutos(k).dameminutos(j));
                            bateria.add(new Entry(l, (float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveBV()));
                            solar.add(new Entry(l, (float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveSV()));
                            presionbaro.add(new Entry(l, (float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveBP()));
                            humedadrelativa.add(new Entry(l, (float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveRH()));
                            temperaturavulvoseco.add(new Entry(l, (float) dia.damehoras(i).dameminutos(k).damepaquete(j).givedT()));
                            temperaturainterna.add(new Entry(l, (float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveiT()));
                            windspeed.add(new Entry(l, (float) dia.damehoras(i).dameminutos(k).damepaquete(j).giveWS()));
                            l++;
                        }
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
        LineDataSet LDwindspeed = CreaDataLine(windspeed,"Velocidad del viento",colores[0]);
        LDtemperaturainterna.enableDashedLine(30,10,10);
        LDtemperaturainterna.setLineWidth(0.8f);
        return new statedatapack(dataseparada(LDhumedadrelativa),dataseparada(LDpresionbarometrica),juntardata(LDS,LDSV),juntardata(LDtemperaturavulvoseco,LDtemperaturainterna),dataseparada(LDwindspeed),XlabelsSTATE);
    }
    public void agregarano(){

    }

}
