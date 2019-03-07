package com.fitz.commonlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.library.base.BaseAppCompatActivity;
import com.android.library.util.ToastUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToastUtils.LongToast(getApplicationContext(), "哈哈");
    }
}
