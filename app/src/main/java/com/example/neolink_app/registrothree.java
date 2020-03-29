package com.example.neolink_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registrothree extends AppCompatActivity {

    private TextInputEditText ticket;
    private TextInputLayout layticket;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrothree);
        ticket = findViewById(R.id.ticketrecuperarthree);
        layticket = findViewById(R.id.layoutcodigoregistrothree);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ticket.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==3){
                    s.append("-");
                }
                if(s.length()==12){
                    s.append("-");
                }
                layticket.setError("Separe el ticket utilizando guiones -");
            }
        });
    }

    public boolean validarticket(String ticket){
        mDatabase.child("Token").child(ticket).getKey();
        

        return true;
    }

    public void siguiente(View view){
        if(validarticket(ticket.getText().toString())){
            //Terminar mensaje y irnos al main
            Intent itwo = getIntent();
            Bundle extras = itwo.getExtras();
            String correo = extras.getString("correo");
            String passw = extras.getString("passw");
            EnvioRegistro(correo,passw);
            Toast.makeText( this, "Registro completado", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else Toast.makeText( this, "Ticket invalido", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this,registrothree.class);
        startActivity(i);
    }

    public void EnvioRegistro(String correo, String password){
        // Se envia por firebase
    }

    public void volver(View view){
        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
