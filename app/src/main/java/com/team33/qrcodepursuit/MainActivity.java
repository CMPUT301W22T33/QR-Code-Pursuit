package com.team33.qrcodepursuit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu = findViewById(R.id.bottomnavigation);

        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment frag = null;

                switch (item.getItemId()) {
                    case R.id.bottomnavigation_menu_scan:
                        frag = new ScanFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();

                return false;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ScanFragment()).commit();
    }

}