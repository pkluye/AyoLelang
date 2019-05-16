package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ags.ayolelang.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class Deskripsi extends AppCompatActivity {

    ArrayList<String> provs = new ArrayList<>();
    ArrayList<String> kotas = new ArrayList<>();
    EditText judul, deskripsi, deadline;
    Spinner pembayaran, provinsi, kota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskripsi);

        kota = findViewById(R.id.kota);
        provinsi = findViewById(R.id.provinsi);
        pembayaran = findViewById(R.id.spinner_pembayaran);
        judul = findViewById(R.id.in_judul);
        deskripsi = findViewById(R.id.in_deskripsi);
        deadline = findViewById(R.id.in_Deadline);

        String s = DetailSpesifikasi.req_pekerjaan.toString();

        Log.d("testsst", s);

        provinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectitem = provinsi.getSelectedItem().toString();
                loadkota(selectitem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadkota(String s) {

    }

    private void loadprovinsi() {

    }

    public void next(View view) {
        String pembayaran = this.pembayaran.getSelectedItem().toString(),
                provinsi = this.provinsi.getSelectedItem().toString(),
                kota = this.kota.getSelectedItem().toString(),
                judul = this.judul.getText().toString(),
                deskripsi = this.deskripsi.getText().toString(),
                deadline = this.deadline.getText().toString();

        startActivity(new Intent(this, Preview.class));
    }


}
