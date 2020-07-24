package com.example.neolink_app;

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
import android.widget.Spinner;

import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class dialogfechagraf extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private Spinner parametros;
    private CalendarView calendario;
    private ArrayAdapter<String> adapterS;
    private String [] lista ={"Hoy","Este mes","Este año","Elige una fecha"};
    private Calendar horario = Calendar.getInstance();
    //private NavController navController = NavHostFragment.findNavController(this);
    private String result ="";
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
        calendario = view.findViewById(R.id.calendarViewdialogfecha);

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
        archi.datechoosen.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null) lista[3] = s;
            }
        });
        builder.setView(view).setNegativeButton(R.string.cancelardialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton(R.string.aceptardialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(result!=null||result.length()!=0)
                    archi.savedate(result);
            }
        });
        //codigoneolinknuevo = view.findViewById(R.id.ticketdialog);
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String spc = "/";
                String muestra = "";
                muestra = dayOfMonth+spc+(month+1)+spc+(year%100);
                lista[3] = muestra;
                adapterS.notifyDataSetChanged();
                String data = (year%100)+spc+(month+1)+spc+dayOfMonth;
                result = data;


            }
        });
        return builder.create();
    }

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialogfechagraf, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        parametros = view.findViewById(R.id.spinnerdialogfecha);
    }

     */

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
    }
    private void popupcalendar(){
        calendario.setVisibility(View.VISIBLE);
    }
    private String traductordedia(int year, int month, int day){
        year = year%100;
        month = month+1;
        return day +"/" + month + "/" +year;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==3){
            popupcalendar();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
