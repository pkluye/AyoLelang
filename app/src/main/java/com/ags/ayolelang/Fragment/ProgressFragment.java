package com.ags.ayolelang.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.ags.ayolelang.R;

public class ProgressFragment extends Fragment implements View.OnClickListener{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_progress, null);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.klien_pemilihan:
                break;
            case R.id.klien_pengerjaan:
                break;
            case R.id.klien_selesai:
                break;
            case R.id.mitra_penawaran:
                break;
            case R.id.mitra_pengerjaan:
                break;
            case R.id.mitra_selesai:
                break;
        }
    }
}