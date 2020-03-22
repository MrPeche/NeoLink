package com.example.neolink_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout layouteins,layoutswei;
    private TextInputEditText user,pass;
    private CheckBox rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (TextInputEditText)findViewById(R.id.usuario); //La variable siempre se llama diferente que el id
        pass = (TextInputEditText)findViewById(R.id.contraseña);
        rec = (CheckBox)findViewById(R.id.recordarme);
        layouteins = (TextInputLayout)findViewById(R.id.layout1);

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
        //Verifico si el usuario o password es correcto
        if(verificarUser(user.getText().toString()) || verificarPass(pass.getText().toString())){
            if(rec.isChecked()){
                //Se Guarda
            }
            //Se manda a la activity
        }
        else Toast.makeText( this, "Usuario o contraseña Invalido", Toast.LENGTH_SHORT).show();

    }

    public boolean verificarUser(String user){  //Verificacion de Usuario
        return false;
    }
    public boolean verificarPass(String pass){  //Verificacion de password
        return false;
    }
    public void registro(View view){  //Funcion para registrarse
        Toast.makeText( this, "Registrando", Toast.LENGTH_SHORT).show();
    }
    public void recuperar(View view){ //Funcion de recuperar
        //startActivity(new Intent(this,recuperar.class));
    }
}
