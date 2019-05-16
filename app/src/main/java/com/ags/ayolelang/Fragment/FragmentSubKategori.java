package com.ags.ayolelang.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Adapter.AdapterSubKategori;
import com.ags.ayolelang.Models.KategoriResponArray;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class FragmentSubKategori extends Fragment {

    private View v;
    private TextView txt_no_item, txt_subTittle, txt_subPenjelasan;
    private RecyclerView rv_subFragment;
    private ImageButton btn_back;
    private int mode = 1, kategori_id = -1;
    private String tittle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_subkategori, container, false);
        rv_subFragment = v.findViewById(R.id.rv_subFragment);
        rv_subFragment.setLayoutManager(new LinearLayoutManager(getContext()));
        btn_back = v.findViewById(R.id.btn_Back);
        txt_subTittle = v.findViewById(R.id.txt_subTittle);
        txt_subPenjelasan = v.findViewById(R.id.txt_subPenjelasan);
        txt_no_item = v.findViewById(R.id.txt_noItem);

        //transfer data
        Bundle bundle = getArguments();
        mode = bundle.getInt("mode");
        kategori_id = bundle.getInt("id");
        tittle = bundle.getString("tittle");
        String kategori_nama = bundle.getString("kategori_nama");

        //event
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //tampilkan data
        txt_subTittle.setText(tittle);
        if(kategori_nama!=null){
            txt_subPenjelasan.setText(kategori_nama);
        }
        loadData();
        return v;
    }

    void loadData() {
        if (mode == 1) {
            loadSubParentKategori();
        } else {
            loadKategori();
        }
    }

    private void loadKategori() {
        Call<KategoriResponArray> call = RetrofitClient.getInstance().getApi()
                .kategori_getDataKategori(secret_key, kategori_id);

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        //progressDoalog.setMax(100);
        //progressDoalog.setMessage("Loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<KategoriResponArray>() {
            @Override
            public void onResponse(Call<KategoriResponArray> call, Response<KategoriResponArray> response) {
                progressDoalog.dismiss();
                KategoriResponArray kategoriResponArray = response.body();
                if (!kategoriResponArray.isError()) {
                    ArrayList<Kategori> kategoris = kategoriResponArray.getData();
                    isi_rv(kategoris);
                    txt_no_item.setVisibility(v.INVISIBLE);
                } else {
                    txt_no_item.setVisibility(v.VISIBLE);
                    rv_subFragment.setVisibility(v.INVISIBLE);
                    Log.d("error json", kategoriResponArray.getMessage());
                }
            }

            @Override
            public void onFailure(Call<KategoriResponArray> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }

    private void loadSubParentKategori() {
        Call<KategoriResponArray> call = RetrofitClient
                .getInstance().getApi().kategori_getDataSubParentKategori(secret_key, kategori_id);

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<KategoriResponArray>() {
            @Override
            public void onResponse(Call<KategoriResponArray> call, Response<KategoriResponArray> response) {
                progressDoalog.dismiss();
                KategoriResponArray kategoriResponArray = response.body();
                if (!kategoriResponArray.isError()) {
                    ArrayList<Kategori> kategoris = kategoriResponArray.getData();
                    isi_rv(kategoris);
                    txt_no_item.setVisibility(v.INVISIBLE);
                } else {
                    txt_no_item.setVisibility(v.VISIBLE);
                    rv_subFragment.setVisibility(v.INVISIBLE);
                    Log.d("error json", kategoriResponArray.getMessage());
                }
            }

            @Override
            public void onFailure(Call<KategoriResponArray> call, Throwable t) {
                Log.d("error", t.getMessage());
                progressDoalog.dismiss();
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void isi_rv(ArrayList<Kategori> kategoris) {
        AdapterSubKategori adapterSubKategori = new AdapterSubKategori(getContext());
        adapterSubKategori.addItem(kategoris);
        adapterSubKategori.setTittle(tittle);
        rv_subFragment.setAdapter(adapterSubKategori);
    }

}
