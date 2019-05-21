package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Adapter.AdapterItemPekerjaan;
import com.ags.ayolelang.Function.ExpandableTextView;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.LelangRespon;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class Preview extends AppCompatActivity {

    private int lelang_id;
    private TextView txt_judul, txt_alamat, txt_alamatlengkap,txt_deskripsi, txt_ukuran, txt_bahan, txt_jumlah, txt_harga, txt_deadline, txt_pembayaran, txt_attachment, txt_perkiraan_totalHarga;
    private RecyclerView rv_lelang;
    private FloatingActionButton btn_tambah_keranjang;
    private Button btn_edit,btn_posting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        txt_judul = findViewById(R.id.txt_judul);
        txt_alamat = findViewById(R.id.txt_alamat);
        txt_alamatlengkap = findViewById(R.id.txt_alamatlengkap);
        txt_ukuran = findViewById(R.id.txt_ukuran);
        txt_bahan = findViewById(R.id.txt_bahan);
        txt_jumlah = findViewById(R.id.txt_jumlah);
        txt_harga = findViewById(R.id.txt_deadline);
        txt_deadline = findViewById(R.id.txt_pembayaran);
        txt_attachment = findViewById(R.id.txt_attachment);
        txt_deskripsi = findViewById(R.id.txt_deskripsi);
        txt_perkiraan_totalHarga = findViewById(R.id.txt_perkiraan_totalHarga);
        rv_lelang = findViewById(R.id.reyclerview_listItem_lelang);
        btn_tambah_keranjang=findViewById(R.id.btn_tambah_keranjang);
        btn_edit=findViewById(R.id.btn_edit);
        btn_posting=findViewById(R.id.btn_posting);

        Intent intent = getIntent();
        lelang_id = intent.getIntExtra("lelang_id", 0);
        loadDataLelang();
    }

    private void loadDataLelang() {
        Call<LelangRespon> call = RetrofitClient
                .getInstance().getApi().lelang_getLelang(secret_key, lelang_id);

        call.enqueue(new Callback<LelangRespon>() {
            @Override
            public void onResponse(Call<LelangRespon> call, Response<LelangRespon> response) {
                if (response.isSuccessful()) {
                    LelangRespon lelangRespon = response.body();
                    if (!lelangRespon.isError()) {
                        Lelang lelang = lelangRespon.getData();
                        //set tampilan
                        txt_judul.setText(lelang.getLelang_judul());
                        txt_alamat.setText(lelang.getLelang_kotanama() + ", " + lelang.getLelang_provnama() + ", ID");
                        txt_alamatlengkap.setText(lelang.getLelang_alamat());
                        txt_deadline.setText(lelang.getLelang_tglselesai());
                        txt_deskripsi.setText(lelang.getLelang_deskripsi());
                        txt_perkiraan_totalHarga.setText(lelang.getLelang_anggaran() + "");
                        if (lelang.getPekerjaan().size() == 1) {
                            final Pekerjaan pekerjaan = lelang.getPekerjaan().get(0);
                            txt_ukuran.setText(pekerjaan.getPekerjaan_ukuran());
                            txt_bahan.setText(pekerjaan.getPekerjaan_bahan());
                            txt_jumlah.setText(pekerjaan.getPekerjaan_jumlah() + "");
                            txt_harga.setText(pekerjaan.getPekerjaan_harga() + "");
                            if(pekerjaan.getPekerjaan_fileurl()!=null||pekerjaan.getPekerjaan_fileurl()!="-"){
                                txt_attachment.setText(pekerjaan.getPekerjaan_fileurl().substring(0,8)+"...");
                                txt_attachment.setTextColor(Color.parseColor("#0000ff"));
                                txt_attachment.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String url = pekerjaan.getPekerjaan_fileurl();
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                    }
                                });
                            }else{
                                txt_attachment.setText("-");
                            }
                        } else {
                            ArrayList<Pekerjaan> pekerjaans = lelang.getPekerjaan();
                            AdapterItemPekerjaan adapterItemPekerjaan=new AdapterItemPekerjaan(getApplicationContext());
                            adapterItemPekerjaan.addItem(pekerjaans);
                            rv_lelang.setAdapter(adapterItemPekerjaan);
                        }
                    } else {
                        Log.d("error", lelangRespon.getMessage());
                    }

                } else {
                    Log.d("respon error", response.toString());
                }
            }

            @Override
            public void onFailure(Call<LelangRespon> call, Throwable t) {
                Log.d("ERROR REQ", t.getMessage());
            }
        });
    }

}
