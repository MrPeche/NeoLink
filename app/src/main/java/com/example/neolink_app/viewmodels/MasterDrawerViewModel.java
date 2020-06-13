package com.example.neolink_app.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.neolink_app.clases.OLDneolinksboleto;
import com.example.neolink_app.clases.OWNERitems;

public class MasterDrawerViewModel extends AndroidViewModel {
    public LiveData<OWNERitems> Usuarioneolinks;
    public LiveData<OLDneolinksboleto> neolinks;
    public UserInfoRepo appRepo;
    private String uid;

    public MasterDrawerViewModel(@NonNull Application application) {
        super(application);
        appRepo = new UserInfoRepo();
    }
    public void poneruid(String uid){
        this.uid = uid;
    }

    public LiveData<OWNERitems> getLiveNL(){
        Usuarioneolinks = appRepo.dameneolinks(uid);
        return Usuarioneolinks;
    }

    public LiveData<OLDneolinksboleto> getLiveNN(String neolink){
        neolinks = appRepo.damenodos(uid,neolink);
        return neolinks;
    }

}
