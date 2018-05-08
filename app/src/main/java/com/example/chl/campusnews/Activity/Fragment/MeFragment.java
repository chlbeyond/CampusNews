package com.example.chl.campusnews.Activity.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chl.campusnews.Activity.SettingActivity;
import com.example.chl.campusnews.Adapter.MeViewPagerAdapter;
import com.example.chl.campusnews.R;
import com.example.chl.campusnews.Util.GlobalMethod;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class MeFragment extends Fragment implements View.OnClickListener{

    private LayoutInflater mInflater;
    private View view;

    private FrameLayout nav_right_ll;
    private ImageView setting;
    private TextView follow;
    private ImageView personal_icon;
    private TextView personal_name;
    private TextView personal_address;

    //fragment的适配器
    private MeViewPagerAdapter meViewPagerAdapter;

    //viewpager
    private ViewPager mViewPager;

    private SmartTabLayout mTabs;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mInflater = inflater;
        view = inflater.inflate(R.layout.fragment_me, container, false);
        initHead();
        init();
        return view;
    }

    private void initHead() {
        view.findViewById(R.id.nav_right_ll).setOnClickListener(this);
        setting = (ImageView) view.findViewById(R.id.setting);
        follow = (TextView) view.findViewById(R.id.follow);
        personal_icon = (ImageView) view.findViewById(R.id.personal_icon);
        personal_name = (TextView) view.findViewById(R.id.personal_name);
        personal_address = (TextView) view.findViewById(R.id.personal_address);
    }

    //初始化方法
    private void init(){
        mTabs = (SmartTabLayout) view.findViewById(R.id.tabs);
        meViewPagerAdapter = new MeViewPagerAdapter(getChildFragmentManager(),getContext());
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(meViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        mTabs.setViewPager(mViewPager);

        mViewPager.setOffscreenPageLimit(meViewPagerAdapter.getCount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_right_ll:
                GlobalMethod.toast("设置/关注");
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
        }
    }
}
