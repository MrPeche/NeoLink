package com.example.neolink_app;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;

import java.util.ArrayList;


public class dialogfechagraf extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private Spinner parametros;
    private CalendarView calendario;
    private ArrayList<String> listaspinner = new ArrayList<>();


    public dialogfechagraf() {
        // Required empty public constructor
    }

    /*
    public static dialogfechagraf newInstance(String param1, String param2) {
        dialogfechagraf fragment = new dialogfechagraf();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
     */

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialogfechagraf,null);
        parametros = view.findViewById(R.id.spinnerdialogfecha);
        listaspinner = crearlalista();
        ArrayAdapter<String> adapterS = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, listaspinner);
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parametros.setAdapter(adapterS);
        parametros.setSelection(0);
        parametros.setOnItemSelectedListener(this);
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaspinner);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        //spinner.setAdapter(adapter);

        builder.setView(view).setNegativeButton(R.string.cancelardialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton(R.string.aceptardialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //codigoneolinknuevo = view.findViewById(R.id.ticketdialog);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}