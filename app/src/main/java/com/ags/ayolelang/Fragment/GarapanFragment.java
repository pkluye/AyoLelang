package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.R;

public class GarapanFragment extends Fragment implements View.OnClickListener {
    private View v;

    private int id_parent;
    private CardView cv_pendidikan, cv_percetakan, cv_kuliner, cv_hiburan, cv_otomotif, cv_fotografi, cv_palugada;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_garapan, container, false);
        cv_pendidikan = v.findViewById(R.id.cv_pendidikan);
        cv_percetakan = v.findViewById(R.id.cv_percetakan);
        cv_kuliner = v.findViewById(R.id.cv_kuliner);
        cv_hiburan = v.findViewById(R.id.cv_hiburan);
        cv_otomotif = v.findViewById(R.id.cv_otomotif);
        cv_fotografi = v.findViewById(R.id.cv_fotografi);
        cv_palugada = v.findViewById(R.id.cv_palugada);
        cv_pendidikan.setOnClickListener(this);
        cv_percetakan.setOnClickListener(this);
        cv_hiburan.setOnClickListener(this);
        cv_otomotif.setOnClickListener(this);
        cv_fotografi.setOnClickListener(this);
        cv_kuliner.setOnClickListener(this);
        cv_palugada.setOnClickListener(this);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("LELANG");
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = fragment = new FragmentSubKategori();
        ;
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.cv_pendidikan:
                bundle.putInt("id_parent", 1);
                break;
            case R.id.cv_percetakan:
                bundle.putInt("id_parent", 2);
                break;
            case R.id.cv_kuliner:
                bundle.putInt("id_parent", 3);
                break;
            case R.id.cv_otomotif:
                bundle.putInt("id_parent", 4);
                break;
            case R.id.cv_hiburan:
                bundle.putInt("id_parent", 5);
                break;
            case R.id.cv_fotografi:
                bundle.putInt("id_parent", 6);
                break;
            case R.id.cv_palugada:
                bundle.putInt("id_parent", 7);
                break;

        }

        fragment.setArguments(bundle);
        ((MainActivity) getActivity())._loadFragment(fragment);
    }
}
