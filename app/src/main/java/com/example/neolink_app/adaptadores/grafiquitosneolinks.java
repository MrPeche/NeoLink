package com.example.neolink_app.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;
import com.example.neolink_app.clases.OWNERitems;

public class grafiquitosneolinks extends RecyclerView.Adapter<grafiquitosneolinks.grafiquitosneolinksViewHolder> {
    private OWNERitems data;
    private  clickprogramitaprimero cliky;


    public grafiquitosneolinks(OWNERitems data, clickprogramitaprimero onclick){
        this.data = data;
        this.cliky = onclick;
    }

    @NonNull
    @Override
    public grafiquitosneolinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new grafiquitosneolinksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grafiquito_neolinks, parent, false),cliky);
    }

    @Override
    public void onBindViewHolder(@NonNull grafiquitosneolinksViewHolder holder, int position) {
        String owner = data.getlista().get(position);
        holder.codigo.setText(owner);
    }

    @Override
    public int getItemCount() {
        return data.getlista().size();
    }

    public static class grafiquitosneolinksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView codigo;
        clickprogramitaprimero cliky;

        public grafiquitosneolinksViewHolder(@NonNull View itemView,clickprogramitaprimero cliky) {
            super(itemView);
            codigo = itemView.findViewById(R.id.grafiquitoneolink);
            itemView.setOnClickListener(this);
            this.cliky = cliky;
        }

        @Override
        public void onClick(View v) {
            cliky.clickiprogramita(getAbsoluteAdapterPosition());
        }
    }

    public interface clickprogramitaprimero{
        void clickiprogramita(int position);
    }
}
