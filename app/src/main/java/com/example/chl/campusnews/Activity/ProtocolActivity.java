package com.example.chl.campusnews.Activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.chl.campusnews.R;

//校园新鲜事协议
public class ProtocolActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        ((TextView)findViewById(R.id.nav_title)).setText("校园新鲜事协议");

        findViewById(R.id.nav_left_region).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        WebView webView = (WebView)findViewById(R.id.protocol);
        webView.loadUrl("file:///android_asset/protocol.html");
    }
}
