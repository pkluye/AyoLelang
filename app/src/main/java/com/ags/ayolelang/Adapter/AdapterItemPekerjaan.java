package com.ags.ayolelang.Adapter;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
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

public class AdapterItemPekerjaan extends RecyclerView.Adapter<AdapterItemPekerjaan.CustomHolderView> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Pekerjaan> pekerjaanArrayList;

    public AdapterItemPekerjaan(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_preview_lelang, viewGroup, false);
        AdapterItemPekerjaan.CustomHolderView vh = new AdapterItemPekerjaan.CustomHolderView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolderView customHolderView, final int i) {
        final Pekerjaan pekerjaan = pekerjaanArrayList.get(i);

        final KategoriHelper kategoriHelper = new KategoriHelper(context);
        kategoriHelper.open();
        final Kategori kategori = kategoriHelper.getSingleKategori(pekerjaan.getPekerjaan_kategoriid());
        kategoriHelper.close();
        SpecBarangHelper specBarangHelper = new SpecBarangHelper(context);
        specBarangHelper.open();
        String satuan = specBarangHelper.getSatuan(pekerjaan.getPekerjaan_kategoriid() + "");
        specBarangHelper.close();
        customHolderView.txt_judul_item.setText(kategori.getKategori_nama() + "");
        customHolderView.txt_perkiraan_harga.setText("Rp. " + currencyFormat(pekerjaan.getPekerjaan_harga() + ""));
        customHolderView.txt_jumlah.setText(pekerjaan.getPekerjaan_jumlah() + " " + (satuan.equalsIgnoreCase("meter") ? "" : satuan));
        customHolderView.txt_catatan_item.setText(pekerjaan.getPekerjaan_catatan() + "");
        if (pekerjaan.getPekerjaan_catatan().equalsIgnoreCase("-"))
            customHolderView.txt_catatan_item.setVisibility(View.GONE);

        customHolderView.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pekerjaanArrayList.size() > 1) {
                    if (pekerjaan.getPekerjaan_status() == 0) {
                        deleteItem(pekerjaan.getPekerjaan_id());
                    } else {
                        updatestatus(pekerjaan.getPekerjaan_id());
                    }
                    pekerjaanArrayList.remove(i);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Pekerjaan harus lebih dari 1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        customHolderView.btn_detailItemLelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailItem(pekerjaan);
            }
        });
        customHolderView.btn_editItemLelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailSpesifikasi.class);
                intent.putExtra("edit", true);
                intent.putExtra("ukuran", pekerjaan.getPekerjaan_ukuran());
                intent.putExtra("jmlsisi", pekerjaan.getPekerjaan_jmlsisi());
                intent.putExtra("laminasi", pekerjaan.getPekerjaan_laminasi());
                intent.putExtra("bahan", pekerjaan.getPekerjaan_bahan());
                intent.putExtra("jumlah", pekerjaan.getPekerjaan_jumlah());
                intent.putExtra("harga", pekerjaan.getPekerjaan_harga());
                intent.putExtra("catatan", pekerjaan.getPekerjaan_catatan());
                intent.putExtra("lelang_id", pekerjaan.getPekerjaan_lelangid());
                intent.putExtra("pekerjaan_id", pekerjaan.getPekerjaan_id());
                intent.putExtra("kategori_id", pekerjaan.getPekerjaan_kategoriid());
                intent.putExtra("kategori_nama", kategori.getKategori_nama());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void updatestatus(int id) {
        REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(context);
        reqPekerjaanHelper.open();
        reqPekerjaanHelper.updateStatusto1(id);
        reqPekerjaanHelper.close();
    }

    private void detailItem(final Pekerjaan pekerjaan) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_pekerjaan);
        TextView txt_ukuran = dialog.findViewById(R.id.txt_ukuran);
        TextView txt_bahan = dialog.findViewById(R.id.txt_bahan);
        TextView txt_jmlsisi = dialog.findViewById(R.id.txt_jmlsisi);
        TextView txt_laminasi = dialog.findViewById(R.id.txt_laminasi);
        TextView txt_jumlah = dialog.findViewById(R.id.txt_jumlah);
        TextView txt_harga = dialog.findViewById(R.id.txt_harga);
        TextView txt_catatan = dialog.findViewById(R.id.txt_catatan);
        Button close_btn = (Button) dialog.findViewById(R.id.close_btn);

        txt_ukuran.setText(pekerjaan.getPekerjaan_ukuran());
        txt_bahan.setText(pekerjaan.getPekerjaan_bahan());
        txt_jmlsisi.setText(pekerjaan.getPekerjaan_jmlsisi());
        txt_laminasi.setText(pekerjaan.getPekerjaan_laminasi());
        SpecBarangHelper specBarangHelper = new SpecBarangHelper(context);
        specBarangHelper.open();
        String satuan = specBarangHelper.getSatuan(pekerjaan.getPekerjaan_kategoriid() + "");
        specBarangHelper.close();
        txt_jumlah.setText(pekerjaan.getPekerjaan_jumlah() + " " + (satuan.equalsIgnoreCase("meter") ? "" : satuan));
        txt_harga.setText("Rp. " + currencyFormat(pekerjaan.getPekerjaan_harga() + ""));
        txt_catatan.setText(pekerjaan.getPekerjaan_catatan());

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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

    public void deleteItem(int id) {
        REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(context);
        reqPekerjaanHelper.open();
        reqPekerjaanHelper.delete(id);
        reqPekerjaanHelper.close();
    }

    public class CustomHolderView extends RecyclerView.ViewHolder {

        TextView txt_catatan_item;
        TextView txt_judul_item, txt_perkiraan_harga, txt_jumlah;
        ImageButton btn_delete;
        TextView btn_editItemLelang, btn_detailItemLelang;

        public CustomHolderView(@NonNull View itemView) {
            super(itemView);

            txt_judul_item = itemView.findViewById(R.id.txt_judul_item);
            txt_perkiraan_harga = itemView.findViewById(R.id.txt_perkiraan_harga_item);
            txt_jumlah = itemView.findViewById(R.id.txt_jumlah);
            txt_catatan_item = itemView.findViewById(R.id.txt_catatan_item);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_detailItemLelang = itemView.findViewById(R.id.btn_detailItemLelang);
            btn_editItemLelang = itemView.findViewById(R.id.btn_editItemLelang);
        }
    }
}
