package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Models.KotaProvinsi;
import com.ags.ayolelang.Models.KotaProvinsiRespon;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.LelangRespon;
import com.ags.ayolelang.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class Preview extends AppCompatActivity {

    private int lelang_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        Intent intent = getIntent();
        lelang_id=intent.getIntExtra("lelang_id",0);
        loadDataLelang();
    }

    private void loadDataLelang() {
        Call<LelangRespon> call= RetrofitClient
                .getInstance().getApi().lelang_getLelang(secret_key,lelang_id);

        call.enqueue(new Callback<LelangRespon>() {
            @Override
            public void onResponse(Call<LelangRespon> call, Response<LelangRespon> response) {
                if (response.isSuccessful()){
                    LelangRespon lelangRespon =response.body();
                    if (!lelangRespon.isError()){
                        Lelang lelang =lelangRespon.getData();
                        LoadDataKota(lelang.getLelang_kota());
                        //set tampilan
                    }else {
                        Log.d("error",lelangRespon.getMessage());
                    }

                }else{
                    Log.d("respon error",response.toString());
                }
            }

            @Override
            public void onFailure(Call<LelangRespon> call, Throwable t) {
                Log.d("ERROR REQ",t.getMessage());
            }
        });
    }

    private void LoadDataKota(int i) {
        Call<KotaProvinsiRespon>call=RetrofitClient
                .getInstance().getApi().getkabupatenprovinsi(secret_key,i);

        call.enqueue(new Callback<KotaProvinsiRespon>() {
            @Override
            public void onResponse(Call<KotaProvinsiRespon> call, Response<KotaProvinsiRespon> response) {
                if (response.isSuccessful()){
                    KotaProvinsiRespon kotaProvinsiRespon =response.body();
                    if (!kotaProvinsiRespon.isError()){
                        KotaProvinsi kotaProvinsi=kotaProvinsiRespon.getData();
                        //set tampilan
                    }else {
                        Log.d("error",kotaProvinsiRespon.getMessage());
                    }

                }else{
                    Log.d("respon error",response.toString());
                }
            }

            @Override
            public void onFailure(Call<KotaProvinsiRespon> call, Throwable t) {

            }
        });
    }


}
