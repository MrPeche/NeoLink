package com.example.neolink_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.neolink_app.adaptadores.MarkerLineChartAdapter;
import com.example.neolink_app.adaptadores.MarkerLineChartDefault;
import com.example.neolink_app.adaptadores.graficolabelgenerator;
import com.example.neolink_app.adaptadores.labelpersonalizadoX;
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
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import java.util.Calendar;


public class graficodelmapa extends Fragment {
    private String nombre;
    private TextView titulo;
    private CardView cvgrf1;
    private CardView cvgrf2;
    private CardView cvgrf3;
    //private CardView cvgrhumedaddelsuelo;
    private CardView cvgrtemperaturadelsuelo;
    private CardView cvgrconductividadelectrica;
    private CardView cvgrHumedadrelativa;
    private CardView cvgrpresionbarometrica;
    private CardView cvgrtemperaturabulboseco;
    private CardView cvrosadevientos;
    private CardView cvconductividadelectricadelporo;
    private CardView cvcontenidovolumetricodelagua;
    private CardView cvPh;
    private CardView cvnitrato;
    private CardView cvfosfato;
    private CardView cvpotasio;
    private Calendar ahora = Calendar.getInstance();
    private MasterDrawerViewModel archi;
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
    //private LineChart graficoMPhumedaddelsuelo;
    private chartlinealparadapter graficoMPtemperaturadelsuelo;
    private chartlinealparadapter graficoMPconductividadelectrica;
    private chartlinealparadapter graficorosadevientos;
    private chartlinealparadapter graficoconductividadelectricadelporo;
    private chartlinealparadapter graficocontenidovolumetricodelagua;
    private int alpha = 170;
    private int[] colores = {Color.argb(alpha,250,128,114),Color.argb(alpha,60,179,113),Color.argb(alpha,100,149,237),Color.argb(alpha,147,112,219)}; //salmon, medium sea green,corn flower blue, medium purple https://www.rapidtables.com/web/color/RGB_Color.html
    private int MAX_DATAVISIBLE = 48;
    private float LINEWIDTH = 2.5f;
    private static float OFFSETGRAFPHTOP = 20f;
    private static float OFFSETGRAFPHLEFT = 10f;
    private static float OFFSETGRAFPHBOTTOM = 15f;

    public graficodelmapa() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.nombre = graficodelmapaArgs.fromBundle(getArguments()).getNombreNLG();
        }
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graficodelmapa, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        titulo = view.findViewById(R.id.tituloGMapa);
        titulo.setText(nombre);
        grafico1 = view.findViewById(R.id.graficoMP1);
        grafico2= view.findViewById(R.id.graficoMP2);
        grafico3= view.findViewById(R.id.graficoMPBateria);
        graficohumedadrelativa= view.findViewById(R.id.graficoMPHumedadRelativa);
        graficopresionbarometrica= view.findViewById(R.id.graficoMPPresionBarometrica);
        graficotemperaturavulvoseco= view.findViewById(R.id.graficoMPtemperaturavulvoseco);
        graficoph = view.findViewById(R.id.graficoMPph);
        graficonitrato = view.findViewById(R.id.graficoMPnitrato);
        graficofosfato = view.findViewById(R.id.graficoMPfosfato);
        graficopotasio = view.findViewById(R.id.graficoMPpotasio);
        //graficoMPhumedaddelsuelo = view.findViewById(R.id.graficoMPhumedaddelsuelo);
        graficoMPtemperaturadelsuelo = view.findViewById(R.id.graficoMPtemperaturadelsuelo);
        graficoMPconductividadelectrica = view.findViewById(R.id.graficoMPconductividadelectrica);
        graficorosadevientos = view.findViewById(R.id.graficoMProsadevientos);
        graficoconductividadelectricadelporo = view.findViewById(R.id.graficoMPconductividadelectricadelporo);
        graficocontenidovolumetricodelagua = view.findViewById(R.id.graficoMPcontenidovolumetricodelagua);
        cvgrf1 = view.findViewById(R.id.cardVMP1);
        cvgrf2 = view.findViewById(R.id.cardVMP2);
        cvgrf3 = view.findViewById(R.id.cardVMP3); //esta es energia por alguna razon que no quiero ver en este momento ORDENAR LUEGO
        //cvgrhumedaddelsuelo = view.findViewById(R.id.cardMPhumedaddelsuelo);
        cvPh = view.findViewById(R.id.cardMPph);
        cvnitrato = view.findViewById(R.id.cardMPnitrato);
        cvfosfato = view.findViewById(R.id.cardMPfosfato);
        cvpotasio = view.findViewById(R.id.cardMPpotasio);
        cvgrtemperaturadelsuelo = view.findViewById(R.id.cardMPtemperaturadelsuelo);
        cvgrconductividadelectrica = view.findViewById(R.id.cardMPconductividadelectrica);
        cvgrHumedadrelativa = view.findViewById(R.id.cardhumedadrelativa);
        cvgrpresionbarometrica = view.findViewById(R.id.cardpresionbarometrica);
        cvgrtemperaturabulboseco = view.findViewById(R.id.cardtemperaturavulvoseco);
        cvrosadevientos = view.findViewById(R.id.cardrosadevientos);
        cvconductividadelectricadelporo = view.findViewById(R.id.cardMPconductividadelectricadelporo);
        cvcontenidovolumetricodelagua = view.findViewById(R.id.cardMPcontenidovolumetricodelagua);

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
        archi.haycomentarionuevo().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null){
                    Navigation.findNavController(getView()).navigate(graficodelmapaDirections.actionGraficodelmapaToDialogodeagregarcomentarios(s));
                    archi.vaciarelavizodecomentarionuevo();
                }
            }
        });
        archi.masterdatedatapackage(nombre).observe(getViewLifecycleOwner(), new Observer<InfoParaGraficos>() {
            @Override
            public void onChanged(InfoParaGraficos infoParaGraficos) {
                if(infoParaGraficos!=null){
                    if(infoParaGraficos.validarlosdias()){
                        fulldatapack commdata = infoParaGraficos.managedias();
                        if(commdata.sacarkdatapack().sacarloslabels().size()!=0){
                            cleanthisshit();
                            cvgrf1.setVisibility(View.VISIBLE);
                            cvgrf2.setVisibility(View.VISIBLE);
                            setgraficosK(commdata.sacarkdatapack());
                        }
                        if(commdata.sacarstatedatapack().sacaraxislabels().size()!=0){
                            cvgrf3.setVisibility(View.VISIBLE);
                            cvgrHumedadrelativa.setVisibility(View.VISIBLE);
                            cvgrpresionbarometrica.setVisibility(View.VISIBLE);
                            cvgrtemperaturabulboseco.setVisibility(View.VISIBLE);
                            cvrosadevientos.setVisibility(View.VISIBLE);
                            setgraficosstate(commdata.sacarstatedatapack());
                        }
                        if(commdata.sacargdatapack().sacarlabels().size()!=0){
                            cleanG();
                            cvgrconductividadelectrica.setVisibility(View.VISIBLE);
                            //cvgrhumedaddelsuelo.setVisibility(View.VISIBLE);
                            cvgrtemperaturadelsuelo.setVisibility(View.VISIBLE);
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
                        //Kdata
                        cleanthisshit();
                        cvgrf1.setVisibility(View.VISIBLE);
                        cvgrf2.setVisibility(View.VISIBLE);
                        setgraficosK(commdata.sacarkdatapack());
                        //state
                        cvgrf3.setVisibility(View.VISIBLE);
                        cvgrHumedadrelativa.setVisibility(View.VISIBLE);
                        cvgrpresionbarometrica.setVisibility(View.VISIBLE);
                        cvgrtemperaturabulboseco.setVisibility(View.VISIBLE);
                        cvrosadevientos.setVisibility(View.VISIBLE);
                        setgraficosstate(commdata.sacarstatedatapack());
                        //sensorg
                        cleanG();
                        cvgrconductividadelectrica.setVisibility(View.VISIBLE);
                        //cvgrhumedaddelsuelo.setVisibility(View.VISIBLE);
                        cvgrtemperaturadelsuelo.setVisibility(View.VISIBLE);
                        cvconductividadelectricadelporo.setVisibility(View.VISIBLE);
                        cvcontenidovolumetricodelagua.setVisibility(View.VISIBLE);
                        setgraficoG(commdata.sacargdatapack());
                         */
                    }
                }
            }
        });
        archi.fecthlimits(nombre).observe(getViewLifecycleOwner(), new Observer<statelimitsport>() {
            @Override
            public void onChanged(statelimitsport statelimitsport) {
                if(statelimitsport!=null){
                    arrangelimitlines(statelimitsport);
                }
            }
        });

    }
    private void startposition(){
        cvgrf1.setVisibility(View.GONE);
        cvgrf2.setVisibility(View.GONE);
        cvgrf3.setVisibility(View.GONE);
        cvgrHumedadrelativa.setVisibility(View.GONE);
        cvgrpresionbarometrica.setVisibility(View.GONE);
        cvgrtemperaturabulboseco.setVisibility(View.GONE);
        cvgrconductividadelectrica.setVisibility(View.GONE);
        //cvgrhumedaddelsuelo.setVisibility(View.GONE);
        cvgrtemperaturadelsuelo.setVisibility(View.GONE);
        cvPh.setVisibility(View.GONE);
        cvnitrato.setVisibility(View.GONE);
        cvfosfato.setVisibility(View.GONE);
        cvpotasio.setVisibility(View.GONE);
    }
    private void cleanG(){
        graficoMPtemperaturadelsuelo.clear();
        //graficoMPhumedaddelsuelo.clear();
        graficoMPconductividadelectrica.clear();
        graficoconductividadelectricadelporo.clear();
        graficocontenidovolumetricodelagua.clear();
    }
    private void propiedadesgraficoPM(){
        grafico1.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        grafico1.setDrawGridBackground(false);
        grafico1.setDrawBorders(false);
        //grafico1.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        grafico1.getDescription().setEnabled(false);
        grafico1.setTouchEnabled(true);
        grafico1.setDragEnabled(true);
        grafico1.setScaleEnabled(true);
        grafico1.setScaleYEnabled(false);
        grafico1.setScaleXEnabled(true);
        Legend L = grafico1.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    private void propiedadesgraficoTem(){
        grafico2.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        grafico2.setDrawGridBackground(false);
        grafico2.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        grafico2.getDescription().setEnabled(false);
        grafico2.setTouchEnabled(true);
        grafico2.setDragEnabled(true);
        grafico2.setScaleEnabled(true);
        grafico2.setScaleYEnabled(false);
        grafico2.setScaleXEnabled(true);
        Legend L = grafico2.getLegend();
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
    private void propiedadesgrafico3(){
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
    private void propiedadesgraficohumedadrelativa(){
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
    private void propiedadesgraficopresionbarometrica(){
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
    private void propiedadesgraficotemperaturavulvoseco(){
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
        graficoMPhumedaddelsuelo.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficoMPhumedaddelsuelo.setDrawGridBackground(false);
        graficoMPhumedaddelsuelo.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficoMPhumedaddelsuelo.getDescription().setEnabled(false);
        graficoMPhumedaddelsuelo.setTouchEnabled(true);
        graficoMPhumedaddelsuelo.setDragEnabled(true);
        graficoMPhumedaddelsuelo.setScaleYEnabled(false);
        graficoMPhumedaddelsuelo.setScaleXEnabled(true);
        Legend L = graficoMPhumedaddelsuelo.getLegend();
    }
     */
    private void propiedadesgraficotemperaturadeldelsuelo(){
        graficoMPtemperaturadelsuelo.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficoMPtemperaturadelsuelo.setDrawGridBackground(false);
        graficoMPtemperaturadelsuelo.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficoMPtemperaturadelsuelo.getDescription().setEnabled(false);
        graficoMPtemperaturadelsuelo.setTouchEnabled(true);
        graficoMPtemperaturadelsuelo.setDragEnabled(true);
        graficoMPtemperaturadelsuelo.setScaleYEnabled(false);
        graficoMPtemperaturadelsuelo.setScaleXEnabled(true);
        Legend L = graficoMPtemperaturadelsuelo.getLegend();
        L.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }
    private void propiedadesgraficoconductividaddeldelsuelo(){
        graficoMPconductividadelectrica.setBackgroundColor(Color.TRANSPARENT);
        //grafico1.setGridBackgroundColor(Color.BLACK);
        graficoMPconductividadelectrica.setDrawGridBackground(false);
        graficoMPconductividadelectrica.setDrawBorders(false);
        //grafico2.setBorderColor(Color.BLACK);
        //grafico1.setBorderWidth((float) 4);
        graficoMPconductividadelectrica.getDescription().setEnabled(false);
        graficoMPconductividadelectrica.setTouchEnabled(true);
        graficoMPconductividadelectrica.setDragEnabled(true);
        graficoMPconductividadelectrica.setScaleYEnabled(false);
        graficoMPconductividadelectrica.setScaleXEnabled(true);
        Legend L = graficoMPconductividadelectrica.getLegend();
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
    private void cleanthisshit(){
        grafico1.clear();
        grafico2.clear();
    }

    public void onDestroy() {
        super.onDestroy();
        cleanthisshit();
        grafico1.clear();
        grafico2.clear();
        grafico3.clear();
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
            graficoMPtemperaturadelsuelo.invalidate();
        } else if(caso==3){
            graficoMPconductividadelectrica.invalidate();
        } else if(caso==4){
            graficoconductividadelectricadelporo.invalidate();
        } else graficocontenidovolumetricodelagua.invalidate();
    }
    private YAxis dameelaxiselegido(int caso){
        if(caso==0){
            return grafico1.getAxisLeft();
        } else if(caso==1){
            return grafico2.getAxisLeft();
        }
        else if(caso==2){
            return graficoMPtemperaturadelsuelo.getAxisLeft();
        } else if(caso==3){
            return graficoMPconductividadelectrica.getAxisLeft();
        } else if(caso==4){
            return graficoconductividadelectricadelporo.getAxisLeft();
        } else return graficocontenidovolumetricodelagua.getAxisLeft();
    }
    private void setgraficosK(kdatapack pack){
        grafico1.setData(pack.sacarelPM().second);
        grafico1.setXAxisRenderer(new labelpersonalizadoX(grafico1.getViewPortHandler(), grafico1.getXAxis(), grafico1.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxispm = grafico1.getXAxis();
        xaxispm.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxispm.setValueFormatter(new graficolabelgenerator(pack.sacarloslabels()));
        xaxispm.setGranularityEnabled(true);
        //xaxispm.setLabelRotationAngle(-45f);
        MarkerLineChartAdapter adapterpm = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarlasraizes().first,pack.sacarelPM().first,pack.sacarloslabels(),nombre,"k","V1",archi);
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
        MarkerLineChartAdapter adaptertemp = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarlasraizes().second,pack.sacareltemp().first,pack.sacarloslabels(),nombre,"k","V2",archi);
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
        graficohumedadrelativa.setMarker(new MarkerLineChartDefault(getContext(),R.layout.item_dataetiquetasecundaria,pack.sacaraxislabels(),nombre,"state","RH",archi));
        graficohumedadrelativa.invalidate();
        //graficohumedadrelativa.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficohumedadrelativa.fitScreen();

        graficopresionbarometrica.setData(pack.sacarlapresionbarometrica());
        graficopresionbarometrica.setXAxisRenderer(new labelpersonalizadoX(graficopresionbarometrica.getViewPortHandler(), graficopresionbarometrica.getXAxis(), graficopresionbarometrica.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis XaxisPB = graficopresionbarometrica.getXAxis();
        XaxisPB.setPosition(XAxis.XAxisPosition.BOTTOM);
        XaxisPB.setValueFormatter(new graficolabelgenerator(pack.sacaraxislabels()));
        XaxisPB.setGranularityEnabled(true);
        graficopresionbarometrica.setMarker(new MarkerLineChartDefault(getContext(),R.layout.item_dataetiquetasecundaria,pack.sacaraxislabels(),nombre,"state","BP",archi));
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
        graficotemperaturavulvoseco.setMarker(new MarkerLineChartDefault(getContext(),R.layout.item_dataetiquetasecundaria,pack.sacaraxislabels(),nombre,"state","dT",archi));
        graficotemperaturavulvoseco.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficotemperaturavulvoseco.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficotemperaturavulvoseco.setExtraLeftOffset(OFFSETGRAFPHLEFT);
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
        graficorosadevientos.setMarker(new MarkerLineChartDefault(getContext(),R.layout.item_dataetiquetasecundaria,pack.sacaraxislabels(),nombre,"state","WS",archi));
        graficorosadevientos.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficorosadevientos.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficorosadevientos.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficorosadevientos.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        graficorosadevientos.invalidate();
        //graficorosadevientos.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficorosadevientos.fitScreen();
    }
    private void setgraficoG(gdatapack pack){
        /*
        graficoMPhumedaddelsuelo.setData(pack.sacarlahumedad().second);
        graficoMPhumedaddelsuelo.setXAxisRenderer(new labelpersonalizadoX(graficoMPhumedaddelsuelo.getViewPortHandler(), graficoMPhumedaddelsuelo.getXAxis(), graficoMPhumedaddelsuelo.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisHS = graficoMPhumedaddelsuelo.getXAxis();
        xaxisHS.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisHS.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        graficoMPhumedaddelsuelo.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficoMPhumedaddelsuelo.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficoMPhumedaddelsuelo.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterHS = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizhumedad(),pack.sacarlahumedad().first);
        graficoMPhumedaddelsuelo.setMarker(adapterHS);
        graficoMPhumedaddelsuelo.invalidate();
        graficoMPhumedaddelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
         */

        graficoMPtemperaturadelsuelo.setData(pack.sacarlatemperatura().second);
        graficoMPtemperaturadelsuelo.setXAxisRenderer(new labelpersonalizadoX(graficoMPtemperaturadelsuelo.getViewPortHandler(), graficoMPtemperaturadelsuelo.getXAxis(), graficoMPtemperaturadelsuelo.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisTS = graficoMPtemperaturadelsuelo.getXAxis();
        xaxisTS.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisTS.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        xaxisTS.setGranularityEnabled(true);
        graficoMPtemperaturadelsuelo.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficoMPtemperaturadelsuelo.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficoMPtemperaturadelsuelo.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficoMPtemperaturadelsuelo.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterTS = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraiztemperatura(),pack.sacarlatemperatura().first,pack.sacarlabels(),nombre,"g","V2",archi);
        graficoMPtemperaturadelsuelo.setMarker(adapterTS);
        graficoMPtemperaturadelsuelo.invalidate();
        //graficoMPtemperaturadelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficoMPtemperaturadelsuelo.fitScreen();

        graficoMPconductividadelectrica.setData(pack.sacarlaconductividad().second);
        graficoMPconductividadelectrica.setXAxisRenderer(new labelpersonalizadoX(graficoMPconductividadelectrica.getViewPortHandler(), graficoMPconductividadelectrica.getXAxis(), graficoMPconductividadelectrica.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisCS = graficoMPconductividadelectrica.getXAxis();
        xaxisCS.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisCS.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        xaxisCS.setGranularityEnabled(true);
        graficoMPconductividadelectrica.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficoMPconductividadelectrica.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficoMPconductividadelectrica.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficoMPconductividadelectrica.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterCS = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizconductividad(),pack.sacarlaconductividad().first,pack.sacarlabels(),nombre,"g","V3",archi);
        graficoMPconductividadelectrica.setMarker(adapterCS);
        graficoMPconductividadelectrica.invalidate();
        //graficoMPconductividadelectrica.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficoMPconductividadelectrica.fitScreen();

        graficoconductividadelectricadelporo.setData(pack.sacarlaconductividaddelporo().second);
        graficoconductividadelectricadelporo.setXAxisRenderer(new labelpersonalizadoX(graficoconductividadelectricadelporo.getViewPortHandler(), graficoconductividadelectricadelporo.getXAxis(), graficoconductividadelectricadelporo.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisCSP = graficoconductividadelectricadelporo.getXAxis();
        xaxisCSP.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisCSP.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        xaxisCSP.setGranularityEnabled(true);
        graficoconductividadelectricadelporo.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficoconductividadelectricadelporo.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficoconductividadelectricadelporo.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficoconductividadelectricadelporo.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterCSP = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizconductividaddelporo(),pack.sacarlaconductividaddelporo().first,pack.sacarlabels(),nombre,"g","PoreCer",archi);
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
        graficocontenidovolumetricodelagua.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficocontenidovolumetricodelagua.setExtraRightOffset(OFFSETGRAFPHLEFT);
        graficocontenidovolumetricodelagua.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficocontenidovolumetricodelagua.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterCVA = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizcontenidovolumetricodelagua(),pack.sacarelcontenidovolumetricodelagua().first,pack.sacarlabels(),nombre,"g","vwc",archi);
        graficocontenidovolumetricodelagua.setMarker(adapterCVA);
        graficocontenidovolumetricodelagua.invalidate();
        graficocontenidovolumetricodelagua.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
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
        MarkerLineChartAdapter adapterNT = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraiznitrato(),pack.sacarlanitrato().first,pack.sacarlabels(),nombre,"npk","Nitrato",archi);
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
        MarkerLineChartAdapter adapterFS = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizfosfato(),pack.sacarlafosfato().first,pack.sacarlabels(),nombre,"npk","Fosfato",archi);
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
        MarkerLineChartAdapter adapterPT = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizpotasio(),pack.sacarelpotasio().first,pack.sacarlabels(),nombre,"npk","Potasio",archi);
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
        MarkerLineChartAdapter adapterPH = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizph(),pack.sacarelph().first,pack.sacarlabels(),nombre,"ph","ph",archi);
        graficoph.setMarker(adapterPH);
        graficoph.invalidate();
        //graficoMPtemperaturadelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficoph.fitScreen();
    }
}
