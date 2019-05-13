package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import feri.com.lpse.API.RetrofitClient;
import feri.com.lpse.Models.LoginResponse;
import feri.com.lpse.R;
import feri.com.lpse.Storage.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText in_email,in_password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        in_email = (EditText) findViewById(R.id.in_email);
        in_password = (EditText) findViewById(R.id.in_password);
        btn_login = (Button) findViewById(R.id.btn_login);

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

        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().userLogin(email, password);

        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                Log.d("test",loginResponse.getMessage());

                if (!loginResponse.isError()) {
                    if (loginResponse.getUser().isStatus()){
                        SharedPrefManager.getInstance(LoginActivity.this)
                                .saveUser(loginResponse.getUser());

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("username",loginResponse.getUser().getUsername());
                        intent.putExtra("email",loginResponse.getUser().getEmail());
                        intent.putExtra("type","register");
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Log.d("test",loginResponse.getMessage());
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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
