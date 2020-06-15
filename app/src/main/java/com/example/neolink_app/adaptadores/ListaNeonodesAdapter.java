package com.example.neolink_app.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;
import com.example.neolink_app.clases.OLDneolinksboleto;

import java.util.ArrayList;

public class ListaNeonodesAdapter extends RecyclerView.Adapter<ListaNeonodesAdapter.ListaNeonodesAdapterViewHolder> {
    private ArrayList<String> data;



    public ListaNeonodesAdapter(OLDneolinksboleto oldneonodos){
        this.data = oldneonodos.dameneonodos();
    }

    @NonNull
    @Override
    public ListaNeonodesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListaNeonodesAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_neonode_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListaNeonodesAdapter.ListaNeonodesAdapterViewHolder holder, int position) {
        String neonodo = data.get(position);
        holder.codigo.setText(neonodo);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ListaNeonodesAdapterViewHolder extends RecyclerView.ViewHolder{
        public TextView codigo;

        public ListaNeonodesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.codigocardnode);
        }
    }
}
