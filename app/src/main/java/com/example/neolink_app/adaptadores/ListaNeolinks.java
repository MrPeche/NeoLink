package com.example.neolink_app.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;
import com.example.neolink_app.clases.OWNERitems;

import java.util.ArrayList;

public class ListaNeolinks extends RecyclerView.Adapter<ListaNeolinks.ListaNeolinksViewHolder> {
    private OWNERitems data;

    public ListaNeolinks(OWNERitems data) {
        this.data = data;
    }

    @Override
    public ListaNeolinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListaNeolinksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_neolink_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(ListaNeolinksViewHolder holder, int position) {
        String owner = data.getlista().get(position);
        holder.codigo.setText(owner);

    }

    @Override
    public int getItemCount() {
        return data.getlista().size();
    }

    class ListaNeolinksViewHolder extends RecyclerView.ViewHolder{
        TextView codigo;

        public ListaNeolinksViewHolder(View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.codigocard);
        }
    }

}
