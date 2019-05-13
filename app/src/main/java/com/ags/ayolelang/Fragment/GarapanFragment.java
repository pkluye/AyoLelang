package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import feri.com.lpse.Activity.MainActivity;
import feri.com.lpse.R;

public class GarapanFragment extends Fragment implements View.OnClickListener{
    private View v;
    private Button button1,button2,button3,button4,button5,button6,button7;
    private int id_parent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_garapan, container, false);
        button1 = v.findViewById(R.id.btn_kategori1);
        button2 = v.findViewById(R.id.btn_kategori2);
        button3 = v.findViewById(R.id.btn_kategori3);
        button4 = v.findViewById(R.id.btn_kategori4);
        button5 = v.findViewById(R.id.btn_kategori5);
        button6 = v.findViewById(R.id.btn_kategori6);
        button7 = v.findViewById(R.id.btn_kategori7);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("LELANG");
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = fragment = new FragmentSubKategori();;
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.btn_kategori1:
                bundle.putInt("id_parent", 1);
                break;
            case R.id.btn_kategori2:
                bundle.putInt("id_parent", 2);
                break;
            case R.id.btn_kategori3:
                bundle.putInt("id_parent", 3);
                break;
            case R.id.btn_kategori4:
                bundle.putInt("id_parent", 4);
                break;
            case R.id.btn_kategori5:
                bundle.putInt("id_parent", 5);
                break;
            case R.id.btn_kategori6:
                bundle.putInt("id_parent", 6);
                break;
            case R.id.btn_kategori7:
                bundle.putInt("id_parent", 7);
                break;

        }

        fragment.setArguments(bundle);
        ((MainActivity) getActivity())._loadFragment(fragment);
    }
}
