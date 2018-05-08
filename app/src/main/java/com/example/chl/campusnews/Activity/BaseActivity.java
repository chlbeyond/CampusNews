package com.example.chl.campusnews.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.chl.campusnews.Util.GlobalMethod;
//Activity的基类
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalMethod.initState(BaseActivity.this);
    }
}
