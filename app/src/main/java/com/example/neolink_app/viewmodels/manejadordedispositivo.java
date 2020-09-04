package com.example.neolink_app.viewmodels;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class manejadordedispositivo {

    public void guardardispositivo(final String uid){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                String token = task.getResult().getToken();
                guardarnumero(uid,token);
            }
        });
    }
    public void guardarnumero(String uid,String token){
        String path = "/UserDevice/";
        DatabaseReference BaseDatosNL = FirebaseDatabase.getInstance().getReference(path);
        BaseDatosNL.child(uid).setValue(token);
    }
}
