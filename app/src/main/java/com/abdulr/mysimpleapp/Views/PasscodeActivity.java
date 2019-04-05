package com.abdulr.mysimpleapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.abdulr.mysimpleapp.Configs.Helper;
import com.abdulr.mysimpleapp.R;
import com.hanks.passcodeview.PasscodeView;

public class PasscodeActivity extends AppCompatActivity {
    private Helper helper;
    private PasscodeView passcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        init();
    }

    private void init() {
        helper = new Helper(this);

        passcode = findViewById(R.id.passcode);

        setting();
    }

    private void setting() {
        helper.statusBarTransparent();

        passcode.setLocalPasscode("181199").setListener(new PasscodeView.PasscodeViewListener() {
            @Override
            public void onFail() {
                helper.alert("Wrong passcode !", "error");
            }

            @Override
            public void onSuccess(String number) {
                helper.alert("Passcode has been updated", "success");
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
