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

import com.ags.ayolelang.Adapter.AdapterItemPekerjaan_s;
import com.ags.ayolelang.Adapter.AdapterListlelang;
import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.DBHelper.PekerjaanHelper;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;

import java.util.ArrayList;

public class ListPekerjaanFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View v;
    RecyclerView rv_search_pekerjaan;
    int lelang_id;
    private SwipeRefreshLayout swipe_container;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search_kategori_list, container, false);
        rv_search_pekerjaan = v.findViewById(R.id.rv_search_lelang);
        rv_search_pekerjaan.setLayoutManager(new LinearLayoutManager(getContext()));
        v.findViewById(R.id.navigatorr).setVisibility(View.GONE);
        swipe_container = v.findViewById(R.id.swipe_container);

        rv_search_pekerjaan.setPadding(50,50,50,50);

        Bundle bundle = getArguments();
        lelang_id=bundle.getInt("lelang_id");
        loadData(lelang_id);

        swipe_container.setOnRefreshListener(this);
        return v;
    }

    private void loadData(int lelang_id) {
        PekerjaanHelper pekerjaanHelper = new PekerjaanHelper(getContext());
        pekerjaanHelper.open();
        ArrayList<Pekerjaan> pekerjaans = pekerjaanHelper.getPekerjaan(lelang_id);
        pekerjaanHelper.close();
        AdapterItemPekerjaan_s adapterListlelang = new AdapterItemPekerjaan_s(getContext());
        adapterListlelang.addItem(pekerjaans);
        rv_search_pekerjaan.setAdapter(adapterListlelang);
        swipe_container.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        loadData(lelang_id);
    }
}
