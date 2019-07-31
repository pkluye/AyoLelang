package com.ags.ayolelang.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.DBHelper.HistoriTawaranHelper;
import com.ags.ayolelang.DBHelper.KategoriHelper;
import com.ags.ayolelang.DBHelper.KotaHelper;
import com.ags.ayolelang.DBHelper.LelangHelper;
import com.ags.ayolelang.DBHelper.PekerjaanHelper;
import com.ags.ayolelang.DBHelper.ProvinsiHelper;
import com.ags.ayolelang.DBHelper.REQLelangHelper;
import com.ags.ayolelang.DBHelper.REQPekerjaanHelper;
import com.ags.ayolelang.DBHelper.SpecBarangHelper;
import com.ags.ayolelang.DBHelper.TWPekerjaanHelper;
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
import com.ags.ayolelang.Models.SpecBarang;
import com.ags.ayolelang.Models.TWPekerjaan;
import com.ags.ayolelang.Models.Tawaran;
import com.ags.ayolelang.Models.User;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;
import com.ags.ayolelang.BottomNavigationHelper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    private TawaranHelper tawaranHelper = new TawaranHelper(this);
    private HistoriTawaranHelper historiTawaranHelper = new HistoriTawaranHelper(this);
    private SpecBarangHelper specBarangHelper = new SpecBarangHelper(this);
    private TWPekerjaanHelper twPekerjaanHelper = new TWPekerjaanHelper(this);

    ProgressDialog progressDoalog;
    boolean doubleBackToExitPressedOnce = false;

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
            Intent intent = new Intent(this, Splash.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void fetchdata() {
        final String[] token = SharedPrefManager.getInstance(getApplicationContext()).getToken();
        final Observable<FetchDBRespon> observable = RetrofitClient.getInstance().getApi().fetchDB(
                secret_key,
                token[0],
                token[1],
                token[2],
                token[3],
                token[4],
                token[5],
                token[6],
                token[7]);
        for (int i = 0; i < token.length; i++) {
            Log.d("token " + i, token[i] + "");
        }
        progressDoalog.setMessage("Loading....");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        observable.repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FetchDBRespon>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(FetchDBRespon fetchDBRespon) {
                        String[] newtoken = SharedPrefManager.getInstance(getApplicationContext()).getToken();
                        if (!fetchDBRespon.isError()) {
                            Log.d("Update data", "yes " + fetchDBRespon.getMessage());
                            FetchDB fetchDB = fetchDBRespon.getData();

                            if (fetchDBRespon.getMessage().contains("1")) {
                                ArrayList<Kategori> kategoris = fetchDB.getKategoris();
                                insert_kategori(kategoris);
                                newtoken[0] = fetchDB.getToken_kategori();
                            }

                            if (fetchDBRespon.getMessage().contains("2")) {
                                ArrayList<Provinsi> provinsis = fetchDB.getProvinsis();
                                insert_provinsi(provinsis);
                                newtoken[1] = fetchDB.getToken_provinsi();
                            }

                            if (fetchDBRespon.getMessage().contains("3")) {
                                ArrayList<Kota> kotas = fetchDB.getKotas();
                                insert_kota(kotas);
                                newtoken[2] = fetchDB.getToken_kota();
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

                                ArrayList<Tawaran> historitawaran = fetchDB.getHistoritawarans();
                                insert_historitawaran(historitawaran);

                                ArrayList<TWPekerjaan> twPekerjaans = fetchDB.getTwpekerjaans();
                                insert_twpekerjaan(twPekerjaans);

                                newtoken[6] = fetchDB.getToken_tawaran();
                            }

                            if (fetchDBRespon.getMessage().contains("8")) {
                                ArrayList<SpecBarang> specBarangs = fetchDB.getSpecbarangs();
                                insert_specBarang(specBarangs);
                                newtoken[7] = fetchDB.getToken_specbarang();
                            }

                            for(User user : fetchDB.getUsers()){
                                if (user.getUser_id().equalsIgnoreCase(SharedPrefManager.getInstance(getApplicationContext()).getUser().getUser_id())){
                                    SharedPrefManager.getInstance(MainActivity.this).saveUser(new User(
                                            user.getUser_id(),
                                            user.getUser_nama(),
                                            user.getUser_email(),
                                            user.getUser_telpon(),
                                            user.getUser_alamat(),
                                            user.getUser_imgurl(),
                                            user.getUser_skill(),
                                            user.getUser_tentang()
                                    ));
                                }
                            }

                            //check empty db
                            kategoriHelper.open();
                            boolean kategori_isempty = kategoriHelper.isEmpty();
                            kategoriHelper.close();

                            kotaHelper.open();
                            boolean kota_isempty = kotaHelper.isEmpty();
                            kotaHelper.close();

                            provinsiHelper.open();
                            boolean provinsi_isempty = provinsiHelper.isEmpty();
                            provinsiHelper.close();

                            specBarangHelper.open();
                            boolean specbarang_isempty = specBarangHelper.isempty();
                            specBarangHelper.close();

                            userHelper.open();
                            boolean user_isempty = userHelper.isEmpty();
                            userHelper.close();

                            if (kategori_isempty) {
                                newtoken[0] = null;
                            }

                            if (provinsi_isempty) {
                                newtoken[1] = null;
                            }

                            if (kota_isempty) {
                                newtoken[2] = null;
                            }

                            if (user_isempty) {
                                newtoken[6] = null;
                            }

                            if (specbarang_isempty) {
                                newtoken[7] = null;
                            }

                            for (int i = 0; i < token.length; i++) {
                                Log.d("token " + i, newtoken[i] + "");
                            }

                            SharedPrefManager.getInstance(getApplicationContext()).saveToken(newtoken);
                            disposable.dispose();
                            fetchdata();
                        } else {
                            progressDoalog.dismiss();
                            Log.d("Update data", "no " + fetchDBRespon.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        progressDoalog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        progressDoalog.dismiss();
                    }
                });
    }

    private void insert_twpekerjaan(ArrayList<TWPekerjaan> twPekerjaans) {
        twPekerjaanHelper.open();
        twPekerjaanHelper.truncate();
        twPekerjaanHelper.bulk_insert(twPekerjaans);
        twPekerjaanHelper.close();
    }


    private void insert_specBarang(ArrayList<SpecBarang> specBarangs) {
        specBarangHelper.open();
        specBarangHelper.truncate();
        specBarangHelper.bulk_insert(specBarangs);
        specBarangHelper.close();
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
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
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

    public void loadFragment_(Fragment fragment) {
        if (fragment != null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        if (fragment.getClass().isInstance(new GarapanFragment())) {
            bottomNavigation.setSelectedItemId(R.id.navigation_garapan);
        } else if (fragment.getClass().isInstance(new InboxFragment())) {
            bottomNavigation.setSelectedItemId(R.id.navigation_inbox);
        } else if (fragment.getClass().isInstance(new ProgressFragment())) {
            bottomNavigation.setSelectedItemId(R.id.navigation_progress);
        } else if (fragment.getClass().isInstance(new AccountFragment())) {
            bottomNavigation.setSelectedItemId(R.id.navigation_account);
        } else {
            bottomNavigation.setSelectedItemId(R.id.navigation_search);
        }
    }


    @Override
    public void onBackPressed() {
        FragmentManager fm=getSupportFragmentManager();
        if (fm.getBackStackEntryCount()==0){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;

            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (disposable.isDisposed()) fetchdata();
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressDoalog.dismiss();
        if (!disposable.isDisposed()) disposable.dispose();
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressDoalog.dismiss();
        if (!disposable.isDisposed()) disposable.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDoalog.dismiss();
        if (!disposable.isDisposed()) disposable.dispose();
    }
}
