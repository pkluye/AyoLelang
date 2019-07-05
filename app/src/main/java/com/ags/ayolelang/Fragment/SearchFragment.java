package com.ags.ayolelang.Fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ags.ayolelang.Adapter.SearchTabAdapter;
import com.ags.ayolelang.R;

public class SearchFragment extends Fragment implements View.OnClickListener {

    Button btn_kategori,btn_event,btn_mitra;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);

        btn_kategori=view.findViewById(R.id.btn_kategori);
        btn_event=view.findViewById(R.id.btn_event);
        btn_mitra=view.findViewById(R.id.btn_mitra);
        btn_kategori.setOnClickListener(this);
        btn_mitra.setOnClickListener(this);
        btn_event.setOnClickListener(this);

        btn_kategori.callOnClick();

        return view;
    }

    public void ReplaceFragment(Fragment fragment) {
        if (fragment != null)
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.search_container, fragment)
                    .commit();
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        Drawable background_white=getResources().getDrawable(R.drawable.button_rectangle_selector);
        Drawable background_selected=getResources().getDrawable(R.drawable.button_rectangle);
        int light=getResources().getColor(R.color.colorWhite);
        int dark=getResources().getColor(R.color.colorGrey1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //backkground
            btn_kategori.setBackground(background_white);
            btn_event.setBackground(background_white);
            btn_mitra.setBackground(background_white);
            //text
            btn_kategori.setTextColor(dark);
            btn_event.setTextColor(dark);
            btn_mitra.setTextColor(dark);
        }

        switch (v.getId()) {
            case R.id.btn_kategori:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn_kategori.setBackground(background_selected);
                    btn_kategori.setTextColor(light);
                }
                fragment=new SearchKategoriFragment();
                break;
            case R.id.btn_event:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn_event.setBackground(background_selected);
                    btn_event.setTextColor(light);
                }
                fragment=new SearchEventFragment();
                break;
            case R.id.btn_mitra:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn_mitra.setBackground(background_selected);
                    btn_mitra.setTextColor(light);
                }
                fragment=new SearchMitraFragment();
                break;
        }
        ReplaceFragment(fragment);
    }
}
