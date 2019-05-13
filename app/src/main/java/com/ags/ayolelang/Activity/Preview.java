package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import feri.com.lpse.R;

public class Preview extends AppCompatActivity {

    private int id_category;
    private EditText txt_ukuran, txt_bahan, txt_quantity, txt_harga, txt_finishing, txt_catatan, txt_pembayaran, txt_alamat, txt_judul, txt_deskripsi, txt_deadline;
    private String ukuran, bahan, quantity, harga, finishing, catatan, pembayaran, alamat, judul, deskripsi, deadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        Intent intent = getIntent();
        ukuran = intent.getStringExtra("ukuran");
        bahan = intent.getStringExtra("bahan");
        quantity = intent.getStringExtra("quantity");
        harga = intent.getStringExtra("harga");
        finishing = intent.getStringExtra("finishing");
        catatan = intent.getStringExtra("catatan");
        id_category = intent.getIntExtra("id_category", 0);
        pembayaran = intent.getStringExtra("pembayaran");
        alamat = intent.getStringExtra("alamat");
        judul = intent.getStringExtra("judul");
        deskripsi = intent.getStringExtra("deskripsi");
        deadline = intent.getStringExtra("deadline");



        loadData();
    }

    private void loadData() {
    }


}
