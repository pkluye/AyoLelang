package com.ags.ayolelang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ags.ayolelang.Activity.EditPassword;
import com.ags.ayolelang.Activity.EditProfile;
import com.ags.ayolelang.Activity.LoginActivity;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

public class AccountFragment extends Fragment implements View.OnClickListener{

    View v;
    private TextView acc_Name,acc_email,acc_noTelp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_account, container,false);
        acc_Name=v.findViewById(R.id.acc_Name);
        acc_email=v.findViewById(R.id.acc_email);
        acc_noTelp=v.findViewById(R.id.acc_noTelp);
        loadAccount();

        v.findViewById(R.id.btn_nextArrow_notifikasi).setOnClickListener(this);
        v.findViewById(R.id.btn_nextArrow_postingGarapan).setOnClickListener(this);
        v.findViewById(R.id.btn_nextArrow_riwayatGarapan).setOnClickListener(this);
        v.findViewById(R.id.btn_nextArrow_gantiBahasa).setOnClickListener(this);
        v.findViewById(R.id.btn_nextArrow_editProfil).setOnClickListener(this);
        v.findViewById(R.id.btn_nextArrow_ubahPassword).setOnClickListener(this);
        v.findViewById(R.id.btn_nextArrow_pusatBantuan).setOnClickListener(this);
        v.findViewById(R.id.btn_nextArrow_tentangAplikasi).setOnClickListener(this);
        v.findViewById(R.id.txt_Logout).setOnClickListener(this);

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_nextArrow_notifikasi:
                break;
            case R.id.btn_nextArrow_postingGarapan:
                break;
            case R.id.btn_nextArrow_riwayatGarapan:
                break;
            case R.id.btn_nextArrow_gantiBahasa:
                break;
            case R.id.btn_nextArrow_editProfil:
                startActivity(new Intent(getActivity(),EditProfile.class));
                break;
            case R.id.btn_nextArrow_ubahPassword:
                startActivity(new Intent(getActivity(), EditPassword.class));
                break;
            case R.id.btn_nextArrow_pusatBantuan:
                break;
            case R.id.btn_nextArrow_tentangAplikasi:
                break;
            case R.id.txt_Logout:
                logout();
                break;
        }
    }
}
