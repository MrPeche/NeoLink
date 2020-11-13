package com.example.neolink_app.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;

public class adaptadordelimitesneolinkinterior extends RecyclerView.Adapter<adaptadordelimitesneolinkinterior.adaptadordelimitesindependientes> {

    @NonNull
    @Override
    public adaptadordelimitesindependientes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new adaptadordelimitesindependientes(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_limiteindependiente,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadordelimitesindependientes holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class adaptadordelimitesindependientes extends RecyclerView.ViewHolder{
        public SwitchCompat swvariable;

        public adaptadordelimitesindependientes(@NonNull View itemView) {
            super(itemView);
        }
    }
}
