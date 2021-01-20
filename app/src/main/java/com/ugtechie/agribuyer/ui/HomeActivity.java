package com.ugtechie.agribuyer.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.fragments.AccountFragment;
import com.ugtechie.agribuyer.fragments.CartFragment;
import com.ugtechie.agribuyer.fragments.HomeFragment;
import com.ugtechie.agribuyer.fragments.ProductsFragment;
import com.ugtechie.agribuyer.fragments.WalletFragment;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.nav_products:
                            selectedFragment = new ProductsFragment();
                            break;

                        case R.id.nav_cart:
                            selectedFragment = new CartFragment();
                            break;

                        case R.id.nav_wallet:
                            selectedFragment = new WalletFragment();
                            break;

                        case R.id.nav_account:
                            selectedFragment = new AccountFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }
}
