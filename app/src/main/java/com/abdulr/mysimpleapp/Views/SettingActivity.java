package com.abdulr.mysimpleapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.abdulr.mysimpleapp.Configs.Helper;
import com.abdulr.mysimpleapp.R;

public class SettingActivity extends AppCompatActivity {
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
    }

    private void init() {
        helper = new Helper(this);

        setting();
    }

    private void setting() {
        helper.defaultToolbar(getSupportActionBar(), "Setting", true);
    }
}
