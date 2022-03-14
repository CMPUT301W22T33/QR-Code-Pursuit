package com.team33.qrcodepursuit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.team33.qrcodepursuit.activities.Login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView menu;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get user
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        // if not logged in, goto LoginActivity
        if (user == null)
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

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