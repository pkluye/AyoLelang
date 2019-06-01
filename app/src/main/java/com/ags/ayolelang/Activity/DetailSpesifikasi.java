package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ags.ayolelang.DBHelper.REQLelangHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;

public class DetailSpesifikasi extends AppCompatActivity {

    private int kategori_id;
    private EditText ukuran, bahan, quantity, harga, catatan;
    private Toolbar toolbar;
    private TextView txt_subtext_kategori_dipilih;
    private boolean edit;
    private int pekerjaan_id;
    private long lastharga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spesifikasi);
        ukuran = findViewById(R.id.in_ukuran);
        bahan = findViewById(R.id.in_bahan);
        quantity = findViewById(R.id.in_quantity);
        harga = findViewById(R.id.in_harga);
        catatan = findViewById(R.id.in_catatan);
        txt_subtext_kategori_dipilih = findViewById(R.id.txt_subtext_kategori_dipilih);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        kategori_id = intent.getIntExtra("kategori_id", 0);
        String kategori_nama = intent.getStringExtra("kategori_nama");
        edit = intent.getBooleanExtra("edit", false);
        txt_subtext_kategori_dipilih.setText(kategori_nama);
        if (edit) {
            pekerjaan_id = intent.getIntExtra("id", 0);
            String ukuran_e= intent.getStringExtra("ukuran");
            ukuran.setText(ukuran_e);
            String bahan_e=intent.getStringExtra("bahan");
            bahan.setText(bahan_e);
            int jumlah=intent.getIntExtra("jumlah", 0);
            quantity.setText(jumlah+"");
            long harga_e=intent.getLongExtra("harga", 0);
            harga.setText(harga_e+"");
            String catatan_e=intent.getStringExtra("catatan");
            catatan.setText(catatan_e);
            lastharga=intent.getLongExtra("harga", 0);
        }
    }

    public void next(View view) {
        String ukuran = this.ukuran.getText().toString().trim(),
                bahan = this.bahan.getText().toString().trim(),
                quantity = this.quantity.getText().toString().trim(),
                harga = this.harga.getText().toString().trim(),
                catatan = this.catatan.getText().toString().trim();

        if (ukuran.isEmpty()) {
            this.ukuran.setError("harap lengkapi form ini");
            this.ukuran.requestFocus();
            return;
        }

        if (bahan.isEmpty()) {
            this.bahan.setError("harap lengkapi form ini");
            this.bahan.requestFocus();
            return;
        }

        if (quantity.isEmpty()) {
            this.quantity.setError("harap lengkapi form ini");
            this.quantity.requestFocus();
            return;
        }

        if (harga.isEmpty()) {
            this.harga.setError("harap lengkapi form ini");
            this.harga.requestFocus();
            return;
        }

        if (catatan.isEmpty()) {
            this.catatan.setError("harap lengkapi form ini");
            this.catatan.requestFocus();
            return;
        }

        REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(this);
        reqPekerjaanHelper.open();
        Intent intent;
        REQLelangHelper reqLelangHelper = new REQLelangHelper(this);
        reqLelangHelper.open();
        boolean lelang_isempty=reqLelangHelper.isempty();
        Lelang lelang = reqLelangHelper.getLelang();
        if (edit) {
            intent = new Intent(this, Preview.class);
            reqPekerjaanHelper.update(new Pekerjaan(ukuran, bahan, catatan, pekerjaan_id, Integer.parseInt(quantity), kategori_id, Long.parseLong(harga)));
            if (!lelang_isempty) {
                lelang.setLelang_anggaran(lelang.getLelang_anggaran() + Long.parseLong(harga) - lastharga);
                reqLelangHelper.update(lelang);
            }
        } else {
            if (!lelang_isempty) {
                lelang.setLelang_anggaran(lelang.getLelang_anggaran() + Long.parseLong(harga));
                reqLelangHelper.update(lelang);
                intent = new Intent(this, Preview.class);
            }else{
                intent = new Intent(this, ListPekerjaan.class);
            }
            intent.putExtra("kategori_id", kategori_id);
            reqPekerjaanHelper.insert(new Pekerjaan(ukuran, bahan, catatan, Integer.parseInt(quantity), kategori_id, Long.parseLong(harga)));

        }
        reqLelangHelper.close();
        reqPekerjaanHelper.close();

        startActivity(intent);
        finish();
    }

    public void back(View view) {
        onBackPressed();
    }
}
