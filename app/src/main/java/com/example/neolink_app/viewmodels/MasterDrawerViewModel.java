package com.example.neolink_app.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.neolink_app.clases.OWNERitems;

public class MasterDrawerViewModel extends AndroidViewModel {
    private LiveData<OWNERitems> Usuarioneolinks;
    private UserInfoRepo appRepo;

    public MasterDrawerViewModel(@NonNull Application application) {
        super(application);
        appRepo = new UserInfoRepo();
    }
    public LiveData<OWNERitems> getLiveNL(String uid){
        Usuarioneolinks = appRepo.dameneolinks(uid);
        return Usuarioneolinks;
    }
}
