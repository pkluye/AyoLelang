package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

public class GarapanFragment extends Fragment implements View.OnClickListener {
    private View v;

    private int id_parent;
    private CardView cv_pendidikan, cv_percetakan, cv_kuliner, cv_hiburan, cv_otomotif, cv_fotografi, cv_palugada;
    private TextView txt_namaPengguna;

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
        txt_namaPengguna = v.findViewById(R.id.txt_namaPengguna);
        cv_pendidikan.setOnClickListener(this);
        cv_percetakan.setOnClickListener(this);
        cv_hiburan.setOnClickListener(this);
        cv_otomotif.setOnClickListener(this);
        cv_fotografi.setOnClickListener(this);
        cv_kuliner.setOnClickListener(this);
        cv_palugada.setOnClickListener(this);

        loadAccount();

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("LELANG");
    }

    private void loadAccount() {
        User user= SharedPrefManager.getInstance(getContext()).getUser();
        txt_namaPengguna.setText(user.getUser_nama());
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = new FragmentSubKategori();

        Bundle bundle = new Bundle();
        bundle.putInt("mode", 1);
        switch (v.getId()) {
            case R.id.cv_pendidikan:
                bundle.putInt("id", 1);
                bundle.putString("tittle",getString(R.string.garapan_1));
                break;
            case R.id.cv_percetakan:
                bundle.putInt("id", 2);
                bundle.putString("tittle",getString(R.string.garapan_2));
                break;
            case R.id.cv_kuliner:
                bundle.putInt("id", 3);
                bundle.putString("tittle",getString(R.string.garapan_3));
                break;
            case R.id.cv_otomotif:
                bundle.putInt("id", 4);
                bundle.putString("tittle",getString(R.string.garapan_4));
                break;
            case R.id.cv_hiburan:
                bundle.putInt("id", 5);
                bundle.putString("tittle",getString(R.string.garapan_5));
                break;
            case R.id.cv_fotografi:
                bundle.putInt("id", 6);
                bundle.putString("tittle",getString(R.string.garapan_6));
                break;
            case R.id.cv_palugada:
                bundle.putInt("id", 7);
                bundle.putString("tittle",getString(R.string.garapan_7));
                break;

        }

        fragment.setArguments(bundle);
        ((MainActivity) getActivity())._loadFragment(fragment);
    }
}
