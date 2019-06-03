package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ags.ayolelang.Adapter.AdapterListlelang_progress;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.util.ArrayList;

public class ProgressFragment extends Fragment {

    RecyclerView rv_search_lelang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, null);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        rv_search_lelang = view.findViewById(R.id.rv_progress);
        rv_search_lelang.setLayoutManager(new LinearLayoutManager(getContext()));

        loadData();

        return view;
    }

    private void loadData() {
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs = lelangHelper.getLelangbyUser(SharedPrefManager.getInstance(getContext()).getUser().getUser_id());
        lelangHelper.close();
        Log.d("size ", lelangs.size() + "");
        AdapterListlelang_progress adapterListlelang = new AdapterListlelang_progress(getContext());
        adapterListlelang.addItem(lelangs);
        rv_search_lelang.setAdapter(adapterListlelang);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Progress Saya");
    }

}
