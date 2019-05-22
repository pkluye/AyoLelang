package com.ags.ayolelang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ags.ayolelang.Activity.DetailSpesifikasi;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;

import java.util.ArrayList;

public class AdapterItemPekerjaan extends RecyclerView.Adapter<AdapterItemPekerjaan.CustomHolderView> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Pekerjaan> pekerjaanArrayList;
    private static String nama;

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
        final Pekerjaan pekerjaan=pekerjaanArrayList.get(i);

        customHolderView.txt_judul_item.setText(pekerjaan.getPekerjaan_kategorinama());
        customHolderView.txt_perkiraan_harga.setText(pekerjaan.getPekerjaan_harga()+"");
        if(pekerjaan.getPekerjaan_fileurl()!=null||pekerjaan.getPekerjaan_fileurl()!="-"){
            customHolderView.txt_file_attachment.setText(pekerjaan.getPekerjaan_fileurl().substring(0,8)+"...");
            customHolderView.txt_file_attachment.setTextColor(Color.parseColor("#0000ff"));
            customHolderView.txt_file_attachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = pekerjaan.getPekerjaan_fileurl();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });
        }else{
            customHolderView.txt_file_attachment.setText("-");
        }
        customHolderView.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(pekerjaan.getPekerjaan_id());
                pekerjaanArrayList.remove(i);
                notifyDataSetChanged();
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
                Intent intent=new Intent(context, DetailSpesifikasi.class);
                intent.putExtra("edit",true);
                intent.putExtra("ukuran",pekerjaan.getPekerjaan_ukuran());
                intent.putExtra("bahan",pekerjaan.getPekerjaan_bahan());
                intent.putExtra("jumlah",pekerjaan.getPekerjaan_jumlah());
                intent.putExtra("harga",pekerjaan.getPekerjaan_harga());
                intent.putExtra("catatan",pekerjaan.getPekerjaan_catatan());
                intent.putExtra("fileurl",pekerjaan.getPekerjaan_fileurl());
                context.startActivity(intent);
            }
        });
    }

    private void detailItem(Pekerjaan pekerjaan) {
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
        //call retrofit pekerjaan delete
    }

    public class CustomHolderView extends RecyclerView.ViewHolder {

        TextView txt_judul_item, txt_perkiraan_harga, txt_file_attachment;
        ImageButton btn_delete;
        Button btn_editItemLelang, btn_detailItemLelang;

        public CustomHolderView(@NonNull View itemView) {
            super(itemView);

            txt_judul_item = itemView.findViewById(R.id.txt_judul_item);
            txt_perkiraan_harga = itemView.findViewById(R.id.txt_perkiraan_totalHarga);
            txt_file_attachment = itemView.findViewById(R.id.txt_file_attachment);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_detailItemLelang = itemView.findViewById(R.id.btn_detailItemLelang);
            btn_editItemLelang = itemView.findViewById(R.id.btn_editItemLelang);
        }
    }
}