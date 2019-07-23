package com.ags.ayolelang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ags.ayolelang.Activity.Profile;
import com.ags.ayolelang.Adapter.AdapterListProgress_itemHistoriTawaran;
import com.ags.ayolelang.DBHelper.HistoriTawaranHelper;
import com.ags.ayolelang.Models.Tawaran;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentMitra extends Fragment {

    private TextView txt_namaMitra;
    private TextView txt_kotaProv;
    private Button btn_profilMitra;
    private Button btn_messageMitra;
    private EditText txt_penawaran_mitra;
    private RecyclerView rv_riwayatPenawaran;
    private Button btn_pilihMitra;
    private CircleImageView img_Mitra;
    private ImageButton btn_Menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_mitra, null);
        img_Mitra = view.findViewById(R.id.img_Mitra);
        txt_namaMitra = view.findViewById(R.id.txt_namaMitra);
        txt_kotaProv = view.findViewById(R.id.txt_kotaProv);
        btn_profilMitra = view.findViewById(R.id.btn_profilMitra);
        btn_messageMitra = view.findViewById(R.id.btn_messageMitra);
        txt_penawaran_mitra = view.findViewById(R.id.txt_penawaran_mitra);
        rv_riwayatPenawaran = view.findViewById(R.id.rv_riwayatPenawaran);
        btn_pilihMitra = view.findViewById(R.id.btn_pilihMitra);
        btn_Menu=view.findViewById(R.id.btn_Menu);

        Bundle bundle = getArguments();
        final User user = (User) bundle.getSerializable("user");
        final Tawaran tawaran = (Tawaran) bundle.getSerializable("tawaran");

        txt_namaMitra.setText(user.getUser_nama());
        txt_penawaran_mitra.setText("Rp. "+currencyFormat(tawaran.getTawaran_anggaran()+""));

        HistoriTawaranHelper historiTawaranHelper=new HistoriTawaranHelper(getContext());
        historiTawaranHelper.open();
        ArrayList<Tawaran> tawarans=historiTawaranHelper.getlisttawaran(tawaran.getTawaran_lelangid(),user.getUser_id());
        historiTawaranHelper.close();
        Log.d("count size",tawarans.size()+"");
        AdapterListProgress_itemHistoriTawaran adapterListProgress_itemHistoriTawaran=new AdapterListProgress_itemHistoriTawaran(getContext());
        adapterListProgress_itemHistoriTawaran.addItem(tawarans);
        rv_riwayatPenawaran.setAdapter(adapterListProgress_itemHistoriTawaran);

        btn_profilMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Profile.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        btn_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private String currencyFormat(String harga) {
        Locale localeID = new Locale("in", "ID");
        harga = harga.replaceAll("[.,]", "");
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance(localeID);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("");
        formatRp.setGroupingSeparator('.');
        formatRp.setMonetaryDecimalSeparator(',');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(Double.parseDouble(harga));
    }
}
