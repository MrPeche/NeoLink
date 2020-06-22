package com.example.neolink_app.adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class viewpagergrafiquitosAdapter extends FragmentStateAdapter {

    public viewpagergrafiquitosAdapter(FragmentActivity fa){
        super(fa);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
