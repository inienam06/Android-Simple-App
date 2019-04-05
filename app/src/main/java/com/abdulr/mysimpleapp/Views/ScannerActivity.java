package com.abdulr.mysimpleapp.Views;

import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.abdulr.mysimpleapp.Configs.Helper;
import com.abdulr.mysimpleapp.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private Helper helper;
    private ZXingScannerView scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        init();
    }

    private void init() {
        helper = new Helper(this);

        scanner = findViewById(R.id.scanner);
        setting();
    }

    private void setting() {
        helper.statusBarTransparent();

    }

    @Override
    public void handleResult(Result value) {
        String result = value.getText();

        helper.messageYesNo("Hasil Scan : " + result, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onResume();
            }
        }, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanner.setResultHandler(this);
        scanner.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanner.stopCamera();
    }
}
