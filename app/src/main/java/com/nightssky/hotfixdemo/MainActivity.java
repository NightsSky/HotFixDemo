package com.nightssky.hotfixdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.taobao.sophix.SophixManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_EXTERNAL_STORAGE_PERMISSION = 0;

    private Button btn;
    private Button ErrorActivity;
    private TextView logText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {
            requestExternalStoragePermission();
        }
        initView();

        MyApplication.msgDisplayListener = new MyApplication.MsgDisplayListener() {
            @Override
            public void handle(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logText.append(msg+"\n\n");
                    }
                });
            }
        };
    }


    private void initView() {

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
        ErrorActivity = (Button) findViewById(R.id.ErrorActivity);
        ErrorActivity.setOnClickListener(this);
        logText = (TextView) findViewById(R.id.logText);
        logText.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn://拉取新补丁
                SophixManager.getInstance().queryAndLoadNewPatch();
                break;
            case R.id.ErrorActivity:
                startActivity(new Intent(MainActivity.this,ErrorActivity.class));
                break;
        }
    }

    /**
     * 如果本地补丁放在了外部存储卡中, 6.0以上需要申请读外部存储卡权限才能够使用. 应用内部存储则不受影响
     */

    private void requestExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                }
                break;
            default:
        }
    }


}
