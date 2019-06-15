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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
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

import java.util.ArrayList;

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
        btn_detailgarapan = v.findViewById(R.id.btn_detailgarapan);
        btn_ajukanPenawaran = v.findViewById(R.id.btn_ajukanPenawaran);

        Bundle bundle = getArguments();
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        final Lelang lelang = lelangHelper.getLelang(bundle.getInt("lelang_id"));
        lelangHelper.close();
        lelang_id = lelang.getLelang_id();
        txt_judulgarapan.setText(lelang.getLelang_judul());
        txt_namaPelelang.setText(getNama(lelang.getLelang_userid()));
        txt_alamat.setText(bundle.getString("alamat"));
        txt_eta.setText(bundle.getString("eta"));
        txt_tenggatWaktu.setText(bundle.getString("tenggat_waktu"));
        txt_jumlahmitra.setText(bundle.getInt("count_mitra") + " ");
        txt_pembayaran.setText(getResources().getStringArray(R.array.metode_bayar)[lelang.getLelang_pembayaran()]);
        txt_harga.setText("Rp. " + lelang.getLelang_anggaran());

        String userid = SharedPrefManager.getInstance(getContext()).getUser().getUser_id();

        TawaranHelper tawaranHelper = new TawaranHelper(getActivity());
        tawaranHelper.open();
        boolean isAlreadyBid = tawaranHelper.isAlreadyBid(userid, lelang_id);
        tawaranHelper.close();
        btn_batalkan=v.findViewById(R.id.btn_batalkan);
        if (userid.equalsIgnoreCase(lelang.getLelang_userid())) {
            btn_ajukanPenawaran.setText("Edit Lelang");
            btn_batalkan.setVisibility(View.VISIBLE);
        } else if (isAlreadyBid) {
            btn_ajukanPenawaran.setText("Edit Penawaran");
            btn_batalkan.setVisibility(View.VISIBLE);
        }

        btn_batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_ajukanPenawaran.getText().toString().equalsIgnoreCase("Edit Lelang")) {

                } else if (btn_ajukanPenawaran.getText().toString().equalsIgnoreCase("Edit Penawaran")) {
                    dialogBatalkan();
                }
            }
        });

        btn_ajukanPenawaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_ajukanPenawaran.getText().toString().equalsIgnoreCase("Edit Lelang")) {
                    editLelang(lelang);
                } else if (btn_ajukanPenawaran.getText().toString().equalsIgnoreCase("Edit Penawaran")) {
                    dialogTawaran(true);
                } else {
                    dialogTawaran(false);
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
                ReplaceFragment(fragment);
            }
        });
        return v;
    }

    private void dialogTawaran(final boolean Edit) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_penawaran);
        TextView tv_tittle = dialog.findViewById(R.id.tv_tittle);
        TextView tv_topbid = dialog.findViewById(R.id.tv_topbid);
        if (Edit) {
            tv_tittle.setText("Edit Penawaran");
        }

        TawaranHelper tawaranHelper = new TawaranHelper(getActivity());
        tawaranHelper.open();
        final ArrayList<Tawaran> tawarans = tawaranHelper.getlisttawaran(lelang_id);
        tawaranHelper.close();
        if (tawarans.size() > 0) {
            UserHelper userHelper = new UserHelper(getActivity());
            userHelper.open();
            User user = userHelper.getSingleUser(tawarans.get(0).getTawaran_userid());
            userHelper.close();
            tv_topbid.setText(tawarans.get(0).getTawaran_anggaran() + " [" + user.getUser_nama() + "]");
        }

        final EditText editText = dialog.findViewById(R.id.et_penawaran);
        Button btn_batal = (Button) dialog.findViewById(R.id.btn_batal);
        Button btn_setuju = dialog.findViewById(R.id.btn_setuju);

        btn_setuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                    editText.setError("harap isi tawaran");
                    editText.requestFocus();
                    return;
                }
                if (tawarans.size() > 0) {
                    if (tawarans.get(0).getTawaran_anggaran() < Long.parseLong(editText.getText().toString())) {
                        editText.setError("tawar dengan budget lebih murah");
                        editText.requestFocus();
                        return;
                    }
                }
                long penawaran = Long.parseLong(editText.getText().toString());
                if (Edit) {
                    edit_tawaran(penawaran,dialog);
                } else {
                    new_tawaran(penawaran,dialog);
                }
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

    private void edit_tawaran(long penawaran, final Dialog dialog) {
        String userid = SharedPrefManager.getInstance(getContext()).getUser().getUser_id();
        TawaranHelper tawaranHelper = new TawaranHelper(getActivity());
        tawaranHelper.open();
        Tawaran tawaran = tawaranHelper.SingleTawaran(userid, lelang_id);
        tawaranHelper.close();
        Call<StringRespon> call = RetrofitClient.getInstance()
                .getApi().editTawaran(
                        secret_key,
                        userid,
                        tawaran.getTawaran_id(),
                        penawaran
                );
        call.enqueue(new Callback<StringRespon>() {
            @Override
            public void onResponse(Call<StringRespon> call, Response<StringRespon> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

    private void new_tawaran(long penawaran, final Dialog dialog) {
        String userid = SharedPrefManager.getInstance(getContext()).getUser().getUser_id();
        Call<StringRespon> call = RetrofitClient.getInstance()
                .getApi().buatTawaran(
                        secret_key,
                        userid,
                        lelang_id,
                        penawaran
                );
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        call.enqueue(new Callback<StringRespon>() {
            @Override
            public void onResponse(Call<StringRespon> call, Response<StringRespon> response) {
                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    if (!response.body().isError()) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        btn_ajukanPenawaran.setText("Edit Penawaran");
                        btn_batalkan.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<StringRespon> call, Throwable t) {
                progressDoalog.dismiss();
                Log.e("error", t.getMessage());
            }
        });
    }


    private void editLelang(Lelang lelang) {
        REQLelangHelper reqLelangHelper = new REQLelangHelper(getContext());
        reqLelangHelper.open();
        reqLelangHelper.insert2(lelang);
        reqLelangHelper.close();

        PekerjaanHelper pekerjaanHelper = new PekerjaanHelper(getContext());
        pekerjaanHelper.open();
        ArrayList<Pekerjaan> pekerjaans = pekerjaanHelper.getPekerjaan(lelang.getLelang_id());
        pekerjaanHelper.close();

        REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(getContext());
        reqPekerjaanHelper.open();
        reqPekerjaanHelper.bulk_edit(pekerjaans);
        ArrayList<Pekerjaan> pekerjaanArrayList = reqPekerjaanHelper.getPekerjaan();
        reqPekerjaanHelper.close();

//        for (Pekerjaan pekerjaan:pekerjaanArrayList){
//            Log.d("testtt",pekerjaan.toString());
//        }

        Intent intent = new Intent(getContext(), Preview.class);
        intent.putExtra("editLelang", true);
        startActivity(intent);
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

    public void ReplaceFragment(Fragment fragment) {
        if (fragment != null)
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ContainerFragmentSearch, fragment)
                    .addToBackStack(null)
                    .commit();
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
