package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.Adapter.AdapterListlelang;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.R;

import java.util.ArrayList;

import static com.ags.ayolelang.Fragment.SearchFragment.et_fm_search;

public class ContainerKategoriSearchFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    View v;
    RecyclerView rv_datasearch;
    SwipeRefreshLayout srl_datasearch;
    FrameLayout ContainerFragmentSearch;
    private TextView tv_not_found;
    LelangHelper lelangHelper;
    AdapterListlelang adapterListlelang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search_container, container, false);

        ContainerFragmentSearch=v.findViewById(R.id.ContainerFragmentSearch);
        rv_datasearch=v.findViewById(R.id.rv_search_lelang_datasearch);
        srl_datasearch=v.findViewById(R.id.swipe_container_dataseacrh);
        tv_not_found=v.findViewById(R.id.not_found);

        adapterListlelang=new AdapterListlelang(getActivity());
        lelangHelper=new LelangHelper(getActivity());

        rv_datasearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        ReplaceFragment(new SearchKategoriFragment());
        et_fm_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    srl_datasearch.setVisibility(View.VISIBLE);
                    ContainerFragmentSearch.setVisibility(View.GONE);
                    loadDataSearch(et_fm_search.getText().toString());
                } else {
                    srl_datasearch.setVisibility(View.GONE);
                    ContainerFragmentSearch.setVisibility(View.VISIBLE);
                    tv_not_found.setVisibility(View.GONE);
                    ReplaceFragment(new SearchKategoriFragment());
                }
            }
        });

        srl_datasearch.setOnRefreshListener(this);

        return v;
    }

    private void loadDataSearch(String s) {
        lelangHelper.open();
        ArrayList<Lelang>arrayList=lelangHelper.getlelangbyjudul(s);
        lelangHelper.close();
        //Log.d("search result",arrayList.size()+"");
        tv_not_found.setVisibility(View.GONE);
        if (arrayList.size()<=0){
            tv_not_found.setVisibility(View.VISIBLE);
        }
        adapterListlelang.addItem(arrayList);
        rv_datasearch.setAdapter(adapterListlelang);
    }

    public void ReplaceFragment(Fragment fragment){
        if (fragment != null)
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ContainerFragmentSearch, fragment)
                    //.addToBackStack(null)
                    .commit();
    }


    @Override
    public void onRefresh() {
        loadDataSearch(et_fm_search.getText().toString());
    }
}
