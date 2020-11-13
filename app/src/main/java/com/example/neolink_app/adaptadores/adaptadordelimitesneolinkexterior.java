package com.example.neolink_app.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;
import com.example.neolink_app.clases.configuracion.statelimitsport;

public class adaptadordelimitesneolinkexterior extends RecyclerView.Adapter<adaptadordelimitesneolinkexterior.adaptadordellimiteneolinkpuerto>{
    private statelimitsport obj;
    private FragmentActivity activ;

    public adaptadordelimitesneolinkexterior(statelimitsport obj,FragmentActivity activ){
        this.obj = obj;
        this.activ = activ;
    }

    @NonNull
    @Override
    public adaptadordellimiteneolinkpuerto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new adaptadordellimiteneolinkpuerto(LayoutInflater.from(parent.getContext()).inflate(R.layout.agregarlimitesitems,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadordellimiteneolinkpuerto holder, int position) {
        String puertotitle = "Puerto"+(position+1);
        holder.puertoname.setText(puertotitle);
        holder.plegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.plegar.setVisibility(View.GONE);
                holder.desplegar.setVisibility(View.VISIBLE);
                holder.rvport.setVisibility(View.VISIBLE);
            }
        });
        holder.desplegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.plegar.setVisibility(View.VISIBLE);
                holder.desplegar.setVisibility(View.GONE);
                holder.rvport.setVisibility(View.GONE);
            }
        });
        holder.glm = new GridLayoutManager(activ,1);
        holder.rvport.setLayoutManager(holder.glm);
        //holder.rvport.setAdapter();
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static class adaptadordellimiteneolinkpuerto extends RecyclerView.ViewHolder{
        public TextView puertoname;
        public ImageView plegar;
        public ImageView desplegar;
        public RecyclerView rvport;
        public GridLayoutManager glm;
        public adaptadordellimiteneolinkpuerto(@NonNull View itemView) {
            super(itemView);
            puertoname = itemView.findViewById(R.id.puertonameitem);
            plegar = itemView.findViewById(R.id.plegaricon);
            desplegar = itemView.findViewById(R.id.desplegaricon);
            rvport = itemView.findViewById(R.id.rvportlimititem);

        }
    }
}
