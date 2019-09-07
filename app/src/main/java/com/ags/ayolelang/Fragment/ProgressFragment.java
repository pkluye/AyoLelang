package com.ags.ayolelang.Fragment;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.R;

public class ProgressFragment extends Fragment implements View.OnClickListener {

    private Button btn_klien, btn_mitra;
    private TextView txt_pemilihan;
    private int role;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_progress, null);
        view.findViewById(R.id.klien_pemilihan).setOnClickListener(this);
        view.findViewById(R.id.klien_pengerjaan).setOnClickListener(this);
        view.findViewById(R.id.mitra_selesai).setOnClickListener(this);
        txt_pemilihan = view.findViewById(R.id.txt_pemilihan);
        btn_klien = view.findViewById(R.id.btn_klien);
        btn_mitra = view.findViewById(R.id.btn_mitra);
        btn_mitra.setOnClickListener(this);
        btn_klien.setOnClickListener(this);
        btn_klien.callOnClick();
        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        Drawable background_white = getResources().getDrawable(R.drawable.button_rectangle_selector);
        Drawable background_selected = getResources().getDrawable(R.drawable.button_rectangle);
        int light = getResources().getColor(R.color.colorWhite);
        int dark = getResources().getColor(R.color.colorGrey1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //backkground
            btn_klien.setBackground(background_white);
            btn_mitra.setBackground(background_white);
            //text
            btn_klien.setTextColor(dark);
            btn_mitra.setTextColor(dark);
        }

        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.btn_klien:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn_klien.setBackground(background_selected);
                    btn_klien.setTextColor(light);
                }
                role = 1;
                txt_pemilihan.setText("Pemilihan mitra");
                break;
            case R.id.btn_mitra:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn_mitra.setBackground(background_selected);
                    btn_mitra.setTextColor(light);
                }
                txt_pemilihan.setText("Penawaran Garapan");
                role = 2;
                break;
            case R.id.klien_pemilihan:
                if (role == 1) {
                    fragment = new FragmentlistProgress();
                    bundle.putInt("status", 3);
                } else {
                    bundle.putInt("status", 3);
                    bundle.putBoolean("mitra", true);
                }
                fragment.setArguments(bundle);
                ((MainActivity) getActivity())._loadFragment(fragment);
                break;
            case R.id.klien_pengerjaan:
                if (role == 1) {
                    bundle.putInt("status", 4);
                } else {
                    bundle.putInt("status", 4);
                    bundle.putBoolean("mitra", true);
                }
                fragment = new FragmentlistProgress();
                fragment.setArguments(bundle);
                ((MainActivity) getActivity())._loadFragment(fragment);
                break;
            case R.id.mitra_selesai:
                if (role == 1) {
                    bundle.putInt("status", 6);
                } else {
                    bundle.putInt("status", 6);
                    bundle.putBoolean("mitra", true);
                }
                fragment = new FragmentlistProgress();
                fragment.setArguments(bundle);
                ((MainActivity) getActivity())._loadFragment(fragment);
                break;
        }
    }
}