package com.example.neolink_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.adaptadores.viewpagergrafiquitosAdapter;
import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;

import java.util.ArrayList;
import java.util.Calendar;


public class panelesgrafiquito extends Fragment {
    private String neolinkname;
    private Calendar ahora = Calendar.getInstance();
    private MasterDrawerViewModel archi;
    private ViewPager2 vp;
    private viewpagergrafiquitosAdapter adapter;

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
        int hoyaño = ahora.get(Calendar.YEAR)%100;
        int hoymes = ahora.get(Calendar.MONTH)+1;
        int hoydia = ahora.get(Calendar.DAY_OF_MONTH);

        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        vp = view.findViewById(R.id.viewpagergraficos);
        String nombre = "NL2006-0002";
        String sensor = "k";
        ArrayList<String> nodaso = new ArrayList<>();
        nodaso.add(nombre);
        final ArrayList<String> nodito = nodaso;
        archi.getLivedaylydata(nombre,hoyaño,hoymes,hoydia,sensor);
        archi.datahoy.observe(getViewLifecycleOwner(), new Observer<Horas>(){
            @Override
            public void onChanged(Horas horas) {
                adapter = new viewpagergrafiquitosAdapter(getActivity(),horas,nodito);
                vp.setAdapter(adapter);
            }
        });
        ((actividadbase)getActivity()).fabcalendar();
        ((actividadbase)getActivity()).fabaparecer();
        ((actividadbase)getActivity()).fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    public void onDestroy() {
        super.onDestroy();
        ((actividadbase)getActivity()).fabdesparecer();
        ((actividadbase)getActivity()).fabplus();
    }
}
