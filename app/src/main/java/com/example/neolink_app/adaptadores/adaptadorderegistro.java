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
import java.util.Collections;

public class adaptadorderegistro extends RecyclerView.Adapter<adaptadorderegistro.adaptadorderegistroViewHolder> {
    ArrayList<String> contenido;
    ArrayList<String> fecha;
    ArrayList<String> tiempo;
    String[] variablesg = {"ApPer","Depth","PoreCE","PorePer","V1","V2","V3","vwc"};
    String[] variablesk = {"V1","V2"};
    String[] variablesstate = {"AL","BP","BV","dT","iT","OP_TIME","RH","SV","WD","WS"};
    String[] variablesrealesg = {"","","Conductividad eléctrica del poro","Permibilidad del poro","Humedad del suelo","Temperatura del suelo","Conductividad eléctrica","Contenido volumétrico de agua"};
    String[] variablesrealesk = {"Potencial Mátricual","Temperatura"};
    String[] variablesrealesstate = {"Altitud","Presión Barométrica","Voltaje","Temperatura de bulbo seco","Tiempo de operación","Humedad relativa","Voltaje solar","Dirección del viento","Velocidad del viento"};
    private MasterDrawerViewModel archi;
    private AlertDialog.Builder avizodeborrado;
    public adaptadorderegistro(notihist pack){
        contenido = new ArrayList<>();
        fecha = new ArrayList<>();
        tiempo = new ArrayList<>();
        managepack(pack);
        dalevuelta();
    }
    public adaptadorderegistro(notihist pack, AlertDialog.Builder avizodeborrado,MasterDrawerViewModel archi){
        contenido = new ArrayList<>();
        fecha = new ArrayList<>();
        tiempo = new ArrayList<>();
        managepack(pack);
        dalevuelta();
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
                        //neolink+"5HN5"+time[0]+"5ZT5"+time[1]+"5ZT5"+puertotexto+"5PT5"+sensor+"5TP5"+var+"5TP5"+mensaje+"5MS5""+nombreorreo
                    }
                }
            }
        }
    }
    private void dalevuelta(){
        Collections.reverse(tiempo);
        Collections.reverse(fecha);
        Collections.reverse(contenido);
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
        String second = "";
        if(!(contenido.get(position).contains("5PT5")||contenido.get(position).contains("5PORT5"))){
            second = translateelvalor(contenidounitario[3],contenidounitario[4]);
        } else {
            second = contenidounitario[6] + " " + translateelvalor(contenidounitario[3],contenidounitario[4]);
        }
        if(!contenido.get(position).contains("5PT5")){
            holder.autor.setText(contenidounitario[6]);
        } else holder.autor.setText(contenidounitario[7]);
        holder.secondheaderitem.setText(second);

        holder.borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(archi.cualeselestadofamiliar()){
                    archi.actualizaravizonoerespadre(v);
                } else {
                    avizodeborrado.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String[] fecha = fechaunitaria.split("/");
                            archi.borrarcomentario(contenidounitario[0], fecha[2], fecha[1], fecha[0], tiempounitario);

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    avizodeborrado.show();
                }
            }
        });
        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                archi.dialogoeditarmensajesubir(fechaunitaria,tiempounitario,contenido.get(position));
            }
        });
    }
    private String translateelvalor(String sensor,String variables){
        if(sensor.equals("g")){
            return encontrarreal(variablesg,variablesrealesg,variables);
        } else if(sensor.equals("k")){
            return encontrarreal(variablesk,variablesrealesk,variables);
        } else if(sensor.equals("state")){
            return encontrarreal(variablesstate,variablesrealesstate,variables);
        } else return variables;
    }
    private String encontrarreal(String[] variables,String[] variablesreales,String var){
        String resultado = var;
        for(int i=0;i<variables.length;i++){
            if(variables[i].equals(var)){
                resultado=variablesreales[i];
            }
        }
        return resultado;
    }
    @Override
    public int getItemCount() {
        return this.contenido.size();
    }
    private String[] decodificador(String line){
        if(line.contains("5HN5")){
            String[] neolink = line.split("5HN5");  //neolink + lodemas
            String[] time = neolink[1].split("5ZT5");  //fecha + hora + lodemas
            if (time[2].contains("5PT5")) {
                String[] puerto = time[2].split("5PT5");
                String[] tipo = puerto[1].split("5TP5");
                String[] mensaje = tipo[2].split("5MS5");
                return new String[]{neolink[0], time[0], time[1], tipo[0], tipo[1], mensaje[0], puerto[0], mensaje[1]};
            } else {
                String[] tipo = time[2].split("5TP5"); //sensor + variable + mensaje
                String[] mensaje = tipo[2].split("5MS5");
                return new String[]{neolink[0], time[0], time[1], tipo[0], tipo[1], mensaje[0], mensaje[1]};
            }
        } else {
            String[] neolink = line.split("5HEISSEN5");  //neolink + lodemas
            String[] time = neolink[1].split("5ZEIT5");  //fecha + hora + lodemas
            if (time[2].contains("5PORT5")) {
                String[] puerto = time[2].split("5PORT5");
                String[] tipo = puerto[1].split("5TYP5");
                return new String[]{neolink[0], time[0], time[1], tipo[0], tipo[1], tipo[2], puerto[0]};
            } else {
                String[] tipo = time[2].split("5TYP5"); //sensor + variable + mensaje
                return new String[]{neolink[0], time[0], time[1], tipo[0], tipo[1], tipo[2]};
            }
        }
        //String[] tipo = tipo[];
    }
    public static class adaptadorderegistroViewHolder extends RecyclerView.ViewHolder{
        public TextView contenidoitem;
        public TextView headercontenidoitem;
        public TextView secondheaderitem;
        public TextView autor;
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
            autor = itemView.findViewById(R.id.correoautor);
            secondheaderitem = itemView.findViewById(R.id.secondheaderregistrocontenido);
            editar = itemView.findViewById(R.id.registroeditar);
            borrar = itemView.findViewById(R.id.registroborrar);
        }
    }

}
