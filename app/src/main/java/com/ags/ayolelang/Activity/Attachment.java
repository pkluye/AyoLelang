package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ags.ayolelang.R;

public class Attachment extends AppCompatActivity {

    private int id_category;
    private String ukuran, bahan, quantity, harga, finishing, catatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachment);

        this.setTitle("Attachment Brief");

        Intent intent = getIntent();
        id_category = intent.getIntExtra("id_category", 0);
        ukuran = intent.getStringExtra("ukuran");
        bahan = intent.getStringExtra("bahan");
        quantity=intent.getStringExtra("quantity");
        harga=intent.getStringExtra("harga");
        finishing=intent.getStringExtra("finishing");
        catatan=intent.getStringExtra("catatn");

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    public void next(View view) {
        Intent intent = new Intent(this,Deskripsi.class);
        intent.putExtra("ukuran",ukuran);
        intent.putExtra("bahan",bahan);
        intent.putExtra("quantity",quantity);
        intent.putExtra("harga",harga);
        intent.putExtra("finishing",finishing);
        intent.putExtra("catatan",catatan);
        intent.putExtra("id_category",this.id_category);

        startActivity(intent);
    }

    public void upload(View view) {
    }
}
