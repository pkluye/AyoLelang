package com.ags.ayolelang.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.Activity.MessageActivity;
import com.ags.ayolelang.Activity.PenawaranActivity;
import com.ags.ayolelang.Adapter.AdapterItemPekerjaan_pengerjaanclient;
import com.ags.ayolelang.Adapter.AdapterItemPekerjaan_pengerjaanmitra;
import com.ags.ayolelang.Adapter.AdapterListProgress_itemHistoriTawaran;
import com.ags.ayolelang.DBHelper.HistoriTawaranHelper;
import com.ags.ayolelang.DBHelper.PekerjaanHelper;
import com.ags.ayolelang.DBHelper.TawaranHelper;
import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.Models.Tawaran;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class PengerjaanClientGarapan extends Fragment {

    private View v;
    private TextView txt_subtext_judul;
    private TextView txt_idGarapan;
    private TextView txt_perkiraanBiaya;
    private RecyclerView rv_pekerjaan;
    private Button btn_tawar;
    private TextView txt_namaMitra;
    private TextView txt_deskripsi;
    private TextView txt_alamatlengkap;
    private TextView txt_deadline;
    private TextView txt_attachment;
    private Button btn_selesai;
    private TextView mitraatauclient;
    private Button btn_hubMitra;
    private TextView btn_back;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pengerjaan, container, false);

        txt_subtext_judul=v.findViewById(R.id.txt_subtext_judul);
        txt_idGarapan=v.findViewById(R.id.txt_idGarapan);
        txt_perkiraanBiaya=v.findViewById(R.id.txt_totalBiaya);
        txt_namaMitra=v.findViewById(R.id.txt_namaMitra);
        rv_pekerjaan=v.findViewById(R.id.rv_pekerjaan);
        txt_deskripsi=v.findViewById(R.id.txt_deskripsi);
        txt_alamatlengkap=v.findViewById(R.id.txt_alamatlengkap);
        txt_deadline=v.findViewById(R.id.txt_deadline);
        txt_attachment=v.findViewById(R.id.txt_attachment);
        btn_selesai=v.findViewById(R.id.btn_selesai);
        mitraatauclient=v.findViewById(R.id.mitraatauclient);
        btn_hubMitra=v.findViewById(R.id.btn_hubMitra);
        btn_back=v.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mitraatauclient.setText("Mitra");

        Bundle bundle=getArguments();
        final Lelang lelang= (Lelang) bundle.getSerializable("lelang");

        txt_subtext_judul.setText(lelang.getLelang_judul());
        txt_idGarapan.setText(lelang.getLelang_id()+"");
        txt_deskripsi.setText(lelang.getLelang_deskripsi());
        txt_alamatlengkap.setText(lelang.getLelang_alamat());
        txt_deadline.setText(lelang.getLelang_tglselesai());
        txt_attachment.setText(lelang.getLelang_fileurl());

        btn_hubMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MessageActivity.class);
                intent.putExtra("userid2",lelang.getLelang_mitraid());
                startActivity(intent);
            }
        });

        btn_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lelang_selesai(lelang);
            }
        });

        final TawaranHelper tawaranHelper=new TawaranHelper(getContext());
        tawaranHelper.open();
        Tawaran tawaran=tawaranHelper.getTawaran1st(lelang.getLelang_id());
        tawaranHelper.close();
        txt_perkiraanBiaya.setText("Rp. "+currencyFormat(tawaran.getTawaran_anggaran()+""));
        UserHelper userHelper=new UserHelper(getContext());
        userHelper.open();
        User user=userHelper.getSingleUser(lelang.getLelang_mitraid());
        userHelper.close();
        txt_namaMitra.setText(user.getUser_nama());

        PekerjaanHelper pekerjaanHelper=new PekerjaanHelper(getContext());
        pekerjaanHelper.open();
        ArrayList<Pekerjaan> pekerjaans=pekerjaanHelper.getPekerjaan(lelang.getLelang_id());
        pekerjaanHelper.close();

        AdapterItemPekerjaan_pengerjaanclient pengerjaanclient=new AdapterItemPekerjaan_pengerjaanclient(getContext());
        pengerjaanclient.addItem(pekerjaans);
        rv_pekerjaan.setAdapter(pengerjaanclient);


        return v;
    }

    private void lelang_selesai(Lelang lelang) {
        Single<StringRespon> single= RetrofitClient.getInstance().getApi().lelang_selesai(
                secret_key,
                lelang.getLelang_id()+"",
                lelang.getLelang_userid()
        );
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<StringRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(StringRespon stringRespon) {
                        progressDoalog.dismiss();
                        Fragment fragment=new GarapanFragment();
                        ((MainActivity)getActivity()).loadFragment_(fragment);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDoalog.dismiss();
                        e.printStackTrace();
                    }
                });
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
