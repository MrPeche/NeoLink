package com.example.neolink_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.adaptadores.ListaNeolinks;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class listita extends Fragment implements ListaNeolinks.OnclickListenerItem {

    private RecyclerView rv;
    private GridLayoutManager glm;
    private ListaNeolinks adapter;
    private MasterDrawerViewModel archi;
    private ArrayList<String> lista;

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
        rv = view.findViewById(R.id.lista_neolink);
        glm = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(glm);

        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        archi.getLiveNL();
        archi.Usuarioneolinks.observe(getViewLifecycleOwner(), new Observer<OWNERitems>() {
            @Override
            public void onChanged(OWNERitems owneRitems) {
                adapter = new ListaNeolinks(owneRitems);
                rv.setAdapter(adapter);
                lista = owneRitems.getlista();
            }
        });

        //rv.setAdapter(adapter);
    }

    @Override
    public void Onclickitem(int position) {
        String neolink = lista.get(position);
        Navigation.findNavController(getView()).navigate(R.id.action_listita_to_listaneonodes2);

    }
}
