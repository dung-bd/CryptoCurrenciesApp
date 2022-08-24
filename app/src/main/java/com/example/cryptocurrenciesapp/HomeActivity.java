package com.example.cryptocurrenciesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.cryptocurrenciesapp.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private AppBarConfiguration configuration;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navbar = findViewById(R.id.navbar);
        configuration = new AppBarConfiguration.Builder(R.id.market, R.id.portfolio, R.id.search, R.id.news).build();
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navFragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, configuration);
        NavigationUI.setupWithNavController(navbar, navController);
    }
}