package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;

import org.w3c.dom.Text;

public class SelesaiClientGarapan extends Fragment {

    View v;
    private TextView txt_subtext_judul;
    private TextView txt_lokasiPenawar;
    private TextView txt_namaPenawar;
    private TextView txt_idGarapan;
    private TextView txt_perkiraanBiaya;
    private TextView txt_tglMulai;
    private TextView txt_tglSelesai;
    private ImageButton btn_Menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_selesai_garapan, container, false);

        Bundle bundle=getArguments();
        Lelang lelang= (Lelang) bundle.getSerializable("lelang");

        txt_namaPenawar=v.findViewById(R.id.txt_namaPenawar);
        txt_subtext_judul=v.findViewById(R.id.txt_subtext_judul);
        txt_lokasiPenawar=v.findViewById(R.id.txt_lokasiPenawar);
        txt_idGarapan=v.findViewById(R.id.txt_idGarapan);
        txt_perkiraanBiaya=v.findViewById(R.id.txt_perkiraanBiaya);
        txt_tglMulai=v.findViewById(R.id.txt_tglMulai);
        txt_tglSelesai=v.findViewById(R.id.txt_tglSelesai);
        btn_Menu=v.findViewById(R.id.btn_Menu);
        btn_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        UserHelper userHelper=new UserHelper(getContext());
        userHelper.open();
        User user=userHelper.getSingleUser(lelang.getLelang_mitraid());
        userHelper.close();
        txt_namaPenawar.setText(user.getUser_nama()+"");;
        txt_lokasiPenawar.setText(user.getUser_alamat()+"");
        txt_subtext_judul.setText(lelang.getLelang_judul()+"");
        txt_idGarapan.setText(lelang.getLelang_id()+"");
        txt_perkiraanBiaya.setText(lelang.getLelang_anggaran()+"");
        txt_tglMulai.setText(lelang.getLelang_tglmulai()+"");
        txt_tglSelesai.setText(lelang.getLelang_tglselesai()+"");
        return v;
    }



}
