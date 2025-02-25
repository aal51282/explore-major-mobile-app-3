package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button enterButton = findViewById(R.id.buttonEnter);
        enterButton.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, MasterDetailActivity.class);
            startActivity(intent);
            finish();
        });
    }
}