package com.example.neolink_app.adaptadores;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.clases.LinkNodo;
import com.example.neolink_app.clases.database_state.horasstate;
import com.example.neolink_app.planografico;

import java.util.ArrayList;

public class viewpagergrafiquitosAdapter extends FragmentStateAdapter {
    private ArrayList<String> data = new ArrayList<>();
    private Horas paquetehora;
    private Pair<Horas, horasstate> paquete;

    public viewpagergrafiquitosAdapter(FragmentActivity fa, Horas horas, ArrayList<String> nodaso){
        super(fa);
        this.data = nodaso;
        this.paquetehora = horas;
    }
    public viewpagergrafiquitosAdapter(FragmentActivity fa, Pair<Horas, horasstate> paquete, ArrayList<String> nodaso){
        super(fa);
        this.data = nodaso;
        this.paquete = paquete;
    }
    public viewpagergrafiquitosAdapter(FragmentActivity fa, ArrayList<String> nodaso){
        super(fa);
        this.data = nodaso;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment plano = planografico.newInstance(data.get(position));
        //Fragment plano = planografico.newInstance(this.paquete,data.get(position));
        return plano;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
