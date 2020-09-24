package com.example.neolink_app.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;

import java.util.ArrayList;

public class adaptadorderegistro extends RecyclerView.Adapter<adaptadorderegistro.adaptadorderegistroViewHolder> {
    ArrayList<String> contenido;
    ArrayList<String> fecha;
    ArrayList<String> tiempo;

    public adaptadorderegistro(ArrayList<String> contenido, ArrayList<String> fecha, ArrayList<String> tiempo){
        this.contenido = contenido;
        this.fecha = fecha;
        this.tiempo = tiempo;
    }

    @NonNull
    @Override
    public adaptadorderegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new adaptadorderegistroViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_registro, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorderegistroViewHolder holder, int position) {
        String contenidounitario = contenido.get(position);
        String fechaunitaria = fecha.get(position);
        String tiempounitario = tiempo.get(position);
        holder.contenidoitem.setText(contenidounitario);
        holder.tiempoitem.setText(tiempounitario);
        holder.fechaitem.setText(fechaunitaria);
    }

    @Override
    public int getItemCount() {
        return this.contenido.size();
    }


    public static class adaptadorderegistroViewHolder extends RecyclerView.ViewHolder{
        public TextView contenidoitem;
        public TextView fechaitem;
        public TextView tiempoitem;


        public adaptadorderegistroViewHolder(@NonNull View itemView) {
            super(itemView);
            contenidoitem = itemView.findViewById(R.id.registrocontenido);
            fechaitem = itemView.findViewById(R.id.fecha);
            tiempoitem = itemView.findViewById(R.id.tiempo);
        }
    }

}
