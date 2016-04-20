package com.tq;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tq.dampingviewpager.DampingPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<Fragment>mFragments;
    private String[]tabs={"第一","第二","第三"};
    private DampingPager mDampingPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDampingPager= (DampingPager) findViewById(R.id.damp_viewpager);
        mTabLayout= (TabLayout) findViewById(R.id.tab_layout);
        mToolbar= (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle("阻尼效果");
        mToolbar.setSubtitle("Dreamer");
        mToolbar.setSubtitleTextColor(Color.WHITE);
        mToolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFragments=new ArrayList<>();
        mFragments.add(TabFragment.newInstance("第一页"));
        mFragments.add(TabFragment.newInstance("第二页"));
        mFragments.add(TabFragment.newInstance("第三页"));


        mDampingPager.setPagerCount(mFragments.size());
        mDampingPager.setOffscreenPageLimit(mFragments.size());
        mDampingPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                        mDampingPager.setCurrentIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mDampingPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }
        });

        mTabLayout.setupWithViewPager(mDampingPager);
    }
}
