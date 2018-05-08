package com.example.chl.campusnews.Activity.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chl.campusnews.Activity.SearchActivity;
import com.example.chl.campusnews.Adapter.FragmentPagerAdapte;
import com.example.chl.campusnews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainPageFragment extends Fragment implements View.OnClickListener {

    private EditText search_edit;
    private String keyword = "";

    private View view;
    private TextView friend_text;
    private TextView friend_text_underline;
    private TextView follow_text;
    private TextView follow_text_underline;
    private TextView fans_text;
    private TextView fans_text_underline;
    private ViewPager mViewPager;
    private ArrayList<Fragment> fragments;
    private NewsFragment newsFragment;
    private ClubFragment clubFragment;
    private AskAndAnswerFragment askAndAnswerFragment;
    private LinearLayout follow_linear;

    private String type = "搜索校园新鲜事";//当前处于首页中哪一个fragment

    public MainPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mainpage, container, false);
        initNav();
        initHead();
        initViewPager();
        initData();
        return view;
    }

    private void initHead() {
        view.findViewById(R.id.friend_linear).setOnClickListener(new TextListener(0));
        friend_text = (TextView) view.findViewById(R.id.friend_text);
        friend_text_underline = (TextView) view.findViewById(R.id.friend_text_underline);
        follow_linear = (LinearLayout) view.findViewById(R.id.follow_linear);
        follow_linear.setOnClickListener(new TextListener(1));
        follow_text = (TextView) view.findViewById(R.id.follow_text);
        follow_text_underline = (TextView) view.findViewById(R.id.follow_text_underline);
        view.findViewById(R.id.fans_linear).setOnClickListener(new TextListener(2));
        fans_text = (TextView) view.findViewById(R.id.fans_text);
        fans_text_underline = (TextView) view.findViewById(R.id.fans_text_underline);
    }

    private void initNav() {
        view.findViewById(R.id.search_ll).setOnClickListener(this);
        view.findViewById(R.id.forClick_tv).setOnClickListener(this);
        search_edit = (EditText) view.findViewById(R.id.search_edit);
        search_edit.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.forClick_tv:
            case R.id.search_ll:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
                break;
        }
    }

    //内部类 重写TextView点击事件
    public class TextListener implements View.OnClickListener {
        private int index = 0;

        public TextListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            if (index == 0) {
                mViewPager.setCurrentItem(0);
            } else if (index == 1) {
                mViewPager.setCurrentItem(1);
            } else {
                mViewPager.setCurrentItem(2);
            }
        }
    }

    private void initViewPager() {
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);
        fragments = new ArrayList();
        newsFragment = new NewsFragment();
        clubFragment = new ClubFragment();
        askAndAnswerFragment = new AskAndAnswerFragment();
        fragments.add(newsFragment);
        fragments.add(clubFragment);
        fragments.add(askAndAnswerFragment);
        mViewPager.setAdapter(new FragmentPagerAdapte(getChildFragmentManager(), fragments));
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new myOnPageChangeListener());
    }

    public class myOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageSelected(int position) {
            setColor(position);//viewPager滚动的时候，文字及其下划线颜色要改变
        }
    }

    private void setColor(int index) {
        if (index == 0) {
            friend_text.setTextColor(getResources().getColor(R.color.viewPager_title_selected));
            friend_text_underline.setBackgroundColor(getResources().getColor(R.color.viewPager_underline));
            follow_text.setTextColor(getResources().getColor(R.color.viewPager_title));
            follow_text_underline.setBackgroundColor(getResources().getColor(R.color.white));
            fans_text.setTextColor(getResources().getColor(R.color.viewPager_title));
            fans_text_underline.setBackgroundColor(getResources().getColor(R.color.white));
            search_edit.setHint("搜索校园新鲜事");
            type = "搜索校园新鲜事";
        } else if (index == 1) {
            friend_text.setTextColor(getResources().getColor(R.color.viewPager_title));
            friend_text_underline.setBackgroundColor(getResources().getColor(R.color.white));
            follow_text.setTextColor(getResources().getColor(R.color.viewPager_title_selected));
            follow_text_underline.setBackgroundColor(getResources().getColor(R.color.viewPager_underline));
            fans_text.setTextColor(getResources().getColor(R.color.viewPager_title));
            fans_text_underline.setBackgroundColor(getResources().getColor(R.color.white));
            search_edit.setHint("搜索社团活动");
            type = "搜索社团活动";
        } else if (index == 2) {
            friend_text.setTextColor(getResources().getColor(R.color.viewPager_title));
            friend_text_underline.setBackgroundColor(getResources().getColor(R.color.white));
            follow_text.setTextColor(getResources().getColor(R.color.viewPager_title));
            follow_text_underline.setBackgroundColor(getResources().getColor(R.color.white));
            fans_text.setTextColor(getResources().getColor(R.color.viewPager_title_selected));
            fans_text_underline.setBackgroundColor(getResources().getColor(R.color.viewPager_underline));
            search_edit.setHint("搜索你问我答");
            type = "搜索你问我答";
        }
    }

    private void initData() {
    }
}
