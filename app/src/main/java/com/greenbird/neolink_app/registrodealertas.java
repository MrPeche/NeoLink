package com.greenbird.neolink_app;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.neolink_app.R;
import com.example.neolink_app.adaptadores.ListaNeolinks;
import com.example.neolink_app.adaptadores.adaptadorderegistro;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.clases.clasesdelregistro.notihist;
import com.example.neolink_app.clases.paqueteneolinkasociados;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class registrodealertas extends Fragment {

    private RecyclerView rv;
    private GridLayoutManager glm;
    private adaptadorderegistro adapter;
    private MasterDrawerViewModel archi;
    private ArrayList<String> lista;
    private AlertDialog.Builder dialogodeborrado;

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
        dialogodeborrado = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),R.style.AlertDialogCustom));
        dialogodeborrado.setMessage("¿Esta seguro que quiere borrar este mensaje?");
        rv = view.findViewById(R.id.lista_registro);
        glm = new GridLayoutManager(getActivity(),1);
        rv.setLayoutManager(glm);
        //OWNERitems items = archi.Usuarioneolinks.getValue();

        archi.gettodoelpaqueteneolinkasociado().observe(getViewLifecycleOwner(), new Observer<paqueteneolinkasociados>() {
            @Override
            public void onChanged(paqueteneolinkasociados paqueteneolinkasociados) {
                if(paqueteneolinkasociados!=null){
                    ArrayList<String> todoslosdispositivos = new ArrayList<>();
                    for(int i=0;i<paqueteneolinkasociados.dameneolinks().size();i++){
                        todoslosdispositivos.add(paqueteneolinkasociados.dameneolinks().get(i));
                        todoslosdispositivos.addAll(paqueteneolinkasociados.dameneonodos().get(i));
                    }
                    archi.funcionrecoleccionderegristro3(todoslosdispositivos).observe(getViewLifecycleOwner(), new Observer<notihist>() {
                        @Override
                        public void onChanged(notihist notihist) {
                            if(notihist!=null){
                                adapter = new adaptadorderegistro(notihist,dialogodeborrado,archi);
                                rv.setAdapter(adapter);
                            }
                        }
                    });
                }
            }
        });
        /*
        archi.funcionderecoleccionderegistro(items.getitem(0)).observe(getViewLifecycleOwner(), new Observer<notihist>() {
            @Override
            public void onChanged(notihist notihist) {
                if(notihist!=null){
                    adapter = new adaptadorderegistro(notihist,dialogodeborrado,archi);
                    rv.setAdapter(adapter);
                }
            }
        });
        */
        archi.dialogoeditarmensaje().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                if(strings!=null){
                    if(archi.cualeselestadofamiliar()){
                        archi.actualizaravizonoerespadre(view);
                    } else
                        Navigation.findNavController(getView()).navigate(registrodealertasDirections.actionRegistrodealertasToDialogoeditarcomentario(strings[0],strings[1],strings[2]));
                }
            }
        });

    }
}