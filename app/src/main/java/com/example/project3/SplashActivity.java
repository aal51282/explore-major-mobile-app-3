package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

/**
 * SplashActivity is the first activity that appears when the app is launched.
 * It contains a button that takes the user to the MasterDetailActivity.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Set up the button to take the user to the MasterDetailActivity.
        Button enterButton = findViewById(R.id.buttonEnter);
        enterButton.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, MasterDetailActivity.class);
            startActivity(intent);

            // Prevent the user from navigating back to the SplashActivity.
            finish();
        });
    } // onCreate
} // SplashActivity