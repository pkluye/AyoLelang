package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import feri.com.lpse.API.RetrofitClient;
import feri.com.lpse.Models.DefaultResponse;
import feri.com.lpse.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText in_username,in_email,in_password,in_repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        in_username = (EditText) findViewById(R.id.in_username);
        in_email = (EditText) findViewById(R.id.in_email);
        in_password = (EditText) findViewById(R.id.in_password);
        in_repassword = (EditText) findViewById(R.id.in_repassword);

    }

    public void register(View view) {
        String username = in_username.getText().toString().trim();
        String email = in_email.getText().toString().trim();
        String password = in_password.getText().toString().trim();
        String repassword = in_repassword.getText().toString().trim();

        if (username.isEmpty()) {
            in_username.setError("Username is required");
            in_username.requestFocus();
            return;
        }

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

        if (!password.equalsIgnoreCase(repassword)){
            in_repassword.setError("Password not match");
            in_repassword.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance().getApi().userRegister(username,email, password);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse registerResponse = response.body();

                if (!registerResponse.isError()){
                    Intent intent = new Intent(RegisterActivity.this, VerificationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("username",in_username.getText().toString());
                    intent.putExtra("email",in_email.getText().toString());
                    intent.putExtra("type","register");
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),registerResponse.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }
}
