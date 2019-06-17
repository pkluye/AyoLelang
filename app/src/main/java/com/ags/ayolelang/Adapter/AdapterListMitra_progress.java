package com.ags.ayolelang.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Models.Tawaran;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;

import java.util.ArrayList;

public class AdapterListMitra_progress extends RecyclerView.Adapter<AdapterListMitra_progress.CustomHolderView> {
    ArrayList<Tawaran> tawarans;
    Context context;
    private LayoutInflater mInflater;

    public AdapterListMitra_progress(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_mitra, viewGroup, false);
        AdapterListMitra_progress.CustomHolderView vh = new AdapterListMitra_progress.CustomHolderView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolderView customHolderView, int i) {
        Tawaran tawaran = tawarans.get(i);
        Log.d("tawaran",tawaran.toString());
        UserHelper userHelper = new UserHelper(context);
        userHelper.open();
        User user = userHelper.getSingleUser(tawaran.getTawaran_userid());
        userHelper.close();

        customHolderView.txt_nama.setText(user.getUser_nama());
        customHolderView.txt_tawaranBiaya.setText("Rp. " + tawaran.getTawaran_anggaran());
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

        TextView txt_tawaranBiaya;
        TextView txt_nama;

        public CustomHolderView(@NonNull View v) {
            super(v);
            txt_nama = v.findViewById(R.id.txt_namaMitra);
            txt_tawaranBiaya = v.findViewById(R.id.txt_tawaranBiaya);
        }
    }
}
