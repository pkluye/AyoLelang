package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;

public class DetailSpesifikasi extends AppCompatActivity {

    public static Pekerjaan req_pekerjaan = new Pekerjaan();

    private int kategori_id;
    private EditText ukuran, bahan, quantity, harga, catatan;
    private ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spesifikasi);
        this.setTitle("Detail Spesifikasi");
        Intent intent = getIntent();
        kategori_id = intent.getIntExtra("kategori_id", 0);
        ukuran = findViewById(R.id.in_ukuran);
        bahan = findViewById(R.id.in_bahan);
        quantity = findViewById(R.id.in_quantity);
        harga = findViewById(R.id.in_harga);
        catatan = findViewById(R.id.in_catatan);
        //Log.d("id_category",""+id_category);
    }

    public void next(View view) {
        String ukuran = this.ukuran.getText().toString(),
                bahan = this.bahan.getText().toString(),
                quantity = this.quantity.getText().toString(),
                harga = this.harga.getText().toString(),
                catatan = this.catatan.getText().toString();

        req_pekerjaan.setPekerjaan_kategoriid(kategori_id);
        req_pekerjaan.setPekerjaan_bahan(bahan);
        req_pekerjaan.setPekerjaan_catatan(catatan);
        req_pekerjaan.setPekerjaan_harga(Long.parseLong(harga));
        req_pekerjaan.setPekerjaan_ukuran(ukuran);
        req_pekerjaan.setPekerjaan_jumlah(Integer.parseInt(quantity));

        startActivity(new Intent(this, Attachment.class));
    }

    public void back(View view) {
        onBackPressed();
    }
}
