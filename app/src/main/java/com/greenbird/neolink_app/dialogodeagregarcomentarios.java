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


public class dialogodeagregarcomentarios extends AppCompatDialogFragment {
    private TextInputEditText mensajecomentario;
    private MasterDrawerViewModel archi;
    private String referencia;

    public dialogodeagregarcomentarios() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialogodeagregarcomentarios,null);
        mensajecomentario = view.findViewById(R.id.mensajecomentario);
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        builder.setView(view).setNegativeButton(R.string.cancelardialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton(R.string.aceptardialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mensajecomentario.getText().toString().length()!=0) {
                    //archi.agregarneolink(codigoneolinknuevo.getText().toString());
                    //neolink+"5HEISSEN5"+time[0]+"5ZEIT5"+time[1]+"5ZEIT5"+sensor+"5TYP5"+var
                    archi.guardarcomentarioneolink(decodificarnombre(referencia),referencia+"5TYP5"+mensajecomentario.getText().toString());
                }
            }
        });

        return builder.create();
    }
    private String decodificarnombre(String line){
        String[] resultado = line.split("5HEISSEN5");
        return resultado[0];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.referencia = dialogodeagregarcomentariosArgs.fromBundle(getArguments()).getReferencia();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_dialogodeagregarcomentarios, container, false);
    }
}