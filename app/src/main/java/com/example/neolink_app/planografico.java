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
import com.example.neolink_app.clases.DepthPackage;
import com.example.neolink_app.clases.clasesparaformargraficos.InfoParaGraficos;
import com.example.neolink_app.clases.clasesparaformargraficos.fulldatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.gdatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.kdatapack;
import com.example.neolink_app.clases.clasesparaformargraficos.statedatapack;
import com.example.neolink_app.clases.configuracion.statelimitsport;
import com.example.neolink_app.clases.paquetedatasetPuertos;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;


public class planografico extends Fragment {
    private TextView nombre;
    private String name;
    private LineChart grafico1;
    private LineChart grafico2;
    private LineChart grafico3;
    private LineChart graficopresionbarometrica;
    private LineChart graficohumedadrelativa;
    private LineChart graficotemperaturavulvoseco;
    private LineChart graficohumedaddelsuelo;
    private LineChart graficotemperaturadelsuelo;
    private LineChart graficoconductividaddelsuelo;
    private LineChart graficorosadevientos;
    private LineChart graficoconductividadelectricadelporo;
    private LineChart graficocontenidovolumetricodelagua;

    private CardView cvpotencialMatricial;
    private CardView cvtemperatura;
    private CardView cvEnergia;
    private CardView cvhumedaddelsuelo;
    private CardView cvtemperaturadelsuelo;
    private CardView cvconductividadelectrica;
    private CardView cvHumedadrelativa;
    private CardView cvpresionbarometrica;
    private CardView cvtemperaturabulboseco;
    private CardView cvrosadevientos;
    private CardView cvconductividadelectricadelporo;
    private CardView cvcontenidovolumetricodelagua;

    private MasterDrawerViewModel archi;
    private paquetedatasetPuertos YPM2 = new paquetedatasetPuertos();
    private paquetedatasetPuertos YTemp2 = new paquetedatasetPuertos();
    private DepthPackage DepthP2 = new DepthPackage();
    //private MarkerLineChartAdapter adapter;
    private int alpha = 170;
    private int[] colores = {Color.argb(alpha,250,128,114),Color.argb(alpha,60,179,113),Color.argb(alpha,100,149,237),Color.argb(alpha,176,196,222)}; //salmon, medium sea green,corn flower blue, light steel blue https://www.rapidtables.com/web/color/RGB_Color.html
    private int MAX_DATAVISIBLE = 48;
    private float LINEWIDTH = 2.5f;
    private static float OFFSETGRAFPHTOP = 20f;
    private static float OFFSETGRAFPHLEFT = 10f;
    private static float OFFSETGRAFPHBOTTOM = 15f;


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
        nombre = view.findViewById(R.id.Nombrepager);
        nombre.setText(name);
        grafico1 = view.findViewById(R.id.graficochart1);
        grafico2= view.findViewById(R.id.graficochar2);
        grafico3= view.findViewById(R.id.graficoBat);
        graficohumedadrelativa= view.findViewById(R.id.graficoHumedadRelativa);
        graficopresionbarometrica= view.findViewById(R.id.graficoPresionBarometrica);
        graficotemperaturavulvoseco= view.findViewById(R.id.graficotemperaturavulvoseco);
        graficotemperaturadelsuelo= view.findViewById(R.id.graficotemperaturadelsuelo);
        graficohumedaddelsuelo = view.findViewById(R.id.graficohumedaddelsuelo);
        graficoconductividaddelsuelo = view.findViewById(R.id.graficoConductividadSuelo);
        graficoconductividadelectricadelporo = view.findViewById(R.id.graficoconductividadPoro);
        graficocontenidovolumetricodelagua = view.findViewById(R.id.graficocontenidovolumetricodelagua);
        graficorosadevientos = view.findViewById(R.id.graficorosadevientos);
        cvpotencialMatricial = view.findViewById(R.id.cvpotencialMatricial);
        cvtemperatura = view.findViewById(R.id.cvtemperatura);
        cvEnergia = view.findViewById(R.id.cvEnergia);
        cvhumedaddelsuelo = view.findViewById(R.id.cvhumedaddelsuelo);
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
        propiedadesgraficohumedaddeldelsuelo();
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
                        cvhumedaddelsuelo.setVisibility(View.VISIBLE);
                        cvtemperaturadelsuelo.setVisibility(View.VISIBLE);
                        cvconductividadelectricadelporo.setVisibility(View.VISIBLE);
                        cvcontenidovolumetricodelagua.setVisibility(View.VISIBLE);
                        setgraficoG(commdata.sacargdatapack());

                    }
                }
            }
        });

    }
    private void cleanthisshit(){
        YPM2.clean();
        YTemp2.clean();
        DepthP2.clean();
        grafico1.clear();
        grafico2.clear();
        grafico3.clear();
    }
    private void cleanG(){
        graficotemperaturadelsuelo.clear();
        graficohumedaddelsuelo.clear();
        graficoconductividaddelsuelo.clear();
        graficocontenidovolumetricodelagua.clear();
        graficoconductividadelectricadelporo.clear();
        graficorosadevientos.clear();
    }
    private void startposition(){
        cvpotencialMatricial.setVisibility(View.GONE);
        cvtemperatura.setVisibility(View.GONE);
        cvEnergia.setVisibility(View.GONE);
        cvhumedaddelsuelo.setVisibility(View.GONE);
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
    }
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
            graficohumedaddelsuelo.invalidate();
        } else if(caso==3){
            graficotemperaturadelsuelo.invalidate();
        } else{graficoconductividaddelsuelo.invalidate();}
    }
    private YAxis dameelaxiselegido(int caso){
        if(caso==0){
            return grafico1.getAxisLeft();
        } else if(caso==1){
            return grafico2.getAxisLeft();
        } else if(caso==2){
            return graficohumedaddelsuelo.getAxisLeft();
        } else if(caso==3){
            return graficotemperaturadelsuelo.getAxisLeft();
        } else{return graficoconductividaddelsuelo.getAxisLeft();}
    }
    private void setgraficosK(kdatapack pack){
        grafico1.setData(pack.sacarelPM().second);
        grafico1.setXAxisRenderer(new labelpersonalizadoX(grafico1.getViewPortHandler(), grafico1.getXAxis(), grafico1.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxispm = grafico1.getXAxis();
        xaxispm.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxispm.setValueFormatter(new graficolabelgenerator(pack.sacarloslabels()));
        //xaxispm.setLabelRotationAngle(-45f);
        MarkerLineChartAdapter adapterpm = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarlasraizes().first,pack.sacarelPM().first);
        grafico1.setMarker(adapterpm);
        grafico1.setExtraTopOffset(OFFSETGRAFPHTOP);
        grafico1.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        grafico1.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        grafico1.invalidate();
        grafico1.setVisibleXRangeMaximum(MAX_DATAVISIBLE);

        grafico2.setData(pack.sacareltemp().second);
        grafico2.setXAxisRenderer(new labelpersonalizadoX(grafico2.getViewPortHandler(), grafico2.getXAxis(), grafico2.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xAxistemp = grafico2.getXAxis();
        xAxistemp.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxistemp.setValueFormatter(new graficolabelgenerator(pack.sacarloslabels()));
        //xAxistemp.setLabelRotationAngle(-45f);
        MarkerLineChartAdapter adaptertemp = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarlasraizes().second,pack.sacareltemp().first);
        grafico2.setMarker(adaptertemp);
        grafico2.setExtraTopOffset(OFFSETGRAFPHTOP);
        grafico2.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        grafico2.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        grafico2.invalidate();
        grafico2.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
    }
    private void setgraficosstate(statedatapack pack){

        graficohumedadrelativa.setData(pack.sacarlahumedadrelativa());
        graficohumedadrelativa.setXAxisRenderer(new labelpersonalizadoX(graficohumedadrelativa.getViewPortHandler(), graficohumedadrelativa.getXAxis(), graficohumedadrelativa.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis XaxisHR = graficohumedadrelativa.getXAxis();
        XaxisHR.setPosition(XAxis.XAxisPosition.BOTTOM);
        XaxisHR.setValueFormatter(new graficolabelgenerator(pack.sacaraxislabels()));
        graficohumedadrelativa.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficohumedadrelativa.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficohumedadrelativa.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        graficohumedadrelativa.invalidate();
        graficohumedadrelativa.setVisibleXRangeMaximum(MAX_DATAVISIBLE);

        graficopresionbarometrica.setData(pack.sacarlapresionbarometrica());
        graficopresionbarometrica.setXAxisRenderer(new labelpersonalizadoX(graficopresionbarometrica.getViewPortHandler(), graficopresionbarometrica.getXAxis(), graficopresionbarometrica.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis XaxisPB = graficopresionbarometrica.getXAxis();
        XaxisPB.setPosition(XAxis.XAxisPosition.BOTTOM);
        XaxisPB.setValueFormatter(new graficolabelgenerator(pack.sacaraxislabels()));
        graficopresionbarometrica.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficopresionbarometrica.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficopresionbarometrica.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        graficopresionbarometrica.invalidate();
        graficopresionbarometrica.setVisibleXRangeMaximum(MAX_DATAVISIBLE);

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
        grafico3.setExtraTopOffset(OFFSETGRAFPHTOP);
        grafico3.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        grafico3.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        grafico3.invalidate();
        grafico3.setVisibleXRangeMaximum(MAX_DATAVISIBLE);

        graficotemperaturavulvoseco.setData(pack.sacarlatemperaturavulvoseco());
        graficotemperaturavulvoseco.setXAxisRenderer(new labelpersonalizadoX(graficotemperaturavulvoseco.getViewPortHandler(), graficotemperaturavulvoseco.getXAxis(), graficotemperaturavulvoseco.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis XaxisTVS = graficotemperaturavulvoseco.getXAxis();
        XaxisTVS.setPosition(XAxis.XAxisPosition.BOTTOM);
        XaxisTVS.setValueFormatter(new graficolabelgenerator(pack.sacaraxislabels()));
        graficotemperaturavulvoseco.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficotemperaturavulvoseco.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficotemperaturavulvoseco.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        graficotemperaturavulvoseco.invalidate();
        graficotemperaturavulvoseco.setVisibleXRangeMaximum(MAX_DATAVISIBLE);

        graficorosadevientos.setData(pack.sacarelwindspeed());
        graficorosadevientos.setXAxisRenderer(new labelpersonalizadoX(graficorosadevientos.getViewPortHandler(), graficorosadevientos.getXAxis(), graficorosadevientos.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis XaxisRV = graficorosadevientos.getXAxis();
        XaxisRV.setPosition(XAxis.XAxisPosition.BOTTOM);
        XaxisRV.setValueFormatter(new graficolabelgenerator(pack.sacaraxislabels()));
        graficorosadevientos.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficorosadevientos.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficorosadevientos.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        graficorosadevientos.invalidate();
        graficorosadevientos.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
    }
    private void setgraficoG(gdatapack pack){
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
        graficohumedaddelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);

        graficotemperaturadelsuelo.setData(pack.sacarlatemperatura().second);
        graficotemperaturadelsuelo.setXAxisRenderer(new labelpersonalizadoX(graficotemperaturadelsuelo.getViewPortHandler(), graficotemperaturadelsuelo.getXAxis(), graficotemperaturadelsuelo.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisTS = graficotemperaturadelsuelo.getXAxis();
        xaxisTS.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisTS.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        graficotemperaturadelsuelo.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficotemperaturadelsuelo.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficotemperaturadelsuelo.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterTS = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraiztemperatura(),pack.sacarlatemperatura().first);
        graficotemperaturadelsuelo.setMarker(adapterTS);
        graficotemperaturadelsuelo.invalidate();
        graficotemperaturadelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);

        graficoconductividaddelsuelo.setData(pack.sacarlaconductividad().second);
        graficoconductividaddelsuelo.setXAxisRenderer(new labelpersonalizadoX(graficoconductividaddelsuelo.getViewPortHandler(), graficoconductividaddelsuelo.getXAxis(), graficoconductividaddelsuelo.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisCS = graficoconductividaddelsuelo.getXAxis();
        xaxisCS.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisCS.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        graficoconductividaddelsuelo.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficoconductividaddelsuelo.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficoconductividaddelsuelo.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterCS = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizconductividad(),pack.sacarlaconductividad().first);
        graficoconductividaddelsuelo.setMarker(adapterCS);
        graficoconductividaddelsuelo.invalidate();
        graficoconductividaddelsuelo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);

        graficoconductividadelectricadelporo.setData(pack.sacarlaconductividaddelporo().second);
        graficoconductividadelectricadelporo.setXAxisRenderer(new labelpersonalizadoX(graficoconductividadelectricadelporo.getViewPortHandler(), graficoconductividadelectricadelporo.getXAxis(), graficoconductividadelectricadelporo.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisCSP = graficoconductividadelectricadelporo.getXAxis();
        xaxisCSP.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisCSP.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        graficoconductividadelectricadelporo.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficoconductividadelectricadelporo.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficoconductividadelectricadelporo.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterCSP = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizconductividaddelporo(),pack.sacarlaconductividaddelporo().first);
        graficoconductividadelectricadelporo.setMarker(adapterCSP);
        graficoconductividadelectricadelporo.invalidate();
        graficoconductividadelectricadelporo.setVisibleXRangeMaximum(MAX_DATAVISIBLE);

        graficocontenidovolumetricodelagua.setData(pack.sacarelcontenidovolumetricodelagua().second);
        graficocontenidovolumetricodelagua.setXAxisRenderer(new labelpersonalizadoX(graficocontenidovolumetricodelagua.getViewPortHandler(), graficocontenidovolumetricodelagua.getXAxis(), graficocontenidovolumetricodelagua.getTransformer(YAxis.AxisDependency.LEFT)));
        XAxis xaxisCVA = graficocontenidovolumetricodelagua.getXAxis();
        xaxisCVA.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxisCVA.setValueFormatter(new graficolabelgenerator(pack.sacarlabels()));
        graficocontenidovolumetricodelagua.setExtraTopOffset(OFFSETGRAFPHTOP);
        graficocontenidovolumetricodelagua.setExtraLeftOffset(OFFSETGRAFPHLEFT);
        graficocontenidovolumetricodelagua.setExtraBottomOffset(OFFSETGRAFPHBOTTOM);
        MarkerLineChartAdapter adapterCVA = new MarkerLineChartAdapter(getContext(),R.layout.item_dataetiqueta,pack.sacareldepth(),pack.sacarraizcontenidovolumetricodelagua(),pack.sacarelcontenidovolumetricodelagua().first);
        graficocontenidovolumetricodelagua.setMarker(adapterCVA);
        graficocontenidovolumetricodelagua.invalidate();
        graficocontenidovolumetricodelagua.setVisibleXRangeMaximum(MAX_DATAVISIBLE);
    }
}
