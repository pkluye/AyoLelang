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
    private int id_category;
    private String ukuran, bahan, quantity, harga, finishing, catatan;

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
        loadprovinsi();

        Intent intent = getIntent();
        id_category = intent.getIntExtra("id_category", 0);
        ukuran = intent.getStringExtra("ukuran");
        bahan = intent.getStringExtra("bahan");
        quantity = intent.getStringExtra("quantity");
        harga = intent.getStringExtra("harga");
        finishing = intent.getStringExtra("finishing");
        catatan = intent.getStringExtra("catatan");

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
        kotas.clear();
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (s.equalsIgnoreCase(jsonObject.getString("prov"))) {
                    JSONArray jsonArray1 = jsonObject.getJSONArray("kota");
                    Log.d("kota1", jsonArray1.toString());
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        kotas.add(jsonArray1.getString(j));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("kota2", kotas.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, kotas);
        adapter.notifyDataSetChanged();
        kota.setAdapter(adapter);
    }

    private void loadprovinsi() {
        try {
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                provs.add(jsonObject.getString("prov"));
            }
            Log.d("size prov", jsonArray.length() + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("prov2", provs.toString());
        ArrayAdapter<String> provadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, provs);
        provadapter.notifyDataSetChanged();
        provinsi.setAdapter(provadapter);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("provinsi_kota.json");
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

    public void next(View view) {
        String pembayaran = this.pembayaran.getSelectedItem().toString(),
                provinsi = this.provinsi.getSelectedItem().toString(),
                kota = this.kota.getSelectedItem().toString(),
                judul = this.judul.getText().toString(),
                deskripsi = this.deskripsi.getText().toString(),
                deadline = this.deadline.getText().toString();

        Intent intent = new Intent(this, Preview.class);
        intent.putExtra("ukuran", this.ukuran);
        intent.putExtra("bahan", bahan);
        intent.putExtra("quantity", quantity);
        intent.putExtra("harga", harga);
        intent.putExtra("finishing", finishing);
        intent.putExtra("catatan", catatan);
        intent.putExtra("id_category", this.id_category);
        intent.putExtra("pembayaran", pembayaran);
        intent.putExtra("alamat", kota + "," + provinsi);
        intent.putExtra("judul", judul);
        intent.putExtra("deskripsi", deskripsi);
        intent.putExtra("deadline", deadline);

        startActivity(intent);
    }


}
