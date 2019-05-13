package com.ags.ayolelang.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.ags.ayolelang.Fragment.AccountFragment;
import com.ags.ayolelang.Fragment.GarapanFragment;
import com.ags.ayolelang.Fragment.InboxFragment;
import com.ags.ayolelang.Fragment.ProgressFragment;
import com.ags.ayolelang.Fragment.SearchFragment;
import com.ags.ayolelang.R;
import com.ags.ayolelang.Storage.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation=findViewById(R.id.navigation);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {

            bottomNavigation.setOnNavigationItemSelectedListener(this);
            if (savedInstanceState == null) {
                bottomNavigation.setSelectedItemId(R.id.navigation_garapan);
            }
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

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
