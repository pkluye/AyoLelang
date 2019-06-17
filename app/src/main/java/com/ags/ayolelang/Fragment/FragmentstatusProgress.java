package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ags.ayolelang.Adapter.AdapterListMitra_progress;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.DBHelper.TawaranHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Tawaran;
import com.ags.ayolelang.R;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class FragmentstatusProgress extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    StepView stepView;
    private int currentStep = 0;
    private TextView txt_idGarapan;
    private TextView txt_biayaGarapan;
    private TextView txt_totalMitra;
    private RecyclerView rv_mitra;
    private int lelang_id;
    private SwipeRefreshLayout srl_status_progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_status_progress, null);

        stepView = view.findViewById(R.id.step_view);
        txt_idGarapan = view.findViewById(R.id.txt_idGarapan);
        txt_biayaGarapan = view.findViewById(R.id.txt_biayaGarapan);
        txt_totalMitra = view.findViewById(R.id.txt_totalMitra);
        rv_mitra=view.findViewById(R.id.rv_mitra);
        Bundle bundle = getArguments();
        lelang_id=bundle.getInt("lelang_id");
        srl_status_progress=view.findViewById(R.id.srl_status_progress);
        srl_status_progress.setOnRefreshListener(this);
        loadData();
        return view;
    }

    private void loadData() {
        LelangHelper lelangHelper = new LelangHelper(getActivity());
        lelangHelper.open();
        Lelang lelang = lelangHelper.getLelang(lelang_id);
        lelangHelper.close();
        txt_idGarapan.setText(lelang.getLelang_id() + "");
        txt_biayaGarapan.setText("Rp. " + lelang.getLelang_anggaran());

        TawaranHelper tawaranHelper = new TawaranHelper(getActivity());
        tawaranHelper.open();
        ArrayList<Tawaran> tawarans = tawaranHelper.getlisttawaran(lelang.getLelang_id());
        tawaranHelper.close();
        txt_totalMitra.setText(tawarans.size() + "");
        int status_lelang=11;
        //sementara (10-14)
        // 10 tidak ada yg selese, 10 untuk lelang garapan dst
        // 11 done step 1(lelang garapan done)
        // 12 done step 2
        // dst
        currentStep=status_lelang%10;

        stepView.go(currentStep, true);
        // perlu status finish, buat step done

        AdapterListMitra_progress adapterListMitra_progress=new AdapterListMitra_progress(getActivity());
        adapterListMitra_progress.addItem(tawarans);

        rv_mitra.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_mitra.setAdapter(adapterListMitra_progress);
        srl_status_progress.setRefreshing(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Status");
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
