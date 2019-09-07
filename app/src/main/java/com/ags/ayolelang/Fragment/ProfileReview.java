package com.ags.ayolelang.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ags.ayolelang.Adapter.AdapterItemPortofolio;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;

import java.util.ArrayList;

public class ProfileReview extends Fragment {


    private RecyclerView recycle_portofolio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        if (intent != null) {
//            User user = (User) intent.getSerializableExtra("user");
//            txt_namaMitra.setText(user.getUser_nama());
//            txt_kotaProv.setText(user.getUser_alamat() != null ? user.getUser_alamat() : "belum diset");
//            text_about.setText(user.getUser_tentang() != null ? user.getUser_tentang() : "belum diset");
//            String text_skill = user.getUser_skill();
//
//            if (text_skill != null && !text_skill.isEmpty()) {
//                final String[] skills = text_skill.trim().split("\\s*,\\s*");
//                for (String s : skills) {
//                    TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_skill, null);
//                    textView.setText(s);
//                    flowlayout.addView(textView);
//                    flowlayout.relayoutToAlign();
//                }
//            }
//            LelangHelper lelangHelper = new LelangHelper(this);
//            lelangHelper.open();
//            ArrayList<Lelang> lelangs = lelangHelper.getLelangbyMitra(user.getUser_id(), 6);
//            lelangHelper.close();
//            AdapterItemPortofolio portofolio = new AdapterItemPortofolio(getApplicationContext());
//            portofolio.addItem(lelangs);
//            recycle_portofolio.setAdapter(portofolio);
//        }

        View view = inflater.inflate(R.layout.fragment_profile_review, null);
        recycle_portofolio = view.findViewById(R.id.recycle_portofolio);
        Bundle bundle = getArguments();
        User user = (User) bundle.getSerializable("user");
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs = lelangHelper.getLelangbyMitra(user.getUser_id(), 6);
        lelangHelper.close();
        AdapterItemPortofolio portofolio = new AdapterItemPortofolio(getContext());
        portofolio.addItem(lelangs);
        recycle_portofolio.setAdapter(portofolio);
        return view;
    }

}
