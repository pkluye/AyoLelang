package com.ags.ayolelang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ags.ayolelang.Activity.DetailSpesifikasi;
import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.Fragment.FragmentSubKategori;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.R;

import java.util.ArrayList;

public class AdapterSubKategori extends RecyclerView.Adapter<AdapterSubKategori.CustomViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Kategori> kategoris;
    private String tittle;

    public AdapterSubKategori(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public AdapterSubKategori.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_subkategori, viewGroup, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSubKategori.CustomViewHolder customViewHolder, int i) {
        Kategori kategori = kategoris.get(i);
        final String kategori_nama = kategori.getKategori_nama();
        final int kategori_id = kategori.getKategori_id();
        int kategori_parentid = kategori.getKategori_parentid();
        int kategori_subparentid = kategori.getKategori_subparentid();

        boolean mode = kategori_parentid != 0 && kategori_subparentid == 0;

        customViewHolder.txt_item.setText(kategori_nama);
        if (mode) {
            loadDesc(kategori_id, customViewHolder.txt_ItemDesc);
            customViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentSubKategori fragmentSubKategori = new FragmentSubKategori();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", kategori_id);
                    bundle.putInt("mode", 2);
                    bundle.putString("tittle", tittle);
                    bundle.putString("kategori_nama", kategori_nama);
                    fragmentSubKategori.setArguments(bundle);
                    ((MainActivity) context)._loadFragment(fragmentSubKategori);
                }
            });
        } else {
            customViewHolder.txt_ItemDesc.setVisibility(View.GONE);
            customViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailSpesifikasi.class);
                    intent.putExtra("kategori_id", kategori_id);
                    intent.putExtra("kategori_nama", kategori_nama);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void loadDesc(int kategori_id, final TextView txt) {
        KategoriHelper kategoriHelper = new KategoriHelper(context);
        kategoriHelper.open();
        ArrayList<Kategori> kategoris = kategoriHelper.getKategori(kategori_id);
        kategoriHelper.close();
        if (kategoris.size() > 0) {
            if (kategoris.size() > 3) {
                String s = "";
                for (int i = 0; i < 3; i++) {
                    s += kategoris.get(i).getKategori_nama() + ", ";
                }
                s += "...";
                //Log.d("desc", s);
                txt.setText(s);
            } else {
                String s = null;
                for (int i = 0; i < kategoris.size(); i++) {
                    s += kategoris.get(i).getKategori_nama() + ", ";
                }
                s += "...";
                //Log.d("desc", s);
                txt.setText(s);
            }

        } else {
            txt.setText("...");
        }
    }


    @Override
    public int getItemCount() {
        return kategoris.size();
    }

    public void addItem(ArrayList<Kategori> mData) {
        this.kategoris = mData;
        notifyDataSetChanged();
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView txt_item, txt_ItemDesc;
        LinearLayout layout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_item = itemView.findViewById(R.id.txt_Item);
            txt_ItemDesc = itemView.findViewById(R.id.txt_ItemDesc);
            layout = itemView.findViewById(R.id.layout_kategori);
        }
    }
}
