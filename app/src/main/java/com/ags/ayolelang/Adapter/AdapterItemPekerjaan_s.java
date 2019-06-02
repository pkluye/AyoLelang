package com.ags.ayolelang.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;

import java.util.ArrayList;

public class AdapterItemPekerjaan_s extends RecyclerView.Adapter<AdapterItemPekerjaan_s.CustomHolderView> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Pekerjaan> pekerjaanArrayList;

    public AdapterItemPekerjaan_s(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_preview_lelang, viewGroup, false);
        AdapterItemPekerjaan_s.CustomHolderView vh = new AdapterItemPekerjaan_s.CustomHolderView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolderView customHolderView, final int i) {
        final Pekerjaan pekerjaan = pekerjaanArrayList.get(i);

        final KategoriHelper kategoriHelper=new KategoriHelper(context);
        kategoriHelper.open();
        final Kategori kategori=kategoriHelper.getSingleKategori(pekerjaan.getPekerjaan_kategoriid());
        kategoriHelper.close();
        customHolderView.txt_judul_item.setText(kategori.getKategori_nama());
        customHolderView.txt_perkiraan_harga.setText(pekerjaan.getPekerjaan_harga() + "");
        customHolderView.txt_jumlah.setText(pekerjaan.getPekerjaan_jumlah()+"");
        customHolderView.btn_delete.setVisibility(View.GONE);

        customHolderView.btn_detailItemLelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailItem(pekerjaan);
            }
        });
        customHolderView.btn_editItemLelang.setVisibility(View.GONE);
    }

    private void detailItem(final Pekerjaan pekerjaan) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_pekerjaan);
        TextView txt_ukuran = dialog.findViewById(R.id.txt_ukuran);
        TextView txt_bahan = dialog.findViewById(R.id.txt_bahan);
        TextView txt_jumlah = dialog.findViewById(R.id.txt_jumlah);
        TextView txt_harga = dialog.findViewById(R.id.txt_harga);
        TextView txt_catatan = dialog.findViewById(R.id.txt_catatan);
        Button close_btn = (Button) dialog.findViewById(R.id.close_btn);

        txt_ukuran.setText(pekerjaan.getPekerjaan_ukuran());
        txt_bahan.setText(pekerjaan.getPekerjaan_bahan());
        txt_jumlah.setText(pekerjaan.getPekerjaan_jumlah() + "");
        txt_harga.setText(pekerjaan.getPekerjaan_harga() + "");
        txt_catatan.setText(pekerjaan.getPekerjaan_catatan());

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return pekerjaanArrayList.size();
    }

    public void addItem(ArrayList<Pekerjaan> mdata) {
        pekerjaanArrayList = mdata;
        notifyDataSetChanged();
    }


    public class CustomHolderView extends RecyclerView.ViewHolder {

        TextView txt_judul_item, txt_perkiraan_harga, txt_jumlah;
        ImageButton btn_delete;
        Button btn_editItemLelang, btn_detailItemLelang;

        public CustomHolderView(@NonNull View itemView) {
            super(itemView);

            txt_judul_item = itemView.findViewById(R.id.txt_judul_item);
            txt_perkiraan_harga = itemView.findViewById(R.id.txt_perkiraan_harga_item);
            txt_jumlah = itemView.findViewById(R.id.txt_jumlah);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_detailItemLelang = itemView.findViewById(R.id.btn_detailItemLelang);
            btn_editItemLelang = itemView.findViewById(R.id.btn_editItemLelang);
        }
    }
}
