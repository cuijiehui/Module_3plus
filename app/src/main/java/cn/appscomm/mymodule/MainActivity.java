package cn.appscomm.mymodule;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.appscomm.presenter.util.LogUtil;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    LoginUI loginUI;

    private TextView tv_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_path = (TextView) findViewById(R.id.tv_path);
        if (!checkNotificationPermission()) {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            final PackageManager packageManager = getApplication().getPackageManager();
            List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() > 0) this.startActivity(intent);
        }
        loginUI = new LoginUI(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginUI.end();
    }

    public void loginService(View v) {
        if (checkGPS(true)) {
            loginUI.start();
        }
    }

    public void choose(View v) {
        File file = Environment.getExternalStoragePublicDirectory("application_3.1.zip");
        if (file.exists()) {
            LogUtil.i(TAG, "文件存在:" + file.getAbsolutePath());
        }
        selectFile();
    }

    public void update(View v) {
        loginUI.update();
    }

    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 100);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    String path = getPath(data.getData());
                    LogUtil.i(TAG, "path : " + path);
                    tv_path.setText(path);
                }
                break;
        }
    }

    public boolean checkNotificationPermission() {
        String pkgName = getApplicationContext().getPackageName();
        final String flat = Settings.Secure.getString(getApplication().getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void endService(View v) {
        loginUI.end();
    }

    public void restartService(View v) {
        loginUI.restart();
    }

    public void getWatchID(View v) {
        loginUI.getWatchID();
    }

    public void getUserInfo(View v) {
        loginUI.getUserInfo();
    }

    public void getSportDB(View v) {
        loginUI.getSportDB();
    }

    public void play(View v) {
        loginUI.play();
    }

    public void pause(View v) {
        loginUI.pause();
    }

    public void pre(View v) {
        loginUI.pre();
    }

    public void next(View v) {
        loginUI.next();
    }

    public boolean checkGPS(boolean isShowTip) {
        /*if (android.os.Build.VERSION.SDK_INT >= 23) {
            LogUtil.i(TAG, "手机Android系统是6.0或以上的，需要检查GPS...");
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean gpsProviderFlag = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkProviderFlag = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            LogUtil.i(TAG, "gpsProviderFlag : " + gpsProviderFlag + " networkProviderFlag : " + networkProviderFlag);
            if (!(gpsProviderFlag || networkProviderFlag)) {
                LogUtil.i(TAG, "GPS没有打开");
                if (isShowTip) {
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 666666);
                }
                return false;
            }
        }*/
        return true;
    }

    public String getPath(Uri uri) {
        LogUtil.i(TAG, "uri.getScheme() : " + uri.getScheme());
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
}
