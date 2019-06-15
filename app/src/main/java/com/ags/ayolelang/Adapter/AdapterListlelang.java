package com.ags.ayolelang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.DBHelper.KotaHelper;
import com.ags.ayolelang.DBHelper.ProvinsiHelper;
import com.ags.ayolelang.DBHelper.TawaranHelper;
import com.ags.ayolelang.Fragment.FragmentDetailLelang_s;
import com.ags.ayolelang.Models.Kota;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Provinsi;
import com.ags.ayolelang.Models.Tawaran;
import com.ags.ayolelang.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.ags.ayolelang.Fragment.SearchFragment.et_fm_search;

public class AdapterListlelang extends RecyclerView.Adapter<AdapterListlelang.CustomHolderView> {
    private LayoutInflater mInflater;
    Context context;
    ArrayList<Lelang> lelangs;

    public AdapterListlelang(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_search_kategori, viewGroup, false);
        AdapterListlelang.CustomHolderView vh = new AdapterListlelang.CustomHolderView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolderView cvh, int i) {
        final Lelang lelang = lelangs.get(i);
        cvh.txt_judulgarapan.setText(lelang.getLelang_judul());
        cvh.txt_harga.setText("Rp. " + lelang.getLelang_anggaran());
        cvh.txt_waktu.setText(getTime(lelang.getLelang_tglmulai()));
        final String tenggat_waktu=lelang.getLelang_tglmulai().substring(0, 11) + " ~ " + lelang.getLelang_tglselesai().substring(0, 11);
        cvh.txt_tenggatWaktu.setText(tenggat_waktu);
        final String eta=getEta(lelang.getLelang_tglselesai());
        cvh.txt_eta.setText("(" + eta + ")");
        final String alamat=getKotaProv(lelang.getLelang_kota());
        cvh.txt_alamat.setText(alamat);
        //Log.d("getkotaProv", getKotaProv(lelang.getLelang_kota()));
        TawaranHelper tawaranHelper=new TawaranHelper(context);
        tawaranHelper.open();
        final int count_mitra=tawaranHelper.getlisttawaran(lelang.getLelang_id()).size();
        tawaranHelper.close();
        cvh.txt_jumlahmitra.setText(count_mitra +" Mitra Bersedia");
        cvh.item_search_kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_fm_search.setText(null);
                Bundle bundle=new Bundle();
                bundle.putString("eta",eta);
                bundle.putString("alamat",alamat);
                bundle.putInt("lelang_id",lelang.getLelang_id());
                bundle.putString("tenggat_waktu",tenggat_waktu);
                bundle.putInt("count_mitra",count_mitra);
                Fragment fragment = new FragmentDetailLelang_s();
                fragment.setArguments(bundle);
                ReplaceFragment(fragment);
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

    private String getTime(String lelang_tglmulai) {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = dateFormat.format(Calendar.getInstance().getTime());
        Date now = null;
        Date createLelang = null;
        try {
            now = dateFormat.parse(s);
            createLelang = dateFormat.parse(lelang_tglmulai);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = now.getTime() - createLelang.getTime();
        //Log.d("time", now.getTime() + " - " + createLelang.getTime()+"="+diff);
        return diff(diff) + " yang lalu";
    }

    private String getEta(String lelang_tglselesai) {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = dateFormat.format(Calendar.getInstance().getTime());
        Date now = null;
        Date deadline = null;
        try {
            now = dateFormat.parse(s);
            deadline = dateFormat.parse(lelang_tglselesai);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = deadline.getTime() - now.getTime();
        //Log.d("time", now.getTime() + " " + createLelang.getTime());
        if (!diff(diff).equalsIgnoreCase("e")) {
            return diff(diff) + " lagi";
        }
        return "expired";
    }

    private String diff(long diff) {
        if (diff / 1000 % 60 > 0) {
            if (diff / (1000 * 60) % 60 > 0) {
                if (diff / (1000 * 60 * 60) > 0) {
                    if (diff / (1000 * 60 * 60 * 24) > 0) {
                        if (diff / (1000 * 60 * 60 * 24 * 30) > 0) {
                            if (diff / (1000 * 60 * 60 * 24 * 365) > 0) {
                                int selisih = Integer.parseInt(diff / (1000 * 60 * 60 * 24 * 365) + "");
                                return selisih + " tahun";
                            }
                            int selisih = Integer.parseInt(diff / (1000 * 60 * 60 * 24 * 30) + "");
                            return selisih + " bulan";
                        }
                        int selisih = Integer.parseInt(diff / (1000 * 60 * 60 * 24) + "");
                        return selisih + " hari";
                    }
                    int selisih = Integer.parseInt(diff / (1000 * 60 * 60) + "");
                    return selisih + " jam";
                }
                int selisih = Integer.parseInt(diff / (1000 * 60) % 60 + "");
                return selisih + " menit";
            }
            //return diff / 1000 % 60 + " detik yang lalu";
            int selisih = Integer.parseInt(diff / 1000 % 60 + "");
            return selisih + " detik";
        }
        return "e";
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
        TextView txt_judulgarapan, txt_waktu, txt_alamat, txt_harga, txt_tenggatWaktu, txt_eta, txt_jumlahmitra;
        LinearLayout item_search_kategori;

        public CustomHolderView(@NonNull View itemView) {
            super(itemView);
            txt_judulgarapan = itemView.findViewById(R.id.txt_judulgarapan);
            txt_waktu = itemView.findViewById(R.id.txt_waktu);
            txt_alamat = itemView.findViewById(R.id.txt_alamat);
            txt_harga = itemView.findViewById(R.id.txt_harga);
            txt_tenggatWaktu = itemView.findViewById(R.id.txt_tenggatWaktu);
            txt_eta = itemView.findViewById(R.id.txt_eta);
            txt_jumlahmitra = itemView.findViewById(R.id.txt_jumlahmitra);
            item_search_kategori=itemView.findViewById(R.id.item_search_kategori);
        }
    }
}
