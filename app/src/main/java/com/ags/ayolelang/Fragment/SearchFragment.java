package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ags.ayolelang.Adapter.SearchTabAdapter;
import com.ags.ayolelang.R;

public class SearchFragment extends Fragment {

    private SearchTabAdapter mSearchTabAdapter;

    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);

        mSearchTabAdapter = new SearchTabAdapter(getFragmentManager());

        mViewPager = view.findViewById(R.id.tab_pager);

        mSearchTabAdapter.addFragment(new ContainerKategoriSearchFragment(), "Kategori");
        mSearchTabAdapter.addFragment(new SearchEventFragment(), "Event");
        mSearchTabAdapter.addFragment(new SearchMitraFragment(), "Mitra");

        mViewPager.setAdapter(mSearchTabAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setText("Kategori");
        tabLayout.getTabAt(1).setText("Event");
        tabLayout.getTabAt(2).setText("Mitra");

        return view;
    }


//    private void setupViewPager(ViewPager viewPager) {
//        SearchTabAdapter adapter = new SearchTabAdapter(getFragmentManager());
//        adapter.addFragment(new SearchKategoriFragment(), "Kategori");
//        adapter.addFragment(new SearchEventFragment(), "Event");
//        adapter.addFragment(new SearchMitraFragment(), "Mitra");
//    }
}
