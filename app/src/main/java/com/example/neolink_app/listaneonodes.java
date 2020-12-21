package com.example.neolink_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.adaptadores.ListaNeonodesAdapter;
import com.example.neolink_app.adaptadores.adaptadorparaelswipping;
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
    private OLDneolinksboleto oldnenodos;
    private ArrayList<String> lista = new ArrayList<>();
    private MasterDrawerViewModel archi;
    private AlertDialog.Builder dialogodeborrado;


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
        final Snackbar Avizoneolinklisto = Snackbar.make(view,"Neolink Guardado", BaseTransientBottomBar.LENGTH_SHORT);
        final Snackbar Avizonelinkfallido = Snackbar.make(view,"Problemas al conectar con el servidor intentelo luego", BaseTransientBottomBar.LENGTH_SHORT);
        rv = view.findViewById(R.id.lista_neonode);
        glm = new GridLayoutManager(getActivity(),1);
        rv.setLayoutManager(glm);

        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
//        archi.updateneolinkF(neolinkname);
//        archi.getLiveNN();
//        archi.neonodos.observe(getViewLifecycleOwner(), new Observer<OLDneolinksboleto>() {
//            @Override
//            public void onChanged(OLDneolinksboleto olDneolinksboleto) {
//                if(olDneolinksboleto!=null){
//                    LinkNodo nuevo = new LinkNodo(neolinkname,olDneolinksboleto);
//                    lista = olDneolinksboleto.dameneonodos();
//                    adapter = new ListaNeonodesAdapter(nuevo,listaneonodes.this);
//                    rv.setAdapter(adapter);
//                }
//            }
//        });
        archi.dameneonodos(neolinkname).observe(getViewLifecycleOwner(), new Observer<OLDneolinksboleto>() {
            @Override
            public void onChanged(OLDneolinksboleto olDneolinksboleto) {
                if(olDneolinksboleto!=null){
                    oldnenodos=olDneolinksboleto;
                    LinkNodo nuevo = new LinkNodo(neolinkname,olDneolinksboleto);
                    lista.add(nuevo.getneolink());
                    if(nuevo.getoldnodo().dameneonodos()!=null) lista.addAll(nuevo.getoldnodo().dameneonodos());
                    adapter = new ListaNeonodesAdapter(nuevo,listaneonodes.this);
                    rv.setAdapter(adapter);
                    permitirelswipe();
                }
            }
        });

        archi.neonododialogo().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if((s!=null)&&(oldnenodos!=null)){
                    ArrayList<String> neonodos = new ArrayList<>();
                    if(oldnenodos.dameneonodos()!=null) neonodos.addAll(oldnenodos.dameneonodos());
                    archi.agregarneonodo(s,neolinkname,neonodos).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean){
                                Avizoneolinklisto.show();
                            } else Avizonelinkfallido.show();
                        }
                    });
                }
            }
        });


    }
    private void recuperarlalista(){
        rv.setAdapter(adapter);
    }
    private void permitirelswipe(){
        adaptadorparaelswipping swipping = new adaptadorparaelswipping(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                String item = lista.get(position);
                ArrayList<String> nuevalista = lista;
                dialogodeborrado = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),R.style.AlertDialogCustom));
                if(position==0) {
                    dialogodeborrado.setMessage("No puede borrar un neolink en la pantalla de los neonodos").setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            recuperarlalista();
                        }
                    }).show();
                } else {
                    dialogodeborrado.setMessage("¿Está seguro que quiere borrar este neonodo? (Luego no podrá volverlo a usar sin la ayuda de la empresa)").setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(lista.size()==2){
                                ArrayList<String> listavacia = new ArrayList<>();
                                listavacia.add("NaN");
                                archi.borrarunneonodo(neolinkname,listavacia);
                            } else {
                                nuevalista.remove(position);
                                nuevalista.remove(0);
                                archi.borrarunneonodo(neolinkname,nuevalista);
                            }
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            recuperarlalista();
                        }
                    }).show();
                }
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipping);
        itemTouchhelper.attachToRecyclerView(rv);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((actividadbase)getActivity()).fabplus();
        ((actividadbase)getActivity()).fabaparecer();
        ((actividadbase)getActivity()).fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(archi.cualeselestadofamiliar()){
                    archi.actualizaravizonoerespadre(v);
                } else{
                    Navigation.findNavController(getView()).navigate(listaneonodesDirections.actionListaneonodesToDialogneonodo());
                }

            }
        });
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
