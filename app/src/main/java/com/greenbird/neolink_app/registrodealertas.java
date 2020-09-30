package com.greenbird.neolink_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.R;
import com.example.neolink_app.adaptadores.ListaNeolinks;
import com.example.neolink_app.adaptadores.adaptadorderegistro;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.clases.clasesdelregistro.notihist;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

import java.util.ArrayList;

public class registrodealertas extends Fragment {

    private RecyclerView rv;
    private GridLayoutManager glm;
    private adaptadorderegistro adapter;
    private MasterDrawerViewModel archi;
    private ArrayList<String> lista;

    public registrodealertas() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        return inflater.inflate(R.layout.fragment_registrodealertas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.lista_registro);
        glm = new GridLayoutManager(getActivity(),1);
        rv.setLayoutManager(glm);
        OWNERitems items = archi.Usuarioneolinks.getValue();
        archi.funcionderecoleccionderegistro(items.getitem(0)).observe(getViewLifecycleOwner(), new Observer<notihist>() {
            @Override
            public void onChanged(notihist notihist) {
                if(notihist!=null){
                    adapter = new adaptadorderegistro(notihist);
                    rv.setAdapter(adapter);
                }
            }
        });

    }
}