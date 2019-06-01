package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ags.ayolelang.Adapter.AdapterSubKategori;
import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.R;

import java.util.ArrayList;

public class FragmentSubKategori extends Fragment {

    private View v;
    private TextView txt_no_item, txt_subTittle, txt_subPenjelasan;
    private RecyclerView rv_subFragment;
    private ImageView btn_back;
    private int mode = 1, kategori_id = -1, lelang_id = 0;
    private String tittle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_subkategori, container, false);
        rv_subFragment = v.findViewById(R.id.rv_subFragment);
        rv_subFragment.setLayoutManager(new LinearLayoutManager(getContext()));
        btn_back = v.findViewById(R.id.btn_Back);
        txt_subTittle = v.findViewById(R.id.txt_subTittle);
        txt_subPenjelasan = v.findViewById(R.id.txt_subPenjelasan);
        txt_no_item = v.findViewById(R.id.txt_noItem);

        //transfer data
        Bundle bundle = getArguments();
        mode = bundle.getInt("mode", 1);
        kategori_id = bundle.getInt("id");
        tittle = bundle.getString("tittle");
        lelang_id = bundle.getInt("lelang_id", 0);

        String kategori_nama = bundle.getString("kategori_nama");

        //event
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //tampilkan data
        txt_subTittle.setText(tittle);
        if (kategori_nama != null) {
            txt_subPenjelasan.setText(kategori_nama);
        }
        loadData();
        return v;
    }

    void loadData() {
        if (mode == 1) {
            loadSubParentKategori();
        } else {
            loadKategori();
        }
    }

    private void loadKategori() {
        KategoriHelper kategoriHelper = new KategoriHelper(getContext());
        kategoriHelper.open();
        ArrayList<Kategori> kategoris = kategoriHelper.getKategori(kategori_id);
        kategoriHelper.close();
        if (kategoris.size() > 0) {
            isi_rv(kategoris);
            txt_no_item.setVisibility(v.INVISIBLE);
        } else {
            txt_no_item.setVisibility(v.VISIBLE);
            rv_subFragment.setVisibility(v.INVISIBLE);
        }

    }

    private void loadSubParentKategori() {
        KategoriHelper kategoriHelper = new KategoriHelper(getContext());
        kategoriHelper.open();
        ArrayList<Kategori> kategoris = kategoriHelper.getKategori_SubParent(kategori_id);
        kategoriHelper.close();
        if (kategoris.size() > 0) {
            isi_rv(kategoris);
            txt_no_item.setVisibility(v.INVISIBLE);
        } else {
            txt_no_item.setVisibility(v.VISIBLE);
            rv_subFragment.setVisibility(v.INVISIBLE);
        }
    }

    private void isi_rv(ArrayList<Kategori> kategoris) {
        AdapterSubKategori adapterSubKategori = new AdapterSubKategori(getContext());
        adapterSubKategori.addItem(kategoris);
        adapterSubKategori.setTittle(tittle);
        rv_subFragment.setAdapter(adapterSubKategori);
    }

}
