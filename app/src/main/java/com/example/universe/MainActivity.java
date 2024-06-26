package com.example.universe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.universe.calendar.CalendarMonthActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavController navController;

    FloatingActionButton calendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fix dex issues
        File dexOutputDir = getCodeCacheDir();
        dexOutputDir.setReadOnly();

        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //create bottom navigation Instance and the navigation menu
        bottomNavigationView = findViewById(R.id.bottomNavigation_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        bottomNavigationView.setBackground(null);

        //passing each fragment IDs for switch
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_tasks, R.id.navigation_finance, R.id.navigation_settings)
                .build();

        //setup bottom nav
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        //when pressing calendar button
        calendarButton = findViewById(R.id.fab_calendar);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start CalendarMonthActivity
                Intent intent = new Intent(view.getContext(), CalendarMonthActivity.class);

                // Start the activity
                view.getContext().startActivity(intent);
            }
        });


    }
}