package com.ags.ayolelang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import feri.com.lpse.Activity.DetailSpesifikasi;
import feri.com.lpse.Activity.MainActivity;
import feri.com.lpse.Fragment.FragmentSubKategori;
import feri.com.lpse.Models.Kategori;
import feri.com.lpse.R;

public class AdapterSubKategori extends RecyclerView.Adapter<AdapterSubKategori.CustomViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Kategori> kategoris;

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
        String nama_kategori=kategoris.get(i).getName();
        final int id_category = kategoris.get(i).getId_category();
        int id_parent = kategoris.get(i).getId_parent();
        int id_subparent = kategoris.get(i).getId_sub_parent();
        int id_priority = kategoris.get(i).getPriority();

        customViewHolder.nama_kategori.setText(nama_kategori);
        if (id_priority ==1){
            customViewHolder.nama_kategori.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentSubKategori fragmentSubKategori=new FragmentSubKategori();
                    Bundle bundle=new Bundle();
                    bundle.putInt("id_subparent",id_category);
                    fragmentSubKategori.setArguments(bundle);
                    ((MainActivity)context)._loadFragment(fragmentSubKategori);
                }
            });
        }else{
            customViewHolder.nama_kategori.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailSpesifikasi.class);
                    intent.putExtra("id_category",id_category);
                    context.startActivity(intent);
                }
            });
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

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView nama_kategori;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_kategori=itemView.findViewById(R.id.nama_kategori);
        }
    }
}
