package com.ags.ayolelang.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;

public class ProfileTentang extends Fragment {

    GridLayout gridLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tentang, null);
        gridLayout=view.findViewById(R.id.skill_grid);
        Bundle bundle = getArguments();
        User user = (User) bundle.getSerializable("user");
        String text_skill = user.getUser_skill();

        if (text_skill != null && !text_skill.isEmpty()) {
            final String[] skills = text_skill.trim().split("\\s*,\\s*");
            for (String s : skills) {
                TextView textView= (TextView) getLayoutInflater().inflate(R.layout.text_skill, null);
                textView.setText(s);
                LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.item_skill, null);
                linearLayout.addView(textView);
                gridLayout.addView(linearLayout);
            }
        }
        return view;
    }

}
