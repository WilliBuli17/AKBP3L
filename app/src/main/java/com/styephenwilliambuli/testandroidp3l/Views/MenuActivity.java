package com.styephenwilliambuli.testandroidp3l.Views;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.styephenwilliambuli.testandroidp3l.R;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, new ViewsMenu()).commit();
        }
    }

    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navbarMenu:
                        selectedFragment = new ViewsMenu();
                        break;
                    case R.id.navbarChartSementara:
                        break;
                    case R.id.navbarPesananSaya:
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,
                        Objects.requireNonNull(selectedFragment)).commit();

                return true;
            };
}