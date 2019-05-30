package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.R;

public class ContainerKategoriSearchFragment extends Fragment {

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search_container, container, false);

        ReplaceFragment(new SearchKategoriFragment());

        return v;
    }

    public void ReplaceFragment(Fragment fragment){
        if (fragment != null)
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ContainerFragmentSearch, fragment)
                    //.addToBackStack(null)
                    .commit();
    }


}
