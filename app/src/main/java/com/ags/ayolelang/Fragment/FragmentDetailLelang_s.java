package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.R;

public class FragmentDetailLelang_s extends Fragment {
    private View v;
    private TextView txt_judulgarapan, txt_alamat, txt_tenggatWaktu, txt_eta, txt_namaPelelang, txt_deskripsi, txt_harga, txt_jumlahmitra, txt_pembayaran, txt_status;
    private LinearLayout btn_detailgarapan;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.lelang_detail, container, false);
        txt_judulgarapan=v.findViewById(R.id.txt_judulgarapan);
        txt_alamat=v.findViewById(R.id.txt_alamat);
        txt_tenggatWaktu=v.findViewById(R.id.txt_tenggatWaktu);
        txt_eta=v.findViewById(R.id.txt_eta);
        txt_namaPelelang=v.findViewById(R.id.txt_namaPelelang);
        txt_deskripsi=v.findViewById(R.id.txt_deskripsi);
        txt_harga=v.findViewById(R.id.txt_harga);
        txt_jumlahmitra=v.findViewById(R.id.txt_jumlahmitra);
        txt_pembayaran=v.findViewById(R.id.txt_pembayaran);
        txt_status=v.findViewById(R.id.txt_status);
        btn_detailgarapan=v.findViewById(R.id.btn_detailgarapan);
        Bundle bundle=getArguments();
        LelangHelper lelangHelper=new LelangHelper(getContext());
        lelangHelper.open();
        final Lelang lelang=lelangHelper.getLelang(bundle.getInt("lelang_id"));
        lelangHelper.close();
        txt_judulgarapan.setText(lelang.getLelang_judul());
        txt_namaPelelang.setText(getNama(lelang.getLelang_userid()));
        txt_alamat.setText(bundle.getString("alamat"));
        txt_eta.setText(bundle.getString("eta"));
        txt_tenggatWaktu.setText(bundle.getString("tenggat_waktu"));
        txt_jumlahmitra.setText(bundle.getInt("count_mitra")+"");
        txt_pembayaran.setText(getResources().getStringArray(R.array.metode_bayar)[lelang.getLelang_pembayaran()]);
        txt_harga.setText("Rp. "+lelang.getLelang_anggaran());
        btn_detailgarapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1=new Bundle();
                bundle1.putInt("lelang_id",lelang.getLelang_id());
                Fragment fragment=new ListPekerjaanFragment();
                fragment.setArguments(bundle1);
                ReplaceFragment(fragment);
            }
        });
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

    private String getNama(String lelang_userid) {
        String nama="belum di set";
        //user helper
        return nama;
    }

}
