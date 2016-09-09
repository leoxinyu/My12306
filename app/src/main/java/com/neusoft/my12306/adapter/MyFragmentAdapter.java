package com.neusoft.my12306.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.neusoft.my12306.fragment.MyFragment;
import com.neusoft.my12306.fragment.OrderListFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class MyFragmentAdapter extends FragmentStatePagerAdapter{
    private List<Fragment> ls;
    public MyFragmentAdapter(FragmentManager fm, List<Fragment> ls) {
        super(fm);
        this.ls = ls;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container, position);
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return ls.get(position);
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void replace(Fragment oldFragment, Fragment newFragment){
        if(oldFragment instanceof OrderListFragment){
            ls.remove(1);
            ls.add(1,newFragment);
        }
        if(oldFragment instanceof MyFragment){
            ls.remove(2);
            ls.add(2,newFragment);
        }
        notifyDataSetChanged();
    }
}
