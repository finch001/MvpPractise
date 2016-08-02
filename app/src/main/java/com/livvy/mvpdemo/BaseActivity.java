package com.livvy.mvpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by livvy on 2016/8/2 0002.
 */
public abstract class BaseActivity extends AppCompatActivity {
    abstract public void initView();

    abstract public void initListener();

    abstract public void loadData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        loadData();
    }
}
