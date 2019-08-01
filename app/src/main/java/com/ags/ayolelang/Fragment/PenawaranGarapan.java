package com.ags.ayolelang.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ags.ayolelang.Adapter.AdapterListProgress_itemHistoriTawaran;
import com.ags.ayolelang.DBHelper.HistoriTawaranHelper;
import com.ags.ayolelang.DBHelper.TawaranHelper;
import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Tawaran;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class PenawaranGarapan extends Fragment {

    private View v;
    private TextView txt_subtext_judul;
    private TextView txt_idGarapan;
    private TextView txt_perkiraanBiaya;
    private TextView penawaran_terendah;
    private RecyclerView rv_riwayatPenawaran;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_penawaran_garapan, container, false);

        txt_subtext_judul=v.findViewById(R.id.txt_subtext_judul);
        txt_idGarapan=(TextView) v.findViewById(R.id.txt_idGarapan);
        txt_perkiraanBiaya=v.findViewById(R.id.txt_perkiraanBiaya);
        penawaran_terendah=v.findViewById(R.id.penawaran_terendah);
        rv_riwayatPenawaran=v.findViewById(R.id.rv_riwayatPenawaran);

        Bundle bundle=getArguments();
        Lelang lelang= (Lelang) bundle.getSerializable("lelang");

        txt_subtext_judul.setText(lelang.getLelang_judul());
        txt_idGarapan.setText(lelang.getLelang_id()+"");
        txt_perkiraanBiaya.setText("Rp. "+currencyFormat(lelang.getLelang_anggaran()+""));
        TawaranHelper tawaranHelper=new TawaranHelper(getContext());
        tawaranHelper.open();
        Tawaran tawaran=tawaranHelper.getTawaran1st(lelang.getLelang_id());
        tawaranHelper.close();
        UserHelper userHelper=new UserHelper(getContext());
        userHelper.open();
        User user=userHelper.getSingleUser(tawaran.getTawaran_userid());
        userHelper.close();
        penawaran_terendah.setText("Rp. "+currencyFormat(tawaran.getTawaran_anggaran()+"")+" ("+user.getUser_nama()+")");
        HistoriTawaranHelper historiTawaranHelper=new HistoriTawaranHelper(getContext());
        historiTawaranHelper.open();
        ArrayList<Tawaran> tawarans=historiTawaranHelper.getlisttawaran(lelang.getLelang_id(), SharedPrefManager.getInstance(getContext()).getUser().getUser_id());
        historiTawaranHelper.close();
        AdapterListProgress_itemHistoriTawaran adapterListProgress_itemHistoriTawaran=new AdapterListProgress_itemHistoriTawaran(getContext());
        adapterListProgress_itemHistoriTawaran.addItem(tawarans);
        rv_riwayatPenawaran.setAdapter(adapterListProgress_itemHistoriTawaran);
        return v;
    }

    private String currencyFormat(String harga) {
        Locale localeID = new Locale("in", "ID");
        harga = harga.replaceAll("[.,]", "");
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance(localeID);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("");
        formatRp.setGroupingSeparator('.');
        formatRp.setMonetaryDecimalSeparator(',');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(Double.parseDouble(harga));
    }
}
