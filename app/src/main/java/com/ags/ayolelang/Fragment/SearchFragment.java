package com.ags.ayolelang.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ags.ayolelang.Adapter.AdapterListMitra;
import com.ags.ayolelang.Adapter.AdapterListlelang;
import com.ags.ayolelang.Adapter.SearchTabAdapter;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements View.OnClickListener {

    Button btn_kategori, btn_event, btn_mitra;
    EditText et_fm_search;
    int fragment_active;
    FrameLayout search_container;
    private RecyclerView rv_search_lelang;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);

        btn_kategori = view.findViewById(R.id.btn_kategori);
        btn_event = view.findViewById(R.id.btn_event);
        btn_mitra = view.findViewById(R.id.btn_mitra);
        btn_kategori.setOnClickListener(this);
        btn_mitra.setOnClickListener(this);
        btn_event.setOnClickListener(this);
        et_fm_search = view.findViewById(R.id.et_fm_search);
        search_container = view.findViewById(R.id.search_container);
        rv_search_lelang = view.findViewById(R.id.rv_search_lelang);
        rv_search_lelang.setLayoutManager(new LinearLayoutManager(getContext()));

        btn_kategori.callOnClick();

        et_fm_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    search_container.setVisibility(View.GONE);
                    rv_search_lelang.setVisibility(View.VISIBLE);
                    switch (fragment_active) {
                        case R.id.btn_kategori:
                            loadLelang(et_fm_search.getText().toString());
                            break;
                        case R.id.btn_event:
                            break;
                        case R.id.btn_mitra:
                            loadMitra(et_fm_search.getText().toString());
                            break;
                    }
                } else {
                    search_container.setVisibility(View.VISIBLE);
                    rv_search_lelang.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    private void loadMitra(String s) {
        UserHelper userHelper = new UserHelper(getContext());
        userHelper.open();
        ArrayList<User> users = userHelper.getUserbyName(s);
        if (userHelper.getUserbyName(s).size() == 0) {
            Toast.makeText(getActivity(), "item tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
        userHelper.close();
        AdapterListMitra adapterListMitra = new AdapterListMitra(getContext());
        adapterListMitra.addItem(users);
        rv_search_lelang.setAdapter(adapterListMitra);
    }

    private void loadLelang(String s) {
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs = lelangHelper.getlelangbyjudul(s);
        if (lelangHelper.getlelangbyjudul(s).size() == 0) {
            Toast.makeText(getActivity(), "item tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
        lelangHelper.close();
        AdapterListlelang adapterListlelang = new AdapterListlelang(getContext());
        adapterListlelang.addItem(lelangs);
        rv_search_lelang.setAdapter(adapterListlelang);
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

        Drawable background_white = getResources().getDrawable(R.drawable.button_rectangle_selector);
        Drawable background_selected = getResources().getDrawable(R.drawable.button_rectangle);
        int light = getResources().getColor(R.color.colorWhite);
        int dark = getResources().getColor(R.color.colorGrey1);
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
                fragment_active = R.id.btn_kategori;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn_kategori.setBackground(background_selected);
                    btn_kategori.setTextColor(light);
                }
                fragment = new SearchKategoriFragment();
                break;
            case R.id.btn_event:
                boolean premium = false;
                if (premium) {
                    fragment_active = R.id.btn_event;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn_event.setBackground(background_selected);
                        btn_event.setTextColor(light);
                    }
                    fragment = new SearchEventFragment();
                } else {
                    dialogBukanPremium();
                }
                break;
            case R.id.btn_mitra:
                fragment_active = R.id.btn_mitra;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn_mitra.setBackground(background_selected);
                    btn_mitra.setTextColor(light);
                }
                fragment = new SearchMitraFragment();
                break;
        }
        ReplaceFragment(fragment);
    }

    private void dialogBukanPremium() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_upgrade);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        ImageButton btn_close=dialog.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
