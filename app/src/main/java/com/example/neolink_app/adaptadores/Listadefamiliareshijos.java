package com.example.neolink_app.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

import java.util.ArrayList;

public class Listadefamiliareshijos extends RecyclerView.Adapter<Listadefamiliareshijos.ListaFamiliaresViewHolder> {
    private ArrayList<String> correos;
    private MasterDrawerViewModel archi;
    private LifecycleOwner act;

    public Listadefamiliareshijos(ArrayList<String> correos, MasterDrawerViewModel archi, LifecycleOwner act){
        this.correos = correos;
        this.archi = archi;
        this.act = act;
    }

    @NonNull
    @Override
    public ListaFamiliaresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListaFamiliaresViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuentavinculada, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListaFamiliaresViewHolder holder, int position) {
        holder.correo.setText(correos.get(position));
        holder.borrar.setOnClickListener(v -> archi.fetchuiddelhijo(correos.get(position)).observe(act, s -> {
            if(s!=null){
                if(archi.cualeselestadofamiliar()){
                    archi.actualizaravizonoerespadre(holder.itemView);
                } else
                    archi.borrarhijo(s);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return correos.size();
    }


    public static class ListaFamiliaresViewHolder extends RecyclerView.ViewHolder {
        public TextView correo;
        public ImageView borrar;

        public ListaFamiliaresViewHolder(View itemView) {
            super(itemView);
            correo = itemView.findViewById(R.id.usuariovinculado);
            borrar = itemView.findViewById(R.id.borrarvinculo);
        }

    }
}
