package com.example.madprojectassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.madprojectassignment.databinding.ActivityHomeBinding;
import com.example.madprojectassignment.ui.home.HomeFragment;
import com.example.madprojectassignment.ui.profile.ProfileFragment;
import com.example.madprojectassignment.ui.settings.SettingsFragment;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.DynamicColorsOptions;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Define your DynamicColorsOptions here
        DynamicColorsOptions dynamicColorsOptions = new DynamicColorsOptions.Builder().build();
        DynamicColors.applyToActivityIfAvailable(this, dynamicColorsOptions);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navigate(R.id.nav_home);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            navigate(item.getItemId());
            return true;
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            navigate(item.getItemId());
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

    }

    private void navigate(int menuItemId) {
        Fragment fragment = null;
        if (menuItemId == R.id.nav_home) {
            fragment = new HomeFragment();
        } else if (menuItemId == R.id.nav_profile) {
            fragment = new ProfileFragment();
        } else if (menuItemId == R.id.nav_settings) {
            fragment = new SettingsFragment();
        } else if (menuItemId == R.id.nav_about) {
            Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(intent);
            return;
        }

        if (fragment != null) {
            replaceFragment(fragment);
            // Directly update the BottomNavigationView
            binding.bottomNavigation.getMenu().findItem(menuItemId).setChecked(true);
        }
    }



    private void replaceFragment(Fragment fragment) {
        String fragmentTag = fragment.getClass().getSimpleName();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if (currentFragment == null || !currentFragment.isVisible()) {
            Log.d("HomeActivity", "Replacing fragment: " + fragmentTag);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, fragmentTag)
                    .commit();
        }
    }
}