package com.abdulr.mysimpleapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdulr.mysimpleapp.Models.M_MainMenu;
import com.abdulr.mysimpleapp.R;
import com.abdulr.mysimpleapp.Views.MainActivity;
import com.abdulr.mysimpleapp.Views.GenerateActivity;
import com.abdulr.mysimpleapp.Views.PasscodeActivity;
import com.abdulr.mysimpleapp.Views.PatternLockActivity;
import com.abdulr.mysimpleapp.Views.RatingBarActivity;
import com.abdulr.mysimpleapp.Views.ScannerActivity;

import java.util.ArrayList;

public class MainMenuAdapter extends BaseAdapter {
    ArrayList<M_MainMenu> model;
    MainActivity activity;

    public MainMenuAdapter(ArrayList<M_MainMenu> model, MainActivity activity) {
        this.model = model;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView tvName;
        ImageView ivIcon;
        CardView cvMenu;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder = new Holder();
        TextView[] tvBolds;
        View v;

        v = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu_home, null);
        holder.tvName = v.findViewById(R.id.tvName);
        holder.ivIcon = v.findViewById(R.id.ivIcon);
        holder.cvMenu = v.findViewById(R.id.cvMenu);

        holder.tvName.setText(model.get(position).getNama());
        holder.ivIcon.setImageResource(model.get(position).getImg());
        holder.ivIcon.setColorFilter(activity.getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        holder.cvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                switch (model.get(position).getNama().toLowerCase()) {
                    case "generate":
                        i = activity.helper.goIntent(GenerateActivity.class, null);
                        break;

                    case "pattern lock":
                        i = activity.helper.goIntent(PatternLockActivity.class, null);
                        break;

                    case "rating bar":
                        i = activity.helper.goIntent(RatingBarActivity.class, null);
                        break;

                    case "passcode":
                        i = activity.helper.goIntent(PasscodeActivity.class, null);
                        break;

                    case "scan":
                        i = activity.helper.goIntent(ScannerActivity.class, null);
                        break;

                    default:
                        activity.helper.alert("Unknown Action", "error");
                        break;
                }

                if (i != null) {
                    i.putExtra("title", holder.tvName.getText().toString());

                    activity.startActivity(i);
                }
            }
        });

        return v;
    }
}
