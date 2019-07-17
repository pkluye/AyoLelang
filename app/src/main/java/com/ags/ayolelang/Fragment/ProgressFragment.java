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

import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.R;

public class ProgressFragment extends Fragment implements View.OnClickListener{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_progress, null);
        view.findViewById(R.id.klien_pemilihan).setOnClickListener(this);
        view.findViewById(R.id.klien_pengerjaan).setOnClickListener(this);
        view.findViewById(R.id.klien_selesai).setOnClickListener(this);
        view.findViewById(R.id.mitra_penawaran).setOnClickListener(this);
        view.findViewById(R.id.mitra_pengerjaan).setOnClickListener(this);
        view.findViewById(R.id.mitra_selesai).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment=null;
        Bundle bundle=new Bundle();
        switch (v.getId()){
            case R.id.klien_pemilihan:
                fragment=new FragmentlistProgress();
                bundle.putInt("status",3);
                fragment.setArguments(bundle);
                ((MainActivity)getActivity())._loadFragment(fragment);
                break;
            case R.id.klien_pengerjaan:
                fragment=new FragmentlistProgress();
                bundle.putInt("status",4);
                fragment.setArguments(bundle);
                ((MainActivity)getActivity())._loadFragment(fragment);
                break;
            case R.id.klien_selesai:
                fragment=new FragmentlistProgress();
                bundle.putInt("status",6);
                fragment.setArguments(bundle);
                ((MainActivity)getActivity())._loadFragment(fragment);
                break;
            case R.id.mitra_penawaran:
                fragment=new FragmentlistProgress();
                bundle.putInt("status",3);
                bundle.putBoolean("mitra",true);
                fragment.setArguments(bundle);
                ((MainActivity)getActivity())._loadFragment(fragment);
                break;
            case R.id.mitra_pengerjaan:
                fragment=new FragmentlistProgress();
                bundle.putInt("status",4);
                bundle.putBoolean("mitra",true);
                fragment.setArguments(bundle);
                ((MainActivity)getActivity())._loadFragment(fragment);
                break;
            case R.id.mitra_selesai:
                fragment=new FragmentlistProgress();
                bundle.putInt("status",6);
                bundle.putBoolean("mitra",true);
                fragment.setArguments(bundle);
                ((MainActivity)getActivity())._loadFragment(fragment);
                break;
        }
    }
}