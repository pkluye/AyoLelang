package com.ags.ayolelang.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.Activity.PenawaranActivity;
import com.ags.ayolelang.Activity.Preview;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.DBHelper.PekerjaanHelper;
import com.ags.ayolelang.DBHelper.REQLelangHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class FragmentDetailLelang_s extends Fragment {
    private View v;
    private TextView txt_judulgarapan, txt_alamat, txt_tenggatWaktu, txt_eta, txt_namaPelelang, txt_deskripsi, txt_harga, txt_jumlahmitra, txt_pembayaran, txt_status;
    private LinearLayout btn_detailgarapan;
    private Button btn_ajukanPenawaran,btn_batalkan;
    private int lelang_id;
    private TextView txt_subTittle;
    private ImageView btn_back;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.detail_lelang, container, false);

        txt_judulgarapan = v.findViewById(R.id.txt_judulgarapan);
        txt_alamat = v.findViewById(R.id.txt_alamat);
        txt_tenggatWaktu = v.findViewById(R.id.txt_tenggatWaktu);
        txt_eta = v.findViewById(R.id.txt_eta);
        txt_namaPelelang = v.findViewById(R.id.txt_namaPelelang);
        txt_deskripsi = v.findViewById(R.id.txt_deskripsi);
        txt_harga = v.findViewById(R.id.txt_harga);
        txt_jumlahmitra = v.findViewById(R.id.txt_jumlahmitra);
        txt_pembayaran = v.findViewById(R.id.txt_pembayaran);
        txt_status = v.findViewById(R.id.txt_status);
        txt_subTittle=v.findViewById(R.id.txt_subTittle);
        btn_detailgarapan = v.findViewById(R.id.btn_detailgarapan);
        btn_ajukanPenawaran = v.findViewById(R.id.btn_ajukanPenawaran);
        btn_back=v.findViewById(R.id.btn_Back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        loadData();
        return v;
    }

    private void loadData() {
        Bundle bundle = getArguments();
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        final Lelang lelang = lelangHelper.getLelang(bundle.getInt("lelang_id"));
        lelangHelper.close();
        lelang_id = lelang.getLelang_id();
        txt_judulgarapan.setText(lelang.getLelang_judul());
        txt_subTittle.setText(lelang.getLelang_judul());
        txt_namaPelelang.setText(getNama(lelang.getLelang_userid()));
        txt_alamat.setText(bundle.getString("alamat"));
        txt_eta.setText("("+bundle.getString("eta")+")");
        txt_tenggatWaktu.setText(bundle.getString("tenggat_waktu"));
        txt_jumlahmitra.setText(bundle.getInt("count_mitra") + " ");
        txt_pembayaran.setText(getResources().getStringArray(R.array.metode_bayar)[lelang.getLelang_pembayaran()]);
        txt_harga.setText("Rp. " + currencyFormat(lelang.getLelang_anggaran()+""));
        String userid = SharedPrefManager.getInstance(getContext()).getUser().getUser_id();

        TawaranHelper tawaranHelper = new TawaranHelper(getActivity());
        tawaranHelper.open();
        boolean isAlreadyBid = tawaranHelper.isAlreadyBid(userid, lelang_id);
        tawaranHelper.close();
        btn_batalkan=v.findViewById(R.id.btn_batalkan);

        if (lelang.getLelang_userid().equalsIgnoreCase(SharedPrefManager.getInstance(getContext()).getUser().getUser_id())){
            btn_ajukanPenawaran.setVisibility(View.GONE);
        }
        int tawaran_id=0;
        if (isAlreadyBid) {
            tawaranHelper.open();
            tawaran_id=tawaranHelper.SingleTawaran(userid,lelang_id).getTawaran_id();
            tawaranHelper.close();
            btn_ajukanPenawaran.setText("Edit Penawaran");
            btn_batalkan.setVisibility(View.VISIBLE);
        }

        btn_batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_ajukanPenawaran.getText().toString().equalsIgnoreCase("Edit Penawaran")) {
                    dialogBatalkan();
                }
            }
        });

        final int finalTawaran_id = tawaran_id;
        btn_ajukanPenawaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_ajukanPenawaran.getText().toString().equalsIgnoreCase("Edit Penawaran")) {
                    Intent intent= new Intent(getActivity(), PenawaranActivity.class);
                    intent.putExtra("edit",true);
                    intent.putExtra("lelang_id",lelang_id);
                    intent.putExtra("tawaran_id", finalTawaran_id);
                    startActivity(intent);
                } else {
                    Intent intent= new Intent(getActivity(), PenawaranActivity.class);
                    intent.putExtra("lelang_id",lelang_id);
                    startActivity(intent);
                }
            }
        });

        btn_detailgarapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putInt("lelang_id", lelang.getLelang_id());
                Fragment fragment = new ListPekerjaanFragment();
                fragment.setArguments(bundle1);
                ((MainActivity)getActivity())._loadFragment(fragment);
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

    private void dialogBatalkan() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_progress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Button btn_OK = (Button) dialog.findViewById(R.id.btn_OK);
        Button btn_batal = (Button) dialog.findViewById(R.id.btn_batal);
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_tawaran(dialog);
            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void delete_tawaran(final Dialog dialog) {
        String userid = SharedPrefManager.getInstance(getContext()).getUser().getUser_id();
        final TawaranHelper tawaranHelper = new TawaranHelper(getActivity());
        tawaranHelper.open();
        final Tawaran tawaran = tawaranHelper.SingleTawaran(userid, lelang_id);
        tawaranHelper.close();
        Call<StringRespon> call = RetrofitClient.getInstance()
                .getApi().deleteTawaran(
                        secret_key,
                        userid,
                        tawaran.getTawaran_id()
                );
        call.enqueue(new Callback<StringRespon>() {
            @Override
            public void onResponse(Call<StringRespon> call, Response<StringRespon> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        Toast.makeText(getActivity(), "Tawaran dibatalkan", Toast.LENGTH_SHORT).show();
                        btn_ajukanPenawaran.setText("Ajukan Penawaran");
                        btn_batalkan.setVisibility(View.GONE);
                        tawaranHelper.open();
                        tawaranHelper.delete(tawaran.getTawaran_id());
                        tawaranHelper.close();;
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Tawaran gagal dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<StringRespon> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private String getNama(String lelang_userid) {
        String nama = "belum di set";
        //user helper
        UserHelper userHelper = new UserHelper(getActivity());
        userHelper.open();
        User user = userHelper.getSingleUser(lelang_userid);
        userHelper.close();
        nama = user.getUser_nama();
        return nama;
    }

}
