package com.ags.ayolelang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ags.ayolelang.Activity.LoginActivity;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

public class AccountFragment extends Fragment {

    View v;
    TextView logout_btn;
    private TextView acc_Name,acc_email,acc_noTelp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_account, container,false);
        logout_btn = v.findViewById(R.id.txt_Logout);
        acc_Name=v.findViewById(R.id.acc_Name);
        acc_email=v.findViewById(R.id.acc_email);
        acc_noTelp=v.findViewById(R.id.acc_noTelp);
        loadAccount();
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return v;
    }

    private void loadAccount() {
        User user=SharedPrefManager.getInstance(getContext()).getUser();
        acc_Name.setText(user.getUser_nama());
        acc_email.setText(user.getUser_email());
        acc_noTelp.setText(user.getUser_telpon());
    }


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        getActivity().setTitle("Profil Saya");
//    }

    public void logout() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
