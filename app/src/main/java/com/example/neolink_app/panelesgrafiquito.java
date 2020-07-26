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
    private Calendar ahora = Calendar.getInstance();
    private MasterDrawerViewModel archi;
    private ViewPager2 vp;
    private viewpagergrafiquitosAdapter adapter;
    private String dateselected;
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
        int hoyaño = ahora.get(Calendar.YEAR)%100;
        int hoymes = ahora.get(Calendar.MONTH)+1;
        int hoydia = ahora.get(Calendar.DAY_OF_MONTH);


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
                    dateselected = s;
                    archi.updatedate(managedate(s,1),managedate(s,2),managedate(s,3));
                }
            }
        });

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

    /*
    private void setmycalendarbruh(){
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        this.builder.setTheme(R.style.materialtheme);
        this.builder.setTitleText("Seleccione el rango de días que quiere visualizar");
        //this.builder.setSelection(today);
    }

    private CalendarConstraints.Builder buildtheconstraint(){
        CalendarConstraints.Builder constraing = new CalendarConstraints.Builder();
        constraing.setValidator(DateValidatorPointBackward.now());
        Calendar iniciocalendario = GregorianCalendar.getInstance();
        iniciocalendario.set(2020,0,1);
        constraing.setStart(iniciocalendario.getTimeInMillis());
        return constraing;
    }
    */
    private Calendar traductordecalendario(long date){
        Calendar resultado = Calendar.getInstance();
        resultado.setTimeInMillis(date);
        return resultado;
    }
    private int managedate(String date, int whichone){
        int resultado = 0;
        String []split = date.split("/");
        switch(whichone){
            case 1:
                resultado= Integer.parseInt(split[0]);
                break;
            case 2:
                resultado= Integer.parseInt(split[1]);
                break;
            case 3:
                resultado= Integer.parseInt(split[2]);
                break;
        }
        return resultado;
    }
}
