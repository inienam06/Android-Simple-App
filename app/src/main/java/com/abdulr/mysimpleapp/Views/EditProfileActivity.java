package com.abdulr.mysimpleapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.abdulr.mysimpleapp.Configs.Helper;
import com.abdulr.mysimpleapp.Configs.Session;
import com.abdulr.mysimpleapp.R;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Helper helper;
    private Session session;
    private AutoCompleteTextView actName, actEmail;
    private Button bSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        init();
    }

    private void init() {
        helper = new Helper(this);
        session = new Session(this);

        actName = findViewById(R.id.actName);
        actEmail = findViewById(R.id.actEmail);
        bSave = findViewById(R.id.bSave);

        setting();
    }

    private void setting() {
        helper.defaultToolbar(getSupportActionBar(), "Edit Profile", true);

        actName.setText(session.getName());
        actEmail.setText(session.getEmail());

        bSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSave:
                if(actName.getText().toString().trim().equals("")) {
                    helper.alert("Please fill the name", "error");
                    return;
                }

                if(actEmail.getText().toString().trim().equals("")) {
                    helper.alert("Please fill the email", "error");
                    return;
                }

                if(!helper.validateEmail(actEmail.getText().toString().trim())) {
                    helper.alert("Wrong Format Email !", "error");
                    return;
                }

                doSave(actName.getText().toString(), actEmail.getText().toString());
                break;
        }
    }

    private void doSave(String name, String email) {
        session.setAll(name, email);
        helper.alert("Success saved", "success");
        startActivity(helper.goIntent(MainActivity.class, null));
    }
}
