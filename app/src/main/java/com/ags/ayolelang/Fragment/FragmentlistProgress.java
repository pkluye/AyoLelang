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
import android.widget.Button;
import android.widget.LinearLayout;

import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.Adapter.AdapterListProgress_PemilihanMitra;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.util.ArrayList;

public class FragmentlistProgress extends Fragment {
    RecyclerView rv_lelang;
    LinearLayout default_layout;
    private Button btn_buatLelang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_list_progress, null);

        default_layout = view.findViewById(R.id.default_layout);
        rv_lelang = view.findViewById(R.id.rv_progress);
        rv_lelang.setLayoutManager(new LinearLayoutManager(getContext()));
        btn_buatLelang = view.findViewById(R.id.btn_buatLelang);
        btn_buatLelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment_(new GarapanFragment());
            }
        });
        Bundle bundle = getArguments();
        int status = bundle.getInt("status", 0);
        boolean mitra = bundle.getBoolean("mitra", false);
        if (mitra) {
            loadDataMitra(status);
        } else {
            loadData(status);
        }
        return view;
    }

    private void loadDataMitra(final int status) {
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs = lelangHelper.getLelangbyMitra(SharedPrefManager.getInstance(getContext()).getUser().getUser_id(), status);
        lelangHelper.close();
        AdapterListProgress_PemilihanMitra adapterListlelang = new AdapterListProgress_PemilihanMitra(getContext());
        adapterListlelang.addItem(lelangs);
        rv_lelang.setAdapter(adapterListlelang);
        if (rv_lelang.getAdapter().getItemCount() <1) {
            rv_lelang.setVisibility(View.GONE);
            default_layout.setVisibility(View.VISIBLE);
        } else {
            default_layout.setVisibility(View.GONE);
            rv_lelang.setVisibility(View.VISIBLE);
        }
        rv_lelang.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (rv_lelang.getAdapter().getItemCount() <1) {
                    rv_lelang.setVisibility(View.GONE);
                    default_layout.setVisibility(View.VISIBLE);
                } else {
                    default_layout.setVisibility(View.GONE);
                    rv_lelang.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadData(final int status) {
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs = lelangHelper.getLelangbyUser(SharedPrefManager.getInstance(getContext()).getUser().getUser_id(), status);
        lelangHelper.close();
        AdapterListProgress_PemilihanMitra adapterListlelang = new AdapterListProgress_PemilihanMitra(getContext());
        adapterListlelang.addItem(lelangs);
        rv_lelang.setAdapter(adapterListlelang);
        if (rv_lelang.getAdapter().getItemCount() <1) {
            rv_lelang.setVisibility(View.GONE);
            default_layout.setVisibility(View.VISIBLE);
        } else {
            default_layout.setVisibility(View.GONE);
            rv_lelang.setVisibility(View.VISIBLE);
        }
        rv_lelang.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (rv_lelang.getAdapter().getItemCount() <1) {
                    rv_lelang.setVisibility(View.GONE);
                    default_layout.setVisibility(View.VISIBLE);
                } else {
                    default_layout.setVisibility(View.GONE);
                    rv_lelang.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}