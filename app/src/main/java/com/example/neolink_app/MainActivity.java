package com.example.neolink_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.neolink_app.clases.LoginFirebase.PCUN;
import com.example.neolink_app.clases.LoginFirebase.UsuarioNeoL;
import com.example.neolink_app.viewmodels.loginviewmodel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout layouteins,layoutswei;
    private TextInputEditText user,pass;
    private CheckBox rec;
    private Button botonM;
    private TextView creaC,recuC;
    private ProgressBar loadM;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private loginviewmodel archi;
    private PCUN usuarioguardadoanteriormente;
    private String actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                moveTaskToBack(true);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
        createNotificationChannel();
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.usuario); //La variable siempre se llama diferente que el id
        pass = findViewById(R.id.contraseña);
        rec = findViewById(R.id.recordarme);
        layouteins = findViewById(R.id.layout1);
        botonM =  findViewById(R.id.botoningreso);
        creaC = findViewById(R.id.crearcuenta);
        recuC= findViewById(R.id.recuperarcontraseña);
        loadM = findViewById(R.id.CargadoMain);
        mAuth = FirebaseAuth.getInstance();
        archi = new ViewModelProvider(this).get(loginviewmodel.class);

        archi.exist.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    PCUN us = archi.info().getValue();
                    usuarioguardadoanteriormente = us;
                    if(us!=null) {
                        user.setText(us.getus());
                        pass.setText(us.getcn());
                        rec.setChecked(true);
                    }
                }
            }
        });
        /*
        String[] archivos = fileList(); // "NeoLinkid.txt"
        String Lineaguardada;

        if(archivoexiste(archivos,"NeoLinkid.txt")){
            try {

                InputStreamReader id = new InputStreamReader(openFileInput("NeoLinkid.txt"));
                BufferedReader br = new BufferedReader(id);
                Lineaguardada = br.readLine();
                id.close();
                br.close();
                user.setText(Lineaguardada.substring(0,Lineaguardada.indexOf(" ")));
                pass.setText(Lineaguardada.substring(Lineaguardada.indexOf(" ")+1));
                rec.setChecked(true);
                //logthisshit();

            } catch (IOException ignored){

            }
        }
        */
        /*user.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(user.didTouchFocusSelect()){
                    user.setHint("");
                }
            }
        });*/

    }

    public void logeo(View view){
        logthisshit();
    }
    void logthisshit(){
        setitMain();
        //Verifico si el usuario o password es correcto
        if((user.length()!=0)&&(pass.length()!=0)) {
            setitMain();
            if(usuarioguardadoanteriormente!=null){
                actual = usuarioguardadoanteriormente.getus() + " " + usuarioguardadoanteriormente.getcn() + '\n';
            } else {
                actual = " ";
            }
            archi.Log(user.getText().toString(), pass.getText().toString(), rec.isChecked(), actual, this).observe(this, new Observer<UsuarioNeoL>() {
                @Override
                public void onChanged(UsuarioNeoL usuarioNeoL) {
                    if(usuarioNeoL.Validate()==0) {
                        Intent i = new Intent(MainActivity.this, actividadbase.class);
                        i.putExtra("uid", usuarioNeoL.giveUID());
                        i.putExtra("correo", usuarioNeoL.giveMail());
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                    } else {
                        switch(usuarioNeoL.Validate()){
                            case 1:
                                Toast.makeText(MainActivity.this, "El correo no existe", Toast.LENGTH_SHORT).show();
                                archi.borrarlavariablelog();
                                break;
                            case 2:
                                Toast.makeText(MainActivity.this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                                archi.borrarlavariablelog();
                                break;
                            case 3:
                                Toast.makeText(MainActivity.this, "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
                                archi.borrarlavariablelog();
                                break;
                        }
                    }
                    setitbackMain();
                }
            });

        }
    }

    void setitMain(){
        user.setFocusable(false);
        pass.setFocusable(false);
        rec.setFocusable(false);
        botonM.setEnabled(false);
        creaC.setEnabled(false);
        recuC.setEnabled(false);
        loadM.setVisibility(View.VISIBLE);
    }
    void setitbackMain(){
        user.setFocusableInTouchMode(true);
        pass.setFocusableInTouchMode(true);
        rec.setFocusable(true);
        botonM.setEnabled(true);
        creaC.setEnabled(true);
        recuC.setEnabled(true);
        loadM.setVisibility(View.GONE);
    }


    public void registro(View view){ //Funcion para registrarse
        startActivity(new Intent(this,registroone.class));
    }
    public void recuperar(View view){ //Funcion de recuperar
        startActivity(new Intent(this,recuperar.class));
    }

    @Override
    public void onRestart() {
        super.onRestart();
        setitbackMain();
    }
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            String CHANNEL_ID = getString(R.string.CHANNEL_ID);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
