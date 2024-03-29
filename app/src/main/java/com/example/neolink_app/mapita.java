package com.example.neolink_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neolink_app.clases.GPS;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.clases.paqueteneolinkasociados;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class mapita extends Fragment implements OnMapReadyCallback {
    private MapView mapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private GoogleMap map;
    private MasterDrawerViewModel archi;
    private Marker marker;


    public mapita() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                getActivity().moveTaskToBack(true);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mapita, container, false);
        mapView = v.findViewById(R.id.mapView);
        InitGoogleMap(savedInstanceState);
        return v;
    }

    private void InitGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        archi = new ViewModelProvider(getActivity()).get(MasterDrawerViewModel.class);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        if(mapView!=null)
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        //map.setIndoorEnabled(false);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Set a preference for minimum and maximum zoom.
        map.setMinZoomPreference(8f);
        map.setMaxZoomPreference(16f);
        archi.recibirtodoslosgps().observe(getViewLifecycleOwner(), new Observer<ArrayList<Pair<ArrayList<String>, ArrayList<GPS>>>>() {
            @Override
            public void onChanged(ArrayList<Pair<ArrayList<String>, ArrayList<GPS>>> pairs) {
                if(pairs!=null){
                    agregarmarkadores2(pairs);
                }
            }
        });
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String nombre = marker.getTitle();
                Navigation.findNavController(getView()).navigate(mapitaDirections.actionMapitaToDatosgenerales(nombre));
            }
        });
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                String nombre = marker.getTitle();
                Navigation.findNavController(getView()).navigate(mapitaDirections.actionMapitaToGraficodelmapa(nombre));
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });
    }
    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView!=null)
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    private void agregarmarkadores2(ArrayList<Pair<ArrayList<String>, ArrayList<GPS>>> dispositivos){
        if(marker!=null) marker.remove();
        LatLngBounds.Builder bld = new LatLngBounds.Builder();
        for(Pair<ArrayList<String>, ArrayList<GPS>> disp: dispositivos){
            for(int i=0;i<disp.first.size();i++){
                if(disp.second.get(i)!=null){
                    LatLng posicionmarcador2 = new LatLng(disp.second.get(i).getLat(),disp.second.get(i).getLong());
                    bld.include(posicionmarcador2);
                    marker = map.addMarker(new MarkerOptions().position(posicionmarcador2).title(disp.first.get(i)).icon(BitmapDescriptorFactory.fromResource(R.drawable.icono22)).draggable(true));
                    map.moveCamera(CameraUpdateFactory.newLatLngBounds(bld.build(),0));
                    //map.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionmarcador2,14));
                }
            }
        }
    }
}
