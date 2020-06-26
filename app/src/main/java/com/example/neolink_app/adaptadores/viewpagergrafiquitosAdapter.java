package com.example.neolink_app.adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.clases.LinkNodo;
import com.example.neolink_app.planografico;

import java.util.ArrayList;

public class viewpagergrafiquitosAdapter extends FragmentStateAdapter {
    private ArrayList<String> data = new ArrayList<>();
    private Horas paquetehora;

    public viewpagergrafiquitosAdapter(FragmentActivity fa, Horas horas, ArrayList<String> nodaso){
        super(fa);
        this.data = nodaso;
        this.paquetehora = horas;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment plano = planografico.newInstance(this.paquetehora);
        return plano;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
