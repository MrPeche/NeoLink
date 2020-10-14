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

import com.example.neolink_app.adaptadores.MarkerLineChartAdapter;
import com.example.neolink_app.adaptadores.graficolabelgenerator;
import com.example.neolink_app.adaptadores.labelpersonalizadoX;
import com.example.neolink_app.clases.clasesparaformargraficos.InfoParaGraficos;
import com.example.neolink_app.clases.clasesparaformargraficos.fulldatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.gdatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.kdatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.statedatapack;
import com.example.neolink_app.clases.configuracion.statelimitsport;
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
    private Calendar ahora = Calendar.getInstance();
    private MasterDrawerViewModel archi;
    private LineChart grafico1;
    private LineChart grafico2;
    private LineChart grafico3;
    private LineChart graficopresionbarometrica;
    private LineChart graficohumedadrelativa;
    private LineChart graficotemperaturavulvoseco;
    //private LineChart graficoMPhumedaddelsuelo;
    private LineChart graficoMPtemperaturadelsuelo;
    private LineChart graficoMPconductividadelectrica;
    private LineChart graficorosadevientos;
    private LineChart graficoconductividadelectricadelporo;
    private LineChart graficocontenidovolumetricodelagua;
    private int alpha = 170;
    private int[] colores = {Color.argb(alpha,250,128,114),Color.argb(alpha,60,179,113),Color.argb(alpha,100,149,237),Color.argb(alpha,176,196,222)}; //salmon, medium sea green,corn flower blue, light steel blue https://www.rapidtables.com/web/color/RGB_Color.html
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
        //propiedadesgraficohumedaddeldelsuelo();
        propiedadesgraficotemperaturadeldelsuelo();
        propiedadesgraficoconductividaddeldelsuelo();

        startposition();

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
        boolean[] switchs = {deintaboolean(obj.dameP1().damek().dameV1().damebool()),
                deintaboolean(obj.dameP1().damek().dameV2().damebool()),
                deintaboolean(obj.dameP1().dameG().dameV1().damebool()),
                deintaboolean(obj.dameP1().dameG().dameV2().damebool()),
                deintaboolean(obj.dameP1().dameG().dameV3().damebool())};
        double[] superior = {obj.dameP1().damek().dameV1().dameMAX(),
                obj.dameP1().damek().dameV2().dameMAX(),
                obj.dameP1().dameG().dameV1().dameMAX(),
                obj.dameP1().dameG().dameV2().dameMAX(),
                obj.dameP1().dameG().dameV3().dameMAX()};
        double[] inferior = {obj.dameP1().damek().dameV1().dameMin(),
                obj.dameP1().damek().dameV2().dameMin(),
                obj.dameP1().dameG().dameV1().dameMin(),
                obj.dameP1().dameG().dameV2().dameMin(),
                obj.dameP1().dameG().dameV3().dameMin()};
        for(int i=0;i<switchs.length;i++){
            if(switchs[i]){
                LimitLine arriba = new LimitLine((float)superior[i]);
                arriba.setLineColor(colores[0]);
                arriba.setLineWidth(0.9f);
                arriba.enableDashedLine(30,10,10);
                LimitLine abajo = new LimitLine((float)inferior[i]);
                arriba.setLineWidth(0.9f);
                arriba.enableDashedLine(10,10,10);
                abajo.setLineColor(colores[0]);
                agregarloslimites(arriba,abajo,i);
            }
        }
    }
    private boolean deintaboolean(int data){
        return data==1;
    }
    private void agregarloslimites(LimitLine superior, LimitLine inferior,int caso){
        YAxis yAxis = dameelaxiselegido(caso);
        yAxis.setDrawLimitLinesBehindData(true);
        yAxis.addLimitLine(superior);
        yAxis.addLimitLine(inferior);
        invalidation(caso);
    }

    private void invalidation(int caso){
        if(caso==0){
            grafico1.invalidate();
        } else if(caso==1){
            grafico2.invalidate();
        } else if(caso==2){
            //graficoMPhumedaddelsuelo.invalidate();
        } else if(caso==3){
            graficoMPtemperaturadelsuelo.invalidate();
        } else{graficoMPconductividadelectrica.invalidate();}
    }
    private YAxis dameelaxiselegido(int caso){
        if(caso==0){
            return grafico1.getAxisLeft();
        } else if(caso==1){
            return grafico2.getAxisLeft();
        }
        /*
        else if(caso==2){
            return graficoMPhumedaddelsuelo.getAxisLeft();
        }
         */

        else if(caso==3){
            return graficoMPtemperaturadelsuelo.getAxisLeft();
        }
        else{return graficoMPconductividadelectrica.getAxisLeft();}
    }
    private void setgraficosK(kdatapack pack){
        grafico1.setData(pack.sacarelPM().second);
        grafico1.setXAxisRenderer(new labelpersonalizadoX(grafico1.getViewPortHandler(), grafico1.getXAxis(), grafico1.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxispm = grafico1.getXAxis();
        xaxispm.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxispm.setValueFormatter(new graficolabelgenerator(pack.sacarloslabels()));
        xaxispm.setGranularityEnabled(true);
        //xaxispm.setLabelRotationAngle(-45f);
        MarkerLineChartAdapter adapterpm = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarlasraizes().first,pack.sacarelPM().first);
        grafico1.setMarker(adapterpm);
        grafico1.setExtraTopOffset(OFFSETGRAFPHTOP);
        grafico1.setExtraLeftOffset(OFFSETGRAFPHLEFT);
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
        MarkerLineChartAdapter adaptertemp = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarlasraizes().second,pack.sacareltemp().first);
        grafico2.setMarker(adaptertemp);
        grafico2.setExtraTopOffset(OFFSETGRAFPHTOP);
        grafico2.setExtraLeftOffset(OFFSETGRAFPHLEFT);
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
        graficohumedadrelativa.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        graficohumedadrelativa.invalidate();
        //graficohumedadrelativa.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficohumedadrelativa.fitScreen();

        graficopresionbarometrica.setData(pack.sacarlapresionbarometrica());
        graficopresionbarometrica.setXAxisRenderer(new labelpersonalizadoX(graficopresionbarometrica.getViewPortHandler(), graficopresionbarometrica.getXAxis(), graficopresionbarometrica.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis XaxisPB = graficopresionbarometrica.getXAxis();
        XaxisPB.setPosition(XAxis.XAxisPosition.BOTTOM);
        XaxisPB.setValueFormatter(new graficolabelgenerator(pack.sacaraxislabels()));
        XaxisPB.setGranularityEnabled(true);
        graficopresionbarometrica.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficopresionbarometrica.setExtraLeftOffset(OFFSETGRAFPHLEFT);
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
        yaxisr.setAxisMaximum(4.3f); //4.5
        yaxisr.setTextColor(colores[0]);//
        XAxis xaxis3= grafico3.getXAxis();
        xaxis3.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis3.setValueFormatter(new graficolabelgenerator(pack.sacaraxislabels()));
        xaxis3.setGranularityEnabled(true);
        grafico3.setExtraTopOffset(OFFSETGRAFPHTOP);
        grafico3.setExtraLeftOffset(OFFSETGRAFPHLEFT);
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
        graficotemperaturavulvoseco.setExtraTopOffset(OFFSETGRAFPHTOP);
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
        graficorosadevientos.setExtraTopOffset(OFFSETGRAFPHTOP);
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
        graficoMPtemperaturadelsuelo.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficoMPtemperaturadelsuelo.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterTS = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraiztemperatura(),pack.sacarlatemperatura().first);
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
        graficoMPconductividadelectrica.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficoMPconductividadelectrica.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterCS = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizconductividad(),pack.sacarlaconductividad().first);
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
        graficoconductividadelectricadelporo.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficoconductividadelectricadelporo.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterCSP = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizconductividaddelporo(),pack.sacarlaconductividaddelporo().first);
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
        graficocontenidovolumetricodelagua.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficocontenidovolumetricodelagua.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterCVA = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizcontenidovolumetricodelagua(),pack.sacarelcontenidovolumetricodelagua().first);
        graficocontenidovolumetricodelagua.setMarker(adapterCVA);
        graficocontenidovolumetricodelagua.invalidate();
        graficocontenidovolumetricodelagua.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
        graficocontenidovolumetricodelagua.fitScreen();
    }
}
