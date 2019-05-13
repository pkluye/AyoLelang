package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ags.ayolelang.Models.Verification;
import com.goodiebag.pinview.Pinview;

import feri.com.lpse.API.RetrofitClient;
import feri.com.lpse.Models.DefaultResponse;
import feri.com.lpse.Models.User;
import feri.com.lpse.Models.VerifCodeRespon;
import feri.com.lpse.Models.Verification;
import feri.com.lpse.R;
import feri.com.lpse.Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity {

    EditText code1,code2,code3,code4,code5,code6;
    Verification verification;
    String username,email,pin;
    Pinview pinview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        loadData();

        pinview = (Pinview) findViewById(R.id.pinview);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                pin=pinview.getValue().toString();
            }
        });

    }

    private void loadData() {
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        Log.d("loaddd testtt","haloooooooo "+username+" "+email);
        Call<VerifCodeRespon> call = RetrofitClient.getInstance()
                .getApi().getVerifCode(username,"register");

        call.enqueue(new Callback<VerifCodeRespon>() {

            @Override
            public void onResponse(Call<VerifCodeRespon> call, Response<VerifCodeRespon> response) {
                VerifCodeRespon verifCodeRespon=response.body();
                Log.d("tessssss","test2");
                if (!verifCodeRespon.isError()){
                    verification=verifCodeRespon.getVerification();
                }else{
                    Toast.makeText(getApplicationContext(),verifCodeRespon.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VerifCodeRespon> call, Throwable t) {
                Log.d("gagall",t.getMessage());
            }
        });
    }



    public void verif(View view) {
        String code = pin;

        if (code.equals(verification.getCode())){
            Call<DefaultResponse> call=RetrofitClient.getInstance()
                    .getApi().userVerification(verification.getId_verification(),username);
            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    DefaultResponse defaultResponse = response.body();

                    if(!defaultResponse.isError()){
                        SharedPrefManager.getInstance(VerificationActivity.this)
                                .saveUser(new User(username,email,null,null,null,true));

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {

                }
            });
        }else{
            return;
        }
    }
}
