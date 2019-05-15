package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Models.UserRespon;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.google.gson.internal.LinkedTreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

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
        String nama = in_username.getText().toString().trim();
        String email = in_email.getText().toString().trim();
        String password = in_password.getText().toString().trim();
        String repassword = in_repassword.getText().toString().trim();

        if (nama.isEmpty()) {
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

        Call<UserRespon> call = RetrofitClient
                .getInstance().getApi().auth_register(secret_key,nama,email, password);

        call.enqueue(new Callback<UserRespon>() {
            @Override
            public void onResponse(Call<UserRespon> call, Response<UserRespon> response) {
                UserRespon registerResponse = response.body();
                if (!registerResponse.isError()){
                    User user=registerResponse.getData();
                    Intent intent = new Intent(RegisterActivity.this, VerificationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("user_id",user.getUser_id());
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),registerResponse.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserRespon> call, Throwable t) {

            }
        });
    }
}
