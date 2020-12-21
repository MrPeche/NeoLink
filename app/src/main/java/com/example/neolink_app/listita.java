package com.example.neolink_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.adaptadores.DialogNeolink;
import com.example.neolink_app.adaptadores.ListaNeolinks;
import com.example.neolink_app.adaptadores.adaptadorparaelswipping;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.clases.paqueteneolinkasociados;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.navigation.NavDirections;


public class listita extends Fragment implements ListaNeolinks.OnclickListenerItem {

    private RecyclerView rv;
    private GridLayoutManager glm;
    private ListaNeolinks adapter;
    private OWNERitems owner;
    private MasterDrawerViewModel archi;
    private ArrayList<String> lista;
    private AlertDialog.Builder dialogodeborrado;


    public listita() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View estacosa = inflater.inflate(R.layout.fragment_listita, container, false);

        return estacosa;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        final Snackbar Avizoneolinklisto = Snackbar.make(view,"Neolink Guardado", BaseTransientBottomBar.LENGTH_SHORT);
        final Snackbar Avizonelinkfallido = Snackbar.make(view,"Problemas al conectar con el servidor intentelo luego", BaseTransientBottomBar.LENGTH_SHORT);

        rv = view.findViewById(R.id.lista_neolink);
        glm = new GridLayoutManager(getActivity(),1);
        rv.setLayoutManager(glm);
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        //archi.getLiveNL();
//        archi.Usuarioneolinks.observe(getViewLifecycleOwner(), new Observer<OWNERitems>() {
//            @Override
//            public void onChanged(OWNERitems owneRitems) {
//                adapter = new ListaNeolinks(owneRitems, listita.this);
//                rv.setAdapter(adapter);
//                lista = owneRitems.damelista();
//            }
//        });
        archi.dametodoslosneolinks().observe(getViewLifecycleOwner(), new Observer<OWNERitems>() {
            @Override
            public void onChanged(OWNERitems owneRitems) {
                if(owneRitems!=null){
                    owner = owneRitems;
                    adapter = new ListaNeolinks(owneRitems, listita.this);
                    rv.setAdapter(adapter);
                    lista = owneRitems.damelista();
                    permitirelswipe();
                }
            }
        });
        archi.neolinkdeldialogo().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if((s!=null)&&(owner!=null)){
                    archi.agregarneolink2(s,owner.damelista()).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean){
                                Avizoneolinklisto.show();
                            } else Avizonelinkfallido.show();
                        }
                    });
                    /*
                    archi.agregarneolink(s);
                    archi.segraboelneolink().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean!=null) {
                                if (aBoolean) {
                                    Avizoneolinklisto.show();
                                    //archi.neolinkguardadopositivo();
                                } else {
                                    Avizonelinkfallido.show();
                                    //archi.neolinkguardadopositivo();
                                }
                            }
                        }
                    });
                    archi.vacialneolinkdeldialogo();
                     */
                }
            }
        });

        /*
        archi.neolinkguardado.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    archi.neolinkguardadopositivo();
                }
            }
        });
        */
        //rv.setAdapter(adapter);
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
                if(lista.size()==1){
                    dialogodeborrado.setMessage("No puede realizar esta operación pues es su ultimo neolink").setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            recuperarlalista();
                        }
                    }).show();
                } else {
                    dialogodeborrado.setMessage("¿Está seguro que quiere borrar este neolink y todos los neonodos asociados a este? (Luego no podrá volverlo a usar sin la ayuda de la empresa)").setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            nuevalista.remove(position);
                            archi.borrarunneolink(item,nuevalista);
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
                } else
                    Navigation.findNavController(getView()).navigate(listitaDirections.actionListitaToDialogNeolink());
            }
        });
    }

    @Override
    public void Onclickitem(int position) {
        String neolink = lista.get(position);
        Navigation.findNavController(getView()).navigate(listitaDirections.actionListitaToListaneonodes2(neolink));
    }
    @Override
    public void onPause() {
        super.onPause();
        ((actividadbase)getActivity()).fabdesparecer();
    }

    public void openDialog(){
        DialogNeolink dialogaso = new DialogNeolink();
        dialogaso.show(getParentFragmentManager(),"dialogo");
    }
}
