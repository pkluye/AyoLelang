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

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Adapter.AdapterListRoom;
import com.ags.ayolelang.Models.RoomPesan;
import com.ags.ayolelang.Models.RoomRespon;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;


public class InboxFragment extends Fragment {

    RecyclerView rv_inbox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, null);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        rv_inbox=view.findViewById(R.id.reyclerview_listItem_message);
        rv_inbox.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadData();

        return view;
    }

    private void loadData() {
//        Log.d("value","test");
        Observable<RoomRespon> roomlist= RetrofitClient.getInstance().getApi().getRoom(
                secret_key,
                SharedPrefManager.getInstance(getActivity()).getUser().getUser_id()
        );
        roomlist.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .repeat()
                .subscribe(new Observer<RoomRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RoomRespon roomRespon) {
                        //Log.d("value",roomRespon.toString());
                        ArrayList<RoomPesan> roomPesans=roomRespon.getData();
                        AdapterListRoom adapterListRoom=new AdapterListRoom(getActivity());
                        adapterListRoom.addItem(roomPesans);
                        rv_inbox.setAdapter(adapterListRoom);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("error",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Kotak Masuk");
    }
}
