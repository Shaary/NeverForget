package com.shaary.neverforget.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.shaary.neverforget.R;
import com.shaary.neverforget.model.Grudge;
import com.shaary.neverforget.model.GrudgePit;
import com.shaary.neverforget.view.MyGrudgeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GrudgeListFragment extends Fragment implements MyGrudgeAdapter.Listener{

    private static final String TAG = GrudgeListFragment.class.getSimpleName();
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private MyGrudgeAdapter newAdapter;
    private boolean isSubtitleVisible;
    private Callbacks callbacks;

    @Override
    public void onClick(Grudge grudge) {
        callbacks.onGrudgeSelected(grudge);
    }

    public interface Callbacks {
        void onGrudgeSelected(Grudge grudge);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grudge_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d(TAG, "onCreateView: called");

        if (savedInstanceState != null) {
            isSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void updateUI() {
        Log.d(TAG, "updateUI: called");
        GrudgePit grudgePit = GrudgePit.get(getActivity());
        List<Grudge> grudges = grudgePit.getGrudges();

        if (newAdapter == null) {
            newAdapter = new MyGrudgeAdapter(grudges, this);
            //Log.d(TAG, "updateUI: callbacks " + callbacks);
            //adapter = new GrudgeAdapter(grudges);
            recyclerView.setAdapter(newAdapter);
        } else {

            //TODO: use DiffUtil
            newAdapter.setGrudges(grudges);
            newAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, isSubtitleVisible);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_grudge_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (isSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_grudge:
                Grudge grudge = new Grudge();
                GrudgePit.get(getActivity()).addGrudge(grudge);
                updateUI();
                callbacks.onGrudgeSelected(grudge);
                return true;

            case R.id.show_subtitle:
                isSubtitleVisible = !isSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        GrudgePit grudgePit = GrudgePit.get(getActivity());
        int grudgeCount = grudgePit.getGrudges().size();
        String subtitle = getString(R.string.subtitle_format, grudgeCount);

        if(!isSubtitleVisible) {
            subtitle = null;
        }

        Log.d(TAG, "updateSubtitle: subtitle " + subtitle);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(subtitle);
    }
}
