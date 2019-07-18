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
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;

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
        String text_skill = null;
        if (intent != null) {
            User user = (User) intent.getSerializableExtra("user");
            txt_namaMitra.setText(user.getUser_nama());
            txt_kotaProv.setText("belum diset");
            text_about.setText("belum diset");
            text_skill = "java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,java, android, photoshop,";
            text_skill = text_skill.substring(0, text_skill.length() - 1).trim();
        }

        final String[] skills = text_skill.split("\\s*,\\s*");
        for (String s:skills){
            TextView textView= (TextView) getLayoutInflater().inflate(R.layout.item_skill,null);
            textView.setText(s);
            flowlayout.addView(textView);
            flowlayout.relayoutToAlign();
        }

        ArrayList<Lelang> lelangs=new ArrayList<>();
        Lelang lelang1=new Lelang();
        lelang1.setLelang_userid("20190616061002eA0");
        lelang1.setLelang_judul("Test saja");
        lelang1.setLelang_anggaran(999999);
        lelang1.setLelang_deskripsi("ini hanya test saja, test pertama");
        lelangs.add(lelang1);
        AdapterItemPortofolio portofolio=new AdapterItemPortofolio(getApplicationContext());
        portofolio.addItem(lelangs);
        recycle_portofolio.setAdapter(portofolio);
    }

    public void back(View view) {
        finish();
    }
}
