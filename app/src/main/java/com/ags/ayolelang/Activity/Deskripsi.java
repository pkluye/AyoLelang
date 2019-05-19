package com.ags.ayolelang.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Fragment.DatePickerFragmentDialog;
import com.ags.ayolelang.Models.IntegerRespon;
import com.ags.ayolelang.Models.Kota;
import com.ags.ayolelang.Models.KotaResponArray;
import com.ags.ayolelang.Models.LelangRespon;
import com.ags.ayolelang.Models.Provinsi;
import com.ags.ayolelang.Models.ProvinsiResponArray;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;


public class Deskripsi extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static ArrayList<Provinsi> provs = new ArrayList<>();
    private static ArrayList<Kota> kotas = new ArrayList<>();
    EditText judul, deskripsi, deadline, alamat;
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
        alamat = findViewById(R.id.in_alamat);

        loadprovinsi();

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new
                SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = dateFormat.format(Calendar.getInstance().getTime());

        deadline.setText(currentDateString);

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

        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragmentDialog();
                datePicker.show(getSupportFragmentManager(), "Custom Date Picker");
            }
        });
    }

    private int getProvid(String s) {
        int i = 0;
        for (Provinsi provinsi : provs) {
            if (provinsi.getNama().equalsIgnoreCase(s)) {
                i = provinsi.getId();
            }
        }
        return i;
    }

    private int getKotaid(String s) {
        int i = 0;
        for (Kota kota : kotas) {
            if (kota.getNama().equalsIgnoreCase(s)) {
                i = kota.getId();
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
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void next(View view) {
        String provinsi = this.provinsi_spinner.getSelectedItem().toString(),
                kota = this.kota_spinner.getSelectedItem().toString(),
                judul = this.judul.getText().toString(),
                deskripsi = this.deskripsi.getText().toString(),
                deadline = this.deadline.getText().toString(),
                alamat = this.alamat.getText().toString();
        int pembayaran = this.pembayaran_spinner.getSelectedItemPosition();

        String s = DetailSpesifikasi.req_pekerjaan.toString();

        Log.d("testsst", s);
        if(DetailSpesifikasi.req_pekerjaan.getPekerjaan_fileurl()== null){
            DetailSpesifikasi.req_pekerjaan.setPekerjaan_fileurl("-");
        }

        Call<StringRespon> call = RetrofitClient
                .getInstance().getApi().lelang_buat_plus_pekerjaan(
                        secret_key,
                        SharedPrefManager.getInstance(getApplicationContext()).getUser().getUser_id(),
                        deskripsi,
                        deadline,
                        judul,
                        pembayaran,
                        alamat,
                        getKotaid(kota),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_ukuran(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_bahan(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_jumlah(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_harga(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_kategoriid(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_fileurl(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_catatan()
                );
        call.enqueue(new Callback<StringRespon>() {
            @Override
            public void onResponse(Call<StringRespon> call, Response<StringRespon> response) {
                if (response.isSuccessful()){
                    if (!response.body().isError()){
                        Log.d("insert lelang", response.body().toString());
                        Intent intent = new Intent(Deskripsi.this, Preview.class);
                        intent.putExtra("lelang_id",Integer.parseInt(response.body().getData()));
                        startActivity(intent);
                    }
                }else {
                    Log.d("insert lelang", response.toString());
//                    try {
//                        longLog(response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<StringRespon> call, Throwable t) {
                Log.d("ERROR REQUEST", t.getMessage().toString());
            }
        });

    }


    public void back(View view) {
        onBackPressed();
    }

    //cek error html
//    public void longLog(String str) {
//        if (str.length() > 4000) {
//            Log.e("HTML ERROR", str.substring(0, 4000));
//            longLog(str.substring(4000));
//        } else
//            Log.e("HTML ERROR", str);
//    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new
                SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = dateFormat.format(calendar.getTime());

        deadline.setText(currentDateString);
    }
}
