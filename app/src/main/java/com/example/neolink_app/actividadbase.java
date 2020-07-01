package com.example.neolink_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.neolink_app.viewmodels.MasterDrawerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class actividadbase extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView correoYO;
    private MasterDrawerViewModel archi;
    public FloatingActionButton fab;

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

        DrawerLayout drawer = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.datosgenerales,R.id.listita,R.id.grafiquitos,R.id.mapita)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //navigationView.setNavigationItemSelectedListener(this);
        View headerweather= navigationView.getHeaderView(0);
        correoYO = headerweather.findViewById(R.id.textocorreo);
        String yo = damecorreo();
        correoYO.setText(yo);
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
}

