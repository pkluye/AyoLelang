package com.ags.ayolelang.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Fragment.FragmentMitra;
import com.ags.ayolelang.Models.Tawaran;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListProgress_itemHistoriTawaran extends RecyclerView.Adapter<AdapterListProgress_itemHistoriTawaran.CustomHolderView> {
    ArrayList<Tawaran> tawarans;
    Context context;
    private LayoutInflater mInflater;

    public AdapterListProgress_itemHistoriTawaran(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_riwayatpenawaran, viewGroup, false);
        AdapterListProgress_itemHistoriTawaran.CustomHolderView vh = new AdapterListProgress_itemHistoriTawaran.CustomHolderView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolderView chv, int i) {
        final Tawaran tawaran = tawarans.get(i);
        String[]splittime=tawaran.getTawaran_cdate().split(" ");
        chv.txt_hari.setText(splittime[0]);
        chv.txt_waktu.setText(splittime[1]);
        chv.anggaran.setText("Rp. "+currencyFormat(tawaran.getTawaran_anggaran()+""));
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
        return tawarans.size();
    }

    public void addItem(ArrayList<Tawaran> tawarans) {
        this.tawarans = tawarans;
        notifyDataSetChanged();
    }

    public class CustomHolderView extends RecyclerView.ViewHolder {

        TextView anggaran;
        TextView txt_waktu;
        TextView txt_hari;

        public CustomHolderView(@NonNull View v) {
            super(v);
            txt_waktu=v.findViewById(R.id.txt_waktu);
            txt_hari=v.findViewById(R.id.txt_hari);
            anggaran=v.findViewById(R.id.anggaran);
        }
    }
}
