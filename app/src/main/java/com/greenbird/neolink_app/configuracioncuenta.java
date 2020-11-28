package com.greenbird.neolink_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.neolink_app.R;
import com.example.neolink_app.adaptadores.ListaNeolinks;
import com.example.neolink_app.adaptadores.Listadefamiliareshijos;
import com.example.neolink_app.listita;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class configuracioncuenta extends Fragment {

    private MasterDrawerViewModel archi;
    private TextView tokendevinculacion;
    private Button botondegenerar;
    private RecyclerView listadefamiliares;
    private Listadefamiliareshijos adapter;
    private GridLayoutManager glm;

    public configuracioncuenta() {
        // Required empty public constructor
    }

    public static configuracioncuenta newInstance(String param1, String param2) {
        configuracioncuenta fragment = new configuracioncuenta();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (getArguments() != null) {

        }
         */
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_configuracioncuenta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tokendevinculacion = view.findViewById(R.id.tokendelvinculo);
        botondegenerar = view.findViewById(R.id.botondegenerar);
        botondegenerar.setOnClickListener(botongenerar);
        listadefamiliares = view.findViewById(R.id.usuariosfamiliares);
        glm = new GridLayoutManager(getActivity(),1);
        listadefamiliares.setLayoutManager(glm);
        archi.tokendevinculoanterior().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null){
                    if(!s.equals(tokendevinculacion.getText().toString())){
                        tokendevinculacion.setText(s);
                    }
                }
            }
        });
        archi.mostrarhijos().observe(getViewLifecycleOwner(), new Observer<Pair<ArrayList<String>, ArrayList<String>>>() {
            @Override
            public void onChanged(Pair<ArrayList<String>, ArrayList<String>> arrayListArrayListPair) {
                if(arrayListArrayListPair!=null){
                    //adapter = new Listadefamiliareshijos(arrayListArrayListPair.second);
                    adapter = new Listadefamiliareshijos(arrayListArrayListPair.second,archi,getViewLifecycleOwner());
                    listadefamiliares.setAdapter(adapter);
                }
            }
        });


    }
    private View.OnClickListener botongenerar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tok = getRandomString(10);
            tokendevinculacion.setText(tok);
            archi.guardartokenparavincular(tok);
        }
    };

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}