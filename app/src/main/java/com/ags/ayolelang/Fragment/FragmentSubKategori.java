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

import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.R;

import java.util.ArrayList;

public class FragmentSubKategori extends Fragment {

    private View v;
    private ArrayList<Kategori> kategoris=new ArrayList<>();
    private RecyclerView rv_subFragment;
    private int id_subparent = -1;
    private int id_parent = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_subkategori, container, false);
        rv_subFragment = v.findViewById(R.id.rv_subFragment);
        rv_subFragment.setLayoutManager(new LinearLayoutManager(getContext()));
        //loadData();
        return v;
    }

//    void loadData() {
//        Call<DefaultResponse> call = RetrofitClient
//                .getInstance().getApi().getwithmessage();
//        call.enqueue(new Callback<KategoriRespon>() {
//            @Override
//            public void onResponse(Call<KategoriRespon> call, Response<KategoriRespon> response) {
//                KategoriRespon kategoriRespon = response.body();
//                if (!kategoriRespon.isError()) {
//                    kategoris = kategoriRespon.getKategori();
//                    AdapterSubKategori adapterSubKategori = new AdapterSubKategori(getContext());
//                    adapterSubKategori.addItem(filterKategori(kategoris, id_parent, id_subparent));
//                    rv_subFragment.setAdapter(adapterSubKategori);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<KategoriRespon> call, Throwable t) {
//
//            }
//        });
//
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            id_parent = bundle.getInt("id_parent", -1);
//            id_subparent = bundle.getInt("id_subparent",-1);
//        }
//    }
//
//    private ArrayList<Kategori> filterKategori(ArrayList<Kategori> kategoris, int _id_parent, int _id_subparent) {
//        ArrayList<Kategori> filteredKategori = new ArrayList<>();
//        if (_id_parent != -1) {
//            for (Kategori kategori : kategoris) {
//                if (kategori.getId_parent() == _id_parent&&kategori.getPriority()==1) {
//                    filteredKategori.add(kategori);
//                }
//            }
//        } else {
//            for (Kategori kategori : kategoris) {
//                if (kategori.getId_sub_parent() == _id_subparent && kategori.getPriority()==2) {
//                    filteredKategori.add(kategori);
//                }
//            }
//        }
//        return filteredKategori;
//    }

}
