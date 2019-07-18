package com.ags.ayolelang.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.Activity.DetailSpesifikasi;
import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.DBHelper.SpecBarangHelper;
import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterItemPortofolio extends RecyclerView.Adapter<AdapterItemPortofolio.CustomHolderView> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Lelang> lelangs;

    public AdapterItemPortofolio(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_portofolio, viewGroup, false);
        AdapterItemPortofolio.CustomHolderView vh = new AdapterItemPortofolio.CustomHolderView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolderView customHolderView, final int i) {
        Lelang lelang = lelangs.get(i);
        customHolderView.text_judulPekerjaan.setText(lelang.getLelang_judul());
        customHolderView.text_deskripsiPekerjaan.setText(lelang.getLelang_deskripsi());
        UserHelper userHelper = new UserHelper(context);
        userHelper.open();
        String pemberi = userHelper.getSingleUser(lelang.getLelang_userid()).getUser_nama();
        userHelper.close();
        customHolderView.text_deskripsiPemberi.setText("by "+pemberi);
        customHolderView.text_hargaPekerjaan.setText("Rp. "+currencyFormat(lelang.getLelang_anggaran()+""));
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

    @Override
    public int getItemCount() {
        return lelangs.size();
    }

    public void addItem(ArrayList<Lelang> mdata) {
        lelangs = mdata;
        notifyDataSetChanged();
    }

    public class CustomHolderView extends RecyclerView.ViewHolder {

        TextView text_judulPekerjaan;
        TextView text_hargaPekerjaan;
        TextView text_deskripsiPekerjaan;
        TextView text_deskripsiWaktu;
        TextView text_deskripsiPemberi;

        public CustomHolderView(@NonNull View v) {
            super(v);
            text_judulPekerjaan = v.findViewById(R.id.text_judulPekerjaan);
            text_hargaPekerjaan = v.findViewById(R.id.text_hargaPekerjaan);
            text_deskripsiPekerjaan = v.findViewById(R.id.text_deskripsiPekerjaan);
            text_deskripsiPemberi = v.findViewById(R.id.text_deskripsiPemberi);
            text_deskripsiWaktu = v.findViewById(R.id.text_deskripsiWaktu);
        }
    }
}
