package com.ags.ayolelang.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.DBHelper.KotaHelper;
import com.ags.ayolelang.DBHelper.ProvinsiHelper;
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
import java.security.cert.Extension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;


public class Deskripsi extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText judul, deskripsi, deadline, alamat;
    Spinner pembayaran_spinner, provinsi_spinner, kota_spinner;
    private Toolbar toolbar;

    private static int temp_kotaid;
    private ImageView image_Kalender;

    ///edit variable
    private String judul_e, deskripsi_e, deadline_e, alamat_e, prov_e, kota_e, pembayaran_e;
    private int lelang_id_e;
    private boolean edit = false;

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
        image_Kalender = findViewById(R.id.image_Kalender);
        Intent intent = getIntent();
        if (intent != null) {
            edit = intent.getBooleanExtra("edit", false);
            judul_e = intent.getStringExtra("judul");
            deskripsi_e = intent.getStringExtra("deskripsi");
            deadline_e = intent.getStringExtra("deadline");
            alamat_e = intent.getStringExtra("alamatlengkap");
            prov_e = intent.getStringExtra("provnama");
            kota_e = intent.getStringExtra("kotanama");
            pembayaran_e = intent.getStringExtra("pembayaran");
            lelang_id_e = intent.getIntExtra("id", 0);
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadprovinsi();

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new
                SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = dateFormat.format(Calendar.getInstance().getTime());

        deadline.setText(currentDateString);

        ArrayAdapter<String> pembayaranadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.metode_bayar));
        pembayaranadapter.notifyDataSetChanged();
        pembayaran_spinner.setAdapter(pembayaranadapter);
        if (edit) {
            judul.setText(judul_e);
            deskripsi.setText(deskripsi_e);
            deadline.setText(deadline_e.substring(0, 10));
            int postion = pembayaranadapter.getPosition(pembayaran_e);
            pembayaran_spinner.setSelection(postion);
            alamat.setText(alamat_e);
        }

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

        image_Kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragmentDialog();
                datePicker.show(getSupportFragmentManager(), "Custom Date Picker");
            }
        });
    }

    private int getProvid(String s) {
        ProvinsiHelper provinsiHelper=new ProvinsiHelper(getApplicationContext());
        provinsiHelper.open();
        Provinsi provinsi=provinsiHelper.getProvinsibyname(s);
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
        ArrayAdapter<String> kotaadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, kota_s);
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

        ArrayAdapter<String> provinsiadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, prov_s);
        provinsiadapter.notifyDataSetChanged();
        provinsi_spinner.setAdapter(provinsiadapter);
    }

    public void next(View view) {
        if (edit) {
            editLelang();
        } else {
            buatLelang();
        }

    }

    private void buatLelang() {
        String provinsi = this.provinsi_spinner.getSelectedItem().toString(),
                kota = this.kota_spinner.getSelectedItem().toString(),
                judul = this.judul.getText().toString(),
                deskripsi = this.deskripsi.getText().toString(),
                deadline = this.deadline.getText().toString(),
                alamat = this.alamat.getText().toString();
        int pembayaran = this.pembayaran_spinner.getSelectedItemPosition();

        String s = DetailSpesifikasi.req_pekerjaan.toString();

        Log.d("testsst", s);
        if (DetailSpesifikasi.req_pekerjaan.getPekerjaan_fileurl() == null) {
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

        call.enqueue(new Callback<StringRespon>() {
            @Override
            public void onResponse(Call<StringRespon> call, Response<StringRespon> response) {
                progressDoalog.dismiss();
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        Log.d("insert lelang", response.body().toString());
                        Intent intent = new Intent(Deskripsi.this, Preview.class);
                        intent.putExtra("lelang_id", Integer.parseInt(response.body().getData()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                } else {
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
                progressDoalog.dismiss();
                Log.d("ERROR REQUEST", t.getMessage().toString());
            }
        });
    }

    private void editLelang() {
        String provinsi = this.provinsi_spinner.getSelectedItem().toString(),
                kota = this.kota_spinner.getSelectedItem().toString(),
                judul = this.judul.getText().toString(),
                deskripsi = this.deskripsi.getText().toString(),
                deadline = this.deadline.getText().toString(),
                alamat = this.alamat.getText().toString();
        int pembayaran = this.pembayaran_spinner.getSelectedItemPosition();

        Call<StringRespon> call = RetrofitClient
                .getInstance().getApi().lelang_edit(
                        secret_key,
                        lelang_id_e,
                        SharedPrefManager.getInstance(getApplicationContext()).getUser().getUser_id(),
                        deskripsi,
                        deadline,
                        judul,
                        pembayaran,
                        alamat,
                        getKotaid(kota)
                );

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

        call.enqueue(new Callback<StringRespon>() {
            @Override
            public void onResponse(Call<StringRespon> call, Response<StringRespon> response) {
                progressDoalog.dismiss();
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        Intent intent = new Intent(Deskripsi.this, Preview.class);
                        intent.putExtra("lelang_id", lelang_id_e);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                } else {
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
                progressDoalog.dismiss();
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
