package com.ags.ayolelang.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.Activity.Preview;
import com.ags.ayolelang.DBHelper.KotaHelper;
import com.ags.ayolelang.DBHelper.ProvinsiHelper;
import com.ags.ayolelang.DBHelper.REQLelangHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.Fragment.FragmentDetailLelang_s;
import com.ags.ayolelang.Models.Kota;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Provinsi;
import com.ags.ayolelang.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdapterListlelang_progress extends RecyclerView.Adapter<AdapterListlelang_progress.CustomHolderView> {
    private LayoutInflater mInflater;
    Context context;
    ArrayList<Lelang> lelangs;

    public AdapterListlelang_progress(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_progress, viewGroup, false);
        AdapterListlelang_progress.CustomHolderView vh = new AdapterListlelang_progress.CustomHolderView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolderView cvh, int i) {
        final Lelang lelang = lelangs.get(i);
        cvh.txt_judulgarapan.setText(lelang.getLelang_judul());
        cvh.txt_harga.setText("Rp. " + lelang.getLelang_anggaran());
        final String tenggat_waktu=lelang.getLelang_tglmulai().substring(0, 11) + " ~ " + lelang.getLelang_tglselesai().substring(0, 11);
        cvh.txt_tenggatWaktu.setText(tenggat_waktu);
        final String alamat=getKotaProv(lelang.getLelang_kota());
        cvh.txt_alamat.setText(alamat);
        //Log.d("getkotaProv", getKotaProv(lelang.getLelang_kota()));
        cvh.btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.popup_progress);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                Button btn_OK = (Button) dialog.findViewById(R.id.btn_OK);
                Button btn_batal = (Button) dialog.findViewById(R.id.btn_batal);
                btn_OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(context,"belum buat api hehe...",Toast.LENGTH_SHORT).show();
                    }
                });

                btn_batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public void ReplaceFragment(Fragment fragment){
        if (fragment != null)
            ((MainActivity)context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ContainerFragmentSearch, fragment)
                    .addToBackStack(null)
                    .commit();
    }

    private String getKotaProv(int i) {
        String s = "";
        KotaHelper kotaHelper = new KotaHelper(context);
        kotaHelper.open();
        Kota kota = kotaHelper.getKotabyidKota(i);
        kotaHelper.close();
        ProvinsiHelper provinsiHelper = new ProvinsiHelper(context);
        provinsiHelper.open();
        Provinsi provinsi = provinsiHelper.getProvinsibyProvid(kota.getProvinsi_id());
        provinsiHelper.close();
        return kota.getNama() + "," + provinsi.getNama();
    }

    @Override
    public int getItemCount() {
        return lelangs.size();
    }

    public void addItem(ArrayList<Lelang> lelangs) {
        this.lelangs = lelangs;
        notifyDataSetChanged();
    }

    public class CustomHolderView extends RecyclerView.ViewHolder {
        private Button btn_batal;
        TextView txt_judulgarapan, txt_alamat, txt_harga, txt_tenggatWaktu;

        public CustomHolderView(@NonNull View itemView) {
            super(itemView);
            txt_judulgarapan = itemView.findViewById(R.id.txt_judulgarapan);
            txt_alamat = itemView.findViewById(R.id.txt_alamat);
            txt_harga = itemView.findViewById(R.id.txt_harga);
            txt_tenggatWaktu = itemView.findViewById(R.id.txt_tenggatWaktu);
            btn_batal=itemView.findViewById(R.id.btn_batal);
        }
    }
}
