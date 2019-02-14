package com.shaary.neverforget.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.shaary.neverforget.R;
import com.shaary.neverforget.model.Grudge;
import com.shaary.neverforget.model.GrudgePit;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GrudgePagerActivity extends AppCompatActivity
        implements GrudgeFragment.Callbacks{

    public static final String TAG = GrudgePagerActivity.class.getSimpleName();

    private static final String EXTRA_GRUDGE_ID = "com.shaary.android.grudgeintent.grudge_id";

    @BindView(R.id.grudge_view_pager) ViewPager viewPager;
    @BindView(R.id.grudge_first_button) Button toFirstButton;
    @BindView(R.id.grudge_last_button) Button toLastButton;

    private List<Grudge> grudges;

    public static Intent newIntent(Context packageContext, long grudgeId) {
        Intent intent = new Intent(packageContext, GrudgePagerActivity.class);
        intent.putExtra(EXTRA_GRUDGE_ID, grudgeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grudge_pager);
        ButterKnife.bind(this);

        long grudgeId = getIntent().getLongExtra(EXTRA_GRUDGE_ID, 0);

        //grudges = GrudgePit.get(this).getGrudges();
        grudges = GrudgePit.getInstance(this).getGrudgeList();
        Log.d(TAG, "onCreate: pager grudges number " + grudges.size());
        FragmentManager fragmentManager = getSupportFragmentManager();

        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Grudge grudge = grudges.get(position);
                //return GrudgeFragment.newInstance(grudge.getId());
                return BasicFragment.newInstance(grudge.getId());
            }

            @Override
            public int getCount() {
                return grudges.size();
            }
        });

        //Forwards you to the chosen grudge in the pager
        for (int i = 0; i < grudges.size(); i++) {
            if (grudges.get(i).getId() == (grudgeId)) {
                viewPager.setCurrentItem(i);
                break;
            }
            if (grudges.get(i).getId() == 0) {
                viewPager.setCurrentItem(i+1);
                break;
            }
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    toFirstButton.setEnabled(false);
                }
                else if (position == grudges.size()-1) {
                    toLastButton.setEnabled(false);
                }
                else {
                    toFirstButton.setEnabled(true);
                    toLastButton.setEnabled(true);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        toFirstButton.setOnClickListener(v -> viewPager.setCurrentItem(0));

        toLastButton.setOnClickListener(v -> viewPager.setCurrentItem(grudges.size()-1));
    }

    @Override
    public void onGrudgeUpdated(Grudge grudge) {

    }
}
