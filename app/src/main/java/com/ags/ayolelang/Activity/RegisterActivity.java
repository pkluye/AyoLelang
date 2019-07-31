package com.ags.ayolelang.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
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

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class RegisterActivity extends AppCompatActivity {

    private EditText in_username,in_email,in_password,in_repassword;
    private EditText in_ponsel;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        in_username = (EditText) findViewById(R.id.in_username);
        in_email = (EditText) findViewById(R.id.in_email);
        in_password = (EditText) findViewById(R.id.in_password);
        in_repassword = (EditText) findViewById(R.id.in_repassword);
        in_ponsel=findViewById(R.id.in_ponsel);

    }

    public void register(View view) {
        String nama = in_username.getText().toString().trim();
        String email = in_email.getText().toString().trim();
        String password = in_password.getText().toString().trim();
        String repassword = in_repassword.getText().toString().trim();
        String ponsel=in_ponsel.getText().toString().trim();

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

        if (ponsel.isEmpty()){
            in_ponsel.setError("Nomor Handphone is required");
            in_ponsel.requestFocus();
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

        Single<UserRespon> call = RetrofitClient
                .getInstance().getApi().auth_register(secret_key,nama,email, password,ponsel);

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(RegisterActivity.this);
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<UserRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable=d;
                    }

                    @Override
                    public void onSuccess(UserRespon userRespon) {
                        if (!userRespon.isError()){
                            User user=userRespon.getData();
                            Intent intent = new Intent(RegisterActivity.this, VerificationActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("user_id",user.getUser_id());
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),userRespon.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDoalog.dismiss();
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()){
            disposable.dispose();
        }
    }

    public void login(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }
}
