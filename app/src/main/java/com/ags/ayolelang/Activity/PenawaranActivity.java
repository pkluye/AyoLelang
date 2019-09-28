package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Adapter.AdapterItemTawaran;
import com.ags.ayolelang.DBHelper.PekerjaanHelper;
import com.ags.ayolelang.DBHelper.TWPekerjaanHelper;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.Models.TWPekerjaan;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class PenawaranActivity extends AppCompatActivity {

    private RecyclerView rv_item_tawaran;
    public static TextView txt_totalHarga;
    private int lelang_id;
    private boolean edit;
    private int tawaran_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penawaran);

        txt_totalHarga = findViewById(R.id.txt_totalHarga);
        rv_item_tawaran = findViewById(R.id.rv_item_tawaran);
        rv_item_tawaran.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        edit = intent.getBooleanExtra("edit", false);
        lelang_id = intent.getIntExtra("lelang_id", 0);
        tawaran_id = intent.getIntExtra("tawaran_id", 0);
        PekerjaanHelper pekerjaanHelper = new PekerjaanHelper(this);
        pekerjaanHelper.open();
        ArrayList<Pekerjaan> pekerjaans_array = pekerjaanHelper.getPekerjaan(lelang_id);
        ArrayList<Pekerjaan> pekerjaans = new ArrayList<>();
        if (edit) {
            TWPekerjaanHelper twPekerjaanHelper = new TWPekerjaanHelper(this);
            for (Pekerjaan pekerjaan : pekerjaans_array) {
                twPekerjaanHelper.open();
                long anggaran=0;
                anggaran=twPekerjaanHelper.getSingleTWPekerjaanby(tawaran_id+"", pekerjaan.getPekerjaan_id()+"").getTwpekerjaan_anggaran();
                twPekerjaanHelper.close();
                pekerjaan.setHargaTawaran(anggaran);
                pekerjaans.add(pekerjaan);
            }
        } else {
            for (Pekerjaan pekerjaan : pekerjaans_array) {
                pekerjaan.setHargaTawaran(pekerjaan.getPekerjaan_harga());
                pekerjaans.add(pekerjaan);
            }
        }
        AdapterItemTawaran adapterItemTawaran = new AdapterItemTawaran(this);
        adapterItemTawaran.addItem(pekerjaans);
        txt_totalHarga.setText("Rp. " + currencyFormat(adapterItemTawaran.gethargaTawaran() + ""));
        rv_item_tawaran.setAdapter(adapterItemTawaran);
    }

    public void cancel(View view) {
        finish();
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

    public void tawar(View view) {
        JSONObject tawaran = new JSONObject();
        try {
            tawaran.put("tawaran_userid", SharedPrefManager.getInstance(this).getUser().getUser_id());
            tawaran.put("tawaran_lelangid", lelang_id);
            if (rv_item_tawaran.getAdapter() != null) {
                tawaran.put("tawaran_anggaran", ((AdapterItemTawaran) rv_item_tawaran.getAdapter()).gethargaTawaran());
            } else {
                tawaran.put("tawaran_anggaran", 0);
            }
            if (edit) {
                tawaran.put("tawaran_id", tawaran_id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray twpekerjaans = new JSONArray();
        if (rv_item_tawaran.getAdapter() != null) {
            TWPekerjaanHelper twPekerjaanHelper = new TWPekerjaanHelper(this);
            for (Pekerjaan pekerjaan : ((AdapterItemTawaran) rv_item_tawaran.getAdapter()).getPekerjaanArrayList()) {
                JSONObject twpekerjaan = new JSONObject();
                try {
                    if (edit){
                        twPekerjaanHelper.open();
                        int twpid=twPekerjaanHelper.getSingleTWPekerjaanby(tawaran_id+"", pekerjaan.getPekerjaan_id()+"").getTwpekerjaan_id();
                        twPekerjaanHelper.close();
                        twpekerjaan.put("twpekerjaan_id",twpid);
                    }
                    twpekerjaan.put("twpekerjaan_pekerjaanid", pekerjaan.getPekerjaan_id());
                    twpekerjaan.put("twpekerjaan_anggaran", pekerjaan.getHargaTawaran());
                    twpekerjaans.put(twpekerjaan);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        JSONObject data_req = new JSONObject();
        try {
            data_req.put("secret_key", secret_key);
            data_req.put("tawaran", tawaran);
            data_req.put("twpekerjaan", twpekerjaans);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("print json", data_req.toString());

        Single<StringRespon> stringResponSingle;
        if (edit) {
            stringResponSingle = RetrofitClient.getInstance().getApi().tawaran_editv2(data_req.toString());
        }else{
            stringResponSingle = RetrofitClient.getInstance().getApi().tawaran_buatv2(data_req.toString());
        }
        stringResponSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<StringRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(StringRespon stringRespon) {
                        Toast.makeText(PenawaranActivity.this, stringRespon.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getMessage());
                    }
                });
    }
}
