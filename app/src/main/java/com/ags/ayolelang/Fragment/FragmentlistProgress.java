package com.ags.ayolelang.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ags.ayolelang.Adapter.AdapterListlelang_progress;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.util.ArrayList;

public class FragmentlistProgress extends Fragment{
    RecyclerView rv_lelang;
    LinearLayout default_layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_list_progress, null);

        default_layout = view.findViewById(R.id.default_layout);
        rv_lelang = view.findViewById(R.id.rv_progress);
        rv_lelang.setLayoutManager(new LinearLayoutManager(getContext()));

        loadData();
        return view;
    }

    private void loadData() {
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs = lelangHelper.getLelangbyUser(SharedPrefManager.getInstance(getContext()).getUser().getUser_id());
        lelangHelper.close();
        AdapterListlelang_progress adapterListlelang = new AdapterListlelang_progress(getContext());
        adapterListlelang.addItem(lelangs);
        if (adapterListlelang.getItemCount()>0){
            default_layout.setVisibility(View.GONE);
            rv_lelang.setVisibility(View.VISIBLE);
        }
        rv_lelang.setAdapter(adapterListlelang);
        rv_lelang.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                if (rv_lelang.getAdapter().getItemCount()<1){
                    default_layout.setVisibility(View.VISIBLE);
                    rv_lelang.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Progress Saya");
    }
}
