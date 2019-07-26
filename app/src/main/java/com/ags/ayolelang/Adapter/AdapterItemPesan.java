package com.ags.ayolelang.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ags.ayolelang.Activity.DetailSpesifikasi;
import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.Models.InterfacePesan;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.Models.Pesan;
import com.ags.ayolelang.Models.TanggalPesan;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.util.ArrayList;

public class AdapterItemPesan extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_SENTMESSAGE = 1;
    private static final int TYPE_RECEIVEMESSAGE = 2;
    private Context context;
    private ArrayList<InterfacePesan> mPesan;

    public AdapterItemPesan(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {

            case TYPE_HEADER:
                View v1 = inflater.inflate(R.layout.item_tanggalpesan, viewGroup, false);
                viewHolder = new TanggalPesanHolder(v1);
                break;

            case TYPE_SENTMESSAGE:
                View v2 = inflater.inflate(R.layout.item_message_sent, viewGroup, false);
                viewHolder = new SentMessageHolder(v2);
                break;
            case TYPE_RECEIVEMESSAGE:
                View v3 = inflater.inflate(R.layout.item_message_received, viewGroup, false);
                viewHolder = new ReceiveMessageHolder(v3);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        InterfacePesan interfacePesan = mPesan.get(position);

        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                TanggalPesan tp=(TanggalPesan)interfacePesan;

                TanggalPesanHolder tph = (TanggalPesanHolder) holder;
                tph.p_tanggal_txt.setText(tp.getTanggal());

                break;

            case TYPE_SENTMESSAGE:
                Pesan pesan_s=(Pesan)interfacePesan;

                SentMessageHolder smh = (SentMessageHolder) holder;
                smh.txt_message_body.setText(pesan_s.getPesan_isi());
                if (pesan_s.getTanggal()!=null){
                    smh.txt_message_time.setText(pesan_s.getTanggal().substring(11,16));
                }

                break;

            case TYPE_RECEIVEMESSAGE:
                Pesan pesan_r=(Pesan)interfacePesan;

                ReceiveMessageHolder rmh = (ReceiveMessageHolder) holder;
                rmh.txt_message_body.setText(pesan_r.getPesan_isi());
                if (pesan_r.getTanggal()!=null){
                    rmh.txt_message_time.setText(pesan_r.getTanggal().substring(11,16));
                }
                //Glide picture
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mPesan.size();
    }

    public void addItem(ArrayList<InterfacePesan> mdata) {
        mPesan = mdata;
        notifyDataSetChanged();
    }

    public InterfacePesan getLastItem(){
        return mPesan.get(mPesan.size()-1);
    }

    @Override
    public int getItemViewType(int position) {
        if (mPesan.get(position).isHeader()) {
            return TYPE_HEADER;
        } else {
            if (SharedPrefManager.getInstance(context).getUser().getUser_id()
                    .equalsIgnoreCase(mPesan.get(position).getPengirim())) {
                return TYPE_SENTMESSAGE;
            } else {
                return TYPE_RECEIVEMESSAGE;
            }
        }
    }

    public void addPesan(Pesan pesan) {
        if (mPesan!=null){
            mPesan=new ArrayList<>();
        }
        mPesan.add(pesan);
        notifyDataSetChanged();
    }

    public void addTanggal(TanggalPesan tgl) {
        mPesan.add(tgl);
        notifyDataSetChanged();
    }

    public boolean isAlreadySet(Pesan pesan){
        return mPesan.contains(pesan);
    }

    public ArrayList<Integer> getlistIdPesan(){
        ArrayList<Integer> pesans_id=new ArrayList<>();
        for (InterfacePesan interfacePesan:mPesan){
            if (interfacePesan.getClass().isInstance(new Pesan())){
                pesans_id.add(((Pesan) interfacePesan).getPesan_id());
            }
        }
        return pesans_id;
    }

    public void removeItem(InterfacePesan pesan) {
        mPesan.remove(pesan);
        notifyDataSetChanged();
    }

    private class TanggalPesanHolder extends RecyclerView.ViewHolder {
        TextView p_tanggal_txt;

        public TanggalPesanHolder(View v1) {
            super(v1);
            p_tanggal_txt = v1.findViewById(R.id.p_tanggal_txt);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView txt_message_body;
        TextView txt_message_time;

        public SentMessageHolder(View v2) {
            super(v2);
            txt_message_body = v2.findViewById(R.id.txt_message_body);
            txt_message_time = v2.findViewById(R.id.txt_message_time);
        }
    }

    private class ReceiveMessageHolder extends RecyclerView.ViewHolder {
        TextView txt_message_body;
        TextView txt_message_time;
        ImageView img_sender;

        public ReceiveMessageHolder(View v3) {
            super(v3);
            txt_message_body = v3.findViewById(R.id.txt_message_body);
            txt_message_time = v3.findViewById(R.id.txt_message_time);
            img_sender = v3.findViewById(R.id.img_sender);
        }
    }
}
