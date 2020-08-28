package com.example.neolink_app.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;
import com.example.neolink_app.clases.LinkNodo;
import com.example.neolink_app.clases.OLDneolinksboleto;

import java.util.ArrayList;

public class ListaNeonodesAdapter extends RecyclerView.Adapter<ListaNeonodesAdapter.ListaNeonodesAdapterViewHolder> {
    private ArrayList<String> data = new ArrayList<>();
    private OnclickListenerItem onclickListenerItem;



    public ListaNeonodesAdapter(LinkNodo nodaso,  OnclickListenerItem onclickListenerItem){
        this.data.add(nodaso.getneolink());
        this.onclickListenerItem = onclickListenerItem;
        if(nodaso.getoldnodo().dameneonodos()!=null) this.data.addAll(nodaso.getoldnodo().dameneonodos());
        //if(nodaso.getoldnodo().dameneonodos()!=null)
    }
    @Override
    public int getItemViewType(int position){
        if(position==0)
            return 0;
        else
            return 1;
    }

    @NonNull
    @Override
    public ListaNeonodesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0){
            return new  ListaNeonodesAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_neolink_cardview, parent, false),onclickListenerItem);
        } else {
            return new ListaNeonodesAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_neonode_cardview, parent, false),onclickListenerItem);
        }
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

    public static class ListaNeonodesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView codigo;
        OnclickListenerItem onclickitemlistener;

        public ListaNeonodesAdapterViewHolder(@NonNull View itemView,  OnclickListenerItem onclickitemlistener) {
            super(itemView);
            codigo = itemView.findViewById(R.id.codigocard);
            itemView.setOnClickListener(this);
            this.onclickitemlistener = onclickitemlistener;
        }

        @Override
        public void onClick(View v) {
            onclickitemlistener.Onclickitem(getAbsoluteAdapterPosition());
        }
    }

    public interface OnclickListenerItem{
        void Onclickitem(int position);
    }
}
