package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ags.ayolelang.Adapter.AdapterListProgress_itemMitra;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.DBHelper.TawaranHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Tawaran;
import com.ags.ayolelang.R;
import com.shuhart.stepview.StepView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class FragmentPemilihanMitra extends Fragment {

    private TextView txt_idGarapan;
    private TextView txt_biayaGarapan;
    private TextView txt_totalMitra;
    private TextView txt_subtext_judul;
    private RecyclerView rv_mitra;
    private int lelang_id;
    private ImageButton btn_Menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_pemilihan_mitra, null);

        txt_idGarapan = view.findViewById(R.id.txt_idGarapan);
        txt_biayaGarapan = view.findViewById(R.id.txt_perkiraanBiaya);
        txt_totalMitra = view.findViewById(R.id.txt_jumlahMitra);
        txt_subtext_judul=view.findViewById(R.id.txt_subtext_judul);
        rv_mitra=view.findViewById(R.id.rv_mitra);
        btn_Menu=view.findViewById(R.id.btn_Menu);
        btn_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        Bundle bundle = getArguments();
        lelang_id=bundle.getInt("lelang_id");
        loadData();
        return view;
    }

    private void loadData() {
        LelangHelper lelangHelper = new LelangHelper(getActivity());
        lelangHelper.open();
        Lelang lelang = lelangHelper.getLelang(lelang_id);
        lelangHelper.close();
//        if (lelang.getLelang_judul().length()<=28){
            txt_subtext_judul.setText(lelang.getLelang_judul());
//        }else{
//            txt_subtext_judul.setText(lelang.getLelang_judul().substring(0,28)+"...");
//        }

        txt_idGarapan.setText(lelang.getLelang_id() + "");
        txt_biayaGarapan.setText("Rp. " + currencyFormat(lelang.getLelang_anggaran()+""));

        TawaranHelper tawaranHelper = new TawaranHelper(getActivity());
        tawaranHelper.open();
        ArrayList<Tawaran> tawarans = tawaranHelper.getlisttawaran(lelang.getLelang_id());
        tawaranHelper.close();
        txt_totalMitra.setText(tawarans.size() + "");

        AdapterListProgress_itemMitra adapterListProgressitemMitra_ = new AdapterListProgress_itemMitra(getActivity());
        adapterListProgressitemMitra_.addItem(tawarans);

        rv_mitra.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_mitra.setAdapter(adapterListProgressitemMitra_);
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
