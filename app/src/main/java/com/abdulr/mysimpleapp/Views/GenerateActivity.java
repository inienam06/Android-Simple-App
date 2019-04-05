package com.abdulr.mysimpleapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.abdulr.mysimpleapp.Configs.Helper;
import com.abdulr.mysimpleapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    private Helper helper;

    private AutoCompleteTextView atvValue;
    private  RadioGroup rgParent;
    private  RadioButton rbSelected;
    private  Button bGenerate;
    private  ImageView ivResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        init();
    }

    private void init() {
        helper = new Helper(this);

        atvValue = findViewById(R.id.atvValue);
        rgParent = findViewById(R.id.rgParent);
        bGenerate = findViewById(R.id.bGenerate);
        ivResult = findViewById(R.id.ivResult);

        setting();
    }

    private void setting() {
        helper.defaultToolbar(getSupportActionBar(), getIntent().getStringExtra("title"), true);

        bGenerate.setOnClickListener(this);
        ivResult.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bGenerate:
                int selectedId = rgParent.getCheckedRadioButtonId();
                rbSelected = findViewById(selectedId);

                String value = atvValue.getText().toString();

                if(value.trim().equals("")) {
                    helper.alert("Tuliskan sesuatu terlebih dahulu !", "error");
                    return;
                }

                if(selectedId != -1) {
                    String tipe = rbSelected.getText().toString();

                    generate(tipe, value);
                } else {
                    helper.alert("Silahkan pilih generate type", "error");
                }
                break;
        }
    }

    private void generate(String tipe, String value) {
        ivResult.setImageBitmap(helper.customQRCode(tipe, value));
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.ivResult:
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                helper.saveToGallery(ivResult, "My Sample App_" + timeStamp,  rbSelected.getText().toString());
                helper.alert("Image has been saved", "success");
                break;
        }
        return false;
    }
}
