package com.example.neolink_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.adaptadores.ListaNeonodesAdapter;
import com.example.neolink_app.clases.LinkNodo;
import com.example.neolink_app.clases.OLDneolinksboleto;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class listaneonodes extends Fragment implements ListaNeonodesAdapter.OnclickListenerItem{
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
            this.neolinkname = listaneonodesArgs.fromBundle(getArguments()).getNeolink();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_listaneonodes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);


        rv = view.findViewById(R.id.lista_neonode);
        glm = new GridLayoutManager(getActivity(),1);
        rv.setLayoutManager(glm);

        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        archi.updateneolinkF(neolinkname);
        archi.getLiveNN();
        archi.neonodos.observe(getViewLifecycleOwner(), new Observer<OLDneolinksboleto>() {
            @Override
            public void onChanged(OLDneolinksboleto olDneolinksboleto) {
                if(olDneolinksboleto!=null){
                    LinkNodo nuevo = new LinkNodo(neolinkname,olDneolinksboleto);
                    lista = olDneolinksboleto.dameneonodos();
                    adapter = new ListaNeonodesAdapter(nuevo,listaneonodes.this);
                    rv.setAdapter(adapter);
                }
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        ((actividadbase)getActivity()).fabplus();
        ((actividadbase)getActivity()).fabaparecer();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((actividadbase)getActivity()).fabdesparecer();
    }


    @Override
    public void Onclickitem(int position) {
        String neolink = "";
        if(position==0){
            neolink = neolinkname;
        } else neolink = lista.get(position-1);
        Navigation.findNavController(getView()).navigate(listaneonodesDirections.actionListaneonodesToConfiguracionesmodelo(neolink));
    }
}
