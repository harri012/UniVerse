package com.example.universe.card;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.os.Bundle;

import com.example.universe.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        // Set title
        getSupportActionBar().setTitle("About Us");

        // Enable the back button on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }
        @Override
        public boolean onSupportNavigateUp() {
            // Handle the back button click
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
}