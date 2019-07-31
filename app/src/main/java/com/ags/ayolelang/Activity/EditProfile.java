package com.ags.ayolelang.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class EditProfile extends Activity {
    private EditText txt_nama;
    private EditText txt_email;
    private EditText txt_alamat;
    private EditText txt_nomorTelp;
    private EditText txt_tentang;
    private MultiAutoCompleteTextView txt_skill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        txt_nama=findViewById(R.id.txt_nama);
        txt_email=findViewById(R.id.txt_email);
        txt_alamat=findViewById(R.id.txt_alamat);
        txt_nomorTelp=findViewById(R.id.txt_nomorTelp);
        txt_tentang=findViewById(R.id.txt_tentang);
        txt_skill=findViewById(R.id.txt_skill);
        ArrayList<String>skills=new ArrayList<>();
        UserHelper userHelper=new UserHelper(this);
        userHelper.open();
        for (User user:userHelper.getUser()){
            if (user.getUser_skill()!=null){
                String skills_[]=user.getUser_skill().split(",");
                for (String s:skills_){
                    if (!skills.contains(s)){
                        skills.add(s);
                    }
                }
            }
        }
        userHelper.close();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, skills);
        txt_skill.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        txt_skill.setAdapter(arrayAdapter);

        User user=SharedPrefManager.getInstance(this).getUser();
        txt_nama.setText(user.getUser_nama()+"");
        txt_email.setText(user.getUser_email()+"");
        txt_alamat.setText(user.getUser_alamat()+"");
        txt_nomorTelp.setText(user.getUser_telpon()+"");
        txt_tentang.setText(user.getUser_tentang()+"");
        txt_skill.setText(user.getUser_skill()+"");
    }

    public void back(View view) {
        onBackPressed();
    }

    public void simpan(View view) {
        JSONObject jsonObject=new JSONObject();
        try {
            JSONObject user= new JSONObject();
            user.put("user_nama",txt_nama.getText().toString().trim());
            user.put("user_email",txt_email.getText().toString().trim());
            user.put("user_alamat",txt_alamat.getText().toString().trim());
            user.put("user_telpon",txt_nomorTelp.getText().toString().trim());
            user.put("user_tentang",txt_tentang.getText().toString().trim());
            user.put("user_skill",txt_skill.getText().toString().trim());

            jsonObject.put("secret_key",secret_key);
            jsonObject.put("user_id",SharedPrefManager.getInstance(this).getUser().getUser_id());
            jsonObject.put("users",user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ProgressDialog progressDoalog=new ProgressDialog(this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        Single<StringRespon> stringResponSingle= RetrofitClient.getInstance().getApi().edit_profil(jsonObject.toString());
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
                            Toast.makeText(EditProfile.this,stringRespon.getMessage(),Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(EditProfile.this,stringRespon.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDoalog.dismiss();
                        e.printStackTrace();
                    }
                });
    }
}
