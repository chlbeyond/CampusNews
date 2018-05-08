package com.example.chl.campusnews.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.chl.campusnews.Activity.Fragment.MainTabFragment;
import com.example.chl.campusnews.Activity.Fragment.MeAnswerFragment;
import com.example.chl.campusnews.Activity.Fragment.MeAskFragment;
import com.example.chl.campusnews.Activity.Fragment.MeNewsFragment;
import com.example.chl.campusnews.R;

import java.util.ArrayList;

public class MeViewPagerAdapter extends FragmentStatePagerAdapter {

    public ArrayList<Fragment> fragments;

    public Context mContext;

    private String[] titles;

    public MeViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        initFragments();
    }

    public ArrayList<Fragment> getFragments() {
        return fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private void initFragments() {
        titles = new String[]{
                mContext.getResources().getString(R.string.me_1),
                mContext.getResources().getString(R.string.me_2),
                mContext.getResources().getString(R.string.me_3),
                mContext.getResources().getString(R.string.me_4),
        };

        fragments = new ArrayList<>();
        fragments.add(MeNewsFragment.newInstance());
        fragments.add(MeAnswerFragment.newInstance());
        fragments.add(MeAskFragment.newInstance());
        fragments.add(MainTabFragment.newInstance());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
