package com.example.neolink_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registrothree extends AppCompatActivity {

    private TextInputEditText ticket;
    private TextInputLayout layticket;
    private DatabaseReference mDatabase;
    private static final String TAG = "Leyendo el dato";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrothree);
        ticket = findViewById(R.id.ticketrecuperarthree);
        layticket = findViewById(R.id.layoutcodigoregistrothree);
        mDatabase = FirebaseDatabase.getInstance().getReference("Token");
        final String[] antes = new String[1];
        ticket.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                antes[0] = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if((s.length()==3)&&((!antes[0].contains("-")||antes[0].length()==3))){
                    s.append("-");
                }
                if(s.length()==12){
                    s.append("-");
                }
                layticket.setError("Separe el ticket utilizando guiones -");
            }
        });
    }

    public boolean validarticket(final String ticket){
        final boolean[] val = new boolean[0];
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.child(ticket).exists())&&(dataSnapshot.child(ticket).getValue().toString().equals("nulo"))){
                    val[0] = true;
                } else val[0] = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
        return val[0];

    }

    public void siguiente(View view){
        if(ticket.length()!=0){
            if(validarticket(ticket.getText().toString())) {
                //Terminar mensaje y irnos al main
                Intent itwo = getIntent();
                Bundle extras = itwo.getExtras();
                String correo = extras.getString("correo");
                String passw = extras.getString("passw");
                EnvioRegistro(correo, ticket.getText().toString());
                Toast.makeText(this, "Registro completado", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } else Toast.makeText( this, "Ticket invalido", Toast.LENGTH_SHORT).show();
        } else Toast.makeText( this, "Escriba un ticket", Toast.LENGTH_SHORT).show();

    }

    public void EnvioRegistro(String correo, String ticket){
        mDatabase.child(ticket).setValue(correo);
    }

    public void volver(View view){
        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
