package com.example.neolink_app.adaptadores;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;
import com.example.neolink_app.clases.configuracion.stateport;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class adaptadordelimitesneolinkinterior extends RecyclerView.Adapter<adaptadordelimitesneolinkinterior.adaptadordelimitesindependientes> {
    private String[] variable = {"Potencial Matricial (KPa)","Temperatura (°C)","Temperatura del Suelo (°C)","Conductividad Eléctrica del Poro (uS/cm)","Contenido Volumétrico de agua (m3/m3)","Conductividad Eléctrica del Suelo (uS/cm)"};
    private int[] nombredesensor = {1,1,0,0,0,0};
    private int[] nombredelavariable = {0,1,1,3,4,2};
    private ArrayList<EditText> limitessuperiores = new ArrayList<>();
    private ArrayList<EditText> limitesinferiores = new ArrayList<>();
    private ArrayList<SwitchCompat> activaciondelimites = new ArrayList<>();
    private ArrayList<EditText> limitesespeciales = new ArrayList<>();
    private ArrayList<Double> limitesuperiororiginal = new ArrayList<>();
    private ArrayList<Double> limiteinferiororiginal = new ArrayList<>();
    private ArrayList<Integer> swithcoriginal = new ArrayList<>();
    private ArrayList<Double> limiteespecialoriginal = new ArrayList<>();
    private stateport portdata;
    public adaptadordelimitesneolinkinterior(stateport portdata){
        this.portdata = portdata;
    }

    @NonNull
    @Override
    public adaptadordelimitesindependientes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new adaptadordelimitesindependientes(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_limiteindependiente,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadordelimitesindependientes holder, int position) {
        holder.swvariable.setText(variable[position]);
        holder.limiteinferior.setVisibility(View.GONE);
        holder.limsuperior.setVisibility(View.GONE);
        if(position==4){
            String sup = "CC : capacidad de campo";
            String inf = "PMP: punto de marchitez permanente";
            holder.textsuperior.setText(sup);
            holder.textinferior.setText(inf);
        }
        holder.swvariable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(holder.swvariable.isChecked()){
                    holder.limiteinferior.setVisibility(View.VISIBLE);
                    holder.limsuperior.setVisibility(View.VISIBLE);
                    if(position==4){
                        holder.limiteespecial.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.limiteinferior.setVisibility(View.GONE);
                    holder.limsuperior.setVisibility(View.GONE);
                    if(position==4){
                        holder.limiteespecial.setVisibility(View.GONE);
                    }
                }
            }
        });
        if(nombredesensor[position]==0){
            if(nombredelavariable[position]==1){
                holder.swvariable.setChecked(deintaboleano(portdata.dameG().dameV2().damebool()));
                holder.valorinferior.setText(String.valueOf(portdata.dameG().dameV2().dameMin()));
                holder.valorsuperior.setText(String.valueOf(portdata.dameG().dameV2().dameMAX()));
                limitesuperiororiginal.add(portdata.dameG().dameV2().dameMAX());
                limiteinferiororiginal.add(portdata.dameG().dameV2().dameMin());
                swithcoriginal.add(portdata.dameG().dameV2().damebool());
                limiteespecialoriginal.add(0.0);
            } else if(nombredelavariable[position]==2){
                holder.swvariable.setChecked(deintaboleano(portdata.dameG().dameV3().damebool()));
                holder.valorinferior.setText(String.valueOf(portdata.dameG().dameV3().dameMin()));
                holder.valorsuperior.setText(String.valueOf(portdata.dameG().dameV3().dameMAX()));
                limitesuperiororiginal.add(portdata.dameG().dameV3().dameMAX());
                limiteinferiororiginal.add(portdata.dameG().dameV3().dameMin());
                swithcoriginal.add(portdata.dameG().dameV3().damebool());
                limiteespecialoriginal.add(0.0);
            } else if(nombredelavariable[position]==3){
                holder.swvariable.setChecked(deintaboleano(portdata.dameG().damePoreCer().damebool()));
                holder.valorinferior.setText(String.valueOf(portdata.dameG().damePoreCer().dameMin()));
                holder.valorsuperior.setText(String.valueOf(portdata.dameG().damePoreCer().dameMAX()));
                limitesuperiororiginal.add(portdata.dameG().damePoreCer().dameMAX());
                limiteinferiororiginal.add(portdata.dameG().damePoreCer().dameMin());
                swithcoriginal.add(portdata.dameG().damePoreCer().damebool());
                limiteespecialoriginal.add(0.0);
            } else if(nombredelavariable[position]==4){
                holder.swvariable.setChecked(deintaboleano(portdata.dameG().damevwc().damebool()));
                holder.valorinferior.setText(String.valueOf(portdata.dameG().damevwc().damePMP()));
                holder.valorsuperior.setText(String.valueOf(portdata.dameG().damevwc().dameCC()));
                holder.valorespecial.setText(String.valueOf(portdata.dameG().damevwc().dameAU()));
                limitesuperiororiginal.add(portdata.dameG().damevwc().dameCC());
                limiteinferiororiginal.add(portdata.dameG().damevwc().damePMP());
                swithcoriginal.add(portdata.dameG().damevwc().damebool());
                limiteespecialoriginal.add(portdata.dameG().damevwc().dameAU());
            }

        } else{
            if(nombredelavariable[position]==0){
                holder.swvariable.setChecked(deintaboleano(portdata.damek().dameV1().damebool()));
                holder.valorinferior.setText(String.valueOf(portdata.damek().dameV1().dameMin()));
                holder.valorsuperior.setText(String.valueOf(portdata.damek().dameV1().dameMAX()));
                limitesuperiororiginal.add(portdata.damek().dameV1().dameMAX());
                limiteinferiororiginal.add(portdata.damek().dameV1().dameMin());
                swithcoriginal.add(portdata.damek().dameV1().damebool());
            } else{
                holder.swvariable.setChecked(deintaboleano(portdata.damek().dameV2().damebool()));
                holder.valorinferior.setText(String.valueOf(portdata.damek().dameV2().dameMin()));
                holder.valorsuperior.setText(String.valueOf(portdata.damek().dameV2().dameMAX()));
                limitesuperiororiginal.add(portdata.damek().dameV2().dameMAX());
                limiteinferiororiginal.add(portdata.damek().dameV2().dameMin());
                swithcoriginal.add(portdata.damek().dameV2().damebool());
            }
            limiteespecialoriginal.add(0.0);
        }
        limitessuperiores.add(holder.valorsuperior);
        limitesinferiores.add(holder.valorinferior);
        limitesespeciales.add(holder.valorespecial);
        activaciondelimites.add(holder.swvariable);
    }
    public Pair<Pair<ArrayList<SwitchCompat>,ArrayList<EditText>>,Pair<ArrayList<EditText>,ArrayList<EditText>>> entregardatoscompletosdelimitesidependientes(){
        return new Pair<>(new Pair<>(activaciondelimites,limitessuperiores),new Pair<>(limitesinferiores,limitesespeciales));
    }
    public Pair<Pair<ArrayList<Integer>,ArrayList<Double>>,Pair<ArrayList<Double>,ArrayList<Double>>> entregardatoscompletosdelimitesidependientesoriginales(){
        return new Pair<>(new Pair<>(swithcoriginal,limitesuperiororiginal),new Pair<>(limiteinferiororiginal,limiteespecialoriginal));
    }
    private boolean deintaboleano(int v){
        return v==1;
    }

    @Override
    public int getItemCount() {
        return variable.length;
    }

    public static class adaptadordelimitesindependientes extends RecyclerView.ViewHolder{
        public SwitchCompat swvariable;
        public LinearLayout limsuperior;
        public LinearLayout limiteinferior;
        public LinearLayout limiteespecial;
        public TextView textsuperior;
        public TextView textinferior;
        public TextView textespecial;
        public EditText valorsuperior;
        public EditText valorinferior;
        public EditText valorespecial;
        public adaptadordelimitesindependientes(@NonNull View itemView) {
            super(itemView);
            swvariable = itemView.findViewById(R.id.variabledelpuerto);
            limsuperior = itemView.findViewById(R.id.limitesuperioritem);
            limiteinferior = itemView.findViewById(R.id.limiteinferioritem);
            valorsuperior = itemView.findViewById(R.id.valorsuperior);
            valorinferior = itemView.findViewById(R.id.valorinferior);
            valorespecial = itemView.findViewById(R.id.valorespecial);
            textsuperior = itemView.findViewById(R.id.textosuperioritem);
            textinferior = itemView.findViewById(R.id.textoinferioritem);
            textespecial = itemView.findViewById(R.id.textoespecialitem);
            limiteespecial = itemView.findViewById(R.id.limiteespecialritem);
        }
    }
}
