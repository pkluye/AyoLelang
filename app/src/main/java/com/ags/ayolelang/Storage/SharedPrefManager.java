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
        editor.putBoolean("verif", user.isUser_verif());
        editor.putString("alamat",user.getUser_alamat());
        editor.putString("img_url",user.getUser_imgurl());

        editor.apply();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", null) != null;
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
                sharedPreferences.getBoolean("verif",false)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
