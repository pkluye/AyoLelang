package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Models.DefaultResponse;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;
import com.goodiebag.pinview.Pinview;


import java.util.ArrayList;

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
        Call<DefaultResponse> call = RetrofitClient.getInstance()
                .getApi().auth_verif(secret_key,user_id,pin);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                ArrayList<User> users=(ArrayList<User>)(ArrayList<?>) defaultResponse.getData();
                if (!defaultResponse.isError()) {
                    SharedPrefManager.getInstance(VerificationActivity.this)
                            .saveUser(users.get(0));
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else {
                    Log.d("test", defaultResponse.getMessage());
                    Toast.makeText(VerificationActivity.this, defaultResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });

    }
}
