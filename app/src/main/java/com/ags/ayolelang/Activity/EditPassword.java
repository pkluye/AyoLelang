package com.ags.ayolelang.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class EditPassword extends AppCompatActivity {


    private EditText txt_passwordLama;
    private EditText txt_passwordBaru;
    private EditText txt_passwordKonfirmasi;
    private TextView txt_nama;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpassword);
        txt_nama=findViewById(R.id.txt_nama);
        txt_passwordLama=findViewById(R.id.txt_passwordLama);
        txt_passwordBaru=findViewById(R.id.txt_passwordBaru);
        txt_passwordKonfirmasi=findViewById(R.id.txt_passwordKonfirmasi);
        txt_nama.setText(SharedPrefManager.getInstance(this).getUser().getUser_nama());
        txt_passwordKonfirmasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!txt_passwordBaru.getText().toString().trim().equalsIgnoreCase(txt_passwordKonfirmasi.getText().toString().trim())){
                    txt_passwordKonfirmasi.setError("password tidak cocok");
                    txt_passwordKonfirmasi.requestFocus();
                    return;
                }
            }
        });
        txt_passwordBaru.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txt_passwordKonfirmasi.getText().length()>0){
                    if (!txt_passwordBaru.getText().toString().trim().equalsIgnoreCase(txt_passwordKonfirmasi.getText().toString().trim())){
                        txt_passwordKonfirmasi.setError("password tidak cocok");
                        txt_passwordKonfirmasi.requestFocus();
                        return;
                    }
                }
            }
        });
    }

    public void simpan(View view) {
        if (txt_passwordBaru.getText().length()<6){
            txt_passwordBaru.setError("Password harus lebih dari 6 karakter");
            txt_passwordBaru.requestFocus();
            return;
        }

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("secret_key",secret_key);
            jsonObject.put("user_id", SharedPrefManager.getInstance(this).getUser().getUser_id());
            jsonObject.put("password_baru",txt_passwordBaru.getText().toString().trim());
            jsonObject.put("password_lama",txt_passwordLama.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ProgressDialog progressDoalog=new ProgressDialog(this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        Single<StringRespon> stringResponSingle= RetrofitClient.getInstance().getApi().edit_password(jsonObject.toString());
        stringResponSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<StringRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(StringRespon stringRespon) {
                        progressDoalog.dismiss();
                        if (!stringRespon.isError()){
                            Toast.makeText(EditPassword.this,stringRespon.getMessage(),Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(EditPassword.this,stringRespon.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDoalog.dismiss();
                        e.printStackTrace();
                    }
                });
    }

    public void back(View view) {
        onBackPressed();
    }
}
