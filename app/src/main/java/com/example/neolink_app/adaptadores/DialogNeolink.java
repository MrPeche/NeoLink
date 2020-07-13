package com.example.neolink_app.adaptadores;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.neolink_app.R;
import com.google.android.material.textfield.TextInputEditText;

public class DialogNeolink extends AppCompatDialogFragment {
    private TextInputEditText codigoneolinknuevo;

    public DialogNeolink() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogoneolink,null);
        builder.setView(view).setNegativeButton(R.string.cancelardialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton(R.string.aceptardialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        codigoneolinknuevo = view.findViewById(R.id.ticketdialog);
        return builder.create();
    }
}
