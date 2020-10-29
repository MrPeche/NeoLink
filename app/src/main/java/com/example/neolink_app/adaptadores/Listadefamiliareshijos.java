package com.example.neolink_app.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;

import java.util.ArrayList;

public class Listadefamiliareshijos extends RecyclerView.Adapter<Listadefamiliareshijos.ListaFamiliaresViewHolder> {
    ArrayList<String> correos;
    public Listadefamiliareshijos(ArrayList<String> correos){
        this.correos = correos;
    }

    @NonNull
    @Override
    public ListaFamiliaresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListaFamiliaresViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuentavinculada, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListaFamiliaresViewHolder holder, int position) {
        holder.correo.setText(correos.get(position));
    }

    @Override
    public int getItemCount() {
        return correos.size();
    }


    public static class ListaFamiliaresViewHolder extends RecyclerView.ViewHolder {
        public TextView correo;

        public ListaFamiliaresViewHolder(View itemView) {
            super(itemView);
            correo = itemView.findViewById(R.id.usuariovinculado);
        }

    }
}
