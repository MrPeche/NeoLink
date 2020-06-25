package com.example.neolink_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

import java.util.Calendar;


public class panelesgrafiquito extends Fragment {
    private String neolinkname;
    private Calendar ahora = Calendar.getInstance();
    private MasterDrawerViewModel archi;

    public panelesgrafiquito() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.neolinkname = panelesgrafiquitoArgs.fromBundle(getArguments()).getNeolinkselected();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_panelesgrafiquito, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        int hoyaño = ahora.get(Calendar.YEAR);
        int hoymes = ahora.get(Calendar.MONTH);
        int hoydia = ahora.get(Calendar.DAY_OF_MONTH);

        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        String nombre = "NL2006-0002";
        String sensor = "k";
        archi.getLivedaylydata(nombre,hoyaño,hoymes,hoydia,sensor);



    }

}
