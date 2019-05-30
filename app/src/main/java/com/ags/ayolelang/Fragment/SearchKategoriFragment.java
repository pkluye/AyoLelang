package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ags.ayolelang.R;

public class SearchKategoriFragment extends Fragment implements View.OnClickListener{

    View v;
    private LinearLayout btn_search_pendidikan;
    private LinearLayout btn_search_printing;
    private LinearLayout btn_search_kuliner;
    private LinearLayout btn_search_otomotif;
    private LinearLayout btn_search_Hiburan;
    private LinearLayout btn_search_fotografi;
    private LinearLayout btn_search_palugada;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search_kategori, container, false);
        btn_search_pendidikan=v.findViewById(R.id.btn_search_pendidikan);
        btn_search_printing=v.findViewById(R.id.btn_search_printing);
        btn_search_kuliner=v.findViewById(R.id.btn_search_kuliner);
        btn_search_otomotif=v.findViewById(R.id.btn_search_otomotif);
        btn_search_Hiburan=v.findViewById(R.id.btn_search_Hiburan);
        btn_search_fotografi=v.findViewById(R.id.btn_search_fotografi);
        btn_search_palugada=v.findViewById(R.id.btn_search_palugada);

        btn_search_pendidikan.setOnClickListener(this);
        btn_search_printing.setOnClickListener(this);
        btn_search_kuliner.setOnClickListener(this);
        btn_search_otomotif.setOnClickListener(this);
        btn_search_Hiburan.setOnClickListener(this);
        btn_search_fotografi.setOnClickListener(this);
        btn_search_palugada.setOnClickListener(this);
        return v;
    }

    public void ReplaceFragment(Fragment fragment){
        if (fragment != null)
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ContainerFragmentSearch, fragment)
                    .addToBackStack(null)
                    .commit();
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = new ListLelangFragment();

        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.btn_search_pendidikan:
                bundle.putInt("id", 1);
                bundle.putString("tittle",getString(R.string.garapan_1));
                break;
            case R.id.btn_search_printing:
                bundle.putInt("id", 2);
                bundle.putString("tittle",getString(R.string.garapan_2));
                break;
            case R.id.btn_search_kuliner:
                bundle.putInt("id", 3);
                bundle.putString("tittle",getString(R.string.garapan_3));
                break;
            case R.id.btn_search_otomotif:
                bundle.putInt("id", 4);
                bundle.putString("tittle",getString(R.string.garapan_4));
                break;
            case R.id.btn_search_Hiburan:
                bundle.putInt("id", 5);
                bundle.putString("tittle",getString(R.string.garapan_5));
                break;
            case R.id.btn_search_fotografi:
                bundle.putInt("id", 6);
                bundle.putString("tittle",getString(R.string.garapan_6));
                break;
            case R.id.btn_search_palugada:
                bundle.putInt("id", 7);
                bundle.putString("tittle",getString(R.string.garapan_7));
                break;

        }

        fragment.setArguments(bundle);
        ReplaceFragment(fragment);
    }
}
