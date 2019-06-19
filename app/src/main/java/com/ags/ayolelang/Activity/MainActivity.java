package com.ags.ayolelang.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.DBHelper.HistoriTawaranHelper;
import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.DBHelper.KotaHelper;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.DBHelper.PekerjaanHelper;
import com.ags.ayolelang.DBHelper.ProvinsiHelper;
import com.ags.ayolelang.DBHelper.REQLelangHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.DBHelper.TawaranHelper;
import com.ags.ayolelang.DBHelper.UserHelper;
import com.ags.ayolelang.Fragment.AccountFragment;
import com.ags.ayolelang.Fragment.FragmentSubKategori;
import com.ags.ayolelang.Fragment.GarapanFragment;
import com.ags.ayolelang.Fragment.InboxFragment;
import com.ags.ayolelang.Fragment.ProgressFragment;
import com.ags.ayolelang.Fragment.SearchFragment;
import com.ags.ayolelang.Models.FetchDB;
import com.ags.ayolelang.Models.FetchDBRespon;
import com.ags.ayolelang.Models.Kategori;
import com.ags.ayolelang.Models.Kota;
import com.ags.ayolelang.Models.Lelang;
import com.ags.ayolelang.Models.Pekerjaan;
import com.ags.ayolelang.Models.Provinsi;
import com.ags.ayolelang.Models.Tawaran;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;
import com.ags.ayolelang.BottomNavigationHelper;

import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigation;
    KotaHelper kotaHelper = new KotaHelper(this);
    ProvinsiHelper provinsiHelper = new ProvinsiHelper(this);
    KategoriHelper kategoriHelper = new KategoriHelper(this);
    LelangHelper lelangHelper = new LelangHelper(this);
    PekerjaanHelper pekerjaanHelper = new PekerjaanHelper(this);
    Disposable disposable;
    private UserHelper userHelper = new UserHelper(this);
    private TawaranHelper tawaranHelper=new TawaranHelper(this);
    private HistoriTawaranHelper historiTawaranHelper=new HistoriTawaranHelper(this);

    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.navigation);
        BottomNavigationHelper.disableShiftMode(bottomNavigation);
        progressDoalog = new ProgressDialog(this);

        fetchdata();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            bottomNavigation.setOnNavigationItemSelectedListener(this);
            if (savedInstanceState == null) {
                bottomNavigation.setSelectedItemId(R.id.navigation_garapan);
            }
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.getBooleanExtra("tambah_pekerjaan", false)) {
                    FragmentSubKategori fragmentSubKategori = new FragmentSubKategori();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", intent.getIntExtra("id", 0));
                    bundle.putString("tittle", intent.getStringExtra("tittle"));
                    fragmentSubKategori.setArguments(bundle);
                    loadFragment(fragmentSubKategori);
                } else {
                    REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(this);
                    REQLelangHelper reqLelangHelper = new REQLelangHelper(this);

                    reqLelangHelper.open();
                    reqLelangHelper.truncate();
                    reqLelangHelper.close();

                    reqPekerjaanHelper.open();
                    reqPekerjaanHelper.truncate();
                    reqPekerjaanHelper.close();
                }
            } else {
                REQPekerjaanHelper reqPekerjaanHelper = new REQPekerjaanHelper(this);
                REQLelangHelper reqLelangHelper = new REQLelangHelper(this);

                reqLelangHelper.open();
                reqLelangHelper.truncate();
                reqLelangHelper.close();

                reqPekerjaanHelper.open();
                reqPekerjaanHelper.truncate();
                reqPekerjaanHelper.close();
            }

        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void fetchdata() {
        disposable = Observable.interval(0, 10,
                TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        kategoriHelper.open();
                        final boolean kategori_isempty = kategoriHelper.isEmpty();
                        kategoriHelper.close();

                        kotaHelper.open();
                        final boolean kota_isempty = kotaHelper.isEmpty();
                        kotaHelper.close();

                        provinsiHelper.open();
                        final boolean provinsi_isempty = provinsiHelper.isEmpty();
                        provinsiHelper.close();



                        if (kategori_isempty == true && kota_isempty == true &&
                                provinsi_isempty == true) {
                            loadToServer(0);
                        } else {
                            loadToServer(1);
                        }
                    }
                });
    }

    private void loadToServer(final int i) {
        String[] token = SharedPrefManager.getInstance(getApplicationContext()).getToken();
        Call<FetchDBRespon> call = RetrofitClient.getInstance()
                .getApi().fetchDB(
                        secret_key,
                        token[0],
                        token[1],
                        token[2],
                        token[3],
                        token[4],
                        token[5],
                        token[6]
                );
        //Log.d("token",token[0]+" "+token[1]+" "+token[2]+" ");
        // Set up progress before call
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (i == 0) {
            // show it
            progressDoalog.show();
        }

        call.enqueue(new Callback<FetchDBRespon>() {
            @Override
            public void onResponse(Call<FetchDBRespon> call, Response<FetchDBRespon> response) {
                if (i == 0) {
                    progressDoalog.dismiss();
                }
                if (response.isSuccessful()) {
                    FetchDBRespon fetchDBRespon = response.body();
                    if (!fetchDBRespon.isError()) {
                        FetchDB fetchDB = fetchDBRespon.getData();
                        Log.d("Update data", "yes " + fetchDBRespon.getMessage());
                        String[] newtoken = SharedPrefManager.getInstance(getApplicationContext()).getToken();
                        if (fetchDBRespon.getMessage().contains("3")) {
                            ArrayList<Kota> kotas = fetchDB.getKotas();
                            insert_kota(kotas);
                            newtoken[2] = fetchDB.getToken_kota();
                            //Log.d("token kota",newtoken[2]+"");
                        }
                        if (fetchDBRespon.getMessage().contains("2")) {
                            ArrayList<Provinsi> provinsis = fetchDB.getProvinsis();
                            insert_provinsi(provinsis);
                            newtoken[1] = fetchDB.getToken_provinsi();
                        }
                        if (fetchDBRespon.getMessage().contains("1")) {
                            ArrayList<Kategori> kategoris = fetchDB.getKategoris();
                            insert_kategori(kategoris);
                            newtoken[0] = fetchDB.getToken_kategori();
                        }

                        if (fetchDBRespon.getMessage().contains("4")) {
                            ArrayList<Lelang> lelangs = fetchDB.getLelangs();
                            insert_lelang(lelangs);
                            newtoken[3] = fetchDB.getToken_lelang();
                        }

                        if (fetchDBRespon.getMessage().contains("5")) {
                            ArrayList<Pekerjaan> pekerjaans = fetchDB.getPekerjaans();
                            insert_pekerjaan(pekerjaans);
                            newtoken[4] = fetchDB.getToken_pekerjaan();
                        }

                        if (fetchDBRespon.getMessage().contains("6")) {
                            ArrayList<User> users = fetchDB.getUsers();
                            insert_user(users);
                            newtoken[5] = fetchDB.getToken_user();
                        }

                        if (fetchDBRespon.getMessage().contains("7")) {
                            ArrayList<Tawaran> tawarans = fetchDB.getTawarans();
                            insert_tawaran(tawarans);

                            ArrayList<Tawaran> historitawaran=fetchDB.getHistoritawarans();
                            insert_historitawaran(historitawaran);

                            newtoken[6] = fetchDB.getToken_tawaran();
                        }

                        SharedPrefManager.getInstance(getApplicationContext()).saveToken(newtoken);
                    } else {
                        Log.d("Update data", "no " + fetchDBRespon.getMessage());
                    }
                } else {
                    Log.d("error ambil data", response.message());
                }
            }

            @Override
            public void onFailure(Call<FetchDBRespon> call, Throwable t) {
                if (i == 0) {
                    progressDoalog.dismiss();
                }
                Log.d("failure", t.getMessage());
            }
        });
    }

//    public void longLog(String str) {
//        if (str.length() > 4000) {
//            Log.e("HTML ERROR", str.substring(0, 4000));
//            longLog(str.substring(4000));
//        } else
//            Log.e("HTML ERROR", str);
//    }

    private void insert_historitawaran(ArrayList<Tawaran> historitawaran) {
        historiTawaranHelper.open();
        historiTawaranHelper.truncate();
        historiTawaranHelper.bulk_insert(historitawaran);
        historiTawaranHelper.close();
    }

    private void insert_tawaran(ArrayList<Tawaran> tawarans) {
        tawaranHelper.open();
        tawaranHelper.truncate();
        tawaranHelper.bulk_insert(tawarans);
        tawaranHelper.close();
    }

    private void insert_pekerjaan(ArrayList<Pekerjaan> pekerjaans) {
        pekerjaanHelper.open();
        pekerjaanHelper.truncate();
        pekerjaanHelper.bulk_insert(pekerjaans);
        pekerjaanHelper.close();
    }

    private void insert_lelang(ArrayList<Lelang> lelangs) {
        lelangHelper.open();
        lelangHelper.truncate();
        lelangHelper.bulk_insert(lelangs);
        lelangHelper.close();
    }

    private void insert_kota(ArrayList<Kota> kotas) {
        kotaHelper.open();
        kotaHelper.truncate();
        kotaHelper.bulk_insert(kotas);
        kotaHelper.close();
    }

    private void insert_provinsi(ArrayList<Provinsi> provinsis) {
        provinsiHelper.open();
        provinsiHelper.truncate();
        provinsiHelper.bulk_insert(provinsis);
        provinsiHelper.close();
    }

    private void insert_kategori(ArrayList<Kategori> kategoris) {
        kategoriHelper.open();
        kategoriHelper.truncate();
        kategoriHelper.bulk_insert(kategoris);
        kategoriHelper.close();
    }

    private void insert_user(ArrayList<User> users) {
        userHelper.open();
        userHelper.truncate();
        userHelper.bulk_insert(users);
        userHelper.close();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_inbox:
                fragment = new InboxFragment();
                break;

            case R.id.navigation_progress:
                fragment = new ProgressFragment();
                break;

            case R.id.navigation_garapan:
                fragment = new GarapanFragment();
                break;

            case R.id.navigation_search:
                fragment = new SearchFragment();
                break;

            case R.id.navigation_account:
                fragment = new AccountFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void _loadFragment(Fragment fragment) {
        if (fragment != null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("state", "onresume");
        if (disposable.isDisposed()) fetchdata();
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressDoalog.dismiss();
        Log.d("state", "onpause");
        if (!disposable.isDisposed()) disposable.dispose();
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressDoalog.dismiss();
        Log.d("state", "onstop");
        if (!disposable.isDisposed()) disposable.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDoalog.dismiss();
        Log.d("state", "ondestroy");
        if (!disposable.isDisposed()) disposable.dispose();
    }
}
