package com.example.neolink_app;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.neolink_app.clases.OLDneolinksboleto;
import com.example.neolink_app.clases.OWNERitems;
import com.example.neolink_app.clases.paqueteneolinkasociados;
import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class actividadbase extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView correoYO;
    private MasterDrawerViewModel archi;
    public FloatingActionButton fab;
    public DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniciarelView();

        setContentView(R.layout.activity_actividadbase);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fabdesparecer();
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        //DrawerLayout drawer = findViewById(R.id.drawer);
        drawer = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.listita,R.id.grafiquitos,R.id.mapita,R.id.registrodealertas,R.id.contactanos,R.id.mainActivity,R.id.configuracioncuenta)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //navigationView.setNavigationItemSelectedListener(this);
        View headerweather= navigationView.getHeaderView(0);
        correoYO = headerweather.findViewById(R.id.textocorreo);
        String yo = damecorreo();
        correoYO.setText(yo.split("@")[0]);
        /*
        MenuItem salida = navigationView.getMenu().findItem(R.id.ndsalida);
        salida.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                
                return true;
            }
        });

         */

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    void iniciarelView(){
        archi = new ViewModelProvider(this).get(MasterDrawerViewModel.class);
        archi.poneruid(dameuid());
        archi.ponercorreo(damecorreo());
        archi.validaruid().observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> booleanStringPair) {
                if(booleanStringPair!=null){
                    if(booleanStringPair.first){
                        archi.eshijo(booleanStringPair.second);
                    } else archi.espadre();
                }
            }
        });
//        archi.getLiveNL();
//        archi.Usuarioneolinks.observe(this, new Observer<OWNERitems>() {
//            @Override
//            public void onChanged(OWNERitems owneRitems) {
//                if(owneRitems!=null){
//                    for(int i=0;i<owneRitems.dametamanolista();i++){
//                        archi.getneonodofromneolink(owneRitems.getitem(i)).observe(actividadbase.this, new Observer<OLDneolinksboleto>() {
//                            @Override
//                            public void onChanged(OLDneolinksboleto olDneolinksboleto) {
//                                if(olDneolinksboleto!=null)
//                                    archi.listacompleta.add(olDneolinksboleto);
//                            }
//                        });
//                    }
//                }
//            }
//        });
        archi.gettodoelpaqueteneolinkasociado().observe(this, new Observer<paqueteneolinkasociados>() {
            @Override
            public void onChanged(paqueteneolinkasociados paqueteneolinkasociados) {
                if(paqueteneolinkasociados!=null){
                    archi.guardarelpaquetedelneolinkasociado(paqueteneolinkasociados);
                }
            }
        });

        /*
        archi.segraboelneolink().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    archi.neolinkguardadopositivo();
                }
            }
        });
         */
    }
    String dameuid(){
        return getIntent().getExtras().getString("uid");
    }
    String damecorreo(){
        return getIntent().getExtras().getString("correo");
    }

    public void fabaparecer(){
        fab.setVisibility(View.VISIBLE);
    }
    public void fabdesparecer(){
        fab.setVisibility(View.INVISIBLE);
    }
    public void fabcalendar() {fab.setImageResource(R.drawable.ic_calendar_fab);}
    public void fabplus(){fab.setImageResource(R.drawable.ic_stat_fab);}
    public void fabcheck(){fab.setImageResource(R.drawable.listosign);}
    public void bloqueardrawer(){ drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); }
    public void desbloqueardrawer(){ drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);}
}

