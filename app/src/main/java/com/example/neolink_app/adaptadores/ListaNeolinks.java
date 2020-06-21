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
    private OnclickListenerItem onclicklisteneritem;

    public ListaNeolinks(OWNERitems data, OnclickListenerItem onclicklisteneritem) {
        this.data = data;
        this.onclicklisteneritem = onclicklisteneritem;
    }

    @Override
    public ListaNeolinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ListaNeolinksViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_neolink_cardview, parent, false),onclicklisteneritem);
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

    public static class ListaNeolinksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView codigo;
        OnclickListenerItem onclickitemlistener;

        public ListaNeolinksViewHolder(View itemView, OnclickListenerItem onclickitemlistener) {
            super(itemView);
            codigo = itemView.findViewById(R.id.codigocard);
            itemView.setOnClickListener(this);
            this.onclickitemlistener = onclickitemlistener;
        }

        @Override
        public void onClick(View v) {
            onclickitemlistener.Onclickitem(getAbsoluteAdapterPosition());
            //getAdapterPosition
        }
    }

    public interface OnclickListenerItem{
        void Onclickitem(int position);
    }

}
