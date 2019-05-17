package com.ags.ayolelang.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Models.Kota;
import com.ags.ayolelang.Models.KotaResponArray;
import com.ags.ayolelang.Models.Provinsi;
import com.ags.ayolelang.Models.ProvinsiResponArray;
import com.ags.ayolelang.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;


public class Deskripsi extends AppCompatActivity {

    private static ArrayList<Provinsi> provs = new ArrayList<>();
    private static ArrayList<Kota> kotas = new ArrayList<>();
    EditText judul, deskripsi, deadline;
    Spinner pembayaran_spinner, provinsi_spinner, kota_spinner;

    private static int temp_kotaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskripsi);

        kota_spinner = findViewById(R.id.kota);
        provinsi_spinner = findViewById(R.id.provinsi);
        pembayaran_spinner = findViewById(R.id.spinner_pembayaran);
        judul = findViewById(R.id.in_judul);
        deskripsi = findViewById(R.id.in_deskripsi);
        deadline = findViewById(R.id.in_Deadline);

        String s = DetailSpesifikasi.req_pekerjaan.toString();

        Log.d("testsst", s);

        loadprovinsi();

        ArrayAdapter<String> pembayaranadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.metode_bayar));
        pembayaranadapter.notifyDataSetChanged();
        pembayaran_spinner.setAdapter(pembayaranadapter);

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

        kota_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectitem = kota_spinner.getSelectedItem().toString();
                temp_kotaid = getProvid(selectitem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private int getProvid(String s) {
        int i = 0;
        for (Provinsi provinsi : provs) {
            if (provinsi.getNama().equalsIgnoreCase(s)){
                i=provinsi.getId();
            }
        }
        return i;
    }

    private int getKotaid(String s) {
        int i = 0;
        for (Kota kota : kotas) {
            if (kota.getNama().equalsIgnoreCase(s)){
                i=kota.getId();
            }
        }
        return i;
    }


    private void loadkota(int i) {
        Call<KotaResponArray> call = RetrofitClient
                .getInstance().getApi().getkabupaten(secret_key, i);
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Deskripsi.this);
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<KotaResponArray>() {
            @Override
            public void onResponse(Call<KotaResponArray> call, Response<KotaResponArray> response) {
                progressDoalog.dismiss();
                KotaResponArray kotaResponArray = response.body();
                ArrayList<String> kota_s = new ArrayList<>();
                if (!kotaResponArray.isError()) {
                    kotas.clear();
                    for (Kota kota : kotaResponArray.getData()) {
                        kotas.add(kota);
                        kota_s.add(kota.getNama());
                    }
                    ArrayAdapter<String> kotaadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, kota_s);
                    kotaadapter.notifyDataSetChanged();
                    kota_spinner.setAdapter(kotaadapter);
                }
            }

            @Override
            public void onFailure(Call<KotaResponArray> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadprovinsi() {
        Call<ProvinsiResponArray> call = RetrofitClient
                .getInstance().getApi().getprovinsi(secret_key);
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Deskripsi.this);
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.enqueue(new Callback<ProvinsiResponArray>() {
            @Override
            public void onResponse(Call<ProvinsiResponArray> call, Response<ProvinsiResponArray> response) {
                progressDoalog.dismiss();
                ProvinsiResponArray provinsiResponArray = response.body();
                //Log.d("Log provinsi",provinsiResponArray.toString());
                ArrayList<String> prov_s = new ArrayList<>();
                if (!provinsiResponArray.isError()) {
                    provs.clear();
                    for (Provinsi provinsi : provinsiResponArray.getData()) {
                        provs.add(provinsi);
                        prov_s.add(provinsi.getNama());
                    }
                    ArrayAdapter<String> provinsiadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, prov_s);
                    provinsiadapter.notifyDataSetChanged();
                    provinsi_spinner.setAdapter(provinsiadapter);
                }
            }

            @Override
            public void onFailure(Call<ProvinsiResponArray> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void next(View view) {
        String pembayaran = this.pembayaran_spinner.getSelectedItem().toString(),
                provinsi = this.provinsi_spinner.getSelectedItem().toString(),
                kota = this.kota_spinner.getSelectedItem().toString(),
                judul = this.judul.getText().toString(),
                deskripsi = this.deskripsi.getText().toString(),
                deadline = this.deadline.getText().toString();

        startActivity(new Intent(this, Preview.class));
    }


    public void back(View view) {
        onBackPressed();
    }
}
