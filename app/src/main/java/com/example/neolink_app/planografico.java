package com.example.neolink_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.neolink_app.adaptadores.MarkerLineChartAdapter;
import com.example.neolink_app.adaptadores.MarkerLineChartDefault;
import com.example.neolink_app.adaptadores.graficolabelgenerator;
import com.example.neolink_app.adaptadores.labelpersonalizadoX;
import com.example.neolink_app.clases.DepthPackage;
import com.example.neolink_app.clases.clasesdecharts.chartlinealparadapter;
import com.example.neolink_app.clases.clasesparaformargraficos.InfoParaGraficos;
import com.example.neolink_app.clases.clasesparaformargraficos.fulldatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.gdatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.kdatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.npkdatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.phdatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.statedatapack;
import com.example.neolink_app.clases.configuracion.statelimitsport;
import com.example.neolink_app.clases.configuracion.statesinglelimitspecialvalues;
import com.example.neolink_app.clases.configuracion.statesinglelimitvalues;
import com.example.neolink_app.clases.paquetedatasetPuertos;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class planografico extends Fragment {
    private TextView nombre;
    private String name;
    private chartlinealparadapter grafico1;
    private chartlinealparadapter grafico2;
    private LineChart grafico3;
    private chartlinealparadapter graficopresionbarometrica;
    private chartlinealparadapter graficohumedadrelativa;
    private chartlinealparadapter graficotemperaturavulvoseco;
    private chartlinealparadapter graficoph;
    private chartlinealparadapter graficonitrato;
    private chartlinealparadapter graficofosfato;
    private chartlinealparadapter graficopotasio;
    //private LineChart graficohumedaddelsuelo;
    private chartlinealparadapter graficotemperaturadelsuelo;
    private chartlinealparadapter graficoconductividaddelsuelo;
    private chartlinealparadapter graficorosadevientos;
    private chartlinealparadapter graficoconductividadelectricadelporo;
    private chartlinealparadapter graficocontenidovolumetricodelagua;

    private CardView cvpotencialMatricial;
    private CardView cvtemperatura;
    private CardView cvEnergia;
    //private CardView cvhumedaddelsuelo;
    private CardView cvPh;
    private CardView cvnitrato;
    private CardView cvfosfato;
    private CardView cvpotasio;
    private CardView cvtemperaturadelsuelo;
    private CardView cvconductividadelectrica;
    private CardView cvHumedadrelativa;
    private CardView cvpresionbarometrica;
    private CardView cvtemperaturabulboseco;
    private CardView cvrosadevientos;
    private CardView cvconductividadelectricadelporo;
    private CardView cvcontenidovolumetricodelagua;

    private MasterDrawerViewModel archi;
    //private MarkerLineChartAdapter adapter;
    private int alpha = 170;    //170
    private int[] colores = {Color.argb(alpha,250,128,114),Color.argb(alpha,60,179,113),Color.argb(alpha,100,149,237),Color.argb(alpha,147,112,219)}; //salmon, medium sea green,corn flower blue, medium purple https://www.rapidtables.com/web/color/RGB_Color.html
    private int MAX_DATAVISIBLE = 48;
    private float LINEWIDTH = 2.5f;
    private static float OFFSETGRAFPHTOP = 20f;
    private static float OFFSETGRAFPHLEFT = 10f;
    private static float OFFSETGRAFPHBOTTOM = 15f;

    private AlertDialog.Builder builder;
    private AppCompatEditText input;


    public planografico() {
        // Required empty public constructor
    }

    public static planografico newInstance(String name) {
        planografico fragment = new planografico();
        Bundle args = new Bundle();
        //args.putParcelable("paquete",paquete);
        args.putString("nombre",name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //this.paquete = getArguments().getParcelable("paquete");
            this.name = getArguments().getString("nombre");
            //this.state = getArguments().getParcelable("state");
        }
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planografico, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        builder = new AlertDialog.Builder(getActivity());
        input = new AppCompatEditText(getActivity());

        nombre = view.findViewById(R.id.Nombrepager);
        nombre.setText(name);
        grafico1 = view.findViewById(R.id.graficochart1);
        grafico2= view.findViewById(R.id.graficochar2);
        grafico3= view.findViewById(R.id.graficoBat);
        graficohumedadrelativa= view.findViewById(R.id.graficoHumedadRelativa);
        graficopresionbarometrica= view.findViewById(R.id.graficoPresionBarometrica);
        graficotemperaturavulvoseco= view.findViewById(R.id.graficotemperaturavulvoseco);
        graficotemperaturadelsuelo= view.findViewById(R.id.graficotemperaturadelsuelo);
        graficoph = view.findViewById(R.id.graficoph);
        graficonitrato = view.findViewById(R.id.graficonitrato);
        graficofosfato = view.findViewById(R.id.graficofosfato);
        graficopotasio = view.findViewById(R.id.graficopotasio);
        //graficohumedaddelsuelo = view.findViewById(R.id.graficohumedaddelsuelo);
        graficoconductividaddelsuelo = view.findViewById(R.id.graficoConductividadSuelo);
        graficoconductividadelectricadelporo = view.findViewById(R.id.graficoconductividadPoro);
        graficocontenidovolumetricodelagua = view.findViewById(R.id.graficocontenidovolumetricodelagua);
        graficorosadevientos = view.findViewById(R.id.graficorosadevientos);
        cvpotencialMatricial = view.findViewById(R.id.cvpotencialMatricial);
        cvtemperatura = view.findViewById(R.id.cvtemperatura);
        cvEnergia = view.findViewById(R.id.cvEnergia);
        cvPh = view.findViewById(R.id.cvph);
        cvnitrato = view.findViewById(R.id.cvnitrato);
        cvfosfato = view.findViewById(R.id.cvfosfato);
        cvpotasio = view.findViewById(R.id.cvpotasio);
        //cvhumedaddelsuelo = view.findViewById(R.id.cvhumedaddelsuelo);
        cvtemperaturadelsuelo = view.findViewById(R.id.cvtemperaturadelsuelo);
        cvconductividadelectrica = view.findViewById(R.id.cvconductividadelectrica);
        cvHumedadrelativa = view.findViewById(R.id.cvHumedadrelativa);
        cvpresionbarometrica = view.findViewById(R.id.cvpresionbarometrica);
        cvtemperaturabulboseco = view.findViewById(R.id.cvtemperaturabulboseco);
        cvconductividadelectricadelporo = view.findViewById(R.id.cvconductividadelectricadelporo);
        cvcontenidovolumetricodelagua = view.findViewById(R.id.cvcontenidovolumetricodelagua);
        cvrosadevientos = view.findViewById(R.id.cvrosadevientos);
        propiedadesgraficoPM();
        propiedadesgraficoTem();
        propiedadesgrafico3();
        propiedadesgraficohumedadrelativa();
        propiedadesgraficopresionbarometrica();
        propiedadesgraficotemperaturavulvoseco();
        propiedadesgraficoconductividadelectricadelporo();
        propiedadesgraficocontenidovolumetricodelagua();
        propiedadesgraficorosadelviento();
        propiedadesgraficoph();
        propiedadesgraficonitrato();
        propiedadesgraficofosfato();
        propiedadesgraficopotasio();
        //propiedadesgraficohumedaddeldelsuelo();
        propiedadesgraficotemperaturadeldelsuelo();
        propiedadesgraficoconductividaddeldelsuelo();
        startposition();

        archi.fecthlimits(name).observe(getViewLifecycleOwner(), new Observer<statelimitsport>() {
            @Override
            public void onChanged(statelimitsport statelimitsport) {
                if(statelimitsport!=null){
                    arrangelimitlines(statelimitsport);
                }
            }
        });

        archi.masterdatedatapackage(name).observe(getViewLifecycleOwner(), new Observer<InfoParaGraficos>() {
            @Override
            public void onChanged(InfoParaGraficos infoParaGraficos) {
                if(infoParaGraficos!=null){
                    if(infoParaGraficos.validarlosdias()){
                        fulldatapack commdata = infoParaGraficos.managedias();
                        //Kdata
                        if(commdata.sacarkdatapack().sacarloslabels().size()!=0){
                            cleanthisshit();
                            cvpotencialMatricial.setVisibility(View.VISIBLE);
                            cvtemperatura.setVisibility(View.VISIBLE);
                            setgraficosK(commdata.sacarkdatapack());
                        }
                        if(commdata.sacarstatedatapack().sacaraxislabels().size()!=0){
                            cvEnergia.setVisibility(View.VISIBLE);
                            cvHumedadrelativa.setVisibility(View.VISIBLE);
                            cvpresionbarometrica.setVisibility(View.VISIBLE);
                            cvtemperaturabulboseco.setVisibility(View.VISIBLE);
                            cvrosadevientos.setVisibility(View.VISIBLE);
                            setgraficosstate(commdata.sacarstatedatapack());
                        }
                        if(commdata.sacargdatapack().sacarlabels().size()!=0){
                            cleanG();
                            cvconductividadelectrica.setVisibility(View.VISIBLE);
                            //cvhumedaddelsuelo.setVisibility(View.VISIBLE);
                            cvtemperaturadelsuelo.setVisibility(View.VISIBLE);
                            cvconductividadelectricadelporo.setVisibility(View.VISIBLE);
                            cvcontenidovolumetricodelagua.setVisibility(View.VISIBLE);
                            setgraficoG(commdata.sacargdatapack());
                        }
                        if(commdata.sacarnpkdatapack().sacarlabels().size()!=0){
                            cvnitrato.setVisibility(View.VISIBLE);
                            cvfosfato.setVisibility(View.VISIBLE);
                            cvpotasio.setVisibility(View.VISIBLE);
                            setgraficonpk(commdata.sacarnpkdatapack());
                        }
                        if(commdata.sacarphdatapack().sacarlabels().size()!=0){
                            cvPh.setVisibility(View.VISIBLE);
                            setgraficoph(commdata.sacarphdatapack());
                        }
                        /*
                        cleanthisshit();
                        cvpotencialMatricial.setVisibility(View.VISIBLE);
                        cvtemperatura.setVisibility(View.VISIBLE);
                        setgraficosK(commdata.sacarkdatapack());
                        //state
                        cvEnergia.setVisibility(View.VISIBLE);
                        cvHumedadrelativa.setVisibility(View.VISIBLE);
                        cvpresionbarometrica.setVisibility(View.VISIBLE);
                        cvtemperaturabulboseco.setVisibility(View.VISIBLE);
                        cvrosadevientos.setVisibility(View.VISIBLE);
                        setgraficosstate(commdata.sacarstatedatapack());
                        //sensorg
                        cleanG();
                        cvconductividadelectrica.setVisibility(View.VISIBLE);
                        //cvhumedaddelsuelo.setVisibility(View.VISIBLE);
                        cvtemperaturadelsuelo.setVisibility(View.VISIBLE);
                        cvconductividadelectricadelporo.setVisibility(View.VISIBLE);
                        cvcontenidovolumetricodelagua.setVisibility(View.VISIBLE);
                        setgraficoG(commdata.sacargdatapack());
                         */

                    }
                }
            }
        });

    }
    private void cleanthisshit(){
        grafico1.clear();
        grafico2.clear();
        grafico3.clear();
    }
    private void cleanG(){
        graficotemperaturadelsuelo.clear();
        //graficohumedaddelsuelo.clear();
        graficoconductividaddelsuelo.clear();
        graficocontenidovolumetricodelagua.clear();
        graficoconductividadelectricadelporo.clear();
    }
    private void startposition(){
        cvpotencialMatricial.setVisibility(View.GONE);
        cvtemperatura.setVisibility(View.GONE);
        cvEnergia.setVisibility(View.GONE);
        cvPh.setVisibility(View.GONE);
        cvnitrato.setVisibility(View.GONE);
        cvfosfato.setVisibility(View.GONE);
        cvpotasio.setVisibility(View.GONE);
        //cvhumedaddelsuelo.setVisibility(View.GONE);
        cvtemperaturadelsuelo.setVisibility(View.GONE);
        cvconductividadelectrica.setVisibility(View.GONE);
        cvHumedadrelativa.setVisibility(View.GONE);
        cvpresionbarometrica.setVisibility(View.GONE);
        cvtemperaturabulboseco.setVisibility(View.GONE);
        cvcontenidovolumetricodelagua.setVisibility(View.GONE);
        cvconductividadelectricadelporo.setVisibility(View.GONE);
        cvrosadevientos.setVisibility(View.GONE);
    }
    public void propiedadesgraficoPM(){
        grafico1.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        grafico1.setDrawGridBackground(false);
        grafico1.setDrawBorders(false);
        //grafico1.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        grafico1.getDescription().setEnabled(false);
        grafico1.setTouchEnabled(true);
        grafico1.setDragEnabled(true);
        grafico1.setScaleYEnabled(false);
        grafico1.setScaleXEnabled(true);
        Legend L = grafico1.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    public void propiedadesgraficoph(){
        graficoph.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficoph.setDrawGridBackground(false);
        graficoph.setDrawBorders(false);
        //grafico1.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficoph.getDescription().setEnabled(false);
        graficoph.setTouchEnabled(true);
        graficoph.setDragEnabled(true);
        graficoph.setScaleYEnabled(false);
        graficoph.setScaleXEnabled(true);
        Legend L = graficoph.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    public void propiedadesgraficonitrato(){
        graficonitrato.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficonitrato.setDrawGridBackground(false);
        graficonitrato.setDrawBorders(false);
        //grafico1.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficonitrato.getDescription().setEnabled(false);
        graficonitrato.setTouchEnabled(true);
        graficonitrato.setDragEnabled(true);
        graficonitrato.setScaleYEnabled(false);
        graficonitrato.setScaleXEnabled(true);
        Legend L = graficonitrato.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    public void propiedadesgraficofosfato(){
        graficofosfato.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficofosfato.setDrawGridBackground(false);
        graficofosfato.setDrawBorders(false);
        //grafico1.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficofosfato.getDescription().setEnabled(false);
        graficofosfato.setTouchEnabled(true);
        graficofosfato.setDragEnabled(true);
        graficofosfato.setScaleYEnabled(false);
        graficofosfato.setScaleXEnabled(true);
        Legend L = graficofosfato.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    public void propiedadesgraficopotasio(){
        graficopotasio.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficopotasio.setDrawGridBackground(false);
        graficopotasio.setDrawBorders(false);
        //grafico1.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficopotasio.getDescription().setEnabled(false);
        graficopotasio.setTouchEnabled(true);
        graficopotasio.setDragEnabled(true);
        graficopotasio.setScaleYEnabled(false);
        graficopotasio.setScaleXEnabled(true);
        Legend L = graficopotasio.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    public void propiedadesgraficoTem(){
        grafico2.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        grafico2.setDrawGridBackground(false);
        grafico2.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        grafico2.getDescription().setEnabled(false);
        grafico2.setTouchEnabled(true);
        grafico2.setDragEnabled(true);
        grafico2.setScaleYEnabled(false);
        grafico2.setScaleXEnabled(true);
        Legend L = grafico2.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    public void propiedadesgrafico3(){
        grafico3.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        grafico3.setDrawGridBackground(false);
        grafico3.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        grafico3.getDescription().setEnabled(false);
        grafico3.setTouchEnabled(true);
        grafico3.setDragEnabled(true);
        grafico3.setScaleYEnabled(false);
        grafico3.setScaleXEnabled(true);
        Legend L = grafico3.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    public void propiedadesgraficohumedadrelativa(){
        graficohumedadrelativa.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficohumedadrelativa.setDrawGridBackground(false);
        graficohumedadrelativa.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficohumedadrelativa.getDescription().setEnabled(false);
        graficohumedadrelativa.setTouchEnabled(true);
        graficohumedadrelativa.setDragEnabled(true);
        graficohumedadrelativa.setScaleYEnabled(false);
        graficohumedadrelativa.setScaleXEnabled(true);
        Legend L = graficohumedadrelativa.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    public void propiedadesgraficopresionbarometrica(){
        graficopresionbarometrica.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficopresionbarometrica.setDrawGridBackground(false);
        graficopresionbarometrica.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficopresionbarometrica.getDescription().setEnabled(false);
        graficopresionbarometrica.setTouchEnabled(true);
        graficopresionbarometrica.setDragEnabled(true);
        graficopresionbarometrica.setScaleYEnabled(false);
        graficopresionbarometrica.setScaleXEnabled(true);
        Legend L = graficopresionbarometrica.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    public void propiedadesgraficotemperaturavulvoseco(){
        graficotemperaturavulvoseco.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficotemperaturavulvoseco.setDrawGridBackground(false);
        graficotemperaturavulvoseco.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficotemperaturavulvoseco.getDescription().setEnabled(false);
        graficotemperaturavulvoseco.setTouchEnabled(true);
        graficotemperaturavulvoseco.setDragEnabled(true);
        graficotemperaturavulvoseco.setScaleYEnabled(false);
        graficotemperaturavulvoseco.setScaleXEnabled(true);
        Legend L = graficotemperaturavulvoseco.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    /*
    private void propiedadesgraficohumedaddeldelsuelo(){
        graficohumedaddelsuelo.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficohumedaddelsuelo.setDrawGridBackground(false);
        graficohumedaddelsuelo.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficohumedaddelsuelo.getDescription().setEnabled(false);
        graficohumedaddelsuelo.setTouchEnabled(true);
        graficohumedaddelsuelo.setDragEnabled(true);
        graficohumedaddelsuelo.setScaleYEnabled(false);
        graficohumedaddelsuelo.setScaleXEnabled(true);
        Legend L = graficohumedaddelsuelo.getLegend();
    }
     */
    private void propiedadesgraficotemperaturadeldelsuelo(){
        graficotemperaturadelsuelo.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficotemperaturadelsuelo.setDrawGridBackground(false);
        graficotemperaturadelsuelo.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficotemperaturadelsuelo.getDescription().setEnabled(false);
        graficotemperaturadelsuelo.setTouchEnabled(true);
        graficotemperaturadelsuelo.setDragEnabled(true);
        graficotemperaturadelsuelo.setScaleYEnabled(false);
        graficotemperaturadelsuelo.setScaleXEnabled(true);
        Legend L = graficotemperaturadelsuelo.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    private void propiedadesgraficoconductividaddeldelsuelo(){
        graficoconductividaddelsuelo.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficoconductividaddelsuelo.setDrawGridBackground(false);
        graficoconductividaddelsuelo.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficoconductividaddelsuelo.getDescription().setEnabled(false);
        graficoconductividaddelsuelo.setTouchEnabled(true);
        graficoconductividaddelsuelo.setDragEnabled(true);
        graficoconductividaddelsuelo.setScaleYEnabled(false);
        graficoconductividaddelsuelo.setScaleXEnabled(true);
        Legend L = graficoconductividaddelsuelo.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    private void propiedadesgraficoconductividadelectricadelporo(){
        graficoconductividadelectricadelporo.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficoconductividadelectricadelporo.setDrawGridBackground(false);
        graficoconductividadelectricadelporo.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficoconductividadelectricadelporo.getDescription().setEnabled(false);
        graficoconductividadelectricadelporo.setTouchEnabled(true);
        graficoconductividadelectricadelporo.setDragEnabled(true);
        graficoconductividadelectricadelporo.setScaleYEnabled(false);
        graficoconductividadelectricadelporo.setScaleXEnabled(true);
        Legend L = graficoconductividadelectricadelporo.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    private void propiedadesgraficocontenidovolumetricodelagua(){
        graficocontenidovolumetricodelagua.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficocontenidovolumetricodelagua.setDrawGridBackground(false);
        graficocontenidovolumetricodelagua.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficocontenidovolumetricodelagua.getDescription().setEnabled(false);
        graficocontenidovolumetricodelagua.setTouchEnabled(true);
        graficocontenidovolumetricodelagua.setDragEnabled(true);
        graficocontenidovolumetricodelagua.setScaleYEnabled(false);
        graficocontenidovolumetricodelagua.setScaleXEnabled(true);
        Legend L = graficocontenidovolumetricodelagua.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    private void propiedadesgraficorosadelviento(){
        graficorosadevientos.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficorosadevientos.setDrawGridBackground(false);
        graficorosadevientos.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficorosadevientos.getDescription().setEnabled(false);
        graficorosadevientos.setTouchEnabled(true);
        graficorosadevientos.setDragEnabled(true);
        graficorosadevientos.setScaleYEnabled(false);
        graficorosadevientos.setScaleXEnabled(true);
        Legend L = graficorosadevientos.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    private void arrangelimitlines(statelimitsport obj){
        limitesnormales(obj.dameP1().damek().dameV1(),obj.dameP2().damek().dameV1(),obj.dameP3().damek().dameV1(),obj.dameP4().damek().dameV1(),0);
        limitesnormales(obj.dameP1().damek().dameV2(),obj.dameP2().damek().dameV2(),obj.dameP3().damek().dameV2(),obj.dameP4().damek().dameV2(),1);
        limitesnormales(obj.dameP1().dameG().dameV2(),obj.dameP2().dameG().dameV2(),obj.dameP3().dameG().dameV2(),obj.dameP4().dameG().dameV2(),2);
        limitesnormales(obj.dameP1().dameG().dameV3(),obj.dameP2().dameG().dameV3(),obj.dameP3().dameG().dameV3(),obj.dameP4().dameG().dameV3(),3);
        limitesnormales(obj.dameP1().dameG().damePoreCer(),obj.dameP2().dameG().damePoreCer(),obj.dameP3().dameG().damePoreCer(),obj.dameP4().dameG().damePoreCer(),4);
        limiteespecial(obj.dameP1().dameG().damevwc(),obj.dameP2().dameG().damevwc(),obj.dameP3().dameG().damevwc(),obj.dameP4().dameG().damevwc());
    }
    private void limitesnormales(statesinglelimitvalues puerto1, statesinglelimitvalues puerto2, statesinglelimitvalues puerto3, statesinglelimitvalues puerto4, int caso){
        YAxis yAxis = dameelaxiselegido(caso);
        yAxis.setDrawLimitLinesBehindData(true);
        if(deintaboolean(puerto1.damebool())){
            yAxis.addLimitLine(crearunlimite((float) puerto1.dameMAX(),0));
            yAxis.addLimitLine(crearunlimite((float) puerto1.dameMin(),0));
        } else if(deintaboolean(puerto2.damebool())){
            yAxis.addLimitLine(crearunlimite((float) puerto2.dameMAX(),1));
            yAxis.addLimitLine(crearunlimite((float) puerto2.dameMin(),1));
        } else if(deintaboolean(puerto3.damebool())){
            yAxis.addLimitLine(crearunlimite((float) puerto3.dameMAX(),2));
            yAxis.addLimitLine(crearunlimite((float) puerto3.dameMin(),2));
        } else if(deintaboolean(puerto4.damebool())){
            yAxis.addLimitLine(crearunlimite((float) puerto4.dameMAX(),3));
            yAxis.addLimitLine(crearunlimite((float) puerto4.dameMin(),3));
        }
        invalidation(caso);
    }
    private void limiteespecial(statesinglelimitspecialvalues puerto1, statesinglelimitspecialvalues puerto2, statesinglelimitspecialvalues puerto3, statesinglelimitspecialvalues puerto4){
        YAxis yAxis = dameelaxiselegido(5);
        yAxis.setDrawLimitLinesBehindData(true);
        if(deintaboolean(puerto1.damebool())){
            yAxis.addLimitLine(crearunlimite((float) puerto1.dameCC(),0));
            yAxis.addLimitLine(crearunlimite((float) puerto1.damePMP(),0));
            yAxis.addLimitLine(crearunlimite((float) puerto1.dameAU(),0));
        } else if(deintaboolean(puerto2.damebool())){
            yAxis.addLimitLine(crearunlimite((float) puerto2.dameCC(),1));
            yAxis.addLimitLine(crearunlimite((float) puerto2.damePMP(),1));
            yAxis.addLimitLine(crearunlimite((float) puerto2.dameAU(),1));
        } else if(deintaboolean(puerto3.damebool())){
            yAxis.addLimitLine(crearunlimite((float) puerto3.dameCC(),2));
            yAxis.addLimitLine(crearunlimite((float) puerto3.damePMP(),2));
            yAxis.addLimitLine(crearunlimite((float) puerto3.dameAU(),2));
        } else if(deintaboolean(puerto4.damebool())){
            yAxis.addLimitLine(crearunlimite((float) puerto4.dameCC(),3));
            yAxis.addLimitLine(crearunlimite((float) puerto4.damePMP(),3));
            yAxis.addLimitLine(crearunlimite((float) puerto4.dameAU(),3));
        }
        invalidation(5);
    }
    private LimitLine crearunlimite(float valor, int puerto){
        LimitLine RS = new LimitLine(valor);
        RS.setLineColor(colores[puerto]);
        RS.setLineWidth(0.9f);
        RS.enableDashedLine(30,10,10);
        return RS;
    }
    private boolean deintaboolean(int data){
        return data==1;
    }
    private void invalidation(int caso){
        if(caso==0){
            grafico1.invalidate();
        } else if(caso==1){
            grafico2.invalidate();
        } else if(caso==2){
            graficotemperaturadelsuelo.invalidate();
        } else if(caso==3){
            graficoconductividaddelsuelo.invalidate();
        } else if(caso==4){
            graficoconductividadelectricadelporo.invalidate();
        } else graficocontenidovolumetricodelagua.invalidate();
    }
    private YAxis dameelaxiselegido(int caso){
        if(caso==0){
            return grafico1.getAxisLeft(); //K v1
        } else if(caso==1){
            return grafico2.getAxisLeft(); //k v2
        } else if(caso==2){
            return graficotemperaturadelsuelo.getAxisLeft(); //g v2
        } else if(caso==3){
            return graficoconductividaddelsuelo.getAxisLeft(); //g v3
        } else if(caso==4){
            return graficoconductividadelectricadelporo.getAxisLeft(); //g PoreCer
        } else return graficocontenidovolumetricodelagua.getAxisLeft(); //g vwc
    }
    private void setgraficosK(kdatapack pack){
        grafico1.setData(pack.sacarelPM().second);
        grafico1.setXAxisRenderer(new labelpersonalizadoX(grafico1.getViewPortHandler(), grafico1.getXAxis(), grafico1.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxispm = grafico1.getXAxis();
        xaxispm.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxispm.setValueFormatter(new graficolabelgenerator(pack.sacarloslabels()));
        xaxispm.setGranularityEnabled(true);
        //barChart.getXAxis().setGranularityEnabled(true);
        //barChart.getXAxis().setGranularity(1.0f);
        //lineChart.getXAxis().setLabelCount(3); 
        //xaxispm.setLabelRotationAngle(-45f);
        MarkerLineChartAdapter adapterpm = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarlasraizes().first,pack.sacarelPM().first,pack.sacarloslabels(),name,"k","V1",archi);
        grafico1.setMarker(adapterpm);
        grafico1.setExtraTopOffset(OFFSETGRAFPHTOP);
        grafico1.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        grafico1.setExtraRightOffset(OFFSETGRAFPHLEFT);
        grafico1.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        grafico1.invalidate();
        //grafico1.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        grafico1.fitScreen();

        grafico2.setData(pack.sacareltemp().second);
        grafico2.setXAxisRenderer(new labelpersonalizadoX(grafico2.getViewPortHandler(), grafico2.getXAxis(), grafico2.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xAxistemp = grafico2.getXAxis();
        xAxistemp.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxistemp.setValueFormatter(new graficolabelgenerator(pack.sacarloslabels()));
        xAxistemp.setGranularityEnabled(true);
        //xAxistemp.setLabelRotationAngle(-45f);
        MarkerLineChartAdapter adaptertemp = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarlasraizes().second,pack.sacareltemp().first,pack.sacarloslabels(),name,"k","V2",archi);
        grafico2.setMarker(adaptertemp);
        grafico2.setExtraTopOffset(OFFSETGRAFPHTOP);
        grafico2.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        grafico2.setExtraRightOffset(OFFSETGRAFPHLEFT);
        grafico2.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        grafico2.invalidate();
        //grafico2.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        grafico2.fitScreen();
    }
    private void setgraficosstate(statedatapack pack){
        graficohumedadrelativa.setData(pack.sacarlahumedadrelativa());
        graficohumedadrelativa.setXAxisRenderer(new labelpersonalizadoX(graficohumedadrelativa.getViewPortHandler(), graficohumedadrelativa.getXAxis(), graficohumedadrelativa.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis XaxisHR = graficohumedadrelativa.getXAxis();
        XaxisHR.setPosition(XAxis.XAxisPosition.BOTTOM);
        XaxisHR.setValueFormatter(new graficolabelgenerator(pack.sacaraxislabels()));
        XaxisHR.setGranularityEnabled(true);
        graficohumedadrelativa.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficohumedadrelativa.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficohumedadrelativa.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficohumedadrelativa.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        graficohumedadrelativa.setMarker(new MarkerLineChartDefault(getContext(),R.layout.item_dataetiquetasecundaria,pack.sacaraxislabels(),name,"state","RH",archi));
        graficohumedadrelativa.invalidate();
        //graficohumedadrelativa.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficohumedadrelativa.fitScreen();

        graficopresionbarometrica.setData(pack.sacarlapresionbarometrica());
        graficopresionbarometrica.setXAxisRenderer(new labelpersonalizadoX(graficopresionbarometrica.getViewPortHandler(), graficopresionbarometrica.getXAxis(), graficopresionbarometrica.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis XaxisPB = graficopresionbarometrica.getXAxis();
        XaxisPB.setPosition(XAxis.XAxisPosition.BOTTOM);
        XaxisPB.setValueFormatter(new graficolabelgenerator(pack.sacaraxislabels()));
        XaxisPB.setGranularityEnabled(true);
        graficopresionbarometrica.setMarker(new MarkerLineChartDefault(getContext(),R.layout.item_dataetiquetasecundaria,pack.sacaraxislabels(),name,"state","BP",archi));
        graficopresionbarometrica.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficopresionbarometrica.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficopresionbarometrica.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficopresionbarometrica.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        graficopresionbarometrica.invalidate();
        //graficopresionbarometrica.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficopresionbarometrica.fitScreen();

        grafico3.setData(pack.sacarlaenergia());
        grafico3.setXAxisRenderer(new labelpersonalizadoX(grafico3.getViewPortHandler(), grafico3.getXAxis(), grafico3.getTransformer(YAxis.AxisDependency.LEFT)));
        YAxis yaxisl = grafico3.getAxisLeft(); //
        //yaxisl.setAxisMinimum(0f);
        //yaxisl.setAxisMaximum(8f); //7.5
        yaxisl.setTextColor(colores[1]);
        YAxis yaxisr = grafico3.getAxisRight();
        yaxisr.setAxisMinimum(3.5f);
        yaxisr.setAxisMaximum(4.5f); //4.5
        yaxisr.setTextColor(colores[0]);//
        XAxis xaxis3= grafico3.getXAxis();
        xaxis3.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis3.setValueFormatter(new graficolabelgenerator(pack.sacaraxislabels()));
        xaxis3.setGranularityEnabled(true);
        grafico3.setMarker(new MarkerLineChartDefault(getContext(),R.layout.item_dataetiquetasecundaria));
        grafico3.setExtraTopOffset(OFFSETGRAFPHTOP);
        grafico3.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        grafico3.setExtraRightOffset(OFFSETGRAFPHLEFT);
        grafico3.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        grafico3.invalidate();
        //grafico3.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        grafico3.fitScreen();

        graficotemperaturavulvoseco.setData(pack.sacarlatemperaturavulvoseco());
        graficotemperaturavulvoseco.setXAxisRenderer(new labelpersonalizadoX(graficotemperaturavulvoseco.getViewPortHandler(), graficotemperaturavulvoseco.getXAxis(), graficotemperaturavulvoseco.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis XaxisTVS = graficotemperaturavulvoseco.getXAxis();
        XaxisTVS.setPosition(XAxis.XAxisPosition.BOTTOM);
        XaxisTVS.setValueFormatter(new graficolabelgenerator(pack.sacaraxislabels()));
        XaxisTVS.setGranularityEnabled(true);
        graficotemperaturavulvoseco.setMarker(new MarkerLineChartDefault(getContext(),R.layout.item_dataetiquetasecundaria,pack.sacaraxislabels(),name,"state","dT",archi));
        graficotemperaturavulvoseco.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficotemperaturavulvoseco.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficotemperaturavulvoseco.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficotemperaturavulvoseco.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        graficotemperaturavulvoseco.invalidate();
        //graficotemperaturavulvoseco.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficotemperaturavulvoseco.fitScreen();

        graficorosadevientos.setData(pack.sacarelwindspeed());
        graficorosadevientos.setXAxisRenderer(new labelpersonalizadoX(graficorosadevientos.getViewPortHandler(), graficorosadevientos.getXAxis(), graficorosadevientos.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis XaxisRV = graficorosadevientos.getXAxis();
        XaxisRV.setPosition(XAxis.XAxisPosition.BOTTOM);
        XaxisRV.setValueFormatter(new graficolabelgenerator(pack.sacaraxislabels()));
        XaxisRV.setGranularityEnabled(true);
        graficorosadevientos.setMarker(new MarkerLineChartDefault(getContext(),R.layout.item_dataetiquetasecundaria,pack.sacaraxislabels(),name,"state","WS",archi));
        graficorosadevientos.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficorosadevientos.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficorosadevientos.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficorosadevientos.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        graficorosadevientos.invalidate();
        //graficorosadevientos.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficorosadevientos.fitScreen();
    }
    private void setgraficoG(gdatapack pack){
        /*
        graficohumedaddelsuelo.setData(pack.sacarlahumedad().second);
        graficohumedaddelsuelo.setXAxisRenderer(new labelpersonalizadoX(graficohumedaddelsuelo.getViewPortHandler(), graficohumedaddelsuelo.getXAxis(), graficohumedaddelsuelo.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisHS = graficohumedaddelsuelo.getXAxis();
        xaxisHS.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisHS.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        graficohumedaddelsuelo.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficohumedaddelsuelo.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficohumedaddelsuelo.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterHS = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizhumedad(),pack.sacarlahumedad().first);
        graficohumedaddelsuelo.setMarker(adapterHS);
        graficohumedaddelsuelo.invalidate();
        //graficohumedaddelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficohumedaddelsuelo.fitScreen();

         */
        //final Snackbar Avizoneolinklisto = Snackbar.make(getView(),"Click", BaseTransientBottomBar.LENGTH_SHORT);

        graficotemperaturadelsuelo.setData(pack.sacarlatemperatura().second);
        graficotemperaturadelsuelo.setXAxisRenderer(new labelpersonalizadoX(graficotemperaturadelsuelo.getViewPortHandler(), graficotemperaturadelsuelo.getXAxis(), graficotemperaturadelsuelo.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisTS = graficotemperaturadelsuelo.getXAxis();
        xaxisTS.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisTS.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        xaxisTS.setGranularityEnabled(true);
        graficotemperaturadelsuelo.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficotemperaturadelsuelo.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficotemperaturadelsuelo.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficotemperaturadelsuelo.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterTS = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraiztemperatura(),pack.sacarlatemperatura().first,pack.sacarlabels(),name,"g","V2",archi);
        graficotemperaturadelsuelo.setMarker(adapterTS);
        graficotemperaturadelsuelo.invalidate();
        //graficotemperaturadelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficotemperaturadelsuelo.fitScreen();

        graficoconductividaddelsuelo.setData(pack.sacarlaconductividad().second);
        graficoconductividaddelsuelo.setXAxisRenderer(new labelpersonalizadoX(graficoconductividaddelsuelo.getViewPortHandler(), graficoconductividaddelsuelo.getXAxis(), graficoconductividaddelsuelo.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisCS = graficoconductividaddelsuelo.getXAxis();
        xaxisCS.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisCS.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        xaxisCS.setGranularityEnabled(true);
        graficoconductividaddelsuelo.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficoconductividaddelsuelo.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficoconductividaddelsuelo.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficoconductividaddelsuelo.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterCS = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizconductividad(),pack.sacarlaconductividad().first,pack.sacarlabels(),name,"g","V3",archi);
        graficoconductividaddelsuelo.setMarker(adapterCS);
        graficoconductividaddelsuelo.invalidate();
        //graficoconductividaddelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficoconductividaddelsuelo.fitScreen();

        graficoconductividadelectricadelporo.setData(pack.sacarlaconductividaddelporo().second);
        graficoconductividadelectricadelporo.setXAxisRenderer(new labelpersonalizadoX(graficoconductividadelectricadelporo.getViewPortHandler(), graficoconductividadelectricadelporo.getXAxis(), graficoconductividadelectricadelporo.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisCSP = graficoconductividadelectricadelporo.getXAxis();
        xaxisCSP.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisCSP.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        xaxisCSP.setGranularityEnabled(true);
        graficoconductividadelectricadelporo.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficoconductividadelectricadelporo.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficoconductividadelectricadelporo.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficoconductividadelectricadelporo.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterCSP = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizconductividaddelporo(),pack.sacarlaconductividaddelporo().first,pack.sacarlabels(),name,"g","PoreCer",archi);
        graficoconductividadelectricadelporo.setMarker(adapterCSP);
        graficoconductividadelectricadelporo.invalidate();
        //graficoconductividadelectricadelporo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficoconductividadelectricadelporo.fitScreen();

        graficocontenidovolumetricodelagua.setData(pack.sacarelcontenidovolumetricodelagua().second);
        graficocontenidovolumetricodelagua.setXAxisRenderer(new labelpersonalizadoX(graficocontenidovolumetricodelagua.getViewPortHandler(), graficocontenidovolumetricodelagua.getXAxis(), graficocontenidovolumetricodelagua.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisCVA = graficocontenidovolumetricodelagua.getXAxis();
        xaxisCVA.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisCVA.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        xaxisCVA.setGranularityEnabled(true);
        //xaxisCVA.setGranularity(50f);
        //xaxisCVA.setLabelCount(6, true);
        graficocontenidovolumetricodelagua.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficocontenidovolumetricodelagua.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficocontenidovolumetricodelagua.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficocontenidovolumetricodelagua.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterCVA = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizcontenidovolumetricodelagua(),pack.sacarelcontenidovolumetricodelagua().first,pack.sacarlabels(),name,"g","vwc",archi);
        graficocontenidovolumetricodelagua.setMarker(adapterCVA);
        graficocontenidovolumetricodelagua.invalidate();
        //graficocontenidovolumetricodelagua.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficocontenidovolumetricodelagua.fitScreen();
    }
    private void setgraficonpk(npkdatapack pack){
        graficonitrato.setData(pack.sacarlanitrato().second);
        graficonitrato.setXAxisRenderer(new labelpersonalizadoX(graficonitrato.getViewPortHandler(), graficonitrato.getXAxis(), graficonitrato.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisNT = graficonitrato.getXAxis();
        xaxisNT.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisNT.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        xaxisNT.setGranularityEnabled(true);
        graficonitrato.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficonitrato.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficonitrato.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficonitrato.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterNT = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraiznitrato(),pack.sacarlanitrato().first,pack.sacarlabels(),name,"npk","Nitrato",archi);
        graficonitrato.setMarker(adapterNT);
        graficonitrato.invalidate();
        //graficoMPtemperaturadelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficonitrato.fitScreen();

        graficofosfato.setData(pack.sacarlafosfato().second);
        graficofosfato.setXAxisRenderer(new labelpersonalizadoX(graficofosfato.getViewPortHandler(), graficofosfato.getXAxis(), graficofosfato.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisFS = graficofosfato.getXAxis();
        xaxisFS.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisFS.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        xaxisFS.setGranularityEnabled(true);
        graficofosfato.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficofosfato.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficofosfato.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficofosfato.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterFS = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizfosfato(),pack.sacarlafosfato().first,pack.sacarlabels(),name,"npk","Fosfato",archi);
        graficofosfato.setMarker(adapterFS);
        graficofosfato.invalidate();
        //graficoMPtemperaturadelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficofosfato.fitScreen();

        graficopotasio.setData(pack.sacarlafosfato().second);
        graficopotasio.setXAxisRenderer(new labelpersonalizadoX(graficopotasio.getViewPortHandler(), graficopotasio.getXAxis(), graficopotasio.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisPT = graficopotasio.getXAxis();
        xaxisPT.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisPT.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        xaxisPT.setGranularityEnabled(true);
        graficopotasio.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficopotasio.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficopotasio.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficopotasio.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterPT = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizpotasio(),pack.sacarelpotasio().first,pack.sacarlabels(),name,"npk","Potasio",archi);
        graficopotasio.setMarker(adapterPT);
        graficopotasio.invalidate();
        //graficoMPtemperaturadelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficopotasio.fitScreen();
    }
    private void setgraficoph(phdatapack pack){
        graficoph.setData(pack.sacarelph().second);
        graficoph.setXAxisRenderer(new labelpersonalizadoX(graficoph.getViewPortHandler(), graficoph.getXAxis(), graficoph.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisPH = graficoph.getXAxis();
        xaxisPH.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisPH.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        xaxisPH.setGranularityEnabled(true);
        graficoph.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficoph.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficoph.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficoph.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterPH = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizph(),pack.sacarelph().first,pack.sacarlabels(),name,"ph","ph",archi);
        graficoph.setMarker(adapterPH);
        graficoph.invalidate();
        //graficoMPtemperaturadelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficoph.fitScreen();
    }
}
