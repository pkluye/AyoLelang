package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ags.ayolelang.API.PesanRespon;
import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.Adapter.AdapterItemPesan;
import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Models.InterfacePesan;
import com.ags.ayolelang.Models.Pesan;
import com.ags.ayolelang.Models.StringRespon;
import com.ags.ayolelang.Models.TanggalPesan;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;


public class MessageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private TextView sender_name;
    private ImageView btn_back;
    private EditText edittext_messagebox;
    private Button btn_sendMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        int room_id = intent.getIntExtra("room_id", 0);
        String userid2=intent.getStringExtra("userid2");
        recyclerView = findViewById(R.id.reyclerview_listItem_message);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        sender_name=findViewById(R.id.sender_name);
        btn_back=findViewById(R.id.btn_back);
        edittext_messagebox=findViewById(R.id.edittext_messagebox);
        btn_sendMessage=findViewById(R.id.btn_sendMessage);
        loadData(room_id,userid2);
    }

    private void loadData(int room_id, String userid2) {
        UserHelper userHelper=new UserHelper(this);
        userHelper.open();
        final User user=userHelper.getSingleUser(userid2);
        userHelper.close();
        sender_name.setText(user.getUser_nama());
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_isi=edittext_messagebox.getText().toString();
                if (text_isi.isEmpty()){
                    edittext_messagebox.requestFocus();
                    return;
                }
                KirimPesan(user.getUser_id());
            }
        });
        Observable<PesanRespon> observable = RetrofitClient.getInstance().getApi().getPesan(
                secret_key,
                room_id);
        observable.repeat()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PesanRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PesanRespon pesanRespon) {
                        ArrayList<Pesan> pesans = pesanRespon.getData();
                        ArrayList<InterfacePesan> iPesan=new ArrayList<>();
                        AdapterItemPesan adapterItemPesan = new AdapterItemPesan(MessageActivity.this);
                        for (Pesan pesan : pesans) {
                            int postion = pesans.indexOf(pesan);
                            if (iPesan.isEmpty()){
                                TanggalPesan tPesan=new TanggalPesan(pesan.getTanggal().substring(0,10));
                                iPesan.add(tPesan);
                                iPesan.add(pesan);
                            }else if(!pesan.getTanggal().substring(0,10).equalsIgnoreCase(pesans.get(postion-1).getTanggal().substring(0,10))){
                                TanggalPesan tPesan=new TanggalPesan(pesan.getTanggal().substring(0,10));
                                iPesan.add(tPesan);
                                iPesan.add(pesan);
                            }else{
                                iPesan.add(pesan);
                            }
                        }
                        adapterItemPesan.addItem(iPesan);
                        recyclerView.setAdapter(adapterItemPesan);
                        recyclerView.scrollToPosition(iPesan.size() - 1);
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

    private void KirimPesan(String user_id) {
        Single<StringRespon> responSingle=RetrofitClient.getInstance().getApi().sentPesan(
                secret_key,
                SharedPrefManager.getInstance(this).getUser().getUser_id(),
                user_id,
                edittext_messagebox.getText().toString()
        );
        responSingle.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        AdapterItemPesan adapterItemPesan= (AdapterItemPesan) recyclerView.getAdapter();
        adapterItemPesan.addPesan(new Pesan(edittext_messagebox.getText().toString(),SharedPrefManager.getInstance(this).getUser().getUser_id()));
        edittext_messagebox.setText("");
    }
}
