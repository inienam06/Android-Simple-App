package com.abdulr.mysimpleapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.abdulr.mysimpleapp.Configs.Helper;
import com.abdulr.mysimpleapp.Configs.Session;
import com.abdulr.mysimpleapp.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Helper helper;
    private Session session;
    private TextView tvNama, tvEmail;
    private Button bEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
    }

    private void init() {
        helper = new Helper(this);
        session = new Session(this);

        tvNama = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        bEdit = findViewById(R.id.bEdit);

        setting();
    }

    private void setting() {
        helper.defaultToolbar(getSupportActionBar(), "", true);

        tvNama.setText(session.getName());
        tvEmail.setText(session.getEmail());

        bEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bEdit:
                startActivity(helper.goIntent(EditProfileActivity.class, null));
                break;
        }
    }
}
