package com.greenbird.neolink_app;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;
import com.example.neolink_app.adaptadores.Listadefamiliareshijos;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

import java.util.ArrayList;
import java.util.Random;

public class configuracioncuenta extends Fragment {

    private MasterDrawerViewModel archi;
    private TextView tokendevinculacion;
    private Button botondegenerar;
    private RecyclerView listadefamiliares;
    private Listadefamiliareshijos adapter;
    private GridLayoutManager glm;
    private AlertDialog.Builder dialogodeborrado;
    private Button botoningresaradrive;
    private Button botoncuentaconfirmada;
    private LinearLayout layoutdeusuarioconfirmado;
    private TextView mensajebienvenidadedrive;
    private Spinner opcionesdelapsodetiempo;
    private Button botongenerarreporte;
    private String [] listadeopciones ={"Últimos 15 días","Últimos 30 días","Últimos 12 meses"};
    private ArrayAdapter<String> adapterlapsodetiempo;

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
        dialogodeborrado = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),R.style.AlertDialogCustom));
        dialogodeborrado.setMessage("¿Esta seguro que quiere eliminar esta cuenta de su lista de invitados?");
        tokendevinculacion = view.findViewById(R.id.tokendelvinculo);
        botondegenerar = view.findViewById(R.id.botondegenerar);
        botondegenerar.setOnClickListener(botongenerar);
        botoncuentaconfirmada = view.findViewById(R.id.botonconfirmaciondecuenta);
        listadefamiliares = view.findViewById(R.id.usuariosfamiliares);
        botoningresaradrive = view.findViewById(R.id.botonparaingresarcuentaG);
        botoningresaradrive.setOnClickListener(botoningresoacuentadrive);
        layoutdeusuarioconfirmado = view.findViewById(R.id.dialogodeusuarioconectado);
        mensajebienvenidadedrive = view.findViewById(R.id.cuentadedrive);
        opcionesdelapsodetiempo = view.findViewById(R.id.opcionesdereportes);
        botongenerarreporte = view.findViewById(R.id.generarreporte);
        botongenerarreporte.setOnClickListener(botongenerarreporteclick);
        adapterlapsodetiempo = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, listadeopciones); // Aqui puedo editar el estilo
        adapterlapsodetiempo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opcionesdelapsodetiempo.setAdapter(adapterlapsodetiempo);
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
                    adapter = new Listadefamiliareshijos(arrayListArrayListPair.second,archi,getViewLifecycleOwner(),dialogodeborrado);
                    listadefamiliares.setAdapter(adapter);
                }
            }
        });
    }
    private View.OnClickListener botongenerar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(archi.cualeselestadofamiliar()){
                archi.actualizaravizonoerespadre(v);
            } else {
                String tok = getRandomString(10);
                tokendevinculacion.setText(tok);
                archi.guardartokenparavincular(tok);
            }
        }
    };
    private View.OnClickListener botoningresoacuentadrive = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            usuarioaceptado();
        }
    };
    private View.OnClickListener botongenerarreporteclick = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

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

    private void usuarioaceptado(){
        layoutdeusuarioconfirmado.setVisibility(View.VISIBLE);
        botoningresaradrive.setVisibility(View.GONE);
        botoncuentaconfirmada.setVisibility(View.VISIBLE);
        String cuenta = "yo";
        String completo = "Bienvenido "+cuenta;
        mensajebienvenidadedrive.setText(completo);
    }
}