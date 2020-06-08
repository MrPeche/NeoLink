package com.example.neolink_app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.adaptadores.ListaNeolinks;


/**
 * A simple {@link Fragment} subclass.
 */
public class listita extends Fragment {

    private RecyclerView rv;
    private GridLayoutManager glm;
    private ListaNeolinks adapter;

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
        rv = estacosa.findViewById(R.id.lista_neolink);
        glm = new GridLayoutManager(getActivity(),2);
        //adapter = new ListaNeolinks(datos);
        rv.setAdapter(adapter);


        return estacosa;
    }
}
