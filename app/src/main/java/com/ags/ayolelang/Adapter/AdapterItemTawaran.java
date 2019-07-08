package com.ags.ayolelang.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.Activity.DetailSpesifikasi;
import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.DBHelper.SpecBarangHelper;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import static com.ags.ayolelang.Activity.PenawaranActivity.txt_totalHarga;

public class AdapterItemTawaran extends RecyclerView.Adapter<AdapterItemTawaran.CustomHolderView> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Pekerjaan> pekerjaanArrayList;

    public AdapterItemTawaran(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_penawaran, viewGroup, false);
        AdapterItemTawaran.CustomHolderView vh = new AdapterItemTawaran.CustomHolderView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomHolderView customHolderView, final int i) {
        final Pekerjaan pekerjaan = pekerjaanArrayList.get(i);
        final KategoriHelper kategoriHelper = new KategoriHelper(context);
        kategoriHelper.open();
        final Kategori kategori = kategoriHelper.getSingleKategori(pekerjaan.getPekerjaan_kategoriid());
        kategoriHelper.close();
        final long lastharga = pekerjaan.getPekerjaan_harga();
        customHolderView.nama_item.setText(kategori.getKategori_nama());
        customHolderView.et_tawaran.setText(currencyFormat(pekerjaan.getHargaTawaran() + "") + "");
        customHolderView.et_tawaran.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                customHolderView.et_tawaran.removeTextChangedListener(this);
                if (s.length() > 0) {
                    if (Long.parseLong(customHolderView.et_tawaran.getText().toString().replaceAll("[.,]", "")) > lastharga) {
                        customHolderView.et_tawaran.setError("Tawaran harus lebih dari Rp. " + currencyFormat(lastharga + ""));
                        customHolderView.et_tawaran.requestFocus();
                        customHolderView.et_tawaran.addTextChangedListener(this);
                        return;
                    }

                    if (customHolderView.et_tawaran.getText().toString().charAt(0) == '0') {
                        customHolderView.et_tawaran.setError("invalid biaya");
                        customHolderView.et_tawaran.requestFocus();
                        customHolderView.et_tawaran.addTextChangedListener(this);
                        return;
                    }

                    Pekerjaan pekerjaan1 = pekerjaan;
                    pekerjaan1.setHargaTawaran(Long.parseLong(customHolderView.et_tawaran.getText().toString().replaceAll("[.,]", "")));
                    pekerjaanArrayList.set(i, pekerjaan1);

                    customHolderView.et_tawaran.setText(currencyFormat(customHolderView.et_tawaran.getText().toString().replaceAll("[.,]", "")) + "");
                    customHolderView.et_tawaran.setSelection(customHolderView.et_tawaran.getText().length());
                    txt_totalHarga.setText("Rp. " + currencyFormat(gethargaTawaran() + ""));
                } else {
                    customHolderView.et_tawaran.setError("harap masukan harga");
                    customHolderView.et_tawaran.requestFocus();
                    customHolderView.et_tawaran.addTextChangedListener(this);
                    return;
                }
                customHolderView.et_tawaran.addTextChangedListener(this);
            }
        });
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
        return pekerjaanArrayList.size();
    }

    public void addItem(ArrayList<Pekerjaan> mdata) {
        pekerjaanArrayList = mdata;
        notifyDataSetChanged();
    }

    public long gethargaTawaran() {
        long hargaTawaran = 0;
        for (Pekerjaan pekerjaan : pekerjaanArrayList) {
            hargaTawaran += pekerjaan.getHargaTawaran();
        }
        return hargaTawaran;
    }

    public ArrayList<Pekerjaan> getPekerjaanArrayList() {
        return pekerjaanArrayList;
    }

    public class CustomHolderView extends RecyclerView.ViewHolder {

        TextView nama_item;
        EditText et_tawaran;

        public CustomHolderView(@NonNull View itemView) {
            super(itemView);
            nama_item = itemView.findViewById(R.id.nama_item);
            et_tawaran = itemView.findViewById(R.id.et_tawaran);
        }
    }
}
