package com.example.chl.campusnews.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by chl on 2018/3/20.
 */

public class FragmentPagerAdapte extends FragmentPagerAdapter {

    ArrayList<Fragment> list;
    public FragmentPagerAdapte(FragmentManager fm, ArrayList<Fragment> list){
        super(fm);
        this.list = list;
    }
    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
}
