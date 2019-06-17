package com.ags.ayolelang.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;

import java.util.ArrayList;

public class AdapterListMitra extends RecyclerView.Adapter<AdapterListMitra.CustomHolderView>{
    ArrayList<User>users;
    Context context;
    private LayoutInflater mInflater;

    public AdapterListMitra(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_search_mitra, viewGroup, false);
        AdapterListMitra.CustomHolderView vh = new AdapterListMitra.CustomHolderView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolderView customHolderView, int i) {
        User user=users.get(i);
        customHolderView.txt_nama.setText(user.getUser_nama());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addItem(ArrayList<User> users){
        this.users=users;
        notifyDataSetChanged();
    }

    public class CustomHolderView extends RecyclerView.ViewHolder  {

        TextView txt_nama;

        public CustomHolderView(@NonNull View v) {
            super(v);
            txt_nama=v.findViewById(R.id.txt_nama);
        }
    }
}
