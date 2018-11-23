package com.gmk.geisa.fragment.tab;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.gmk.geisa.R;
import com.gmk.geisa.adapter.ViewPagerTab;
import com.gmk.geisa.fragment.CallPlanDraftTabViewFragment;
import com.gmk.geisa.fragment.CallPlanTabViewFragment;
import com.gmk.geisa.model.mCallPlan;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by kenjinsan on 5/22/17.
 */

public class DynamicTabPageAdapter extends FragmentStatePagerAdapter
        implements PagerSlidingTabStrip.CustomTabProvider {
    Context context;
    ArrayList<ViewPagerTab> tabs;
    ArrayList<CallPlanTabViewFragment> fragments;

    public DynamicTabPageAdapter(FragmentManager fm, ArrayList<ViewPagerTab> tabs, ArrayList<CallPlanTabViewFragment> fragments) {
        super(fm);
        this.tabs = tabs;
        this.fragments=fragments;
    }


    @Override
    public View getCustomTabView(ViewGroup viewGroup, int i) {
        RelativeLayout tabLayout = (RelativeLayout)
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tab_layout, null);

        TextView tabTitle = (TextView) tabLayout.findViewById(R.id.tab_title);
        TextView badge = (TextView) tabLayout.findViewById(R.id.badge);

        ViewPagerTab tab = tabs.get(i);

        tabTitle.setText(tab.title.toUpperCase());
        //Log.e("isi ta","c"+tab.notifications+","+tab.title);
        if (tab.notifications > 0) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(String.valueOf(tab.notifications));
        } else {
            int color = Color.TRANSPARENT;
            Drawable background = tabLayout.getBackground();
            if (background instanceof ColorDrawable)
                color = ((ColorDrawable) background).getColor();
            badge.setBackgroundColor(color);
        }

        return tabLayout;
    }


    @Override
    public void tabSelected(View view) {
        RelativeLayout tabLayout = (RelativeLayout) view;
        TextView badge = (TextView) tabLayout.findViewById(R.id.badge);
        badge.setTextColor(Color.YELLOW);

    }

    @Override
    public void tabUnselected(View view) {
        RelativeLayout tabLayout = (RelativeLayout) view;
        TextView badge = (TextView) tabLayout.findViewById(R.id.badge);
        badge.setTextColor(Color.WHITE);
    }

    @Override
    public Fragment getItem(int position) {
        return  fragments.get(position);
    }

    public ArrayList<mCallPlan> getAllItem(int position){
        return fragments.get(position).getItem();
    }




    @Override
    public int getCount() {
        return tabs.size();
    }
}
