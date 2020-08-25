package com.example.neolink_app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class dialogfechagraf extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private Spinner parametros;
    private CalendarView calendario;
    private CalendarView calendario2;
    private LinearLayout lineadesde;
    private LinearLayout lineahasta;
    private TextView textoHasta;
    private TextView textoDesde;
    private ArrayAdapter<String> adapterS;
    private String [] lista ={"Hoy","Este mes","Este año","Elige una fecha"};
    private String [] fechaelegida = {"","-",""};
    private Calendar horario = Calendar.getInstance();
    //private NavController navController = NavHostFragment.findNavController(this);
    private String hasta ="";
    private String desde ="";
    private int wichone;
    private MasterDrawerViewModel archi;


    public dialogfechagraf() {
        // Required empty public constructor
    }

    public static dialogfechagraf newInstance() {
        dialogfechagraf fragment = new dialogfechagraf();
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialogfechagraf,null);
        parametros = view.findViewById(R.id.spinnerdialogfecha);
        calendario = view.findViewById(R.id.calendarViewdialogfechadesde);
        calendario2 = view.findViewById(R.id.calendarViewdialoghasta);
        lineadesde = view.findViewById(R.id.lineardesde);
        lineahasta = view.findViewById(R.id.linearhasta);
        textoDesde = view.findViewById(R.id.desdetext);
        textoHasta = view.findViewById(R.id.hastatext);
        setTexts();
        setcalendarprop();

        adapterS = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, lista); // Aqui puedo editar el estilo
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parametros.setAdapter(adapterS);
        parametros.setSelection(0);
        parametros.setOnItemSelectedListener(this);
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaspinner);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        //spinner.setAdapter(adapter);
        String valorinicial = archi.datechoosen.getValue();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if(valorinicial!=null) {
            if(valorinicial.equals(lista[0])){
                parametros.setSelection(0);
            } else if(valorinicial.equals(lista[1])){
                parametros.setSelection(1);
            } else if(valorinicial.equals(lista[2])){
                parametros.setSelection(2);
            } else {
                lista[3] = valorinicial;
                try {
                    Date day = sdf.parse(desde);
                    updatecalendars(desde,0);
                    Date day2 = sdf.parse(hasta);
                    updatecalendars(hasta,1);
                } catch (ParseException ignored) { }
                parametros.setSelection(3);
            }
        }

        builder.setView(view).setNegativeButton(R.string.cancelardialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton(R.string.aceptardialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String opinion = parametros.getSelectedItem().toString();
                if(!lista[3].equals(opinion)){
                    archi.savedate(opinion);
                }else {
                    if (desde != null)
                        archi.savedate(desde);
                }
            }
        });
        //codigoneolinknuevo = view.findViewById(R.id.ticketdialog);
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String spc = "/";
                String muestra = "";
                muestra = fixdate(dayOfMonth)+spc+fixdate(month+1)+spc+(year%100);
                adapterS.notifyDataSetChanged();
                //String data = (year%100)+spc+fixdate(month+1)+spc+fixdate(dayOfMonth);
                String data = (year)+spc+fixdate(month+1)+spc+fixdate(dayOfMonth);
                setoneText(muestra,0);
                desde = data;
                //fechaelegida[0] = muestra;
                try {
                    updatefechaelegida(muestra,0);
                } catch (ParseException ignored) {}
                updatethelist();
            }
        });
        calendario2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String spc = "/";
                String muestra = "";
                muestra = fixdate(dayOfMonth)+spc+fixdate(month+1)+spc+(year%100);
                adapterS.notifyDataSetChanged();
                //String data = (year%100)+spc+fixdate(month+1)+spc+fixdate(dayOfMonth);
                String data = (year)+spc+fixdate(month+1)+spc+fixdate(dayOfMonth);
                setoneText(muestra,1);
                hasta = data;
                //fechaelegida[2]=muestra;
                try {
                    updatefechaelegida(muestra,1);
                } catch (ParseException ignored) {}
                updatethelist();
            }
        });
        return builder.create();
    }
    private String fixdate(int numero){
        String resultado;
        if(numero<10){
            resultado = "0"+numero;
        } else resultado = Integer.toString(numero);
        return resultado;
    }
    private void updatecalendars(String A,int calendar){
        Calendar cal = Calendar.getInstance();
        int[] fecha = crearparametros(A);
        cal.set(fecha[0],fecha[1],fecha[2]);
        if(calendar==0){
            calendario.setDate(cal.getTimeInMillis());
        } else {
            calendario2.setDate(cal.getTimeInMillis());

        }
    }
    private int[] crearparametros(String date){
        String[] lista = date.split("/");
        return new int[]{Integer.parseInt(lista[0]),Integer.parseInt(lista[1]),Integer.parseInt(lista[2])};
    }

    private ArrayList<String> crearlalista(){
        ArrayList<String> resultado = new ArrayList<>();
        resultado.add("Hoy");
        resultado.add("Este mes");
        resultado.add("Este año");
        resultado.add("Elige una fecha");
        return resultado;
    }
    private void setcalendarprop(){
        calendario.setVisibility(View.GONE);
        Calendar iniciocalendario = GregorianCalendar.getInstance();
        iniciocalendario.set(2020,0,1);
        calendario.setMinDate(iniciocalendario.getTimeInMillis());
        calendario2.setVisibility(View.GONE);
        calendario2.setMinDate(iniciocalendario.getTimeInMillis());
    }
    private void setTexts(){
        lineahasta.setVisibility(View.GONE);
        lineadesde.setVisibility(View.GONE);
    }
    private void popupcalendar(){
        calendario.setVisibility(View.VISIBLE);
        calendario2.setVisibility(View.VISIBLE);
    }
    private void popupTexts(){
        lineadesde.setVisibility(View.VISIBLE);
        lineahasta.setVisibility(View.VISIBLE);
    }
    private String traductordedia(int year, int month, int day){
        year = year%100;
        month = month+1;
        return day +"/" + month + "/" +year;
    }
    private void setoneText(String texto,int opcion){
        if(opcion==0){
            textoDesde.setText(texto);
        } else {
            textoHasta.setText(texto);
        }
    }
    private void updatethelist(){
        String code = "";
        String spc = " ";
        if(!fechaelegida[0].equals("")&&!fechaelegida[2].equals("")){
            code = fechaelegida[0]+spc+fechaelegida[1]+spc+fechaelegida[2];
        } else{
            if(!fechaelegida[0].equals("")){
                code = fechaelegida[0];
            } else if(!fechaelegida[2].equals("")){
                code = fechaelegida[2];
            }
        }
        lista[3] = code;
    }
    private void updatefechaelegida(String a, int type) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date A = sdf.parse(a);
        if(type==0){
          if(!fechaelegida[2].equals("")){
              Date B = sdf.parse(fechaelegida[2]);
              String b = fechaelegida[2];
              if(A.before(B)){
                  fechaelegida[0] = a;
              } else {
                  fechaelegida[0] = fechaelegida[2];
                  updatecalendars(b,0);
                  fechaelegida[2] = a;
                  updatecalendars(a,1);
              }
          } else {
              fechaelegida[0] = a;
          }
        } else {
            if(!fechaelegida[0].equals("")){
                Date B = sdf.parse(fechaelegida[0]);
                String b = fechaelegida[2];
                if(A.after(B)){
                    fechaelegida[2] = a;
                } else {
                    fechaelegida[2] = fechaelegida[0];
                    updatecalendars(b,1);
                    fechaelegida[0] = a;
                    updatecalendars(a,0);
                }
            }
        }
    }
    private void depopup(){
        if(calendario.getVisibility()!=View.GONE){
            calendario.setVisibility(View.GONE);
            calendario2.setVisibility(View.GONE);
            lineadesde.setVisibility(View.GONE);
            lineahasta.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==3){
            popupTexts();
            popupcalendar();
        } else {
            depopup();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
