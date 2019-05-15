package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.ags.ayolelang.R;


public class DetailSpesifikasi extends AppCompatActivity {

    int id_category;
    EditText ukuran, bahan, quantity, harga, finishing, catatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spesifikasi);
        this.setTitle("Detail Spesifikasi");
        Intent intent = getIntent();
        id_category = intent.getIntExtra("id_category", 0);
        ukuran = findViewById(R.id.in_ukuran);
        bahan = findViewById(R.id.in_bahan);
        quantity = findViewById(R.id.in_quantity);
        harga = findViewById(R.id.in_harga);
        finishing = findViewById(R.id.in_finishing);
        catatan = findViewById(R.id.in_catatan);
        //Log.d("id_category",""+id_category);

    }

    public void next(View view) {
        String ukuran = this.ukuran.getText().toString(),
                bahan = this.bahan.getText().toString(),
                quantity = this.quantity.getText().toString(),
                harga = this.harga.getText().toString(),
                finishing = this.finishing.toString(),
                catatan = this.catatan.toString();

        Intent intent = new Intent(this,Attachment.class);
        intent.putExtra("ukuran",ukuran);
        intent.putExtra("bahan",bahan);
        intent.putExtra("quantity",quantity);
        intent.putExtra("harga",harga);
        intent.putExtra("finishing",finishing);
        intent.putExtra("catatan",catatan);
        intent.putExtra("id_category",this.id_category);

        startActivity(intent);
    }
}
