package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
//    ArrayList<Integer>listidkategori=new ArrayList<>();
    private SwipeRefreshLayout swipe_container;
    private ImageView btn_back;
    EditText et_fm_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search_kategori_list, container, false);
        txt_subtext_kategori_dipilih=v.findViewById(R.id.txt_subtext_kategori_dipilih);
        rv_search_lelang=v.findViewById(R.id.rv_search_lelang);
        rv_search_lelang.setLayoutManager(new LinearLayoutManager(getContext()));
        swipe_container=v.findViewById(R.id.swipe_container);
        et_fm_search=v.findViewById(R.id.et_fm_search);
        btn_back=v.findViewById(R.id.btn_Back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        Bundle bundle=getArguments();
        txt_subtext_kategori_dipilih.setText(bundle.getString("tittle"));
        kategori_id=bundle.getInt("id");
        et_fm_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    loadData(et_fm_search.getText().toString());
                }else {
                    loadData();
                }
            }
        });
        loadData();

        swipe_container.setOnRefreshListener(this);
        return v;
    }

    private void loadData() {
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs=lelangHelper.getbyKategoriParent(kategori_id+"");
        lelangHelper.close();
        AdapterListlelang adapterListlelang=new AdapterListlelang(getContext());
        adapterListlelang.addItem(lelangs);
        rv_search_lelang.setAdapter(adapterListlelang);
        swipe_container.setRefreshing(false);
    }

    private void loadData(String s) {
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs=lelangHelper.getbyKategoriParentAndSearch(kategori_id+"",s);
        lelangHelper.close();
        AdapterListlelang adapterListlelang=new AdapterListlelang(getContext());
        adapterListlelang.addItem(lelangs);
        rv_search_lelang.setAdapter(adapterListlelang);
        swipe_container.setRefreshing(false);
    }


    @Override
    public void onRefresh() {
        loadData();
    }
}
