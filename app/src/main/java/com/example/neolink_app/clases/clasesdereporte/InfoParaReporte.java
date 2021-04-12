package com.example.neolink_app.clases.clasesdereporte;

import android.util.Pair;

import com.example.neolink_app.clases.DepthPackage;
import com.example.neolink_app.clases.Dias;
import com.example.neolink_app.clases.Meses;
import com.example.neolink_app.clases.SensorG.DiasG;
import com.example.neolink_app.clases.SensorG.MesesG;
import com.example.neolink_app.clases.clasesparaformargraficos.fulldatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.gdatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.kdatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.sortpordias;
import com.example.neolink_app.clases.clasesparaformargraficos.sortpormes;
import com.example.neolink_app.clases.clasesparaformargraficos.statedatapack;
import com.example.neolink_app.clases.database_state.diasstate;
import com.example.neolink_app.clases.database_state.mesesstate;
import com.example.neolink_app.clases.liveclases.paquetededatacompleto;
import com.example.neolink_app.clases.liveclases.paquetededatacompletoparareporte;
import com.example.neolink_app.clases.paquetedatasetPuertos;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.api.services.sheets.v4.model.Border;
import com.google.api.services.sheets.v4.model.Borders;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.CellFormat;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridData;
import com.google.api.services.sheets.v4.model.NumberFormat;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.common.collect.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class InfoParaReporte {
    private String Name;
    private int numerodedias;
    private int numerodepaquetes;
    private ArrayList<paquetededatacompletoparareporte<Dias, diasstate, DiasG>> dias = new ArrayList<>();
    private ArrayList<paquetededatacompletoparareporte<Meses, mesesstate, MesesG>> meses = new ArrayList<>();

    public InfoParaReporte(){}
    public void agregardias(int dias){
        numerodedias = dias;
        numerodepaquetes =0;
    }
    public void sumardias(){
        numerodepaquetes++;
    }
    public boolean checknumerodepaquetesaldia(){
        return numerodedias<=numerodepaquetes;
    }
    public void agregarinfodias(paquetededatacompletoparareporte<Dias,diasstate,DiasG> data){
        this.meses.clear();
        this.dias.add(data);
    }
    public void agregarmesinfomes(paquetededatacompletoparareporte<Meses, mesesstate, MesesG> mes){
        this.dias.clear();
        this.meses.add(mes);
    }
    public boolean validarlosdias(){
        boolean validate = true;
        if(dias.size()!=0){
            for(paquetededatacompletoparareporte<Dias,diasstate,DiasG> dia: dias){
                validate = dia.isitready()&&validate;
            }
            return validate;
        } else if(meses.size()!=0){
            for(paquetededatacompletoparareporte<Meses,mesesstate,MesesG> mes: meses){
                validate = mes.isitready()&&validate;
            }
            return validate;
        } else
            return false;

    }
    public void agregarnombre(String name){
        this.Name=name;
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
    public void actualizardatoespecifico(paquetededatacompletoparareporte<Dias,diasstate,DiasG> data, int pos){
        this.dias.set(pos,data);
    }
    public ArrayList<List<RowData>> managedias(){
        if(dias.size()!=0){
            ArrayList<Dias> sensork = new ArrayList<>();
            ArrayList<diasstate> sensorstate = new ArrayList<>();
            ArrayList<DiasG> sensorg = new ArrayList<>();
            for(paquetededatacompletoparareporte<Dias,diasstate,DiasG> dia: dias){
                sensork.add(dia.damevalorA());
                sensorstate.add(dia.damevalorB());
                sensorg.add(dia.damevalorC());
            }
            return paquetedehojas(createsensorKrows(sensork),createsensorGrows(sensorg),createsensorSTATErows(sensorstate));
        } else {
            ArrayList<Meses> sensorkmes = new ArrayList<>();
            ArrayList<mesesstate> sensorstatemes = new ArrayList<>();
            ArrayList<MesesG> sensorgmes = new ArrayList<>();
            for(paquetededatacompletoparareporte<Meses,mesesstate,MesesG> mes: meses){
                sensorkmes.add(mes.damevalorA());
                sensorstatemes.add(mes.damevalorB());
                sensorgmes.add(mes.damevalorC());
            }
            return paquetedehojas(createsensorKrowsmes(sensorkmes),createsensorGrowsmes(sensorgmes),createsensorSTATErowsmes(sensorstatemes));
        }
    }
    private ArrayList<List<RowData>> paquetedehojas(List<RowData> sensork, List<RowData> sensorg, List<RowData> sensorstate){
        ArrayList<List<RowData>> sensores = new ArrayList<>();
        sensores.add(sensork);
        sensores.add(sensorg);
        sensores.add(sensorstate);
        return sensores;
    }
    private CellData createstringcelldata(String data){
        CellData cells = new CellData();
        ExtendedValue ext = new ExtendedValue();
        Border bordegrueso = new Border().setStyle("SOLID_MEDIUM");
        cells.setUserEnteredValue(ext.setStringValue(data)).setUserEnteredFormat(new CellFormat().setVerticalAlignment("MIDDLE").setWrapStrategy("WRAP").setHorizontalAlignment("CENTER").setBorders(new Borders().setLeft(bordegrueso).setRight(bordegrueso)));
        return cells;
    }
    private CellData createstringtitlecelldata(String data){
        CellData cells = new CellData();
        ExtendedValue ext = new ExtendedValue();
        Border bordegrueso = new Border().setStyle("SOLID_MEDIUM");
        cells.setUserEnteredValue(ext.setStringValue(data)).setUserEnteredFormat(new CellFormat().setVerticalAlignment("MIDDLE").setWrapStrategy("WRAP").setHorizontalAlignment("CENTER").setBorders(new Borders().setBottom(bordegrueso).setTop(bordegrueso).setLeft(bordegrueso).setRight(bordegrueso)));
        return cells;
    }
    private CellData createDatecelldata(Double fecha){
        CellData cell = new CellData();
        ExtendedValue ext = new ExtendedValue();
        Border bordegrueso = new Border().setStyle("SOLID_MEDIUM");
        cell.setUserEnteredValue(ext.setNumberValue(fecha)).setUserEnteredFormat(new CellFormat().setNumberFormat(new NumberFormat().setType("DATE_TIME")).setVerticalAlignment("MIDDLE").setHorizontalAlignment("CENTER").setBorders(new Borders().setLeft(bordegrueso).setRight(bordegrueso)));
        return cell;
    }
    private CellData createNumberscelldata(Double numero){
        CellData cell = new CellData();
        ExtendedValue ext = new ExtendedValue();
        Border bordegrueso = new Border().setStyle("SOLID_MEDIUM");
        cell.setUserEnteredValue(ext.setNumberValue(numero)).setUserEnteredFormat(new CellFormat().setNumberFormat(new NumberFormat().setType("NUMBER")).setVerticalAlignment("MIDDLE").setHorizontalAlignment("CENTER").setBorders(new Borders().setLeft(bordegrueso).setRight(bordegrueso)));
        return cell;
    }
    private RowData createrow(List<CellData> listofcells){
        RowData row = new RowData();
        row.setValues(listofcells);
        return row;
    }
    private List<RowData> createsensorKrows(ArrayList<Dias> data){
        String labelhoras;
        String labelminutos;
        double diaT;
        double horasT;
        List<RowData> rows = new ArrayList<>();
        rows.add(creartitulodedatasensorK());
        //December 30th 1899 30/12/1899
        for(Dias dia:data){
            for(int i =0;i<dia.dametamano();i++){
                diaT =fechadias(dia.damedia(i));
                for(int j = 0; j<dia.damehora(i).dametamano();j++){
                    labelhoras = dia.damehora(i).damehora(j);
                    for(int k = 0;k<dia.damehora(i).dameminutos(j).dametamano();k++){
                        labelminutos = dia.damehora(i).dameminutos(j).dameminuto(k);
                        horasT = fechahorasminutos(labelhoras,labelminutos);
                        for(int m = 0;m<dia.damehora(i).dameminutos(j).damepaquete(k).dametamano();m++){
                            String nombredelpuerto = dia.damehora(i).dameminutos(j).damepaquete(k).damePuerto(m);
                            List<CellData> celdas = new ArrayList<>();
                            celdas.add(createDatecelldata(horasT+diaT));
                            celdas.add(createstringcelldata(Name));
                            switch (nombredelpuerto) {
                                case "P1":
                                    celdas.add(createstringcelldata("Puerto 1"));
                                    break;
                                case "P2":
                                    celdas.add(createstringcelldata("Puerto 2"));
                                    break;
                                case "P3":
                                    celdas.add(createstringcelldata("Puerto 3"));
                                    break;
                                default:
                                    celdas.add(createstringcelldata("Puerto 4"));
                                    break;
                            }
                            celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth()));
                            celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1()));
                            celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2()));
                            rows.add(createrow(celdas));
                        }
                    }
                }
            }
        }
        return rows;
    }
    private List<RowData> createsensorGrows(ArrayList<DiasG> data){
        String labelhora;
        String labelminuto;
        String nombrePuerto;
        double diaT;
        double horasT;
        List<RowData> rows = new ArrayList<>();
        rows.add(creartitulodedatasensorg());
        for(DiasG dia:data){
            for(int i = 0; i<dia.dametamanoG();i++){
                diaT = fechadias(dia.damedia(i));
                for(int j = 0; j<dia.damehora(i).dametamanoG();j++){
                    labelhora = dia.damehora(i).damehora(j);
                    for(int k = 0; k<dia.damehora(i).dameminutos(j).dametamanoG();k++){
                        labelminuto = dia.damehora(i).dameminutos(j).dameminuto(k);
                        horasT = fechahorasminutos(labelhora,labelminuto);
                        for(int m = 0;m<dia.damehora(i).dameminutos(j).damepaquete(k).dametamanoG();m++){
                            nombrePuerto = dia.damehora(i).dameminutos(j).damepaquete(k).damePuerto(m);
                            List<CellData> celdas = new ArrayList<>();
                            celdas.add(createDatecelldata(horasT+diaT));
                            celdas.add(createstringcelldata(Name));
                            switch (nombrePuerto) {
                                case "P1":
                                    celdas.add(createstringcelldata("Puerto 1"));
                                    break;
                                case "P2":
                                    celdas.add(createstringcelldata("Puerto 2"));
                                    break;
                                case "P3":
                                    celdas.add(createstringcelldata("Puerto 3"));
                                    break;
                                default:
                                    celdas.add(createstringcelldata("Puerto 4"));
                                    break;
                            }
                            celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth()));
                            celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1()));
                            celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2()));
                            celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3()));
                            celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameporece()));
                            celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).damevwc()));
                            rows.add(createrow(celdas));
                        }
                    }
                }
            }
        }
        return rows;
    }
    private List<RowData> createsensorSTATErows(ArrayList<diasstate> data){
        //Collections.sort(data,new sortpordias<>());
        double diaT;
        double horasT;
        List<RowData> rows = new ArrayList<>();
        rows.add(creartitulodedataatmosferica());
        String labelhora;

        for(diasstate dia:data){
            for(int i = 0; i<dia.dametamano();i++){
                diaT = fechadias(dia.damedia(i));
                for(int k = 0;k<dia.damehoras(i).dametamano();k++){
                    labelhora = dia.damehoras(i).damehora(k);
                    for(int j = 0; j<dia.damehoras(i).dameminutos(k).dametamano();j++){
                        horasT = fechahorasminutos(labelhora,dia.damehoras(i).dameminutos(k).dameminutos(j));
                        List<CellData> celdas = new ArrayList<>();
                        celdas.add(createDatecelldata(horasT+diaT));
                        celdas.add(createstringcelldata(Name));
                        celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).giveRH()));
                        celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).giveBP()));
                        celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).giveBV()));
                        celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).giveSV()));
                        celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).givedT()));
                        celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).giveWS()));
                        celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).giveiT()));
                        rows.add(createrow(celdas));
                    }
                }
            }
        }
        return rows;
    }
    /*
    private double fechaenserial(String fecha, String hora, String minutos){
        //December 30th 1899 30/12/1899
        Calendar datedesde = Calendar.getInstance();
        Calendar datehasta = Calendar.getInstance();
        datedesde.set(1899,11,30);
        String[] dt = fecha.split("/");
        datehasta.set(Integer.parseInt("20"+dt[0]),Integer.parseInt(dt[1])-1,Integer.parseInt(dt[2]));
        datedesde.get(Calendar.DATE);
        datehasta.get(Calendar.DATE);
        long tiempo = datehasta.getTimeInMillis()-datedesde.getTimeInMillis();
        double dias = (double) tiempo/(24 * 60 * 60 * 1000);
        double horas = (double) (Integer.parseInt(hora)*60+Integer.parseInt(minutos))/1440;
        return dias+horas;
    }
     */
    private double fechadias(String fecha){
        Calendar datedesde = Calendar.getInstance();
        Calendar datehasta = Calendar.getInstance();
        datedesde.set(1899,11,30);
        String[] dt = fecha.split("/");
        datehasta.set(Integer.parseInt("20"+dt[2]),Integer.parseInt(dt[1])-1,Integer.parseInt(dt[0]));
        datedesde.get(Calendar.DATE);
        datehasta.get(Calendar.DATE);
        double tiempo = datehasta.getTimeInMillis()-datedesde.getTimeInMillis();
        return Math.floor(tiempo/(24 * 60 * 60 * 1000));
    }
    private double fechahorasminutos(String hora, String minutos){
        return (double) (Integer.parseInt(hora)*60+Integer.parseInt(minutos))/1440;
    }
    private RowData creartitulodedataatmosferica(){
        String [] titulo = {"Fecha","Nombre","Humedad Relativa (%)","Presión Barométrica (kPa)","Batería (V)","Voltaje solar (V)","Temperatura de bulbo seco (°C)","Velocidad del Viento (m/s)","Temperatura interna (°C)"};
        List<CellData> listtitle = new ArrayList<>();
        for(String name:titulo){
            listtitle.add(createstringtitlecelldata(name));
        }
        return createrow(listtitle);
    }
    private RowData creartitulodedatasensorK(){
        String[] titulo = {"Fecha","Nombre","Puerto","Profundidad del sensor","Potencial Matricial (KPa)","Temperatura (°C)"};
        List<CellData> listtitle = new ArrayList<>();
        for(String name:titulo){
            listtitle.add(createstringtitlecelldata(name));
        }
        return createrow(listtitle);
    }
    private RowData creartitulodedatasensorg(){
        String[] titulo = {"Fecha","Nombre","Puerto","Profundidad del sensor","Humedad del Suelo (raw)","Temperatura del Suelo (°C)","Conductividad Eléctrica del Suelo (uS/cm)","Conductividad Eléctrica del Poro (uS/cm)","Contenido Volumétrico de agua (m3/m3)"};
        List<CellData> listtitle = new ArrayList<>();
        for(String name:titulo){
            listtitle.add(createstringtitlecelldata(name));
        }
        return createrow(listtitle);
    }
    private List<RowData> createsensorKrowsmes(ArrayList<Meses> data){
        //Collections.sort(data, new sortpormes<>());
        String labeldia;
        String labelhoras;
        String labelminutos;
        double diaT;
        double horasT;
        List<RowData> rows = new ArrayList<>();
        rows.add(creartitulodedatasensorK());
        for(Meses mes:data){
            for(Dias dia:mes.dameelarraycompleto()){
                for(int i =0;i<dia.dametamano();i++){
                    labeldia = dia.damedia(i)+"/"+mes.damemes(0);
                    diaT = fechadias(labeldia);
                    for(int j = 0; j<dia.damehora(i).dametamano();j++){
                        labelhoras = dia.damehora(i).damehora(j);
                        for(int k = 0;k<dia.damehora(i).dameminutos(j).dametamano();k++){
                            labelminutos = dia.damehora(i).dameminutos(j).dameminuto(k);
                            horasT = fechahorasminutos(labelhoras,labelminutos);
                            for(int m = 0;m<dia.damehora(i).dameminutos(j).damepaquete(k).dametamano();m++){
                                String nombredelpuerto = dia.damehora(i).dameminutos(j).damepaquete(k).damePuerto(m);
                                List<CellData> celdas = new ArrayList<>();
                                celdas.add(createDatecelldata(horasT+diaT));
                                celdas.add(createstringcelldata(Name));
                                switch (nombredelpuerto) {
                                    case "P1":
                                        celdas.add(createstringcelldata("Puerto 1"));
                                        break;
                                    case "P2":
                                        celdas.add(createstringcelldata("Puerto 2"));
                                        break;
                                    case "P3":
                                        celdas.add(createstringcelldata("Puerto 3"));
                                        break;
                                    default:
                                        celdas.add(createstringcelldata("Puerto 4"));
                                        break;
                                }
                                celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth()));
                                celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1()));
                                celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2()));
                                rows.add(createrow(celdas));
                            }
                        }
                    }
                }
            }
        }
        return rows;
    }
    private List<RowData> createsensorGrowsmes(ArrayList<MesesG> data){
        //Collections.sort(data, new sortpormes<>());
        String labeldia;
        String labelhora;
        String labelminuto;
        String nombrePuerto;
        double diaT;
        double horasT;
        List<RowData> rows = new ArrayList<>();
        rows.add(creartitulodedatasensorg());
        for(MesesG mes:data){
            for(DiasG dia:mes.damearraydedias()){
                for(int i = 0; i<dia.dametamanoG();i++){
                    labeldia = dia.damedia(i)+"/"+mes.damemes(0);
                    diaT = fechadias(labeldia);
                    for(int j = 0; j<dia.damehora(i).dametamanoG();j++){
                        labelhora = dia.damehora(i).damehora(j);
                        for(int k = 0; k<dia.damehora(i).dameminutos(j).dametamanoG();k++){
                            labelminuto = dia.damehora(i).dameminutos(j).dameminuto(k);
                            horasT = fechahorasminutos(labelhora,labelminuto);
                            for(int m = 0;m<dia.damehora(i).dameminutos(j).damepaquete(k).dametamanoG();m++){
                                nombrePuerto = dia.damehora(i).dameminutos(j).damepaquete(k).damePuerto(m);
                                List<CellData> celdas = new ArrayList<>();
                                celdas.add(createDatecelldata(horasT+diaT));
                                celdas.add(createstringcelldata(Name));
                                switch (nombrePuerto) {
                                    case "P1":
                                        celdas.add(createstringcelldata("Puerto 1"));
                                        break;
                                    case "P2":
                                        celdas.add(createstringcelldata("Puerto 2"));
                                        break;
                                    case "P3":
                                        celdas.add(createstringcelldata("Puerto 3"));
                                        break;
                                    default:
                                        celdas.add(createstringcelldata("Puerto 4"));
                                        break;
                                }
                                celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameDepth()));
                                celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV1()));
                                celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV2()));
                                celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameV3()));
                                celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).dameporece()));
                                celdas.add(createNumberscelldata(dia.damehora(i).dameminutos(j).damepaquete(k).damedata(m).damevwc()));
                                rows.add(createrow(celdas));
                            }
                        }
                    }
                }
            }
        }

       return rows;
    }
    private List<RowData> createsensorSTATErowsmes(ArrayList<mesesstate> data){
        //Collections.sort(data, new sortpormes<>());
        String labeldia;
        String labelhora;
        double diaT;
        double horasT;
        List<RowData> rows = new ArrayList<>();
        rows.add(creartitulodedataatmosferica());
        for(mesesstate mes: data) {
            for (diasstate dia : mes.damearraydedias()) {
                for (int i = 0; i < dia.dametamano(); i++) {
                    labeldia = dia.damedia(i)+ "/" +mes.damemeses(0) ;
                    diaT = fechadias(labeldia);
                    for (int k = 0; k < dia.damehoras(i).dametamano(); k++) {
                        labelhora = dia.damehoras(i).damehora(k);
                        for (int j = 0; j < dia.damehoras(i).dameminutos(k).dametamano(); j++) {
                            horasT = fechahorasminutos(labelhora,dia.damehoras(i).dameminutos(k).dameminutos(j));
                            List<CellData> celdas = new ArrayList<>();
                            celdas.add(createDatecelldata(horasT+diaT));
                            celdas.add(createstringcelldata(Name));
                            celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).giveRH()));
                            celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).giveBP()));
                            celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).giveBV()));
                            celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).giveSV()));
                            celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).givedT()));
                            celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).giveWS()));
                            celdas.add(createNumberscelldata(dia.damehoras(i).dameminutos(k).damepaquete(j).giveiT()));
                            rows.add(createrow(celdas));
                        }
                    }
                }
            }
        }
        return rows;
    }
}
