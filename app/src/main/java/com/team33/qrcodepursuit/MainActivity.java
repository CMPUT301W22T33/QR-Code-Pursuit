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

/**
 * main activity, hosts current fragment and persistent menu bar
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView menu;
    private FirebaseAuth auth;
    private FirebaseUser user;

    /**
     * on creation, set up menu bar and initial fragment
     * @param savedInstanceState
     */
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

        // switch between fragments with bottom navigation menu
        menu = findViewById(R.id.bottomnavigation);
        menu.setOnItemSelectedListener(item -> {
            Fragment frag;
            switch (item.getItemId()) {
                case R.id.bottomnavigation_menu_home:
                    frag = new ScanFragment();
                    break;
                case R.id.bottomnavigation_menu_map:
                    // todo: move to MapFragment
                    return false;
                case R.id.bottomnavigation_menu_scoreboard:
                    frag = new ScoreBoardFragment();
                    break;
                case R.id.bottomnavigation_menu_account:
                    // todo: move to AccountFragment
                    return false;
                default: return false;
            }
            // switch to fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, frag)
                    .commit();
            // todo: update menu to show current item selected
            return false;
        });
        // start in home fragment by default
        menu.setSelectedItemId(R.id.bottomnavigation_menu_home);
        getSupportFragmentManager()
                .beginTransaction().
                replace(R.id.container, new ScanFragment())
                .commit();
    }
}