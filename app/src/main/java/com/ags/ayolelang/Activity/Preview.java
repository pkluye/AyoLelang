package com.ags.ayolelang.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Adapter.AdapterItemPekerjaan;
import com.ags.ayolelang.Function.ExpandableTextView;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.LelangRespon;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class Preview extends AppCompatActivity {

    private int lelang_id;
    private TextView txt_judul, txt_alamat, txt_alamatlengkap, txt_deskripsi, txt_ukuran, txt_bahan, txt_jumlah, txt_harga, txt_deadline, txt_pembayaran, txt_attachment, txt_perkiraan_totalHarga, txt_catatan;
    private RecyclerView rv_lelang;
    private FloatingActionButton btn_tambah_keranjang, btn_edit_deskripsi;
    private Button btn_edit, btn_posting;
    private ImageButton btn_home;

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
        txt_harga = findViewById(R.id.txt_harga);
        txt_deadline = findViewById(R.id.txt_deadline);
        txt_attachment = findViewById(R.id.txt_attachment);
        txt_pembayaran = findViewById(R.id.txt_pembayaran);
        txt_deskripsi = findViewById(R.id.txt_deskripsi);
        txt_perkiraan_totalHarga = findViewById(R.id.txt_perkiraan_totalHarga);
        rv_lelang = findViewById(R.id.reyclerview_listItem_lelang);
        btn_tambah_keranjang = findViewById(R.id.btn_tambah_keranjang);
        btn_edit_deskripsi = findViewById(R.id.btn_edit_deskripsi);
        btn_edit = findViewById(R.id.btn_edit);
        btn_posting = findViewById(R.id.btn_posting);
        btn_home = findViewById(R.id.btn_home);
        txt_catatan = findViewById(R.id.txt_catatan);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Preview.this, MainActivity.class));
                finish();
            }
        });
        rv_lelang.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Intent intent = getIntent();
        lelang_id = intent.getIntExtra("lelang_id", 0);
        btn_posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postingLelang(lelang_id);
            }
        });
        loadDataLelang();
    }

    private void postingLelang(int lelang_id) {
        Call<StringRespon>call=RetrofitClient.getInstance()
                .getApi().lelang_posting(
                        secret_key,
                        lelang_id,
                        SharedPrefManager.getInstance(Preview.this).getUser().getUser_id()
                );
        call.enqueue(new Callback<StringRespon>() {
            @Override
            public void onResponse(Call<StringRespon> call, Response<StringRespon> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StringRespon> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadDataLelang() {
        Call<LelangRespon> call = RetrofitClient
                .getInstance().getApi().lelang_getLelang(secret_key, lelang_id);

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Preview.this);
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<LelangRespon>() {
            @Override
            public void onResponse(Call<LelangRespon> call, Response<LelangRespon> response) {
                progressDoalog.dismiss();
                if (response.isSuccessful()) {
                    LelangRespon lelangRespon = response.body();
                    if (!lelangRespon.isError()) {
                        final Lelang lelang = lelangRespon.getData();
                        //set tampilan
                        txt_judul.setText(lelang.getLelang_judul());
                        txt_alamat.setText(lelang.getLelang_kotanama() + ", " + lelang.getLelang_provnama() + ", ID");
                        txt_alamatlengkap.setText(lelang.getLelang_alamat());
                        txt_deadline.setText(lelang.getLelang_tglselesai());
                        txt_deskripsi.setText(lelang.getLelang_deskripsi());
                        txt_perkiraan_totalHarga.setText("Rp. " + lelang.getLelang_anggaran());
                        txt_pembayaran.setText(getResources().getStringArray(R.array.metode_bayar)[Integer.parseInt(lelang.getLelang_pembayaran())]);
                        if (lelang.getPekerjaan().size() == 1) {
                            final Pekerjaan pekerjaan = lelang.getPekerjaan().get(0);
                            txt_ukuran.setText(pekerjaan.getPekerjaan_ukuran());
                            txt_bahan.setText(pekerjaan.getPekerjaan_bahan());
                            txt_jumlah.setText(pekerjaan.getPekerjaan_jumlah() + "");
                            txt_harga.setText("Rp. " + pekerjaan.getPekerjaan_harga());
                            txt_catatan.setText(pekerjaan.getPekerjaan_catatan());
                            if (pekerjaan.getPekerjaan_fileurl().length() > 1) {
                                int posOfSubstr = pekerjaan.getPekerjaan_fileurl().lastIndexOf("/") + 12;
                                String filename = "";
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
                                        startActivity(i);
                                    }
                                });
                            } else {
                                txt_attachment.setText("-");
                            }
                            btn_edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), DetailSpesifikasi.class);
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
                                    startActivity(intent);
                                }
                            });
                        } else {
                            rv_lelang.setVisibility(View.VISIBLE);
                            findViewById(R.id.linear_pekerjaan).setVisibility(View.GONE);
                            ArrayList<Pekerjaan> pekerjaans = lelang.getPekerjaan();
                            AdapterItemPekerjaan adapterItemPekerjaan = new AdapterItemPekerjaan(getApplicationContext());
                            adapterItemPekerjaan.addItem(pekerjaans);
                            rv_lelang.setAdapter(adapterItemPekerjaan);
                        }
                        btn_tambah_keranjang.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("tambah_keranjang", true);
                                intent.putExtra("lelang_id", lelang_id);
                                intent.putExtra("id", lelang.getLelang_kategoriparentid());
                                intent.putExtra("tittle", lelang.getLelang_kategoriparentnama());
                                startActivity(intent);
                            }
                        });

                        btn_edit_deskripsi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), Deskripsi.class);
                                intent.putExtra("judul", lelang.getLelang_judul());
                                intent.putExtra("deskripsi", lelang.getLelang_deskripsi());
                                intent.putExtra("deadline", lelang.getLelang_tglselesai());
                                intent.putExtra("pembayaran", lelang.getLelang_pembayaran());
                                intent.putExtra("id", lelang.getLelang_id());
                                intent.putExtra("alamatlengkap", lelang.getLelang_alamat());
                                intent.putExtra("edit", true);
                                intent.putExtra("provnama", lelang.getLelang_provnama());
                                intent.putExtra("kotanama", lelang.getLelang_kotanama());
                                startActivity(intent);
                            }
                        });
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
