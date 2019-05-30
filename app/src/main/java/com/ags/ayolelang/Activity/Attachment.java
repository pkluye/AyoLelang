package com.ags.ayolelang.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class Attachment extends AppCompatActivity {

    private int PICK_FILE_REQ_CODE = 111;
    private Uri FilePathUri;
    private TextView txt_namaFile, txt_next;
    private Button uploadToserver;
    private boolean edit = false,tambah_keranjang=false;
    private int lelang_id;
    private LinearLayout layout_file;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachment);

        this.setTitle("Attachment Brief");
        txt_namaFile = findViewById(R.id.txt_namaFile);
        uploadToserver = findViewById(R.id.uploadToserver);
        txt_next = findViewById(R.id.btn_next);
        layout_file=findViewById(R.id.layout_file);
        String s = DetailSpesifikasi.req_pekerjaan.toString();
        Intent intent = getIntent();
        edit = intent.getBooleanExtra("edit", false);
        tambah_keranjang=intent.getBooleanExtra("tambah_keranjang",false);
        Log.d("edit",edit+"");
        Log.d("tambah_keranjang",tambah_keranjang+"");
        lelang_id = intent.getIntExtra("lelang_id", 0);
        if (edit) {
            txt_next.setText("save");
            if (DetailSpesifikasi.req_pekerjaan.getPekerjaan_fileurl().length() > 1) {
                int posOfSubstr = DetailSpesifikasi.req_pekerjaan.getPekerjaan_fileurl().lastIndexOf("/") + 12;
                txt_namaFile.setText(DetailSpesifikasi.req_pekerjaan.getPekerjaan_fileurl().substring(posOfSubstr));
                uploadToserver.setEnabled(false);
                layout_file.setVisibility(View.VISIBLE);
            }
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("testsst", s);

    }

    public void next(View view) {
        if (edit) {
            editPekerjaan();
        } else if (tambah_keranjang) {
            buatPekerjaan();
        } else {
            startActivity(new Intent(this, Deskripsi.class));
        }
    }

    private void buatPekerjaan() {
        Call<StringRespon> call = RetrofitClient.getInstance()
                .getApi().pekerjaan_buat(
                        secret_key,
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_lelangid(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_ukuran(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_bahan(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_jumlah(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_harga(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_fileurl(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_catatan(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_kategoriid(),
                        SharedPrefManager.getInstance(getApplicationContext()).getUser().getUser_id()
                );
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Attachment.this);
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
                        Toast.makeText(Attachment.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Attachment.this, Preview.class);
                        intent.putExtra("lelang_id", DetailSpesifikasi.req_pekerjaan.getPekerjaan_lelangid());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Attachment.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Attachment.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StringRespon> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(Attachment.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void editPekerjaan() {
        Call<StringRespon> call = RetrofitClient.getInstance()
                .getApi().pekerjaan_edit(
                        secret_key,
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_id(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_ukuran(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_bahan(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_jumlah(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_harga(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_fileurl(),
                        DetailSpesifikasi.req_pekerjaan.getPekerjaan_catatan(),
                        SharedPrefManager.getInstance(getApplicationContext()).getUser().getUser_id()
                );
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Attachment.this);
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
                        Toast.makeText(Attachment.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Attachment.this, Preview.class);
                        intent.putExtra("lelang_id", DetailSpesifikasi.req_pekerjaan.getPekerjaan_lelangid());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Attachment.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Attachment.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StringRespon> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(Attachment.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void upload(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICK_FILE_REQ_CODE);
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

    public void back(View view) {
        onBackPressed();
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
        progressDoalog = new ProgressDialog(Attachment.this);
        progressDoalog.setMax(100);
        //progressDoalog.setMessage("Loading....");
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
                    DetailSpesifikasi.req_pekerjaan.setPekerjaan_fileurl(stringRespon.getData());
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
        DetailSpesifikasi.req_pekerjaan.setPekerjaan_fileurl(null);
    }
}
