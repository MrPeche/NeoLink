package com.example.neolink_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.adaptadores.ListaNeonodesAdapter;
import com.example.neolink_app.clases.OLDneolinksboleto;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

import java.util.ArrayList;


public class listaneonodes extends Fragment {
    private String neolinkname;
    private RecyclerView rv;
    private GridLayoutManager glm;
    private ListaNeonodesAdapter adapter;
    private ArrayList<String> lista;
    private MasterDrawerViewModel archi;


    public listaneonodes() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            neolinkname = listaneonodesArgs.fromBundle(getArguments()).getNeolink();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_listaneonodes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        rv = view.findViewById(R.id.lista_neonode);
        glm = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(glm);

        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        archi.getLiveNL();
        archi.neonodos.observe(getViewLifecycleOwner(), new Observer<OLDneolinksboleto>() {
            @Override
            public void onChanged(OLDneolinksboleto olDneolinksboleto) {
                adapter = new ListaNeonodesAdapter(olDneolinksboleto);
                rv.setAdapter(adapter);
                lista = olDneolinksboleto.dameneonodos();
            }
        });


    }
}
