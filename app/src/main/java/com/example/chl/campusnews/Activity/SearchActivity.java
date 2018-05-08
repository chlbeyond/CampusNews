package com.example.chl.campusnews.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chl.campusnews.R;

public class SearchActivity extends BaseActivity implements View.OnClickListener{

    private EditText search_edit;
    private LinearLayout delect_ll;
    private String keyword = "";
    private String type = "搜索校园新鲜事"; //指定搜索哪一类的内容

    private TextView unsearch_tv;
    private LinearLayout search_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        type = getIntent().getStringExtra("type");
        initNav();

        initBottom();
    }

    private void initBottom() {
        unsearch_tv = (TextView) findViewById(R.id.unsearch_tv);
        search_linear = (LinearLayout) findViewById(R.id.search_linear);
        findViewById(R.id.news_tv).setOnClickListener(this);
        findViewById(R.id.club_tv).setOnClickListener(this);
        findViewById(R.id.ask_tv).setOnClickListener(this);
    }

    private void initNav() {
        findViewById(R.id.search_image_ll).setOnClickListener(this);
        ((ImageView)findViewById(R.id.search_image)).setImageResource(R.mipmap.back);
        delect_ll = (LinearLayout) findViewById(R.id.delect_ll);
        delect_ll.setOnClickListener(this);
        search_edit = (EditText) findViewById(R.id.search_edit);
        search_edit.addTextChangedListener(textWatcher);
        search_edit.setHint(type);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_image_ll:
                finish();
                break;
            case R.id.delect_ll:
                search_edit.setText("");
                break;
            case R.id.news_tv:
                search_edit.setHint("搜索校园新鲜事");
                break;
            case R.id.club_tv:
                search_edit.setHint("搜索社团活动");
                break;
            case R.id.ask_tv:
                search_edit.setHint("搜索你问我答");
                break;
        }
    }

    //搜索框响应事件
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            keyword = s.toString();
            if (!TextUtils.isEmpty(keyword)) {
                initData();//如果keyword为空则不进行查询
                delect_ll.setVisibility(View.VISIBLE);
            } else {
                delect_ll.setVisibility(View.GONE);
            }
        }
    };

    private void initData() {

    }
}
