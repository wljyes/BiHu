package com.wlj.bihu.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.wlj.bihu.fragment.FavoriteFragment;
import com.wlj.bihu.fragment.QuestionFragment;
import com.wlj.bihu.fragment.UserFragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int COUNT = 3;
    private String[] titles = {"主页", "收藏", "我的"};
    private List<Fragment> fragments = new ArrayList<>();

    {
        fragments.add(new QuestionFragment());
        fragments.add(new FavoriteFragment());
        fragments.add(new UserFragment());
    }

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
