package com.ags.ayolelang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ags.ayolelang.Adapter.AdapterListlelang_progress;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class ProgressFragment extends Fragment {

    RecyclerView rv_search_lelang;
    LinearLayout default_layout;
    StepView stepView;
    private int currentStep = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, null);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        default_layout=view.findViewById(R.id.default_layout);
//        rv_search_lelang = view.findViewById(R.id.rv_progress);
        rv_search_lelang.setLayoutManager(new LinearLayoutManager(getContext()));

        stepView = view.findViewById(R.id.step_view);

//        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentStep < stepView.getStepCount() - 1) {
//                    currentStep++;
//                    stepView.go(currentStep, true);
//                } else {
//                    stepView.done(true);
//                }
//            }
//        });
//
//        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentStep > 0) {
//                    currentStep--;
//                }
//                stepView.done(false);
//                stepView.go(currentStep, true);
//            }
//        });
        loadData();
        return view;
    }

    private void loadData() {
        LelangHelper lelangHelper = new LelangHelper(getContext());
        lelangHelper.open();
        ArrayList<Lelang> lelangs = lelangHelper.getLelangbyUser(SharedPrefManager.getInstance(getContext()).getUser().getUser_id());
        lelangHelper.close();
        //Log.d("size ", lelangs.size() + "");
        AdapterListlelang_progress adapterListlelang = new AdapterListlelang_progress(getContext());
        adapterListlelang.addItem(lelangs);
        if (adapterListlelang.getItemCount()>0){
            default_layout.setVisibility(View.GONE);
            rv_search_lelang.setVisibility(View.VISIBLE);
        }
        rv_search_lelang.setAdapter(adapterListlelang);
        rv_search_lelang.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                if (rv_search_lelang.getAdapter().getItemCount()<1){
                    default_layout.setVisibility(View.VISIBLE);
                    rv_search_lelang.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Progress Saya");
    }

}
