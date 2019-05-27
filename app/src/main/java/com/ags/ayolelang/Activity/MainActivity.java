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
import android.widget.Toast;

import com.ags.ayolelang.API.RetrofitClient;
import com.ags.ayolelang.DBHelper.KotaHelper;
import com.ags.ayolelang.DBHelper.ProvinsiHelper;
import com.ags.ayolelang.Fragment.AccountFragment;
import com.ags.ayolelang.Fragment.FragmentSubKategori;
import com.ags.ayolelang.Fragment.GarapanFragment;
import com.ags.ayolelang.Fragment.InboxFragment;
import com.ags.ayolelang.Fragment.ProgressFragment;
import com.ags.ayolelang.Fragment.SearchFragment;
import com.ags.ayolelang.Models.FetchKotaProv;
import com.ags.ayolelang.Models.FetchKotaProvRespon;
import com.ags.ayolelang.Models.Kota;
import com.ags.ayolelang.Models.Provinsi;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ags.ayolelang.API.RetrofitClient.secret_key;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation=findViewById(R.id.navigation);

        fetchdata();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            bottomNavigation.setOnNavigationItemSelectedListener(this);
            if (savedInstanceState == null) {
                bottomNavigation.setSelectedItemId(R.id.navigation_garapan);
            }
            Intent intent=getIntent();
            if(intent!=null){
                if(intent.getBooleanExtra("tambah_keranjang",false)){
                    FragmentSubKategori fragmentSubKategori=new FragmentSubKategori();
                    Bundle bundle = new Bundle();
                    bundle.putInt("lelang_id",intent.getIntExtra("lelang_id",0));
                    bundle.putInt("id", intent.getIntExtra("id",0));
                    bundle.putString("tittle",intent.getStringExtra("tittle"));
                    fragmentSubKategori.setArguments(bundle);
                    loadFragment(fragmentSubKategori);
                }
            }

        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void fetchdata(){
        ProvinsiHelper provinsiHelper=new ProvinsiHelper(getApplicationContext());
        KotaHelper kotaHelper=new KotaHelper(getApplicationContext());

        kotaHelper.open();
        boolean kota_isempty=kotaHelper.isEmpty();
        kotaHelper.close();
        provinsiHelper.open();
        boolean prov_isempty=provinsiHelper.isEmpty();
        provinsiHelper.close();

        //data yg ga pernah berubah
        if (kota_isempty==true&&prov_isempty==true){
            loadDataKotaProvinsi();
            //Log.d("kepanggil??","yes");
        }else{
            //Log.d("kepanggil??","no "+kota_isempty+" "+prov_isempty);
        }

        //data yg sering berubah
    }

    private void loadDataKotaProvinsi() {
        Call<FetchKotaProvRespon> call= RetrofitClient.getInstance()
                .getApi().fetchKotaProv(
                        secret_key
                );

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(MainActivity.this);
        //progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<FetchKotaProvRespon>() {
            @Override
            public void onResponse(Call<FetchKotaProvRespon> call, Response<FetchKotaProvRespon> response) {
                progressDoalog.dismiss();
                if(response.isSuccessful()){
                    FetchKotaProvRespon fetchKotaProvRespon=response.body();
                    if (!fetchKotaProvRespon.isError()){
                        FetchKotaProv fetchKotaProv=response.body().getData();

                        ArrayList<Kota>kotas=fetchKotaProv.getKotas();
                        ArrayList<Provinsi>provinsis=fetchKotaProv.getProvinsis();

                        ProvinsiHelper provinsiHelper=new ProvinsiHelper(getApplicationContext());
                        KotaHelper kotaHelper=new KotaHelper(getApplicationContext());

                        for(Kota kota:kotas){
                            kotaHelper.open();
                            kotaHelper.insert(kota);
                            //Log.d("Kota insert",kota.toString());
                            kotaHelper.close();
                        }

                        for (Provinsi provinsi:provinsis){
                            provinsiHelper.open();
                            provinsiHelper.insert(provinsi);
                            provinsiHelper.close();
                        }
                    }
                }else{
                    Log.d("error ambil data",response.message());
                }
            }

            @Override
            public void onFailure(Call<FetchKotaProvRespon> call, Throwable t) {
                progressDoalog.dismiss();
                Log.d("failure",t.getMessage());
            }
        });
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
}
