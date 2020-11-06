package com.example.neolink_app.adaptadores;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;
import com.example.neolink_app.clases.clasesdelregistro.notihist;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class adaptadorderegistro extends RecyclerView.Adapter<adaptadorderegistro.adaptadorderegistroViewHolder> {
    ArrayList<String> contenido;
    ArrayList<String> fecha;
    ArrayList<String> tiempo;
    private MasterDrawerViewModel archi;
    private AlertDialog.Builder avizodeborrado;
    public adaptadorderegistro(notihist pack){
        contenido = new ArrayList<>();
        fecha = new ArrayList<>();
        tiempo = new ArrayList<>();
        managepack(pack);
    }
    public adaptadorderegistro(notihist pack, AlertDialog.Builder avizodeborrado,MasterDrawerViewModel archi){
        contenido = new ArrayList<>();
        fecha = new ArrayList<>();
        tiempo = new ArrayList<>();
        managepack(pack);
        this.archi = archi;
        this.avizodeborrado = avizodeborrado;
    }
    public void managepack(notihist pack){
        for(int i=0;i<pack.damelosyears().size();i++){
            String yearname = pack.damelosyears().get(i);
            for(int j=0;j<pack.dameelpackdeyears().get(i).damelosmes().size();j++){
                String mesname = pack.dameelpackdeyears().get(i).damelosmes().get(j);
                for(int k=0;k<pack.dameelpackdeyears().get(i).dameelpackdelosmes().get(j).damelosdias().size();k++){
                    String dianame = pack.dameelpackdeyears().get(i).dameelpackdelosmes().get(j).damelosdias().get(k);
                    for(int l=0;l<pack.dameelpackdeyears().get(i).dameelpackdelosmes().get(j).dameelpackdedias().get(k).dameelcontenido().size();l++){
                        tiempo.add(pack.dameelpackdeyears().get(i).dameelpackdelosmes().get(j).dameelpackdedias().get(k).dameelcontenido().get(l).damelahora());
                        fecha.add(dianame+"/"+mesname+"/"+yearname);
                        contenido.add(pack.dameelpackdeyears().get(i).dameelpackdelosmes().get(j).dameelpackdedias().get(k).dameelcontenido().get(l).dameelcontenido()); //neolink+"5HEISSEN5"+time[0]+"5ZEIT5"+time[1]+"5ZEIT5"+sensor+"5TYP5"+var+"5TYP5"+mensage
                    }
                }
            }
        }
    }

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
        String[] contenidounitario = decodificador(contenido.get(position));
        String header = contenidounitario[0]+" "+contenidounitario[1]+" "+contenidounitario[2];
        String fechaunitaria = fecha.get(position);
        String tiempounitario = tiempo.get(position);
        holder.tiempoitem.setText(tiempounitario);
        holder.fechaitem.setText(fechaunitaria);
        holder.contenidoitem.setText(contenidounitario[5]);
        holder.headercontenidoitem.setText(header);
        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = 0;
            }
        });
        holder.borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avizodeborrado.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] fecha = fechaunitaria.split("/");
                        archi.borrarcomentario(contenidounitario[0],fecha[2],fecha[1],fecha[0],tiempounitario);

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                avizodeborrado.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return this.contenido.size();
    }
    private String[] decodificador(String line){
        String[] neolink = line.split("5HEISSEN5");  //neolink + lodemas
        String[] time = neolink[1].split("5ZEIT5");  //fecha + hora + lodemas
        if(time[2].contains("5PORT5")){
            String[] puerto = time[2].split("5PORT5");
            String[] tipo = puerto[1].split("5TYP5");
            return new String[] {neolink[0],time[0],time[1],tipo[0],tipo[1],tipo[2],puerto[0]};
        } else {
            String[] tipo = time[2].split("5TYP5"); //sensor + variable + mensaje
            return new String[] {neolink[0],time[0],time[1],tipo[0],tipo[1],tipo[2]};
        }
        //String[] tipo = tipo[];
    }
    public static class adaptadorderegistroViewHolder extends RecyclerView.ViewHolder{
        public TextView contenidoitem;
        public TextView headercontenidoitem;
        public TextView fechaitem;
        public TextView tiempoitem;
        public ImageView editar;
        public ImageView borrar;
        public adaptadorderegistroViewHolder(@NonNull View itemView) {
            super(itemView);
            contenidoitem = itemView.findViewById(R.id.registrocontenido);
            fechaitem = itemView.findViewById(R.id.fecha);
            tiempoitem = itemView.findViewById(R.id.tiempo);
            headercontenidoitem = itemView.findViewById(R.id.headerregistrocontenido);
            editar = itemView.findViewById(R.id.registroeditar);
            borrar = itemView.findViewById(R.id.registroborrar);
        }
    }

}
