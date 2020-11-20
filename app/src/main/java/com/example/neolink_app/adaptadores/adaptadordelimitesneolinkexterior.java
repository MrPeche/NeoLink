package com.example.neolink_app.adaptadores;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;
import com.example.neolink_app.clases.configuracion.statelimitsport;
import com.example.neolink_app.clases.configuracion.stateport;

import java.util.ArrayList;

public class adaptadordelimitesneolinkexterior extends RecyclerView.Adapter<adaptadordelimitesneolinkexterior.adaptadordellimiteneolinkpuerto>{
    private statelimitsport obj;
    private FragmentActivity activ;
    private ArrayList<Pair<Pair<ArrayList<SwitchCompat>,ArrayList<EditText>>,Pair<ArrayList<EditText>,ArrayList<EditText>>>> paquetedevaloresinteriores = new ArrayList<>();
    private ArrayList<Pair<Pair<ArrayList<Integer>,ArrayList<Double>>,Pair<ArrayList<Double>,ArrayList<Double>>>> paquetedevaloresinterioresoriginales = new ArrayList<>();

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
                holder.puertoname.setTextColor(Color.BLACK);
                holder.rvport.setVisibility(View.VISIBLE);
            }
        });
        holder.desplegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.plegar.setVisibility(View.VISIBLE);
                holder.desplegar.setVisibility(View.GONE);
                holder.puertoname.setTextColor(Color.parseColor("#9a9a9a"));
                holder.rvport.setVisibility(View.GONE);
            }
        });
        holder.glm = new GridLayoutManager(activ,1);
        holder.rvport.setLayoutManager(holder.glm);
        if(position==0){
            holder.adapter = new adaptadordelimitesneolinkinterior(obj.dameP1());
            if(buscarelestadodelpuerto(obj.dameP1())){
                holder.plegar.performClick();
            }
        } else if(position==1){
            holder.adapter = new adaptadordelimitesneolinkinterior(obj.dameP2());
            if(buscarelestadodelpuerto(obj.dameP2())){
                holder.plegar.performClick();
            }
        } else if(position==2){
            holder.adapter = new adaptadordelimitesneolinkinterior(obj.dameP3());
            if(buscarelestadodelpuerto(obj.dameP3())){
                holder.plegar.performClick();
            }
        } else if(position==3){
            holder.adapter = new adaptadordelimitesneolinkinterior(obj.dameP4());
            if(buscarelestadodelpuerto(obj.dameP4())){
                holder.plegar.performClick();
            }
        }
        //holder.adapter = new adaptadordelimitesneolinkinterior();
        holder.rvport.setAdapter(holder.adapter);
        this.paquetedevaloresinteriores.add(holder.adapter.entregardatoscompletosdelimitesidependientes());
        this.paquetedevaloresinterioresoriginales.add(holder.adapter.entregardatoscompletosdelimitesidependientesoriginales());
    }
    private boolean buscarelestadodelpuerto(stateport obj){
        return inttoboleano(obj.damek().dameV1().damebool())||inttoboleano(obj.damek().dameV2().damebool())||inttoboleano(obj.dameG().dameV2().damebool())||inttoboleano(obj.dameG().dameV3().damebool())||inttoboleano(obj.dameG().damePoreCer().damebool())||inttoboleano(obj.dameG().damevwc().damebool());
    }
    private boolean inttoboleano(int data){return data==1;}
    public ArrayList<Pair<Pair<ArrayList<SwitchCompat>,ArrayList<EditText>>,Pair<ArrayList<EditText>,ArrayList<EditText>>>> entregardatosporpuerto(){
        return paquetedevaloresinteriores;
    }
    public ArrayList<Pair<Pair<ArrayList<Integer>,ArrayList<Double>>,Pair<ArrayList<Double>,ArrayList<Double>>>> entregardatosporpuertooriginales(){
        return paquetedevaloresinterioresoriginales;
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
        public adaptadordelimitesneolinkinterior adapter;
        public adaptadordellimiteneolinkpuerto(@NonNull View itemView) {
            super(itemView);
            puertoname = itemView.findViewById(R.id.puertonameitem);
            plegar = itemView.findViewById(R.id.plegaricon);
            desplegar = itemView.findViewById(R.id.desplegaricon);
            rvport = itemView.findViewById(R.id.rvportlimititem);
        }
    }
}
