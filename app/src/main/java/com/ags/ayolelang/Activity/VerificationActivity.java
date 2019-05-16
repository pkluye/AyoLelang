package com.ags.ayolelang.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Models.UserRespon;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;
import com.goodiebag.pinview.Pinview;
import com.google.gson.internal.LinkedTreeMap;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class VerificationActivity extends AppCompatActivity {

    EditText code1, code2, code3, code4, code5, code6;
    String user_id, pin;
    Pinview pinview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        pinview = (Pinview) findViewById(R.id.pinview);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                pin = pinview.getValue().toString();
            }
        });

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

    }

    public void verif(View view) {
        Call<UserRespon> call = RetrofitClient.getInstance()
                .getApi().auth_verif(secret_key,user_id,pin);

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(VerificationActivity.this);
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<UserRespon>() {
            @Override
            public void onResponse(Call<UserRespon> call, Response<UserRespon> response) {
                progressDoalog.dismiss();
                UserRespon userRespon = response.body();
                if (!userRespon.isError()) {
                    User user=userRespon.getData();
                    SharedPrefManager.getInstance(VerificationActivity.this)
                            .saveUser(user);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else {
                    Log.d("test", userRespon.getMessage());
                    Toast.makeText(VerificationActivity.this, userRespon.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserRespon> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
