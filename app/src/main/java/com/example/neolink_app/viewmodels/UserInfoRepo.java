package com.example.neolink_app.viewmodels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.neolink_app.adaptadores.FirebaseQueryLiveData;
import com.example.neolink_app.clases.OWNERitems;
import com.google.firebase.FirebaseExceptionMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserInfoRepo {

    //private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(HOT_STOCK_REF);
    private final MediatorLiveData ownerdata = new MediatorLiveData();

    private OWNERitems userneolinks = new OWNERitems();
    private DatabaseReference BaseDatos = FirebaseDatabase.getInstance().getReference();

    public UserInfoRepo(){}

    public LiveData<OWNERitems> dameneolinks(String uid){
        String patio = "/OWNERitems/"+uid;
        MutableLiveData<OWNERitems> hola = new MutableLiveData<>();
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(patio);
        final FirebaseQueryLiveData liveDataNL = new FirebaseQueryLiveData(BaseDatosNL);
        ownerdata.addSource(liveDataNL, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable final DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null) {
                    userneolinks = dataSnapshot.getValue(OWNERitems.class);
                    ownerdata.setValue(userneolinks);
                }
                else {
                    ownerdata.setValue(null);
                }
            }
        });
        return ownerdata;
    }

}