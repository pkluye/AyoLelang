package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ags.ayolelang.Adapter.AdapterItemPortofolio;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.zip.Inflater;

import cn.lankton.flowlayout.FlowLayout;

public class Profile extends AppCompatActivity {

    private TextView txt_namaMitra;
    private TextView txt_kotaProv;
    private TextView text_about;
    private RecyclerView recycle_portofolio;
    private FlowLayout flowlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txt_namaMitra = findViewById(R.id.txt_namaMitra);
        txt_kotaProv = findViewById(R.id.txt_kotaProv);
        text_about = findViewById(R.id.text_about);
        flowlayout = findViewById(R.id.flowlayout);
        recycle_portofolio = findViewById(R.id.recycle_portofolio);
        recycle_portofolio.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        Intent intent = getIntent();

        if (intent != null) {
            User user = (User) intent.getSerializableExtra("user");
            txt_namaMitra.setText(user.getUser_nama());
            txt_kotaProv.setText(user.getUser_alamat() != null ? user.getUser_alamat() : "belum diset");
            text_about.setText(user.getUser_tentang() != null ? user.getUser_tentang() : "belum diset");
            String text_skill = user.getUser_skill();

            if (text_skill != null && !text_skill.isEmpty()) {
                final String[] skills = text_skill.trim().split("\\s*,\\s*");
                for (String s : skills) {
                    TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_skill, null);
                    textView.setText(s);
                    flowlayout.addView(textView);
                    flowlayout.relayoutToAlign();
                }
            }
            LelangHelper lelangHelper = new LelangHelper(this);
            lelangHelper.open();
            ArrayList<Lelang> lelangs = lelangHelper.getLelangbyMitra(user.getUser_id(), 6);
            lelangHelper.close();
            AdapterItemPortofolio portofolio = new AdapterItemPortofolio(getApplicationContext());
            portofolio.addItem(lelangs);
            recycle_portofolio.setAdapter(portofolio);
        }
    }

    public void back(View view) {
        finish();
    }
}
