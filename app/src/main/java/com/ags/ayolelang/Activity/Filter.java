package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.ags.ayolelang.DBHelper.KotaHelper;
import com.ags.ayolelang.DBHelper.ProvinsiHelper;
import com.ags.ayolelang.Models.Kota;
import com.ags.ayolelang.Models.Provinsi;
import com.ags.ayolelang.R;

import java.util.ArrayList;

public class Filter extends AppCompatActivity {

    private int code;
    private String hargamin;
    private String hargamax;
    private int kotaid;
    private LinearLayout layout_urutkan;
    private RelativeLayout layout_filter;
    private Spinner provinsi_spinner, kota_spinner;
    private EditText et_hargamin;
    private EditText et_hargamax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        layout_urutkan = findViewById(R.id.layout_urutkan);
        layout_filter = findViewById(R.id.layout_filter);
        et_hargamin=findViewById(R.id.text_hargaMinimum);
        et_hargamax=findViewById(R.id.text_hargaMaximum);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        if (type == 111) {
            layout_urutkan.setVisibility(View.VISIBLE);
            layout_filter.setVisibility(View.GONE);
        } else {
            layout_urutkan.setVisibility(View.GONE);
            layout_filter.setVisibility(View.VISIBLE);
        }

        kota_spinner = findViewById(R.id.kota);
        provinsi_spinner = findViewById(R.id.provinsi);

        loadprovinsi();
        provinsi_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectitem = provinsi_spinner.getSelectedItem().toString();
                loadkota(getProvid(selectitem));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private int getProvid(String s) {
        ProvinsiHelper provinsiHelper = new ProvinsiHelper(getApplicationContext());
        provinsiHelper.open();
        Provinsi provinsi = provinsiHelper.getProvinsibyname(s);
        provinsiHelper.close();
        int i = i = provinsi.getId();
        return i;
    }

    private int getKotaid(String s) {
        KotaHelper kotaHelper = new KotaHelper(getApplicationContext());
        kotaHelper.open();
        Kota kota = kotaHelper.getKotabyname(s);
        kotaHelper.close();
        int i = kota.getId();
        return i;
    }


    private void loadkota(int i) {
        KotaHelper kotaHelper = new KotaHelper(getApplicationContext());
        kotaHelper.open();
        ArrayList<Kota> kotas = kotaHelper.getKotabyProvId(i);
        kotaHelper.close();
        ArrayList<String> kota_s = new ArrayList<>();
        for (Kota kota : kotas) {
            kota_s.add(kota.getNama());
        }

        ArrayAdapter<String> kotaadapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, kota_s);
        kotaadapter.notifyDataSetChanged();
        kota_spinner.setAdapter(kotaadapter);
    }

    private void loadprovinsi() {
        ProvinsiHelper provinsiHelper = new ProvinsiHelper(getApplicationContext());
        provinsiHelper.open();
        ArrayList<Provinsi> provs = provinsiHelper.getAllData();
        provinsiHelper.close();
        ArrayList<String> prov_s = new ArrayList<>();
        for (Provinsi provinsi : provs) {
            prov_s.add(provinsi.getNama());
        }

        ArrayAdapter<String> provinsiadapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, prov_s);
        provinsiadapter.notifyDataSetChanged();
        provinsi_spinner.setAdapter(provinsiadapter);
    }

    public void close(View view) {
        finish();
    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("code", code);
        returnIntent.putExtra("hargamin", hargamin);
        returnIntent.putExtra("hargamax", hargamax);
        returnIntent.putExtra("kotaid", kotaid);
        setResult(RESULT_OK, returnIntent);
        super.finish();
    }

    public void hargaMax(View view) {
        code = 1;
        finish();
    }

    public void hargaMin(View view) {
        code = 2;
    }

    public void Terbaru(View view) {
        code = 3;
        finish();
    }

    public void Terlama(View view) {
        code = 4;
        finish();
    }

    public void CustomFilter(View view) {
        code = 5;
        String selectitem = kota_spinner.getSelectedItem().toString();
        kotaid = getKotaid(selectitem);
        hargamin=et_hargamin.getText().toString().trim();
        hargamax=et_hargamax.getText().toString().trim();
        finish();
    }

    public void clear(View view) {
        code = 0;
        finish();
    }
}