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
    private boolean edit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spesifikasi);
        ukuran = findViewById(R.id.in_ukuran);
        bahan = findViewById(R.id.in_bahan);
        quantity = findViewById(R.id.in_quantity);
        harga = findViewById(R.id.in_harga);
        catatan = findViewById(R.id.in_catatan);
        Intent intent = getIntent();
        edit=intent.getBooleanExtra("edit",false);
        if(!edit) {
            kategori_id = intent.getIntExtra("kategori_id", 0);
        }else{
            String ukuran_ = intent.getStringExtra("ukuran");
            String bahan_=intent.getStringExtra("bahan");
            int jumlah_=intent.getIntExtra("jumlah",0);
            long harga_=intent.getLongExtra("harga", 0);
            String catatan_=intent.getStringExtra("catatan");
            String fileurl=intent.getStringExtra("fileurl");
            req_pekerjaan.setPekerjaan_fileurl(fileurl);

            //set tampilan
            ukuran.setText(ukuran_);
            bahan.setText(bahan_);
            quantity.setText(jumlah_+"");
            harga.setText(harga_+"");
            catatan.setText(catatan_+"");
        }
        //Log.d("id_category",""+id_category);
    }

    public void next(View view) {
        String ukuran = this.ukuran.getText().toString().trim(),
                bahan = this.bahan.getText().toString().trim(),
                quantity = this.quantity.getText().toString().trim(),
                harga = this.harga.getText().toString().trim(),
                catatan = this.catatan.getText().toString().trim();

        if(ukuran.isEmpty()){
            this.ukuran.setError("harap lengkapi form ini");
            this.ukuran.requestFocus();
            return;
        }

        if(bahan.isEmpty()){
            this.bahan.setError("harap lengkapi form ini");
            this.bahan.requestFocus();
            return;
        }

        if(quantity.isEmpty()){
            this.quantity.setError("harap lengkapi form ini");
            this.quantity.requestFocus();
            return;
        }

        if(harga.isEmpty()){
            this.harga.setError("harap lengkapi form ini");
            this.harga.requestFocus();
            return;
        }

        if(catatan.isEmpty()){
            this.catatan.setError("harap lengkapi form ini");
            this.catatan.requestFocus();
            return;
        }

        req_pekerjaan.setPekerjaan_kategoriid(kategori_id);
        req_pekerjaan.setPekerjaan_bahan(bahan);
        req_pekerjaan.setPekerjaan_catatan(catatan);
        req_pekerjaan.setPekerjaan_harga(Long.parseLong(harga));
        req_pekerjaan.setPekerjaan_ukuran(ukuran);
        req_pekerjaan.setPekerjaan_jumlah(Integer.parseInt(quantity));
        Intent intent=new Intent(this, Attachment.class);
        intent.putExtra("edit",true);
        startActivity(intent);
    }

    public void back(View view) {
        onBackPressed();
    }
}
