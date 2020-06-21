package com.example.neolink_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.adaptadores.grafiquitosneolinks;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

import java.util.ArrayList;

public class grafiquitos extends Fragment implements grafiquitosneolinks.clickprogramitaprimero{

    private RecyclerView rv;
    private GridLayoutManager glm;
    private grafiquitosneolinks adapter;
    private MasterDrawerViewModel archi;
    private ArrayList<String> lista;


    public grafiquitos() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafiquitos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        rv = view.findViewById(R.id.recyclergraficos);
        glm = new GridLayoutManager(getActivity(),1);
        rv.setLayoutManager(glm);

        ArrayList<String> lol = new ArrayList<>();
        lol.add("NL2006-0002");
        lol.add("NL2006-0003");
        lol.add("NL2006-0004");
        OWNERitems loli = new OWNERitems(lol);
        lista = lol;
        adapter = new grafiquitosneolinks(loli,grafiquitos.this);
        rv.setAdapter(adapter);
    }

    @Override
    public void clickiprogramita(int position) {
        String neolink = lista.get(position);
        // adelntar
        Navigation.findNavController(getView()).navigate(grafiquitosDirections.actionGrafiquitosToPanelesgrafiquito(neolink));
    }
}
