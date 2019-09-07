package com.ags.ayolelang.Activity;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ags.ayolelang.Adapter.AdapterItemPortofolio;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.Fragment.ProfileReview;
import com.ags.ayolelang.Fragment.ProfileTentang;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.zip.Inflater;

import cn.lankton.flowlayout.FlowLayout;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_namaMitra;
    private TextView txt_kotaProv;
    private RecyclerView recycle_portofolio;
    private FlowLayout flowlayout;
    private int state;
    private User user_raw;
    private Button btn_tentang, btn_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btn_review = findViewById(R.id.btn_review);
        btn_tentang = findViewById(R.id.btn_tentang);
        btn_review.setOnClickListener(this);
        btn_tentang.setOnClickListener(this);
        txt_namaMitra = findViewById(R.id.txt_namaMitra);
        txt_kotaProv = findViewById(R.id.txt_kotaProv);

        Intent intent = getIntent();

        if (intent != null) {
            User user = (User) intent.getSerializableExtra("user");
            user_raw=user;
            txt_namaMitra.setText(user.getUser_nama());
            txt_kotaProv.setText(user.getUser_alamat() != null ? user.getUser_alamat() : "belum diset");
        }
        btn_tentang.callOnClick();
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        Drawable background_white = getResources().getDrawable(R.drawable.button_rectangle_selector);
        Drawable background_selected = getResources().getDrawable(R.drawable.button_rectangle);
        int light = getResources().getColor(R.color.colorWhite);
        int dark = getResources().getColor(R.color.colorGrey1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //backkground
            btn_tentang.setBackground(background_white);
            btn_review.setBackground(background_white);
            //text
            btn_tentang.setTextColor(dark);
            btn_review.setTextColor(dark);
        }
        switch (v.getId()) {
            case R.id.btn_tentang:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn_tentang.setBackground(background_selected);
                    btn_tentang.setTextColor(light);
                }
                fragment = new ProfileTentang();
                break;
            case R.id.btn_review:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn_review.setBackground(background_selected);
                    btn_review.setTextColor(light);
                }
                fragment = new ProfileReview();
                break;
        }
        Bundle bundle=new Bundle();
        bundle.putSerializable("user",user_raw);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.profile_container, fragment)
                .commit();
    }
}
