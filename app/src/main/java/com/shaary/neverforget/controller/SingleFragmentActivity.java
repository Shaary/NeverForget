package com.shaary.neverforget.controller;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shaary.neverforget.R;

public abstract class SingleFragmentActivity extends AppCompatActivity{

    public static final String TAG = SingleFragmentActivity.class.getSimpleName();

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        if(savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.container_layout);
            if (fragment == null) {
                //Log.d(TAG, "onCreate: creating new fragment");
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container_layout, createFragment());
                fragmentTransaction.commit();

//            fragment = createFragment();
//            fm.beginTransaction()
//                    .replace(R.id.container_layout, fragment)
//                    .commit();
            }
        }
    }
}
