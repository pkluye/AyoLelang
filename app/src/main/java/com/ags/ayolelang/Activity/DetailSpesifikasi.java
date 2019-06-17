package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.TextView;

import com.ags.ayolelang.DBHelper.REQLelangHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DetailSpesifikasi extends AppCompatActivity {

    private int kategori_id;
    private EditText ukuran, bahan, quantity, harga, catatan;
    private Toolbar toolbar;
    private TextView txt_subtext_kategori_dipilih;
    private boolean edit;
    private int pekerjaan_id;
    private long lastharga;
    private Spinner ukuransp,bahansp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spesifikasi);
        ukuran = findViewById(R.id.in_ukuran);
        bahan = findViewById(R.id.in_bahan);
        ukuransp = findViewById(R.id.ukuran_sp);
        bahansp = findViewById(R.id.bahan_sp);
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
            pekerjaan_id = intent.getIntExtra("pekerjaan_id", 0);
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
        loadbahan();
        loadukuran();
    }



    public void next(View view) {
        String ukuran = this.ukuran.getText().toString().trim(),
                ukuransp=this.ukuransp.getSelectedItem().toString().trim(),
                bahan = this.bahan.getText().toString().trim(),
                bahansp=this.bahansp.getSelectedItem().toString().trim(),
                quantity = this.quantity.getText().toString().trim(),
                harga = this.harga.getText().toString().trim(),
                catatan = this.catatan.getText().toString().trim();

        if (ukuransp.equalsIgnoreCase("lainnya")) {
            if(ukuran.isEmpty()){
                this.ukuran.setError("harap lengkapi form ini");
                this.ukuran.requestFocus();
                return;
            }
        }else{
            ukuran=ukuransp;
        }

        if (bahansp.equalsIgnoreCase("lainnya")){
            if (bahan.isEmpty()) {
                this.bahan.setError("harap lengkapi form ini");
                this.bahan.requestFocus();
                return;
            }
        }else{
            bahan=bahansp;
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
                if (lelang.getLelang_status()!=0){
                    intent.putExtra("edit",true);
                }
            }else{
                intent = new Intent(this, ListPekerjaan.class);
            }
            intent.putExtra("kategori_id", kategori_id);
            reqPekerjaanHelper.insert(new Pekerjaan(ukuran, bahan, catatan, Integer.parseInt(quantity), kategori_id, Long.parseLong(harga)));
            startActivity(intent);

        }
        reqLelangHelper.close();
        reqPekerjaanHelper.close();

        finish();
    }

    private void loadbahan() {
        ArrayList<String>listbahan=new ArrayList<>();
        try {
            JSONObject datajson = new JSONObject(loadJSONFromAsset());
            JSONArray databahan=datajson.getJSONArray("bahan");
            for (int i=0;i<databahan.length();i++){
                JSONObject bahan=databahan.getJSONObject(i);
                ArrayList<Integer>listid=new ArrayList<>();
                JSONArray list_id=bahan.getJSONArray("group_id");
                JSONArray list_bahan=bahan.getJSONArray("list_bahan");
                for (int j=0;j<list_id.length();j++){
                    listid.add(list_id.getInt(j));
                }
                if (listid.contains(kategori_id)){
                    for (int j=0;j<list_bahan.length();j++){
                        listbahan.add(list_bahan.getString(j));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> spadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listbahan);
        spadapter.notifyDataSetChanged();
        bahansp.setAdapter(spadapter);
        bahansp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectitem = bahansp.getSelectedItem().toString();
                bahan.setVisibility(View.GONE);
                if (selectitem.equalsIgnoreCase("lainnya")){
                    Log.d("testt","oke");
                    bahan.setVisibility(View.VISIBLE);
                    bahan.requestFocus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadukuran() {
        ArrayList<String>listukuran=new ArrayList<>();
        try {
            JSONObject datajson = new JSONObject(loadJSONFromAsset());
            JSONArray databahan=datajson.getJSONArray("ukuran");
            for (int i=0;i<databahan.length();i++){
                JSONObject bahan=databahan.getJSONObject(i);
                ArrayList<Integer>listid=new ArrayList<>();
                JSONArray list_id=bahan.getJSONArray("group_id");
                JSONArray list_bahan=bahan.getJSONArray("list_ukuran");
                for (int j=0;j<list_id.length();j++){
                    listid.add(list_id.getInt(j));
                }
                if (listid.contains(kategori_id)){
                    for (int j=0;j<list_bahan.length();j++){
                        listukuran.add(list_bahan.getString(j));
                        Log.d("testt","oke");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> spadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listukuran);
        spadapter.notifyDataSetChanged();
        ukuransp.setAdapter(spadapter);
        ukuransp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectitem = ukuransp.getSelectedItem().toString();
                ukuran.setVisibility(View.GONE);
                if (selectitem.equalsIgnoreCase("lainnya")){
                    ukuran.setVisibility(View.VISIBLE);
                    ukuran.requestFocus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void back(View view) {
        finish();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("databahanukuran.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
