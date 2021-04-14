package com.greenbird.neolink_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neolink_app.R;
import com.example.neolink_app.actividadbase;
import com.example.neolink_app.adaptadores.Listadefamiliareshijos;
import com.example.neolink_app.clases.clasesdereporte.InfoParaReporte;
import com.example.neolink_app.clases.clasesparaformargraficos.InfoParaGraficos;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.example.neolink_app.viewmodels.clasedeconexionparaeldrive;
import com.example.neolink_app.viewmodels.clasedeusosheets;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridData;
import com.google.api.services.sheets.v4.model.GridProperties;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.PivotGroup;
import com.google.api.services.sheets.v4.model.PivotTable;
import com.google.api.services.sheets.v4.model.PivotValue;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class configuracioncuenta extends Fragment implements AdapterView.OnItemSelectedListener {

    private MasterDrawerViewModel archi;
    private TextView tokendevinculacion;
    private TextView linkdeldrive;
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
    //private String [] listadeopciones ={"Últimos 15 días","Últimos 30 días","Últimos 12 meses"};
    private String [] listadeopciones ={"Últimos 15 días","Últimos 30 días","Últimos 12 meses"};
    private int opcionseleccionada=0;
    private ArrayAdapter<String> adapterlapsodetiempo;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount acct;
    private clasedeconexionparaeldrive mDriveServiceHelper;
    private clasedeusosheets mDriveServiceHelper2;
    private String borrarluego;
    private Snackbar mensajelogrado;
    private View v;
    private  LiveData<ArrayList<ArrayList<InfoParaReporte>>> reporte;
    private ProgressBar cargadodeldrive;



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
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestScopes(new Scope(DriveScopes.DRIVE_FILE), new Scope(DriveScopes.DRIVE_APPDATA), new Scope(SheetsScopes.SPREADSHEETS), new Scope(SheetsScopes.SPREADSHEETS_READONLY)).build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_configuracioncuenta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view;
        linkdeldrive = view.findViewById(R.id.linkdeldrive);
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
        opcionesdelapsodetiempo.setOnItemSelectedListener(this);
        glm = new GridLayoutManager(getActivity(),1);
        listadefamiliares.setLayoutManager(glm);
        cargadodeldrive = view.findViewById(R.id.cargadodeldrive);
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
                    adapter = new Listadefamiliareshijos(arrayListArrayListPair.second,archi,getViewLifecycleOwner(),dialogodeborrado,listadefamiliares);
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
            //reporte = archi.sistemadegenerarreportes(0);
            //reporte.observe(getViewLifecycleOwner(),listenerdelreporte2);
            signIn();
            //usuarioaceptado();
        }
    };
    private View.OnClickListener botongenerarreporteclick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            /*
            mDriveServiceHelper.createFile().addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {
                    mDriveServiceHelper.readFile(s);
                }
            });
            */
            cargadodeldrive.setVisibility(View.VISIBLE);
            bloquearbotones();
            /*archi.sistemadegenerarreportes(2).observe(getViewLifecycleOwner(), new Observer<ArrayList<ArrayList<InfoParaReporte>>>() {
                @Override
                public void onChanged(ArrayList<ArrayList<InfoParaReporte>> arrayLists) {
                    if(arrayLists.size()>0){
                        mensajelogrado.make(v,"se logro",BaseTransientBottomBar.LENGTH_SHORT);
                    }
                }
            });*/
            reporte = archi.sistemadegenerarreportes(opcionseleccionada);
            reporte.observe(getViewLifecycleOwner(),listenerdelreporte);
        }
    };
    private Observer<ArrayList<ArrayList<InfoParaReporte>>> listenerdelreporte = new Observer<ArrayList<ArrayList<InfoParaReporte>>>() {
        @Override
        public void onChanged(ArrayList<ArrayList<InfoParaReporte>> infoParaGraficos) {
            if(infoParaGraficos.size()>0){
                mensajelogrado.make(v,"Los datos fueron recibidos. No cierre la ventana hasta que termine el proceso.",BaseTransientBottomBar.LENGTH_INDEFINITE).show();
                mDriveServiceHelper2.createFileWithData(infoParaGraficos,archi.organizarlosdispositivosparaelreporte(),archi.damelahora(),opcionseleccionada).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String sreedsheetID) {
                        mensajelogrado.make(v,"El proceso terminó con exito",BaseTransientBottomBar.LENGTH_SHORT).show();
                        linkdeldrive.setClickable(true);
                        String link = "https://docs.google.com/spreadsheets/d/"+sreedsheetID+"/edit#gid=0";
                        linkdeldrive.setText(link);
                        cargadodeldrive.setVisibility(View.GONE);
                        desbloquearbotones();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mensajelogrado.make(v,"El proceso falló",BaseTransientBottomBar.LENGTH_SHORT).show();
                        cargadodeldrive.setVisibility(View.GONE);
                        desbloquearbotones();
                    }
                });
                reporte.removeObservers(getViewLifecycleOwner());
            }
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

    private void usuarioaceptado(String nombredebienvenida){
        layoutdeusuarioconfirmado.setVisibility(View.VISIBLE);
        botoningresaradrive.setVisibility(View.GONE);
        botoncuentaconfirmada.setVisibility(View.VISIBLE);
        String completo = "Bienvenido "+nombredebienvenida;
        mensajebienvenidadedrive.setText(completo);
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        Activityparalogearmeagoogledrive.launch(signInIntent);
    }
    ActivityResultLauncher<Intent> Activityparalogearmeagoogledrive = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        acct = GoogleSignIn.getLastSignedInAccount(getActivity());
                        usuarioaceptado(acct.getEmail());
                        GoogleSignIn.getSignedInAccountFromIntent(result.getData()).addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                            @Override
                            public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                                // Use the authenticated account to sign in to the Drive service.
                                GoogleAccountCredential credential =
                                        GoogleAccountCredential.usingOAuth2(
                                                getContext(), Collections.singleton(DriveScopes.DRIVE_FILE));
                                credential.setSelectedAccount(googleSignInAccount.getAccount());
                                Drive googleDriveService =
                                        new Drive.Builder(
                                                AndroidHttp.newCompatibleTransport(),
                                                new GsonFactory(),
                                                credential)
                                                .setApplicationName("Neolink")
                                                .build();
                                Sheets serviceSS = new Sheets.Builder(AndroidHttp.newCompatibleTransport(),new GsonFactory(), credential)
                                        .setApplicationName("NeoLink")
                                        .build();
                                
                                // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                                // Its instantiation is required before handling any onClick actions.
                                mDriveServiceHelper2 = new clasedeusosheets(serviceSS);
                                mDriveServiceHelper = new clasedeconexionparaeldrive(googleDriveService);
                            }
                        });
                    }
                }
            });

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        opcionseleccionada = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void bloquearbotones(){
        botondegenerar.setClickable(false);
        botoningresaradrive.setClickable(false);
        botoncuentaconfirmada.setClickable(false);
        botongenerarreporte.setClickable(false);
        listadefamiliares.setClickable(false);
        ((actividadbase)getActivity()).bloqueardrawer();
    }
    private void desbloquearbotones(){
        botondegenerar.setClickable(true);
        botoningresaradrive.setClickable(true);
        botoncuentaconfirmada.setClickable(true);
        botongenerarreporte.setClickable(true);
        listadefamiliares.setClickable(true);
        ((actividadbase)getActivity()).desbloqueardrawer();
    }
}