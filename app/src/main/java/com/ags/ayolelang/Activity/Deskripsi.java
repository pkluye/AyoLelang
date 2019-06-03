package com.ags.ayolelang.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.MediaRouteButton;
import android.app.ProgressDialog;
import android.app.RemoteAction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.DBHelper.KotaHelper;
import com.ags.ayolelang.DBHelper.ProvinsiHelper;
import com.ags.ayolelang.DBHelper.REQLelangHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.Fragment.DatePickerFragmentDialog;
import com.ags.ayolelang.Models.Kota;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.Models.Provinsi;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;


public class Deskripsi extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText judul, deskripsi, deadline, alamat;
    TextView txt_namaFile;
    Spinner pembayaran_spinner, provinsi_spinner, kota_spinner;
    private Toolbar toolbar;
    Uri FilePathUri;

    private static int temp_kotaid;
    private ImageView image_Kalender;

    ///edit variable
    private String judul_e;
    private String deskripsi_e;
    private String deadline_e;
    private String alamat_e;
    private String prov_e;
    private String kota_e;
    private int pembayaran_e;
    private int lelang_id_e, PICK_FILE_REQ_CODE = 111;
    private boolean edit = false;
    private LinearLayout layout_file;
    private Button uploadToserver;
    private TextView txt_url;
    private final static int PERMISSION_CODE = 222;
    private String txt_url_e;

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
        txt_namaFile = findViewById(R.id.txt_namaFile);
        uploadToserver = findViewById(R.id.uploadToserver);
        layout_file = findViewById(R.id.layout_file);
        image_Kalender = findViewById(R.id.image_Kalender);
        txt_url = findViewById(R.id.txt_url);
        Intent intent = getIntent();
        if (intent != null) {
            edit = intent.getBooleanExtra("edit", false);
            judul_e = intent.getStringExtra("judul");
            deskripsi_e = intent.getStringExtra("deskripsi");
            deadline_e = intent.getStringExtra("deadline");
            alamat_e = intent.getStringExtra("alamatlengkap");
            prov_e = intent.getStringExtra("provnama");
            kota_e = intent.getStringExtra("kotanama");
            pembayaran_e = intent.getIntExtra("pembayaran",0);
            lelang_id_e = intent.getIntExtra("id", 0);
            Log.d("id lelang",lelang_id_e+"");
            txt_url_e = intent.getStringExtra("fileurl");
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadprovinsi();

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateString = dateFormat.format(Calendar.getInstance().getTime());

        deadline.setText(currentDateString);

        ArrayAdapter<String> pembayaranadapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.metode_bayar));
        pembayaranadapter.notifyDataSetChanged();
        pembayaran_spinner.setAdapter(pembayaranadapter);
        if (edit) {
            judul.setText(judul_e);
            deskripsi.setText(deskripsi_e);
            deadline.setText(deadline_e.substring(0, 10));
            pembayaran_spinner.setSelection(pembayaran_e);
            alamat.setText(alamat_e);
            txt_url.setText(txt_url_e);
            if (txt_url_e.length() > 1) {
                String filename="";
                if (txt_url_e.length()>79){
                    int posOfSubstr = txt_url_e.lastIndexOf("/") + 12;
                    filename = txt_url_e.substring(posOfSubstr);
                }else{
                    filename = txt_url_e;
                }
                txt_namaFile.setText(filename);
                layout_file.setVisibility(View.VISIBLE);
                uploadToserver.setEnabled(false);
            }
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
        String provinsi = this.provinsi_spinner.getSelectedItem().toString(),
                kota = this.kota_spinner.getSelectedItem().toString(),
                judul = this.judul.getText().toString(),
                deskripsi = this.deskripsi.getText().toString(),
                deadline = this.deadline.getText().toString(),
                alamat = this.alamat.getText().toString(),
                txt_url = this.txt_url.getText().toString();
        int pembayaran = this.pembayaran_spinner.getSelectedItemPosition();
        REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(this);
        reqPekerjaanHelper.open();
        ArrayList<Pekerjaan> pekerjaans = reqPekerjaanHelper.getPekerjaan();
        reqPekerjaanHelper.close();
        long totalharga = 0;
        for (Pekerjaan pekerjaan : pekerjaans) {
            totalharga += pekerjaan.getPekerjaan_harga();
        }
        REQLelangHelper reqLelangHelper = new REQLelangHelper(this);
        reqLelangHelper.open();
        if (edit) {
            Log.d("deskripsi",deadline);
            Lelang lelang=new Lelang(deskripsi,
                    lelang_id_e,
                    deadline,
                    judul,
                    SharedPrefManager.getInstance(this).getUser().getUser_id(),
                    alamat,
                    txt_url,
                    getKotaid(kota),
                    pembayaran,
                    totalharga);
            Log.d("lelang",lelang.toString());
            reqLelangHelper.update(lelang);
            reqLelangHelper.close();
        } else {
            reqLelangHelper.insert(new Lelang(deskripsi,
                    deadline,
                    judul,
                    SharedPrefManager.getInstance(this).getUser().getUser_id(),
                    alamat,
                    txt_url,
                    getKotaid(kota),
                    pembayaran,
                    totalharga));
            reqLelangHelper.close();
            Intent intent=new Intent(this, Preview.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        finish();
    }

    public void back(View view) {
        finish();
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
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateString = dateFormat.format(calendar.getTime());

        deadline.setText(currentDateString);
    }


    public void upload(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                openfilemanager();
            }
        } else {
            openfilemanager();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission grant
                    openfilemanager();
                } else {
                    //permission denied
                    Toast.makeText(this, "permission denied..", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void openfilemanager() {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, PICK_FILE_REQ_CODE);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            File file = new File(getRealPathFromURI(FilePathUri));
            txt_namaFile.setText(file.getName());
            layout_file.setVisibility(View.VISIBLE);
            uploadToserver.setEnabled(true);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public void uploadToserver(View view) {
        File file_ = new File(getRealPathFromURI(FilePathUri));
        RequestBody file = RequestBody.create(MediaType.parse(getContentResolver().getType(FilePathUri)), file_);
        MultipartBody.Part filepart = MultipartBody.Part.createFormData("userfile", file_.getName(), file);
        RequestBody secret_key_ = RequestBody.create(MediaType.parse("text/plain"), secret_key);
        Call<StringRespon> call = RetrofitClient.getInstance()
                .getApi().uploadfile(secret_key_, filepart);

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Uploading");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<StringRespon>() {
            @Override
            public void onResponse(Call<StringRespon> call, Response<StringRespon> response) {
                progressDoalog.dismiss();
                StringRespon stringRespon = response.body();
                if (!stringRespon.isError()) {
                    Toast.makeText(getApplicationContext(), stringRespon.getMessage(), Toast.LENGTH_LONG).show();
                    txt_url.setText(stringRespon.getData());
                    uploadToserver.setEnabled(false);
                } else {
                    Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StringRespon> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clear(View view) {
        FilePathUri = null;
        layout_file.setVisibility(View.GONE);
        txt_url.setText("-");
    }
}
