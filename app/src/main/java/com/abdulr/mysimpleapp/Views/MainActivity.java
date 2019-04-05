package com.abdulr.mysimpleapp.Views;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import com.abdulr.mysimpleapp.Adapters.MainMenuAdapter;
import com.abdulr.mysimpleapp.Configs.Helper;
import com.abdulr.mysimpleapp.Configs.Session;
import com.abdulr.mysimpleapp.Models.M_MainMenu;
import com.abdulr.mysimpleapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public Helper helper;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new Helper(this);
        session = new Session(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initMenu();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView tvName = header.findViewById(R.id.tvName);
        TextView tvEmail = header.findViewById(R.id.tvEmail);

        navigationView.setNavigationItemSelectedListener(this);
        tvName.setText(session.getName());
        tvEmail.setText(session.getEmail());

        if(!helper.checkPermission(helper.listPermission(0))) {
            helper.allowPermission(0);
        }
    }

    private void initMenu() {
        ArrayList<M_MainMenu> model = new ArrayList<M_MainMenu>();
        GridView menu = findViewById(R.id.menu);

        for (int i = 0; i <= 4; i++) {
            if (i == 0) {
                model.add(new M_MainMenu("Rating Bar", R.drawable.ic_rating_black_24dp));
            } else if (i == 1) {
                model.add(new M_MainMenu("Pattern Lock", R.drawable.ic_screen_lock_portrait_black_24dp));
            } else if (i == 2) {
                model.add(new M_MainMenu("Generate", R.drawable.ic_qr_code));
            } else if (i == 3) {
                model.add(new M_MainMenu("Passcode", R.drawable.ic_screen_lock_portrait_black_24dp));
            } else if (i == 4) {
                model.add(new M_MainMenu("Scan", R.drawable.ic_scan_black_24dp));
            }
        }

        menu.setAdapter(new MainMenuAdapter(model, this));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                startActivity(helper.goIntent(SettingActivity.class, null));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_profile:
                startActivity(helper.goIntent(ProfileActivity.class, null));
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
