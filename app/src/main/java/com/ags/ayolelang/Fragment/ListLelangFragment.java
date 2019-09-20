package com.ags.ayolelang.Fragment;

import android.content.Intent;
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

import com.ags.ayolelang.Activity.Filter;
import com.ags.ayolelang.Adapter.AdapterListlelang;
import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.DBHelper.PekerjaanHelper;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

public class ListLelangFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View v;
    TextView txt_subtext_kategori_dipilih;
    RecyclerView rv_search_lelang;
    int kategori_id;
    //    ArrayList<Integer>listidkategori=new ArrayList<>();
    private SwipeRefreshLayout swipe_container;
    private ImageView btn_back;
    private Button btn_urutkan, btn_filter;
    private int code;
    private String[] customFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search_kategori_list, container, false);
        txt_subtext_kategori_dipilih = v.findViewById(R.id.txt_subtext_kategori_dipilih);
        rv_search_lelang = v.findViewById(R.id.rv_search_lelang);
        rv_search_lelang.setLayoutManager(new LinearLayoutManager(getContext()));
        swipe_container = v.findViewById(R.id.swipe_container);

        btn_back = v.findViewById(R.id.btn_Back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        Bundle bundle = getArguments();
        txt_subtext_kategori_dipilih.setText(bundle.getString("tittle"));
        kategori_id = bundle.getInt("id");

        btn_filter = v.findViewById(R.id.btn_filter);
        btn_urutkan = v.findViewById(R.id.btn_urutkan);

        btn_urutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Filter.class);
                intent.putExtra("type", 111);
                startActivityForResult(intent, 111);
            }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Filter.class);
                intent.putExtra("type", 222);
                startActivityForResult(intent, 222);
            }
        });

        loadData();

        swipe_container.setOnRefreshListener(this);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int code = data.getIntExtra("code", 0);
            if (code != 0) {
                if (requestCode == 111) {
                    if (code != 5) {
                        this.code = code;
                        filterdata(code);
                    }

                } else {
                    this.code=5;
                    int kotaid = (int) data.getIntExtra("kotaid", 0);
                    String hmin = data.getStringExtra("hargamin");
                    String hmax = data.getStringExtra("hargamax");
                    customFilter = new String[]{kotaid + "", hmin, hmax};
                    customFilter(customFilter);
                }
            }else {
                this.code=0;
                loadData();
            }
        }
    }

    private void loadData() {
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs = lelangHelper.getbyKategoriParent(kategori_id + "");
        lelangHelper.close();
        AdapterListlelang adapterListlelang = new AdapterListlelang(getContext());
        adapterListlelang.addItem(lelangs);
        rv_search_lelang.setAdapter(adapterListlelang);
        swipe_container.setRefreshing(false);
    }

    private void filterdata(int code) {
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs = lelangHelper.getbyKategoriParent(kategori_id + "", code);
        lelangHelper.close();
        AdapterListlelang adapterListlelang = new AdapterListlelang(getContext());
        adapterListlelang.addItem(lelangs);
        rv_search_lelang.setAdapter(adapterListlelang);
        swipe_container.setRefreshing(false);
    }

    private void customFilter(String[] s) {
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs = lelangHelper.getbyKategoriParent(kategori_id + "", s);
        lelangHelper.close();
        AdapterListlelang adapterListlelang = new AdapterListlelang(getContext());
        adapterListlelang.addItem(lelangs);
        rv_search_lelang.setAdapter(adapterListlelang);
        swipe_container.setRefreshing(false);
    }


    @Override
    public void onRefresh() {
        if (code == 0) {
            loadData();
        } else if (code < 5 && code > 0) {
            filterdata(code);
        } else {
            customFilter(customFilter);
        }
    }
}
