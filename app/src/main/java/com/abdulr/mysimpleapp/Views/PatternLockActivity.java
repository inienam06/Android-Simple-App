package com.abdulr.mysimpleapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.abdulr.mysimpleapp.Configs.Helper;
import com.abdulr.mysimpleapp.R;
import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class PatternLockActivity extends AppCompatActivity {
    private Helper helper;
    private PatternLockView plView;
    private Button bClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_lock);

        init();
    }

    private void init() {
        helper = new Helper(this);

        plView = findViewById(R.id.plView);
        bClear = findViewById(R.id.bClear);

        setting();
    }

    private void setting() {
        helper.defaultToolbar(getSupportActionBar(), getIntent().getStringExtra("title"), true);

        plView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                helper.alert(PatternLockUtils.patternToString(plView, pattern), "success");
            }

            @Override
            public void onCleared() {
                Log.d(getClass().getName(), "Pattern has been cleared");
            }
        });

        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plView.clearPattern();
            }
        });
    }
}
