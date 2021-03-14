package com.example.neolink_app.viewmodels;

import com.example.neolink_app.clases.clasesdereporte.InfoParaReporte;
import com.example.neolink_app.clases.clasesparaformargraficos.InfoParaGraficos;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridData;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
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
        for(InfoParaReporte reporte:infoParaReporte){
            ArrayList<List<RowData>> datosporsensor = reporte.managedias();
            rowsK.addAll(datosporsensor.get(0));
            rowsG.addAll(datosporsensor.get(1));
            rowsState.addAll(datosporsensor.get(2));
        }
        if(rowsK.size()>1){
            List<GridData> gridK = new ArrayList<>();
            gridK.add(creategrid(rowsK,0,0));
            paginas.add(new Sheet().setProperties(new SheetProperties().setTitle("Neolink:"+nombredehoja+" Sensor:TEROS 21")).setData(gridK));
        }
        if(rowsG.size()>1){
            List<GridData> gridG = new ArrayList<>();
            gridG.add(creategrid(rowsG,0,0));
            paginas.add(new Sheet().setProperties(new SheetProperties().setTitle("Neolink:"+nombredehoja+" Sensor:TEROS 12")).setData(gridG));
        }
        if(rowsState.size()>1){
            List<GridData> gridState = new ArrayList<>();
            gridState.add(creategrid(rowsState,0,0));
            paginas.add(new Sheet().setProperties(new SheetProperties().setTitle("Neolink:"+nombredehoja+" Sensor:ATMOS")).setData(gridState));
        }
        // me falta agregar las tablas
        return paginas;
    }
    private Sheet crearpaginadedatos(List<GridData> data,String nombredehoja){
        return new Sheet().setProperties(new SheetProperties().setTitle(nombredehoja)).setData(data);
    }
    public Task<String> updateFile(String SPREADSHEET_ID) {
        return Tasks.call(mExecutor,() -> {
            List<ValueRange> data = new ArrayList<>();
            data.add(new ValueRange().setRange("A1").setValues(Arrays.asList(
                    Arrays.asList("Expenses January"),
                    Arrays.asList("books", "30"),
                    Arrays.asList("pens", "10"),
                    Arrays.asList("Expenses February"),
                    Arrays.asList("clothes", "20"),
                    Arrays.asList("shoes", "5"))));
            /*
            data.add(new ValueRange().setRange("Sheet2!A1").setValues(Arrays.asList(
                    Arrays.asList("hola"),
                    Arrays.asList("books1", "3011"),
                    Arrays.asList("pens2", "1022"),
                    Arrays.asList("hola2"),
                    Arrays.asList("clothes3", "2033"),
                    Arrays.asList("shoes4", "544"))));

             */
            BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
                    .setValueInputOption("USER_ENTERED")
                    .setData(data);
            BatchUpdateValuesResponse batchResult = mSheetsService.spreadsheets().values()
                    .batchUpdate(SPREADSHEET_ID, batchBody)
                    .execute();
            if(batchResult==null){
                throw new IOException("Null result when requesting file updating damn");
            }
            return batchResult.getSpreadsheetId();
        });
    }

    private CellData createcelldata(String data){
        CellData cells = new CellData();
        ExtendedValue ext = new ExtendedValue();
        cells.setUserEnteredValue(ext.setStringValue(data));
        return cells;
    }
    private RowData createrow(List<CellData> listofcells){
        RowData row = new RowData();
        row.setValues(listofcells);
        return row;
    }
    private GridData creategrid(List<RowData> rows, int startRow, int startcollum){
        GridData maingrid = new GridData();
        maingrid.setStartRow(startRow);
        maingrid.setStartColumn(startcollum);
        maingrid.setRowData(rows);
        return maingrid;
    }
    private Sheet createsensorsdatasheet(){
        Sheet pag = new Sheet();

        return pag;
    }
}
