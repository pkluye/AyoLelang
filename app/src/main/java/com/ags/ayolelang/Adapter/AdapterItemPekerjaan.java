package com.ags.ayolelang.Adapter;

import android.app.TimePickerDialog;
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
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Activity.DetailSpesifikasi;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class AdapterItemPekerjaan extends RecyclerView.Adapter<AdapterItemPekerjaan.CustomHolderView> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Pekerjaan> pekerjaanArrayList;
    private static String nama;
    private TimePickerDialog dialog;

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
        final Pekerjaan pekerjaan = pekerjaanArrayList.get(i);

        customHolderView.txt_judul_item.setText(pekerjaan.getPekerjaan_kategorinama());
        customHolderView.txt_perkiraan_harga.setText(pekerjaan.getPekerjaan_harga() + "");
        if (pekerjaan.getPekerjaan_fileurl().length() > 1) {
            String filename = "";
            int posOfSubstr = pekerjaan.getPekerjaan_fileurl().lastIndexOf("/") + 12;
            if (pekerjaan.getPekerjaan_fileurl().length() > 79) {
                filename = pekerjaan.getPekerjaan_fileurl().substring(posOfSubstr, 17) + "...";
            } else {
                filename = pekerjaan.getPekerjaan_fileurl().substring(posOfSubstr);
            }
            customHolderView.txt_file_attachment.setText(filename);
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
        } else {
            customHolderView.txt_file_attachment.setText("-");
        }
        customHolderView.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pekerjaanArrayList.size() <= 1) {
                    Toast.makeText(context, "Anda tidak dapat menghapus pekerjaan lagi, minimal harus ada 1 pekerjaan yang dilelang",
                            Toast.LENGTH_LONG).show();
                } else {
                    deleteItem(pekerjaan.getPekerjaan_id());
                    pekerjaanArrayList.remove(i);
                    notifyDataSetChanged();
                }

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
                Intent intent = new Intent(context, DetailSpesifikasi.class);
                intent.putExtra("edit", true);
                intent.putExtra("ukuran", pekerjaan.getPekerjaan_ukuran());
                intent.putExtra("bahan", pekerjaan.getPekerjaan_bahan());
                intent.putExtra("jumlah", pekerjaan.getPekerjaan_jumlah());
                intent.putExtra("harga", pekerjaan.getPekerjaan_harga());
                intent.putExtra("catatan", pekerjaan.getPekerjaan_catatan());
                intent.putExtra("fileurl", pekerjaan.getPekerjaan_fileurl());
                intent.putExtra("lelang_id", pekerjaan.getPekerjaan_lelangid());
                intent.putExtra("pekerjaan_id", pekerjaan.getPekerjaan_id());
                intent.putExtra("kategori_id", pekerjaan.getPekerjaan_kategoriid());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void detailItem(final Pekerjaan pekerjaan) {
        dialog.setContentView(R.layout.popup_pekerjaan);
        TextView txt_ukuran = dialog.findViewById(R.id.txt_ukuran);
        TextView txt_bahan = dialog.findViewById(R.id.txt_bahan);
        TextView txt_jumlah = dialog.findViewById(R.id.txt_jumlah);
        TextView txt_harga = dialog.findViewById(R.id.txt_harga);
        TextView txt_attachment = dialog.findViewById(R.id.txt_attachment);
        TextView txt_catatan = dialog.findViewById(R.id.txt_catatan);
        Button close_btn = (Button) dialog.findViewById(R.id.close_btn);

        txt_ukuran.setText(pekerjaan.getPekerjaan_ukuran());
        txt_bahan.setText(pekerjaan.getPekerjaan_bahan());
        txt_jumlah.setText(pekerjaan.getPekerjaan_jumlah() + "");
        txt_harga.setText(pekerjaan.getPekerjaan_harga() + "");
        txt_catatan.setText(pekerjaan.getPekerjaan_catatan());
        if (pekerjaan.getPekerjaan_fileurl().length() > 1) {
            String filename = "";
            int posOfSubstr = pekerjaan.getPekerjaan_fileurl().lastIndexOf("/") + 12;
            if (pekerjaan.getPekerjaan_fileurl().length() > 79) {
                filename = pekerjaan.getPekerjaan_fileurl().substring(posOfSubstr, 17) + "...";
            } else {
                filename = pekerjaan.getPekerjaan_fileurl().substring(posOfSubstr);
            }
            txt_attachment.setText(filename);
            txt_attachment.setTextColor(Color.parseColor("#0000ff"));
            txt_attachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = pekerjaan.getPekerjaan_fileurl();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });
        } else {
            txt_attachment.setText("-");
        }

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

    public void deleteItem(int id) {
        Call<StringRespon> call = RetrofitClient.getInstance()
                .getApi().pekerjaan_delete(
                        secret_key,
                        id,
                        SharedPrefManager.getInstance(context).getUser().getUser_id());
        call.enqueue(new Callback<StringRespon>() {
            @Override
            public void onResponse(Call<StringRespon> call, Response<StringRespon> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StringRespon> call, Throwable t) {

            }
        });
    }

    public class CustomHolderView extends RecyclerView.ViewHolder {

        TextView txt_judul_item, txt_perkiraan_harga, txt_file_attachment;
        ImageButton btn_delete;
        Button btn_editItemLelang, btn_detailItemLelang;

        public CustomHolderView(@NonNull View itemView) {
            super(itemView);

            txt_judul_item = itemView.findViewById(R.id.txt_judul_item);
            txt_perkiraan_harga = itemView.findViewById(R.id.txt_perkiraan_harga_item);
            txt_file_attachment = itemView.findViewById(R.id.txt_file_attachment);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_detailItemLelang = itemView.findViewById(R.id.btn_detailItemLelang);
            btn_editItemLelang = itemView.findViewById(R.id.btn_editItemLelang);
        }
    }
}
