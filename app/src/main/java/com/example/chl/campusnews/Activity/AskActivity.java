package com.example.chl.campusnews.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chl.campusnews.R;
import com.example.chl.campusnews.Util.GlobalMethod;

public class AskActivity extends BaseActivity implements View.OnClickListener {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

        initNav();
        initHead();
    }

    private void initHead() {
        editText = (EditText) findViewById(R.id.editText);
    }

    private void initNav() {
        ((TextView) findViewById(R.id.nav_title)).setText("发布问题");
        findViewById(R.id.nav_left_region).setOnClickListener(this);
        findViewById(R.id.nav_right_text_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.right_click_region_a).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_left_region:
                finish();
                break;
            case R.id.right_click_region_a:
                String content = editText.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    GlobalMethod.toast(content);
                } else {
                    GlobalMethod.toast("发布内容不能为空");
                }
                break;
        }
    }
}
