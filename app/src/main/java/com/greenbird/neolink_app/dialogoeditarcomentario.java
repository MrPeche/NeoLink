package com.greenbird.neolink_app;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.R;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class dialogoeditarcomentario extends AppCompatDialogFragment {
    private TextInputEditText mensajecomentario;
    private MasterDrawerViewModel archi;
    private String fecha;
    private String hora;
    private String comentario;

    public dialogoeditarcomentario() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialogoeditarcomentario,null);
        mensajecomentario = view.findViewById(R.id.mensajeparaeditar);
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        String[] mensaje = comentario.split("5TP5");
        String[] division = mensaje[2].split("5MS5");
        mensajecomentario.setText(division[0]);
        builder.setView(view).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                archi.dilaogoeditarmensajeborrar();
            }
        }).setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!mensajecomentario.getText().toString().equals("")){
                    String nuevocomentario = mensaje[0]+"5TP5"+mensaje[1]+"5TP5"+mensajecomentario.getText().toString()+"5MS5"+division[1];
                    archi.editarmensajes(fecha,hora,nuevocomentario);
                    archi.dilaogoeditarmensajeborrar();
                }
            }
        });

        return builder.create();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.fecha  =dialogoeditarcomentarioArgs.fromBundle(getArguments()).getFecha();
            this.hora   =dialogoeditarcomentarioArgs.fromBundle(getArguments()).getHora();
            this.comentario=dialogoeditarcomentarioArgs.fromBundle(getArguments()).getContenido();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialogoeditarcomentario, container, false);
    }
}