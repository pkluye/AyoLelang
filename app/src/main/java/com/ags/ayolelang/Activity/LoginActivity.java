package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Models.DefaultResponse;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;
import com.google.gson.internal.LinkedTreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class LoginActivity extends AppCompatActivity {

    private EditText in_email,in_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        in_email = (EditText) findViewById(R.id.in_email);
        in_password = (EditText) findViewById(R.id.in_password);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void login(View view) {
        String email = in_email.getText().toString().trim();
        String password = in_password.getText().toString().trim();

        if (email.isEmpty()) {
            in_email.setError("Email is required");
            in_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            in_email.setError("Enter a valid email");
            in_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            in_password.setError("Password required");
            in_password.requestFocus();
            return;
        }

        if (password.length() < 6) {
            in_password.setError("Password should be atleast 6 character long");
            in_password.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance().getApi().userLogin(secret_key,email, password);

        call.enqueue(new Callback<DefaultResponse>() {

            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse loginResponse = response.body();
                LinkedTreeMap<Object,Object> t = (LinkedTreeMap) loginResponse.getData();
                User user=new User(t.get("user_id").toString(),
                        t.get("user_nama").toString(),
                        t.get("user_email").toString(),
                        t.get("user_telpon").toString(),
                        t.get("user_alamat").toString(),
                        t.get("user_imgurl").toString(),
                        (boolean)t.get("user_status"));
                Log.d("print user", user.isUser_status()+"");
                if (!loginResponse.isError()) {
                    if (user.isUser_status()){
                        SharedPrefManager.getInstance(LoginActivity.this)
                                .saveUser(user);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("user_id",user.getUser_id());
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Log.d("test",loginResponse.getMessage());
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.d("errror",t.getMessage());
            }

        });
    }

    public void register(View view) {
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
        finish();
    }

    public void lupapassword(View view) {

    }
}
