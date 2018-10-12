package com.shaary.neverforget.controller;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.shaary.neverforget.R;
import com.shaary.neverforget.model.Grudge;
import com.shaary.neverforget.model.GrudgeListFragment;

public class GrudgeListActivity extends SingleFragmentActivity
        implements GrudgeListFragment.Callbacks, GrudgeFragment.Callbacks{

    private static final String TAG = GrudgeListActivity.class.getSimpleName();

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "createFragment: returned the frag");
        return new GrudgeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onGrudgeSelected(Grudge grudge) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = GrudgePagerActivity.newIntent(this, grudge.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = GrudgeFragment.newInstance(grudge.getId());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    @Override
    public void onGrudgeUpdated(Grudge grudge) {
        GrudgeListFragment listFragment = (GrudgeListFragment)
                getSupportFragmentManager()
                .findFragmentById(R.id.container_layout);
        listFragment.updateUI();
    }
}
