package com.example.neolink_app.viewmodels;

import com.example.neolink_app.clases.clasesdereporte.InfoParaReporte;
import com.example.neolink_app.clases.clasesparaformargraficos.InfoParaGraficos;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BasicChartAxis;
import com.google.api.services.sheets.v4.model.BasicChartDomain;
import com.google.api.services.sheets.v4.model.BasicChartSeries;
import com.google.api.services.sheets.v4.model.BasicChartSpec;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.CellFormat;
import com.google.api.services.sheets.v4.model.ChartData;
import com.google.api.services.sheets.v4.model.ChartSourceRange;
import com.google.api.services.sheets.v4.model.ChartSpec;
import com.google.api.services.sheets.v4.model.EmbeddedChart;
import com.google.api.services.sheets.v4.model.EmbeddedObjectPosition;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridCoordinate;
import com.google.api.services.sheets.v4.model.GridData;
import com.google.api.services.sheets.v4.model.GridProperties;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.OverlayPosition;
import com.google.api.services.sheets.v4.model.PivotGroup;
import com.google.api.services.sheets.v4.model.PivotGroupValueMetadata;
import com.google.api.services.sheets.v4.model.PivotTable;
import com.google.api.services.sheets.v4.model.PivotValue;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.TextPosition;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class clasedeusosheets {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final Sheets mSheetsService;

    public clasedeusosheets(Sheets servicio){
        mSheetsService = servicio;
    }


    public Task<String> createFileWithData(ArrayList<ArrayList<InfoParaReporte>> infoParaReporte, ArrayList<ArrayList<String>> dispositivos,ArrayList<Integer> horas,int opcion){

        return Tasks.call(mExecutor, () -> {
            List<Sheet> paginas = new ArrayList<>();
            for(int i=0;i<infoParaReporte.size();i++){
                paginas.addAll(creaciondepaginas(infoParaReporte.get(i),dispositivos.get(i).get(0)));
            }
            String nombredelaopcion;
            if(opcion==0){
                nombredelaopcion="Quincenal";
            }else if(opcion==1){
                nombredelaopcion="Mensual";
            }else nombredelaopcion="Anual";
            Spreadsheet spreadsheet = new Spreadsheet()
                    .setProperties(new SpreadsheetProperties()
                            .setTitle("Reporte Neolink "+nombredelaopcion+" del "+horas.get(2)+"/"+horas.get(1)+"/"+horas.get(0)+" "+horas.get(3)+":"+horas.get(4)+":"+horas.get(5))).setSheets(paginas);
            spreadsheet = mSheetsService.spreadsheets().create(spreadsheet)
                    .setFields("spreadsheetId")
                    .execute();
            if(spreadsheet==null){
                throw new IOException("Null result when requesting file creation damn");
            }
            return spreadsheet.getSpreadsheetId();
        });
    }

    private List<Sheet> creaciondepaginas(ArrayList<InfoParaReporte> infoParaReporte,String nombredehoja){
        List<Sheet> paginas = new ArrayList<>();
        List<RowData> rowsK = new ArrayList<>();
        List<RowData> rowsG = new ArrayList<>();
        List<RowData> rowsState = new ArrayList<>();
        int index=0;
        for(InfoParaReporte reporte:infoParaReporte){
            ArrayList<List<RowData>> datosporsensor = reporte.managedias();
            rowsK.addAll(datosporsensor.get(0));
            rowsG.addAll(datosporsensor.get(1));
            rowsState.addAll(datosporsensor.get(2));
        }
        if(rowsK.size()>1){
            List<GridData> gridK = new ArrayList<>();
            gridK.add(creategrid(rowsK,0,0));
            paginas.add(new Sheet().setProperties(new SheetProperties().setSheetId(index).setTitle("Neolink:"+nombredehoja+" Sensor de Potencial Matricial y Temperatura").setGridProperties(new GridProperties().setFrozenRowCount(1).setFrozenColumnCount(1).setHideGridlines(true))).setData(gridK));
            index++;
            paginas.add(createpivottablesheet("k",paginas.size()-1,rowsK.size(),6,"Neolink:"+nombredehoja+" Sensor de Potencial Matricial y Temperatura TABLA DINÁMICA",index));
            index++;
            paginas.add(createchartsheet("k",paginas.size()-1, rowsK.size(),"Neolink:"+nombredehoja+" Sensor de Potencial Matricial y Temperatura GRÁFICOS",index,infoParaReporte.size()));
            index++;
        }
        if(rowsG.size()>1){
            List<GridData> gridG = new ArrayList<>();
            gridG.add(creategrid(rowsG,0,0));
            paginas.add(new Sheet().setProperties(new SheetProperties().setSheetId(index).setTitle("Neolink:"+nombredehoja+" Sensor de Humedad, CE, Temperatura").setGridProperties(new GridProperties().setFrozenRowCount(1).setFrozenColumnCount(1).setHideGridlines(true))).setData(gridG));
            index++;
            paginas.add(createpivottablesheet("g",paginas.size()-1,rowsG.size(),9,"Neolink:"+nombredehoja+" Sensor de Humedad, CE, Temperatura TABLA DINÁMICA",index));
            index++;
            paginas.add(createchartsheet("g",paginas.size()-1, rowsG.size(),"Neolink:"+nombredehoja+" Sensor de Humedad, CE, Temperatura GRÁFICOS",index,infoParaReporte.size()));
            index++;
        }
        if(rowsState.size()>1){
            List<GridData> gridState = new ArrayList<>();
            gridState.add(creategrid(rowsState,0,0));
            paginas.add(new Sheet().setProperties(new SheetProperties().setSheetId(index).setTitle("Neolink:"+nombredehoja+" Estación Metereológica").setGridProperties(new GridProperties().setFrozenRowCount(1).setFrozenColumnCount(1).setHideGridlines(true))).setData(gridState));
            index++;
            paginas.add(createpivottablesheet("state",paginas.size()-1,rowsState.size(),9,"Neolink:"+nombredehoja+" Estación Metereológica TABLA DINÁMICA",index));
            index++;
            paginas.add(createchartsheet("state",paginas.size()-1, rowsState.size(),"Neolink:"+nombredehoja+" Estación Metereológica GRÁFICOS",index,infoParaReporte.size()));
        }
        // me falta agregar las tablas
        return paginas;
    }

    private Sheet createpivottablesheet(String tipo,int sheetid,int endrow,int endcol,String nombredehoja,int index){
        List<GridData> gridState = new ArrayList<>();
        List<RowData> rows = new ArrayList<>();
        List<CellData> celdas = new ArrayList<>();
        celdas.add(crealatabladinamica(tipo,sheetid,endrow,endcol));
        rows.add(new RowData().setValues(celdas));
        gridState.add(creategrid(rows,0,0));
        return new Sheet().setProperties(new SheetProperties().setSheetId(index).setTitle(nombredehoja).setGridProperties(new GridProperties().setHideGridlines(true))).setData(gridState);
    }
    private CellData crealatabladinamica(String tipo,int sheetid,int endrow,int endcol){
        CellData celda = new CellData().setUserEnteredFormat(new CellFormat().setVerticalAlignment("MIDDLE").setWrapStrategy("WRAP").setHorizontalAlignment("CENTER"));
        if(tipo.equals("k")){
            List<PivotGroup> columns = new ArrayList<>();
            columns.add( new PivotGroup().setSourceColumnOffset(1).setShowTotals(false).setSortOrder("ASCENDING"));
            columns.add( new PivotGroup().setSourceColumnOffset(2).setShowTotals(false).setSortOrder("ASCENDING"));
            List<PivotValue> values = new ArrayList<>();
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(3));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(4));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(5));
            celda.setPivotTable(new PivotTable().setSource(new GridRange().setSheetId(sheetid).setStartColumnIndex(0).setStartRowIndex(0).setEndColumnIndex(endcol).setEndRowIndex(endrow)).setRows(Collections.singletonList(new PivotGroup().setSourceColumnOffset(0).setShowTotals(false).setSortOrder("ASCENDING"))).setColumns(columns).setValues(values));
            return celda;
        } else if(tipo.equals("g")){
            List<PivotGroup> columns = new ArrayList<>();
            columns.add( new PivotGroup().setSourceColumnOffset(1).setShowTotals(false).setSortOrder("ASCENDING"));
            columns.add( new PivotGroup().setSourceColumnOffset(2).setShowTotals(false).setSortOrder("ASCENDING"));
            List<PivotValue> values = new ArrayList<>();
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(3));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(4));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(5));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(6));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(7));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(8));
            celda.setPivotTable(new PivotTable().setSource(new GridRange().setSheetId(sheetid).setStartColumnIndex(0).setStartRowIndex(0).setEndColumnIndex(endcol).setEndRowIndex(endrow)).setRows(Collections.singletonList(new PivotGroup().setSourceColumnOffset(0).setShowTotals(false).setSortOrder("ASCENDING"))).setColumns(columns).setValues(values));
            return celda;
        } else{ //Este es el ambiental
            List<PivotGroup> columns = new ArrayList<>();
            columns.add( new PivotGroup().setSourceColumnOffset(1).setShowTotals(false).setSortOrder("ASCENDING"));
            List<PivotValue> values = new ArrayList<>();
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(2));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(3));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(4));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(5));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(6));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(7));
            values.add(new PivotValue().setSummarizeFunction("SUM").setSourceColumnOffset(8));
            celda.setPivotTable(new PivotTable().setSource(new GridRange().setSheetId(sheetid).setStartColumnIndex(0).setStartRowIndex(0).setEndColumnIndex(endcol).setEndRowIndex(endrow)).setRows(Collections.singletonList(new PivotGroup().setSourceColumnOffset(0).setShowTotals(false).setSortOrder("ASCENDING"))).setColumns(columns).setValues(values));
            return celda;
        }
    }

    private List<BasicChartAxis> crearsecuenciadevaloresparaaxis(){
        List<BasicChartAxis> charstaxis = new ArrayList<>();
        charstaxis.add(new BasicChartAxis().setTitle("Fechas").setPosition("BOTTOM_AXIS"));
        charstaxis.add(new BasicChartAxis().setTitle("Valores de los sensores por puertos y neolinks").setPosition("LEFT_AXIS"));
        return  charstaxis;
    }
    private List<BasicChartSeries> crearsecuenciadevaloresparaseries(int sourceid,int rowend,int distanciaentrevalores,int numeroderepeteciones,int startrowindex, int startcolumnindex){
        List<BasicChartSeries> chartseries = new ArrayList<>();
        for(int i=0;i<numeroderepeteciones;i++){
            chartseries.add(new BasicChartSeries().setTargetAxis("LEFT_AXIS").setSeries(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(startrowindex).setEndRowIndex(rowend).setStartColumnIndex(startcolumnindex+(distanciaentrevalores*i)).setEndColumnIndex(startcolumnindex+1+(distanciaentrevalores*i)))))));
        }
        return chartseries;
    }
    private EmbeddedChart createPotencialMatricial(int id,int rowend,int index,int sourceid,int numerodedispositivos){

        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,6,4*numerodedispositivos,4,2)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(4).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial del Potencial Matricial (KPa)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(0))))
                ;
    }
    private EmbeddedChart createTemperaturaK(int id,int rowend,int index,int sourceid,int numerodedispositivos){
        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,6,4*numerodedispositivos,4,3)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(4).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial del Potencial Matricial (KPa)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(19))))
                ;
    }

    private EmbeddedChart createcharttemperaturadelsueloG(int id,int rowend,int index,int sourceid,int numerodedispositivos){

        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,6,4*numerodedispositivos,4,3)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(4).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial de la Temperatura del Suelo (°C)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(0))))
                ;
    }
    private EmbeddedChart createchartconductividadelectricadelsuelo(int id,int rowend,int index,int sourceid,int numerodedispositivos){

        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,6,4*numerodedispositivos,4,4)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(4).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial de la Conductividad Eléctrica del Suelo (uS/cm)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(19))))
                ;
    }
    private EmbeddedChart createchartconductividadelectricadelporo(int id,int rowend,int index,int sourceid,int numerodedispositivos){

        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,6,4*numerodedispositivos,4,5)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(4).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial de la Conductividad Eléctrica del Poro (uS/cm)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(38))))
                ;
    }
    private EmbeddedChart createchartcontenidovolumetricodeagua(int id,int rowend,int index,int sourceid,int numerodedispositivos){

        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,6,4*numerodedispositivos,4,6)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(4).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial del Contenido Volumétrico de agua (m3/m3)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(57))))
                ;
    }
    private EmbeddedChart createcharthumedadrelativa(int id,int rowend,int index,int sourceid,int numerodedispositivos){

        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,7,numerodedispositivos,3,1)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(3).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial de la Humedad Relativa (%)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(0))))
                ;
    }
    private EmbeddedChart createchartpresionbarometrica(int id,int rowend,int index,int sourceid,int numerodedispositivos){

        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,7,numerodedispositivos,3,2)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(3).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial de la Presión Barométrica (kPa)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(20))))
                ;
    }
    private EmbeddedChart createchartbateria(int id,int rowend,int index,int sourceid,int numerodedispositivos){

        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,7,numerodedispositivos,3,3)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(3).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial de la Bateria (V)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(40))))
                ;
    }
    private EmbeddedChart createchartvoltajesolar(int id,int rowend,int index,int sourceid,int numerodedispositivos){

        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,7,numerodedispositivos,3,4)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(3).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial del Voltaje solar (V)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(60))))
                ;
    }
    private EmbeddedChart createcharttemperaturadebulboseco(int id,int rowend,int index,int sourceid,int numerodedispositivos){

        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,7,numerodedispositivos,3,5)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(3).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial de la Temperatura de bulbo seco (°C)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(80))))
                ;
    }
    private EmbeddedChart createchartvelocidaddelviento(int id,int rowend,int index,int sourceid,int numerodedispositivos){

        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,7,numerodedispositivos,3,6)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(3).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial de la Velocidad del Viento (m/s)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(100))))
                ;
    }
    private EmbeddedChart createcharttemperaturainterna(int id,int rowend,int index,int sourceid,int numerodedispositivos){

        return new EmbeddedChart().setSpec(new ChartSpec()
                .setBasicChart(new BasicChartSpec().setHeaderCount(1).setChartType("LINE").setLegendPosition("BOTTOM_LEGEND").setAxis(crearsecuenciadevaloresparaaxis()).setSeries(crearsecuenciadevaloresparaseries(sourceid,rowend,7,numerodedispositivos,3,7)).setDomains(Collections.singletonList(new BasicChartDomain().setDomain(new ChartData().setSourceRange(new ChartSourceRange().setSources(Collections.singletonList(new GridRange().setSheetId(sourceid).setStartRowIndex(3).setEndRowIndex(rowend).setStartColumnIndex(0).setEndColumnIndex(1))))))))
                .setTitle("Historial de la Temperatura interna (°C)")
                .setTitleTextPosition(new TextPosition().setHorizontalAlignment("CENTER"))
                .setMaximized(false))
                .setPosition(new EmbeddedObjectPosition().setOverlayPosition(new OverlayPosition().setAnchorCell(new GridCoordinate().setSheetId(index).setColumnIndex(0).setRowIndex(120))))
                ;
    }
    private Sheet createchartsheet(String type,int id,int rowend,String title,int index,int numerodedispositivos){
        List<EmbeddedChart> graf = new ArrayList<>();
        int idchart = 0;
        if(type.equals("k")){
            graf.add(createPotencialMatricial(idchart,rowend,index,id,numerodedispositivos));
            idchart++;
            graf.add(createTemperaturaK(idchart,rowend,index,id,numerodedispositivos));
        } else if(type.equals("g")){
            //graf.add(createcharthumedaddelsuelo(idchart,rowend,index,id,numerodedispositivos));
            //idchart++;
            graf.add(createcharttemperaturadelsueloG(idchart,rowend,index,id,numerodedispositivos));
            idchart++;
            graf.add(createchartconductividadelectricadelsuelo(idchart,rowend,index,id,numerodedispositivos));
            idchart++;
            graf.add(createchartconductividadelectricadelporo(idchart,rowend,index,id,numerodedispositivos));
            idchart++;
            graf.add(createchartcontenidovolumetricodeagua(idchart,rowend,index,id,numerodedispositivos));
        } else {
            graf.add(createcharthumedadrelativa(idchart,rowend,index,id,numerodedispositivos));
            idchart++;
            graf.add(createchartpresionbarometrica(idchart,rowend,index,id,numerodedispositivos));
            idchart++;
            graf.add(createchartbateria(idchart,rowend,index,id,numerodedispositivos));
            idchart++;
            graf.add(createchartvoltajesolar(idchart,rowend,index,id,numerodedispositivos));
            idchart++;
            graf.add(createcharttemperaturadebulboseco(idchart,rowend,index,id,numerodedispositivos));
            idchart++;
            graf.add(createchartvelocidaddelviento(idchart,rowend,index,id,numerodedispositivos));
            idchart++;
            graf.add(createcharttemperaturainterna(idchart,rowend,index,id,numerodedispositivos));
        }
        return new Sheet().setProperties(new SheetProperties().setTitle(title).setSheetId(index)).setCharts(graf);
    }
    private GridData creategrid(List<RowData> rows, int startRow, int startcollum){
        GridData maingrid = new GridData();
        maingrid.setStartRow(startRow);
        maingrid.setStartColumn(startcollum);
        maingrid.setRowData(rows);
        return maingrid;
    }
}
