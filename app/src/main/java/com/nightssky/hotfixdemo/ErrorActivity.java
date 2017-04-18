package com.nightssky.hotfixdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ErrorActivity extends AppCompatActivity {
    private TextView text;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        initView();
        initData();
    }

    private void initView() {
        text = (TextView) findViewById(R.id.text);
        img = (ImageView) findViewById(R.id.img);
    }
    private void initData() {
        text.setText("这是出现BUG的版本");
//        text.setText("这是已修复的版本");
//        text.setTextColor(Color.RED);
        img.setImageResource(R.mipmap.img1);
    }
}
