package com.example.chl.campusnews.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chl.campusnews.Activity.Fragment.AskAndAnswerFragment;
import com.example.chl.campusnews.Activity.Fragment.ClubFragment;
import com.example.chl.campusnews.Activity.Fragment.FollowFragment;
import com.example.chl.campusnews.Activity.Fragment.MainPageFragment;
import com.example.chl.campusnews.Activity.Fragment.MeFragment;
import com.example.chl.campusnews.Adapter.FragmentPagerAdapte;
import com.example.chl.campusnews.R;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ViewPager mViewPager;
    private ArrayList<Fragment> fragments;
    private MainPageFragment mainPageFragment;
    private FollowFragment followFragment;
    private MeFragment meFragment;
    private FragmentPagerAdapte adapter;

    private LinearLayout nav_bottom;
    private TextView mainPage_text;
    private TextView follow_text;
    private TextView me_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewPager();
        initNavBottom();
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);
        fragments = new ArrayList();
        mainPageFragment = new MainPageFragment();
        followFragment = new FollowFragment();
        meFragment = new MeFragment();
        fragments.add(mainPageFragment);
        fragments.add(followFragment);
        fragments.add(meFragment);
        adapter = new FragmentPagerAdapte(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void initNavBottom() {
        nav_bottom = (LinearLayout) findViewById(R.id.nav_bottom);
        findViewById(R.id.mainPage_ll).setOnClickListener(new TextListener(0));
        mainPage_text = (TextView) findViewById(R.id.mainPage_text);
        findViewById(R.id.follow_ll).setOnClickListener(new TextListener(1));
        follow_text = (TextView) findViewById(R.id.follow_text);
        findViewById(R.id.me_ll).setOnClickListener(new TextListener(2));
        me_text = (TextView) findViewById(R.id.me_text);
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

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageSelected(int position) {
            setColor(position);//viewPager滚动的时候，文字及其图片颜色要改变
        }
    }

    private void setColor(int index) {
        if (index == 0) {
            mainPage_text.setTextColor(Color.parseColor("#63BEFB"));
            follow_text.setTextColor(Color.parseColor("#B5B5B5"));
            me_text.setTextColor(Color.parseColor("#B5B5B5"));
        } else if (index == 1) {
            mainPage_text.setTextColor(Color.parseColor("#B5B5B5"));
            follow_text.setTextColor(Color.parseColor("#63BEFB"));
            me_text.setTextColor(Color.parseColor("#B5B5B5"));
        } else if (index == 2) {
            mainPage_text.setTextColor(Color.parseColor("#B5B5B5"));
            follow_text.setTextColor(Color.parseColor("#B5B5B5"));
            me_text.setTextColor(Color.parseColor("#63BEFB"));
        }
    }
}
