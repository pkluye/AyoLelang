package com.ags.ayolelang.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Adapter.AdapterItemPekerjaan;
import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.DBHelper.KotaHelper;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.DBHelper.ProvinsiHelper;
import com.ags.ayolelang.DBHelper.REQLelangHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.Models.Kota;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.LelangRespon;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.Models.Provinsi;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class Preview extends AppCompatActivity {

    private TextView txt_judul, txt_alamat, txt_alamatlengkap, txt_deskripsi, txt_ukuran, txt_bahan, txt_jumlah, txt_harga, txt_deadline, txt_pembayaran, txt_attachment, txt_perkiraan_totalHarga, txt_catatan;
    private RecyclerView rv_lelang;
    private Button btn_edit, btn_posting, btn_edit_deskripsi;
    //    private ImageButton btn_home;
    private Toolbar toolbar;
    REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(this);
    REQLelangHelper reqLelangHelper = new REQLelangHelper(this);
    private TextView txt_laminasi;
    private TextView txt_sisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt_judul = findViewById(R.id.txt_judul);
        txt_alamat = findViewById(R.id.txt_alamat);
        txt_alamatlengkap = findViewById(R.id.txt_alamatlengkap);
        txt_ukuran = findViewById(R.id.txt_ukuran);
        txt_bahan = findViewById(R.id.txt_bahan);
        txt_jumlah = findViewById(R.id.txt_jumlah);
        txt_harga = findViewById(R.id.txt_harga);
        txt_sisi=findViewById(R.id.txt_sisi);
        txt_laminasi=findViewById(R.id.txt_laminasi);
        txt_attachment = findViewById(R.id.txt_attachment);
        txt_deadline=findViewById(R.id.txt_deadline);
        txt_pembayaran = findViewById(R.id.txt_pembayaran);
        txt_deskripsi = findViewById(R.id.txt_deskripsi);
        txt_perkiraan_totalHarga = findViewById(R.id.txt_perkiraan_totalHarga);
        rv_lelang = findViewById(R.id.reyclerview_listItem_lelang);
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit_deskripsi=findViewById(R.id.btn_edit_deskripsi);
        btn_posting = findViewById(R.id.btn_posting);
        txt_catatan = findViewById(R.id.txt_catatan);
        rv_lelang.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        btn_posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    postingLelang();
            }
        });

        loadDataLelang();
    }

    private String currencyFormat(String harga) {
        Locale localeID = new Locale("in", "ID");
        harga = harga.replaceAll("[.,]", "");
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance(localeID);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("");
        formatRp.setGroupingSeparator('.');
        formatRp.setMonetaryDecimalSeparator(',');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(Double.parseDouble(harga));
    }

    public void postingLelang() {
        JSONObject lelang = new JSONObject();
        final REQLelangHelper lelangHelper = new REQLelangHelper(this);
        lelangHelper.open();
        Lelang lelang_data = lelangHelper.getLelang();
        lelangHelper.close();

        try {
            lelang.put("lelang_judul", lelang_data.getLelang_judul());
            lelang.put("lelang_anggaran", lelang_data.getLelang_anggaran());
            lelang.put("lelang_deskripsi", lelang_data.getLelang_deskripsi());
            lelang.put("lelang_tglselesai", lelang_data.getLelang_tglselesai());
            lelang.put("lelang_userid", lelang_data.getLelang_userid());
            lelang.put("lelang_pembayaran", lelang_data.getLelang_pembayaran());
            lelang.put("lelang_kota", lelang_data.getLelang_kota());
            lelang.put("lelang_alamat", lelang_data.getLelang_alamat());
            lelang.put("lelang_fileurl", lelang_data.getLelang_fileurl());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray pekerjaans = new JSONArray();
        final REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(this);
        reqPekerjaanHelper.open();
        ArrayList<Pekerjaan> pekerjaans_data = reqPekerjaanHelper.getPekerjaan();
        reqPekerjaanHelper.close();
        for (Pekerjaan pekerjaan : pekerjaans_data) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("pekerjaan_ukuran", pekerjaan.getPekerjaan_ukuran());
                jsonObject.put("pekerjaan_harga", pekerjaan.getPekerjaan_harga());
                jsonObject.put("pekerjaan_bahan", pekerjaan.getPekerjaan_bahan());
                jsonObject.put("pekerjaan_jmlsisi",pekerjaan.getPekerjaan_jmlsisi());
                jsonObject.put("pekerjaan_laminasi",pekerjaan.getPekerjaan_laminasi());
                jsonObject.put("pekerjaan_jumlah", pekerjaan.getPekerjaan_jumlah());
                jsonObject.put("pekerjaan_catatan", pekerjaan.getPekerjaan_catatan());
                jsonObject.put("pekerjaan_kategoriid", pekerjaan.getPekerjaan_kategoriid());
                pekerjaans.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        final JSONObject req = new JSONObject();
        try {
            req.put("secret_key", secret_key);
            req.put("lelang", lelang);
            req.put("pekerjaan", pekerjaans);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<StringRespon> call = RetrofitClient.getInstance()
                .getApi().buatLelang(req.toString());
        Log.d("test", req.toString());

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Preview.this);
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<StringRespon>() {
            @Override
            public void onResponse(Call<StringRespon> call, Response<StringRespon> response) {
                progressDoalog.dismiss();
                if (response.isSuccessful()) {
                    StringRespon stringRespon = response.body();
                    if (!stringRespon.isError()) {
                        Intent intent = new Intent(Preview.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Preview.this, stringRespon.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Preview.this, stringRespon.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Preview.this, response.message(), Toast.LENGTH_SHORT).show();
                    try {
                        longLog(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<StringRespon> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(Preview.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void longLog(String str) {
        if (str.length() > 4000) {
            Log.e("HTML ERROR", str.substring(0, 4000));
            longLog(str.substring(4000));
        } else
            Log.e("HTML ERROR", str);
    }

    private String getProvnama(int i) {
        ProvinsiHelper provinsiHelper = new ProvinsiHelper(getApplicationContext());
        provinsiHelper.open();
        Provinsi provinsi = provinsiHelper.getProvinsibyProvid(i);
        provinsiHelper.close();
        String s = provinsi.getNama();
        return s;
    }

    private String getKotanama(int i) {
        KotaHelper kotaHelper = new KotaHelper(getApplicationContext());
        kotaHelper.open();
        Kota kota = kotaHelper.getKotabyidKota(i);
        kotaHelper.close();
        String s = kota.getNama();
        return s;
    }

    private void loadDataLelang() {
        reqLelangHelper.open();
        final Lelang lelang = reqLelangHelper.getLelang();
        reqLelangHelper.close();

        KotaHelper kotaHelper = new KotaHelper(getApplicationContext());
        kotaHelper.open();
        Kota kota = kotaHelper.getKotabyidKota(lelang.getLelang_kota());
        kotaHelper.close();
        //set tampilan
        Log.d("lelang", lelang.toString());
        txt_judul.setText(lelang.getLelang_judul());
        txt_alamat.setText(kota.getNama() + ", " + getProvnama(kota.getProvinsi_id()) + ", ID");
        txt_alamatlengkap.setText(lelang.getLelang_alamat());
        txt_deadline.setText(lelang.getLelang_tglselesai()+"");
        txt_deskripsi.setText(lelang.getLelang_deskripsi());
        txt_perkiraan_totalHarga.setText("Rp. " + currencyFormat(lelang.getLelang_anggaran()+""));
        txt_pembayaran.setText(getResources().getStringArray(R.array.metode_bayar)[lelang.getLelang_pembayaran()]);
        if (lelang.getLelang_fileurl() != null) {
            Log.d("url", lelang.getLelang_fileurl());
            int posOfSubstr = lelang.getLelang_fileurl().lastIndexOf("/") + 12;
            String filename = "";
            if (lelang.getLelang_fileurl().length() > 79) {
                filename = lelang.getLelang_fileurl().substring(posOfSubstr, 17) + "...";
            } else {
                filename = lelang.getLelang_fileurl();
            }
            txt_attachment.setText(filename);
        } else {
            txt_attachment.setText("-");
        }

        reqPekerjaanHelper.open();
        ArrayList<Pekerjaan> pekerjaans0 = reqPekerjaanHelper.getPekerjaan();
        reqPekerjaanHelper.close();
        ArrayList<Pekerjaan> pekerjaans = new ArrayList<>();
        for (Pekerjaan pekerjaan_ : pekerjaans0) {
            if (pekerjaan_.getPekerjaan_status() != 1) {
                pekerjaans.add(pekerjaan_);
            }
        }
        final int kategori_id = pekerjaans.get(0).getPekerjaan_kategoriid();
        KategoriHelper kategoriHelper = new KategoriHelper(Preview.this);
        kategoriHelper.open();
        final Kategori lastkategori = kategoriHelper.getSingleKategori(kategori_id);
        final Kategori kategori_parent = kategoriHelper.getSingleKategori(lastkategori.getKategori_parentid());
        kategoriHelper.close();
        Pekerjaan pekerjaan = pekerjaans.get(0);
        LinearlytViewupdate(pekerjaan);
        if (pekerjaans.size() > 1) {
            rv_lelang.setVisibility(View.VISIBLE);
            findViewById(R.id.linear_pekerjaan).setVisibility(View.GONE);
            AdapterItemPekerjaan adapterItemPekerjaan = new AdapterItemPekerjaan(this);
            adapterItemPekerjaan.addItem(pekerjaans);
            rv_lelang.setAdapter(adapterItemPekerjaan);
            rv_lelang.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(@NonNull View view) {
                    rvUpdate();
                }

                @Override
                public void onChildViewDetachedFromWindow(@NonNull View view) {
                    if (rv_lelang.getAdapter().getItemCount()<=1){
                        rv_lelang.setVisibility(View.GONE);
                        findViewById(R.id.linear_pekerjaan).setVisibility(View.VISIBLE);
                    }
                    rvUpdate();
                }
            });
        }

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
                intent.putExtra("fileurl", lelang.getLelang_fileurl());
                intent.putExtra("edit", true);
                startActivity(intent);
            }
        });
    }

    private void rvUpdate() {
        long total_harga = 0;
        reqPekerjaanHelper.open();
        ArrayList<Pekerjaan> pekerjaans1 = reqPekerjaanHelper.getPekerjaan();
        ArrayList<Pekerjaan> pekerjaan_=new ArrayList<>();
        for (Pekerjaan pekerjaan1 : pekerjaans1) {
            if (pekerjaan1.getPekerjaan_status() != 1) {
                total_harga += pekerjaan1.getPekerjaan_harga();
                pekerjaan_.add(pekerjaan1);
            }
        }
        LinearlytViewupdate(pekerjaan_.get(0));
        reqPekerjaanHelper.close();
        reqLelangHelper.open();
        Lelang lelang = reqLelangHelper.getLelang();
        lelang.setLelang_anggaran(total_harga);
        reqLelangHelper.update(lelang);
        reqLelangHelper.close();
        txt_perkiraan_totalHarga.setText(total_harga + "");
    }

    private void LinearlytViewupdate(final Pekerjaan pekerjaan) {
        final int kategori_id = pekerjaan.getPekerjaan_kategoriid();
        KategoriHelper kategoriHelper = new KategoriHelper(Preview.this);
        kategoriHelper.open();
        final Kategori lastkategori = kategoriHelper.getSingleKategori(kategori_id);
        final Kategori kategori_parent = kategoriHelper.getSingleKategori(lastkategori.getKategori_parentid());
        kategoriHelper.close();
        txt_ukuran.setText(pekerjaan.getPekerjaan_ukuran());

        txt_bahan.setText(pekerjaan.getPekerjaan_bahan());
        txt_jumlah.setText(pekerjaan.getPekerjaan_jumlah() + "");
        txt_sisi.setText(pekerjaan.getPekerjaan_jmlsisi());
        txt_laminasi.setText(pekerjaan.getPekerjaan_laminasi());
        txt_harga.setText("Rp. " + currencyFormat(pekerjaan.getPekerjaan_harga()+""));
        txt_catatan.setText(pekerjaan.getPekerjaan_catatan());
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailSpesifikasi.class);
                intent.putExtra("edit", true);
                intent.putExtra("ukuran", pekerjaan.getPekerjaan_ukuran());
                intent.putExtra("bahan", pekerjaan.getPekerjaan_bahan());
                intent.putExtra("jumlah", pekerjaan.getPekerjaan_jumlah());
                intent.putExtra("jmlsisi",pekerjaan.getPekerjaan_jmlsisi());
                intent.putExtra("laminasi",pekerjaan.getPekerjaan_laminasi());
                intent.putExtra("harga", pekerjaan.getPekerjaan_harga());
                intent.putExtra("catatan", pekerjaan.getPekerjaan_catatan());
                intent.putExtra("pekerjaan_id", pekerjaan.getPekerjaan_id());
                intent.putExtra("kategori_id", pekerjaan.getPekerjaan_kategoriid());
                intent.putExtra("kategori_nama", lastkategori.getKategori_nama());
                startActivity(intent);
            }
        });
    }

    public void cancel(View view) {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataLelang();
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_progress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Button btn_OK = (Button) dialog.findViewById(R.id.btn_OK);
        Button btn_batal = (Button) dialog.findViewById(R.id.btn_batal);
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //Preview.super.onBackPressed();
                REQLelangHelper reqLelangHelper = new REQLelangHelper(Preview.this);
                reqLelangHelper.open();
                reqLelangHelper.truncate();
                reqLelangHelper.close();
                REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(Preview.this);
                reqPekerjaanHelper.open();
                reqPekerjaanHelper.truncate();
                reqPekerjaanHelper.close();
                Intent intent = new Intent(Preview.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

