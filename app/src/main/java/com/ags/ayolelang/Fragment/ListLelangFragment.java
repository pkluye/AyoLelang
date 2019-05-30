package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ags.ayolelang.R;

public class ListLelangFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    View v;
    TextView txt_subtext_kategori_dipilih;
    RecyclerView rv_search_lelang;
    int kategori_id;
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
        loadData();

        swipe_container.setOnRefreshListener(this);
        return v;
    }

    private void loadData() {

    }


    @Override
    public void onRefresh() {
        loadData();
    }
}
