package com.ags.ayolelang.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.R;

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
    private TextView txt_namaFile;
    private Button uploadToserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachment);

        this.setTitle("Attachment Brief");
        txt_namaFile=findViewById(R.id.txt_namaFile);
        uploadToserver=findViewById(R.id.uploadToserver);
        String s = DetailSpesifikasi.req_pekerjaan.toString();

        Log.d("testsst", s);

    }


    public void next(View view) {
        startActivity(new Intent(this, Deskripsi.class));
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
            txt_namaFile.setVisibility(View.VISIBLE);
            uploadToserver.setVisibility(View.VISIBLE);
            uploadToserver.setEnabled(true);
            findViewById(R.id.btn_clear).setVisibility(View.VISIBLE);
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
        File file_=new File(getRealPathFromURI(FilePathUri));
        RequestBody file=RequestBody.create(MediaType.parse(getContentResolver().getType(FilePathUri)),file_);
        MultipartBody.Part filepart= MultipartBody.Part.createFormData("userfile",file_.getName(),file);
        RequestBody secret_key_=RequestBody.create(MediaType.parse("text/plain"),secret_key);
        Call<StringRespon> call= RetrofitClient.getInstance()
                .getApi().uploadfile(secret_key_,filepart);

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Attachment.this);
        progressDoalog.setMax(100);
        //progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Uploading");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<StringRespon>() {
            @Override
            public void onResponse(Call<StringRespon> call, Response<StringRespon> response) {
                progressDoalog.dismiss();
                StringRespon stringRespon=response.body();
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
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clear(View view) {
        txt_namaFile.setVisibility(View.INVISIBLE);
        uploadToserver.setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_clear).setVisibility(View.INVISIBLE);
        DetailSpesifikasi.req_pekerjaan.setPekerjaan_fileurl(null);
    }
}
