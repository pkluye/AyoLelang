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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ags.ayolelang.Adapter.AdapterListMitra;
import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;

import java.util.ArrayList;


public class SearchMitraFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    View v;
    private RecyclerView rv_datasearch;
    private SwipeRefreshLayout srl_datasearch;
    private TextView tv_not_found;
    AdapterListMitra adapterListUser;
    UserHelper userHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search_mitra, container, false);

        rv_datasearch=v.findViewById(R.id.rv_search_mitra_datasearch);
        srl_datasearch=v.findViewById(R.id.srl_mitra_dataseacrh);
        tv_not_found=v.findViewById(R.id.not_found);

        userHelper=new UserHelper(getActivity());
        adapterListUser=new AdapterListMitra(getActivity());

        rv_datasearch.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadDataSearch();

        srl_datasearch.setOnRefreshListener(this);

        return v;
    }

    private void loadDataSearch() {
        userHelper.open();
        ArrayList<User> arrayList=userHelper.getUser();
        userHelper.close();
        tv_not_found.setVisibility(View.GONE);
        if (arrayList.size()<=0){
            tv_not_found.setVisibility(View.VISIBLE);
        }
        adapterListUser.addItem(arrayList);
        rv_datasearch.setAdapter(adapterListUser);
        srl_datasearch.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        loadDataSearch();
    }
}
