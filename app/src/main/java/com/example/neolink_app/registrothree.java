package com.example.neolink_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class registrothree extends AppCompatActivity {

    private TextInputEditText ticket;
    private TextInputLayout layticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrothree);
        ticket = findViewById(R.id.ticketrecuperarthree);
        layticket = findViewById(R.id.layoutcodigoregistrothree);
    }

    public boolean validarticket(String ticket){
        return true;
    }

    public void siguiente(View view){
        if(validarticket(ticket.getText().toString())){
            //Terminar mensaje y irnos al main
            Toast.makeText( this, "Registro completado", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else Toast.makeText( this, "Ticket invalido", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this,registrothree.class);
        startActivity(i);
    }

    public void volver(View view){
        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
