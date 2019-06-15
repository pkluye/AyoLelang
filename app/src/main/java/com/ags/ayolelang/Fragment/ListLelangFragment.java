package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ags.ayolelang.Adapter.AdapterListlelang;
import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.DBHelper.PekerjaanHelper;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ListLelangFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    View v;
    TextView txt_subtext_kategori_dipilih;
    RecyclerView rv_search_lelang;
    int kategori_id;
    ArrayList<Integer>listidkategori=new ArrayList<>();
    private SwipeRefreshLayout swipe_container;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search_kategori_list, container, false);
        txt_subtext_kategori_dipilih=v.findViewById(R.id.txt_subtext_kategori_dipilih);
        rv_search_lelang=v.findViewById(R.id.rv_search_lelang);
        rv_search_lelang.setLayoutManager(new LinearLayoutManager(getContext()));
        swipe_container=v.findViewById(R.id.swipe_container);

        Bundle bundle=getArguments();
        txt_subtext_kategori_dipilih.setText(bundle.getString("tittle"));
        kategori_id=bundle.getInt("id");
        KategoriHelper kategoriHelper=new KategoriHelper(getContext());
        kategoriHelper.open();
        ArrayList<Kategori>kategoris=kategoriHelper.getKategoribyParent(kategori_id);
        kategoriHelper.close();
        listidkategori.clear();
        for (Kategori kategori:kategoris){
            listidkategori.add(kategori.getKategori_id());
        }
        loadData();

        swipe_container.setOnRefreshListener(this);
        return v;
    }

    private void loadData() {
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs=lelangHelper.getAllLelang();
        lelangHelper.close();
        //Log.d("lelang size",lelangs.size()+"");
        PekerjaanHelper pekerjaanHelper=new PekerjaanHelper(getContext());
        ArrayList<Lelang> lelangs1=new ArrayList<>();
        if (lelangs.size()>0){
            for (Lelang lelang:lelangs){
                pekerjaanHelper.open();
                ArrayList<Pekerjaan> pekerjaan=pekerjaanHelper.getPekerjaan(lelang.getLelang_id());
                pekerjaanHelper.close();
                if (pekerjaan.size()>0){
                    int id=pekerjaan.get(0).getPekerjaan_kategoriid();
                    if (listidkategori.contains(id)){
                        lelangs1.add(lelang);
                    }
                }
            }
        }
        AdapterListlelang adapterListlelang=new AdapterListlelang(getContext());
        adapterListlelang.addItem(lelangs1);
        rv_search_lelang.setAdapter(adapterListlelang);
        swipe_container.setRefreshing(false);
    }


    @Override
    public void onRefresh() {
        loadData();
    }
}
