package com.ags.ayolelang.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.ags.ayolelang.Models.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "ayolelang_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    public SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", user.getUser_id());
        editor.putString("nama", user.getUser_nama());
        editor.putString("email", user.getUser_email());
        editor.putString("telpon", user.getUser_telpon());
        editor.putBoolean("status", user.isUser_status());
        editor.putString("alamat",user.getUser_alamat());
        editor.putString("img_url",user.getUser_imgurl());
        editor.putString("skill",user.getUser_skill());
        editor.putString("tentang",user.getUser_tentang());

        editor.apply();
    }

    public void saveToken(String[] s) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token_kategori", s[0]);
        editor.putString("token_provinsi", s[1]);
        editor.putString("token_kota", s[2]);
        editor.putString("token_lelang", s[3]);
        editor.putString("token_pekerjaan", s[4]);
        editor.putString("token_user", s[5]);
        editor.putString("token_tawaran", s[6]);
        editor.putString("token_specbarang", s[7]);

        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", null) != null;
    }

    public String[] getToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new String []{
                sharedPreferences.getString("token_kategori",null),
                sharedPreferences.getString("token_provinsi",null),
                sharedPreferences.getString("token_kota",null),
                sharedPreferences.getString("token_lelang",null),
                sharedPreferences.getString("token_pekerjaan",null),
                sharedPreferences.getString("token_user",null),
                sharedPreferences.getString("token_tawaran",null),
                sharedPreferences.getString("token_specbarang",null)
        };
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString("id",null),
                sharedPreferences.getString("nama",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("telpon",null),
                sharedPreferences.getString("alamat",null),
                sharedPreferences.getString("img_url",null),
                sharedPreferences.getString("skill",null),
                sharedPreferences.getString("tentang",null),
                sharedPreferences.getBoolean("status",false)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

