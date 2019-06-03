package com.ags.ayolelang.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.ags.ayolelang.Adapter.AdapterItemPekerjaan;
import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.DBHelper.REQLelangHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;

import java.util.ArrayList;

public class ListPekerjaan extends AppCompatActivity {

    private int kategori_id;
    Kategori kategori_parent;
    private RecyclerView reyclerview_listItem_pekerjaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pekerjaan);

        reyclerview_listItem_pekerjaan=findViewById(R.id.reyclerview_listItem_pekerjaan);
        reyclerview_listItem_pekerjaan.setLayoutManager(new LinearLayoutManager(this));

        Intent intent=getIntent();
        kategori_id=intent.getIntExtra("kategori_id",0);
        KategoriHelper kategoriHelper=new KategoriHelper(this);
        kategoriHelper.open();
        Kategori lastkategori=kategoriHelper.getSingleKategori(kategori_id);
        kategori_parent = kategoriHelper.getSingleKategori(lastkategori.getKategori_parentid());
        kategoriHelper.close();
        loadData();
    }

    private void loadData() {
        REQPekerjaanHelper pekerjaanHelper = new REQPekerjaanHelper(this);
        pekerjaanHelper.open();
        ArrayList<Pekerjaan> pekerjaans=pekerjaanHelper.getPekerjaan();
        pekerjaanHelper.close();
        AdapterItemPekerjaan adapterItemPekerjaan=new AdapterItemPekerjaan(this);
        adapterItemPekerjaan.addItem(pekerjaans);
        reyclerview_listItem_pekerjaan.setAdapter(adapterItemPekerjaan);
    }

    public void next(View view) {
        startActivity(new Intent(this,Deskripsi.class));
    }

    public void tambah(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("tambah_pekerjaan",true);
        intent.putExtra("id",kategori_parent.getKategori_id());
        intent.putExtra("tittle",kategori_parent.getKategori_nama());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_progress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Button btn_OK = (Button) dialog.findViewById(R.id.btn_OK);
        Button btn_batal=(Button) dialog.findViewById(R.id.btn_batal);
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                REQLelangHelper reqLelangHelper = new REQLelangHelper(ListPekerjaan.this);
                reqLelangHelper.open();
                reqLelangHelper.truncate();
                reqLelangHelper.close();
                REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(ListPekerjaan.this);
                reqPekerjaanHelper.open();
                reqPekerjaanHelper.truncate();
                reqPekerjaanHelper.close();
//                Intent intent=new Intent(ListPekerjaan.this,MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                //ListPekerjaan.super.onBackPressed();
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
