package com.abdulr.mysimpleapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.abdulr.mysimpleapp.Configs.Helper;
import com.abdulr.mysimpleapp.R;

public class RatingBarActivity extends AppCompatActivity {
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);

        init();
    }

    private void init() {
        helper = new Helper(this);

        setting();
    }

    private void setting() {
        helper.defaultToolbar(getSupportActionBar(), getIntent().getStringExtra("title"), true);
    }
}
