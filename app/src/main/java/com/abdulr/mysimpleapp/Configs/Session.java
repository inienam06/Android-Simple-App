package com.abdulr.mysimpleapp.Configs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.abdulr.mysimpleapp.R;

public class Session {
    private static String nama = "";
    private static String email = "";
    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Session(Context context) {
        this.context = context;

        pref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    //SETTER
    public void setNama(String value) {
        editor.putString("name", value).commit();
    }

    public void setEmail(String value) {
        editor.putString("email", value).commit();
    }

    public void setAll(String name, String email) {
        editor.putString("name", name).
                putString("email", email).commit();
    }

    //GETTER
    public String getName() {
        return pref.getString("name", nama);
    }

    public String getEmail() {
        return pref.getString("email", email);
    }
}
