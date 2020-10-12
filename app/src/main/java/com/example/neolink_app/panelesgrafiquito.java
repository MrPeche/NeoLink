package com.example.neolink_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.adaptadores.viewpagergrafiquitosAdapter;
import com.example.neolink_app.clases.Horas;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.clases.database_state.horasstate;
import com.example.neolink_app.clases.liveclases.livedaylydatapackage;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class panelesgrafiquito extends Fragment {
    private String neolinkname;
    private MasterDrawerViewModel archi;
    private ViewPager2 vp;
    private viewpagergrafiquitosAdapter adapter;
    private String dateselected;
    private ArrayList<String> nodo;
    private String [] lista ={"Ayer - Hoy","Esta semana","Este mes","Este año","Elige una fecha"}; //"Ayer - Hoy","Este mes","Este año","Elige una fecha"
    /*
    private MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
    */
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


        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
        vp = view.findViewById(R.id.viewpagergraficos);
        String nombre = "NL2006-0002";
        final String sensor = "k";
        ArrayList<String> nodaso = new ArrayList<>();
        nodaso.add(nombre);

        /*
        setmycalendarbruh();
        builder.setCalendarConstraints(buildtheconstraint().build());
        final MaterialDatePicker materialdatepick = builder.build();
        */
        final ArrayList<String> nodito = nodaso;
        /*
        archi.getLivedaylydata(nombre,hoyaño,hoymes,hoydia,sensor);
        archi.datahoy.observe(getViewLifecycleOwner(), new Observer<Horas>(){
            @Override
            public void onChanged(Horas horas) {
                adapter = new viewpagergrafiquitosAdapter(getActivity(),horas,nodito);
                vp.setAdapter(adapter);
            }
        });
        */

        archi.retrivepaqueteDatos(nombre);

        /*
        if(!archi.paquetesdedata.hasObservers()) {
            archi.livelydatafull(nombre, hoyaño, hoymes, hoydia, sensor);
        }
        */
        archi.retrivedate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    if(s.equals(lista[0])){
                        archi.changemode(0);

                    } else if(s.equals(lista[1])){
                        archi.changemode(1);

                    } else if(s.equals(lista[2])){
                        archi.changemode(2);

                    } else if(s.equals(lista[3])){
                        archi.changemode(3);

                    } else {
                        archi.changemode(4);

                    }
                }
            }
        });
        /*
        archi.paquetesdedata.observe(getViewLifecycleOwner(), new Observer<Pair<Horas,horasstate>>() {
            @Override
            public void onChanged(Pair<Horas, horasstate> horashorasstatePair) {
                Boolean ver = archi.paquetesdedata.isitready();
                if(archi.paquetesdedata.isitready()){
                    adapter = new viewpagergrafiquitosAdapter(getActivity(),horashorasstatePair,nodito);
                    vp.setAdapter(adapter);
                }
            }
        });
        */
        /*
        archi.paquetesdedata2dias.observe(getViewLifecycleOwner(), new Observer<Pair<Pair<Horas,horasstate>,Pair<Horas,horasstate>>>() {
            @Override
            public void onChanged(Pair<Pair<Horas, horasstate>, Pair<Horas, horasstate>> pairPairPair) {
                if(archi.paquetesdedata2dias.isitready()){
                    adapter = new viewpagergrafiquitosAdapter(getActivity(),pairPairPair.first,nodito);
                    vp.setAdapter(adapter);
                }
            }
        });
        */
        archi.Usuarioneolinks.observe(getViewLifecycleOwner(), new Observer<OWNERitems>() {
            @Override
            public void onChanged(OWNERitems owneRitems) {
                nodo = owneRitems.damelista();
                adapter = new viewpagergrafiquitosAdapter(getActivity(),nodo);
                vp.setAdapter(adapter);
            }
        });


        ((actividadbase)getActivity()).fabcalendar();
        ((actividadbase)getActivity()).fabaparecer();
        ((actividadbase)getActivity()).fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(panelesgrafiquitoDirections.actionPanelesgrafiquitoToDialogfechagraf());
            }
        });
        /*
        materialdatepick.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {

                Calendar desde = traductordecalendario(selection.first);
                Calendar hasta = traductordecalendario(selection.second);
                int diaD = desde.get(Calendar.DAY_OF_MONTH);
                int diaH = hasta.get(Calendar.DAY_OF_MONTH);
                int mont1 = desde.get(Calendar.MONTH);
                int mont2 = hasta.get(Calendar.MONTH);
            }
        });
        */
    }
    public void onDestroy() {
        super.onDestroy();
        ((actividadbase)getActivity()).fabdesparecer();
        ((actividadbase)getActivity()).fabplus();
    }

}
