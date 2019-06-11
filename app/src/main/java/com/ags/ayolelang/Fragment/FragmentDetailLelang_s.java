package com.ags.ayolelang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ags.ayolelang.Activity.Preview;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.DBHelper.PekerjaanHelper;
import com.ags.ayolelang.DBHelper.REQLelangHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.util.ArrayList;

public class FragmentDetailLelang_s extends Fragment {
    private View v;
    private TextView txt_judulgarapan, txt_alamat, txt_tenggatWaktu, txt_eta, txt_namaPelelang, txt_deskripsi, txt_harga, txt_jumlahmitra, txt_pembayaran, txt_status;
    private LinearLayout btn_detailgarapan;
    private Button btn_ajukanPenawaran;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.detail_lelang, container, false);
        txt_judulgarapan = v.findViewById(R.id.txt_judulgarapan);
        txt_alamat = v.findViewById(R.id.txt_alamat);
        txt_tenggatWaktu = v.findViewById(R.id.txt_tenggatWaktu);
        txt_eta = v.findViewById(R.id.txt_eta);
        txt_namaPelelang = v.findViewById(R.id.txt_namaPelelang);
        txt_deskripsi = v.findViewById(R.id.txt_deskripsi);
        txt_harga = v.findViewById(R.id.txt_harga);
        txt_jumlahmitra = v.findViewById(R.id.txt_jumlahmitra);
        txt_pembayaran = v.findViewById(R.id.txt_pembayaran);
        txt_status = v.findViewById(R.id.txt_status);
        btn_detailgarapan = v.findViewById(R.id.btn_detailgarapan);
        btn_ajukanPenawaran = v.findViewById(R.id.btn_ajukanPenawaran);
        Bundle bundle = getArguments();
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        final Lelang lelang = lelangHelper.getLelang(bundle.getInt("lelang_id"));
        lelangHelper.close();
        txt_judulgarapan.setText(lelang.getLelang_judul());
        txt_namaPelelang.setText(getNama(lelang.getLelang_userid()));
        txt_alamat.setText(bundle.getString("alamat"));
        txt_eta.setText(bundle.getString("eta"));
        txt_tenggatWaktu.setText(bundle.getString("tenggat_waktu"));
        txt_jumlahmitra.setText(bundle.getInt("count_mitra") + " ");
        txt_pembayaran.setText(getResources().getStringArray(R.array.metode_bayar)[lelang.getLelang_pembayaran()]);
        txt_harga.setText("Rp. " + lelang.getLelang_anggaran());
        //Log.d("test",SharedPrefManager.getInstance(getContext()).getUser().getUser_id()+" - "+lelang.getLelang_userid());
        if (SharedPrefManager.getInstance(getContext()).getUser().getUser_id().equalsIgnoreCase(lelang.getLelang_userid())) {
            btn_ajukanPenawaran.setText("Edit");
        }
        btn_ajukanPenawaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_ajukanPenawaran.getText().toString().equalsIgnoreCase("edit")) {
                    edit(lelang);
                } else {
                    ajukanTawaran();
                }
            }
        });

        btn_detailgarapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putInt("lelang_id", lelang.getLelang_id());
                Fragment fragment = new ListPekerjaanFragment();
                fragment.setArguments(bundle1);
                ReplaceFragment(fragment);
            }
        });
        return v;
    }

    private void ajukanTawaran() {
    }

    private void edit(Lelang lelang) {
        REQLelangHelper reqLelangHelper = new REQLelangHelper(getContext());
        reqLelangHelper.open();
        reqLelangHelper.insert2(lelang);
        reqLelangHelper.close();

        PekerjaanHelper pekerjaanHelper = new PekerjaanHelper(getContext());
        pekerjaanHelper.open();
        ArrayList<Pekerjaan> pekerjaans = pekerjaanHelper.getPekerjaan(lelang.getLelang_id());
        pekerjaanHelper.close();

        REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(getContext());
        reqPekerjaanHelper.open();
        reqPekerjaanHelper.bulk_edit(pekerjaans);
        ArrayList<Pekerjaan> pekerjaanArrayList=reqPekerjaanHelper.getPekerjaan();
        reqPekerjaanHelper.close();

        for (Pekerjaan pekerjaan:pekerjaanArrayList){
            Log.d("testtt",pekerjaan.toString());
        }

        Intent intent = new Intent(getContext(), Preview.class);
        intent.putExtra("edit", true);
        startActivity(intent);
    }

    public void ReplaceFragment(Fragment fragment) {
        if (fragment != null)
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ContainerFragmentSearch, fragment)
                    .addToBackStack(null)
                    .commit();
    }

    private String getNama(String lelang_userid) {
        String nama = "belum di set";
        //user helper
        UserHelper userHelper=new UserHelper(getActivity());
        userHelper.open();
        User user=userHelper.getSingleUser(lelang_userid);
        userHelper.close();
        nama=user.getUser_nama();
        return nama;
    }

}
