package com.ags.ayolelang.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Activity.MainActivity;
import com.ags.ayolelang.DBHelper.KotaHelper;
import com.ags.ayolelang.DBHelper.ProvinsiHelper;
import com.ags.ayolelang.Fragment.FragmentPemilihanMitra;
import com.ags.ayolelang.Models.Kota;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Provinsi;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class AdapterListProgress_PemilihanvClient extends RecyclerView.Adapter<AdapterListProgress_PemilihanvClient.CustomHolderView> {
    private LayoutInflater mInflater;
    Context context;
    ArrayList<Lelang> lelangs;

    public AdapterListProgress_PemilihanvClient(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomHolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_pemilihan, viewGroup, false);
        AdapterListProgress_PemilihanvClient.CustomHolderView vh = new AdapterListProgress_PemilihanvClient.CustomHolderView(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolderView cvh, final int i) {
        final Lelang lelang = lelangs.get(i);
        cvh.txt_judulgarapan.setText(lelang.getLelang_judul());
        cvh.txt_harga.setText("Rp. " + lelang.getLelang_anggaran());
        final String tenggat_waktu = lelang.getLelang_tglmulai().substring(0, 11) + " ~ " + lelang.getLelang_tglselesai().substring(0, 11);
        cvh.txt_tenggatWaktu.setText(tenggat_waktu);
        final String alamat = getKotaProv(lelang.getLelang_kota());
        cvh.txt_alamat.setText(alamat);
        //Log.d("getkotaProv", getKotaProv(lelang.getLelang_kota()));
        cvh.btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.popup_progress);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                Button btn_OK = (Button) dialog.findViewById(R.id.btn_OK);
                Button btn_batal = (Button) dialog.findViewById(R.id.btn_batal);
                btn_OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        delete_lelang(lelang.getLelang_id(),i);
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
        });
        cvh.btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentPemilihanMitra();
                Bundle bundle = new Bundle();
                bundle.putInt("lelang_id", lelang.getLelang_id());
                fragment.setArguments(bundle);
                ((MainActivity) context)._loadFragment(fragment);
            }
        });
    }

    private void delete_lelang(int lelang_id, final int i) {
        Single<StringRespon> single = RetrofitClient.getInstance().getApi().
                lelang_delete(
                    secret_key,
                    lelang_id + "",
                    SharedPrefManager.getInstance(context).getUser().getUser_id()
                );
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(context);
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
                        lelangs.remove(i);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDoalog.dismiss();
                        e.printStackTrace();
                    }
                });
    }

    private String getKotaProv(int i) {
        String s = "";
        KotaHelper kotaHelper = new KotaHelper(context);
        kotaHelper.open();
        Kota kota = kotaHelper.getKotabyidKota(i);
        kotaHelper.close();
        ProvinsiHelper provinsiHelper = new ProvinsiHelper(context);
        provinsiHelper.open();
        Provinsi provinsi = provinsiHelper.getProvinsibyProvid(kota.getProvinsi_id());
        provinsiHelper.close();
        return kota.getNama() + "," + provinsi.getNama();
    }

    @Override
    public int getItemCount() {
        return lelangs.size();
    }

    public void addItem(ArrayList<Lelang> lelangs) {
        this.lelangs = lelangs;
        notifyDataSetChanged();
    }

    public class CustomHolderView extends RecyclerView.ViewHolder {
        private Button btn_batal, btn_status;
        TextView txt_judulgarapan, txt_alamat, txt_harga, txt_tenggatWaktu;

        public CustomHolderView(@NonNull View itemView) {
            super(itemView);
            txt_judulgarapan = itemView.findViewById(R.id.txt_judulgarapan);
            txt_alamat = itemView.findViewById(R.id.txt_alamat);
            txt_harga = itemView.findViewById(R.id.txt_harga);
            txt_tenggatWaktu = itemView.findViewById(R.id.txt_tenggatWaktu);
            btn_batal = itemView.findViewById(R.id.btn_batal);
            btn_status = itemView.findViewById(R.id.btn_status);
        }
    }
}
