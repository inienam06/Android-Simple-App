package com.abdulr.mysimpleapp.Configs;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdulr.mysimpleapp.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Map;

public class Helper {
    private Activity activity;
    private AlertDialog.Builder builder;
    private KProgressHUD progress;
    private Dialog dialog;
    private static RequestQueue queue;

    public Helper(Activity activity) {
        this.activity = activity;
        this.builder = new AlertDialog.Builder(activity);
        this.dialog = new Dialog(activity);
        queue = Volley.newRequestQueue(activity);
        progress = KProgressHUD.create(activity);
    }

    public Intent goIntent(Class<?> tujuan, @Nullable JSONArray extras)
    {
        Intent intent = new Intent(activity, tujuan);

        try {
            if(extras != null) {
                for (int i = 0; i < extras.length(); i++) {
                    String name = extras.getJSONObject(i).getString("name");
                    String value = extras.getJSONObject(i).getString("value");

                    intent.putExtra(name, value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    public void defaultToolbar(ActionBar actionBar, String titleActiobar, Boolean showBackBotton) {
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.toolbar);
        actionBar.setElevation(0);

        Toolbar toolbar=(Toolbar)actionBar.getCustomView().getParent();

        //title
        TextView title = toolbar.findViewById(R.id.title);
        title.setText(titleActiobar.toUpperCase());

        ImageButton back = toolbar.findViewById(R.id.btn_back);

        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.getContentInsetEnd();
        toolbar.setPadding(0, 0, 0, 0);

        if(!showBackBotton) {
            back.setVisibility(View.GONE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    public void setFontsTextView(final TextView[] textViews, final int tipe) {
        for (TextView textView : textViews) {
            textView.setTypeface(setCustomFont(tipe));
        }
    }

    public void setFontsButton(final Button[] buttons, final int tipe) {
        for (Button button : buttons) {
            button.setTypeface(setCustomFont(tipe));
        }
    }

    public void setFontsEditText(final EditText[] editTexts, final int tipe) {
        for (EditText editText : editTexts) {
            editText.setTypeface(setCustomFont(tipe));
        }
    }

    private Typeface setCustomFont(int tipe) {
        Typeface fonts = null;

        switch (tipe) {
            case 1 :
                fonts = Typeface.createFromAsset(activity.getAssets(),"fonts/Sunflower-Bold.ttf");
                break;

            case 2 :
                fonts = Typeface.createFromAsset(activity.getAssets(),"fonts/Roboto-Regular.ttf");
                break;

            case 3 :
                fonts = Typeface.createFromAsset(activity.getAssets(),"fonts/Montserrat-Medium.ttf");
                break;
            case 4 :
                fonts = Typeface.createFromAsset(activity.getAssets(),"fonts/Montserrat-Bold.ttf");
                break;
            case 5 :
                fonts = Typeface.createFromAsset(activity.getAssets(),"fonts/Montserrat-Regular.ttf");
                break;
        }

        return fonts;
    }

    public Boolean validateEmail(String value) {
        final String pattern = activity.getResources().getString(R.string.email_pattern);
        boolean isValid = false;

        if(value.matches(pattern)) {
            isValid = true;
        }

        return isValid;
    }

    public void messageYesNo(String message, int code, @Nullable DialogInterface.OnClickListener positive, @Nullable DialogInterface.OnClickListener negative) {
        builder.setMessage(message);
        builder.setCancelable(false);
        switch (code) {
            case 0:

                break;

            case 1:
                builder.setPositiveButton("Yes", positive);
                break;

            case 2:
                builder.setNegativeButton("No", positive);
                break;

            case 3:
                builder.setPositiveButton("Yes", positive);
                builder.setNegativeButton("No", negative);
                break;
            default:

                break;
        }
        builder.create();
        builder.show();
    }

    public void messageOkCancel(String message, int code, @Nullable DialogInterface.OnClickListener positive, @Nullable DialogInterface.OnClickListener negative) {
        builder.setMessage(message);
        builder.setCancelable(false);
        switch (code) {
            case 0:

                break;

            case 1:
                builder.setPositiveButton("Ok", positive);
                break;

            case 2:
                builder.setNegativeButton("Cancel", positive);
                break;

            case 3:
                builder.setPositiveButton("Ok", positive);
                builder.setNegativeButton("Cancel", negative);
                break;
            default:

                break;
        }
        builder.create();
        builder.show();
    }

    public String[] listPermission(int code) {
        String[] PERMISSION_REQUEST;
        final String[] defaultPermission = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        switch (code) {
            case 0:
                PERMISSION_REQUEST = defaultPermission;
                break;

            case 1:
                PERMISSION_REQUEST = new String[]{
                        Manifest.permission.CAMERA
                };
                break;

            case 2:
                PERMISSION_REQUEST = new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                break;

            default:
                PERMISSION_REQUEST = defaultPermission;
                break;
        }

        return PERMISSION_REQUEST;
    }

    public void allowPermission(int code) {
        if(!checkPermission(listPermission(code))) {
            ActivityCompat.requestPermissions(activity, listPermission(code), 1);
        }
    }

    public Boolean checkPermission(String... permission) {
        if (activity != null && permission != null) {
            for (String persn : permission) {
                if (ActivityCompat.checkSelfPermission(activity, persn) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void loading(int code) {
        switch (code) {
            case 1:
                progress.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
                progress.setLabel("Please Wait...");
                progress.setCancellable(false);
                progress.setAnimationSpeed(2);
                progress.setDimAmount(0.5f);
                progress.show();
                break;

            case 0:
                if(progress.isShowing()) {
                    progress.dismiss();
                }
                break;
        }
    }

    private String number_format(int value) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setCurrencySymbol(""); // Don't use null.
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(value);
    }

    public void swipeRefreshSetColor(SwipeRefreshLayout swipe) {
        swipe.setColorSchemeColors(
                activity.getResources().getColor(android.R.color.holo_blue_bright),
                activity.getResources().getColor(android.R.color.holo_green_light),
                activity.getResources().getColor(android.R.color.holo_orange_light),
                activity.getResources().getColor(android.R.color.holo_red_light)
        );
    }

    public void getFromUri(String url, @Nullable final Map headers, Response.Listener<JSONObject> response, Response.ErrorListener error) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response, error) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    public void postToUri(String url, @Nullable final Map headers, JSONObject obj, Response.Listener<JSONObject> response, Response.ErrorListener error) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj, response, error) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    public void alert(String message, String tipe) {
        int _toast;
        switch (tipe) {
            case "default":
                _toast = FancyToast.DEFAULT;
                break;

            case "success":
                _toast = FancyToast.SUCCESS;
                break;

            case "info":
                _toast = FancyToast.INFO;
                break;

            case "warning":
                _toast = FancyToast.WARNING;
                break;

            case "error":
                _toast = FancyToast.ERROR;
                break;

            case "retry":
                _toast = FancyToast.CONFUSING;
                break;

            default:
                _toast = FancyToast.DEFAULT;
                break;
        }

        FancyToast.makeText(activity, message, FancyToast.LENGTH_LONG, _toast,false).show();
    }

    public Bitmap customQRCode(String type, String value) {
        Bitmap bm = null;

        BitMatrix matrix = null;
        try {
            if(type.toLowerCase().equals("barcode")) {
                matrix = new MultiFormatWriter().encode(value, BarcodeFormat.CODE_128, 500, 250);
            } else {
                matrix = new MultiFormatWriter().encode(value, BarcodeFormat.QR_CODE, 500, 500);
            }
            BarcodeEncoder encoder = new BarcodeEncoder();
            bm = encoder.createBitmap(matrix);
        } catch (WriterException e) {
            e.printStackTrace();
            alert("Gagal membuat QRCode", "error");
        }
        return bm;
    }

    public void statusBarTransparent() {
        Window w = activity.getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public void saveToGallery(ImageView image, String title, String desc) {
        image.setDrawingCacheEnabled(true);
        Bitmap bm = image.getDrawingCache();

        MediaStore.Images.Media.insertImage(activity.getContentResolver(), bm, title, desc);
    }
}
