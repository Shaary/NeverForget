package com.shaary.neverforget.controller;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.shaary.neverforget.model.GrudgeListFragment;

public class GrudgeListActivity extends SingleFragmentActivity {
    private static final String TAG = GrudgeListActivity.class.getSimpleName();

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "createFragment: returned the frag");
        return new GrudgeListFragment();
    }
}
