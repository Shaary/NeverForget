package com.shaary.neverforget.controller;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.shaary.neverforget.R;
import com.shaary.neverforget.view.InfoFragment;
import com.shaary.neverforget.viewModel.BasicFragmentVM;
import com.shaary.neverforget.databinding.FragmentBasicBinding;
import com.shaary.neverforget.model.Grudge;
import com.shaary.neverforget.model.GrudgePit;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicFragment extends Fragment {

    private static final String TAG = BasicFragment.class.getSimpleName();
    public static final String ARG_GRUDGE_ID = "grudge_id";

    private BasicFragmentVM viewModel;
    private Grudge grudge;
    private File photoFile;
    private GrudgeFragment.Callbacks callbacks;

    //data binding
    FragmentBasicBinding binding;

    public static BasicFragment newInstance(long grudgeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GRUDGE_ID, grudgeId);

        BasicFragment fragment = new BasicFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basic, container, false);
        binding.setEvent(viewModel);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long grudgeId = getArguments().getLong(ARG_GRUDGE_ID);
        Log.d(TAG, "onCreate: grudge id " + grudgeId);
        viewModel = new BasicFragmentVM(getContext(), grudgeId);
        grudge = GrudgePit.getInstance(getActivity()).getGrudge(grudgeId);
        photoFile = GrudgePit.getInstance(getActivity()).getPhotoFile(grudge);

        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Prevents soft keyboard from popping up when open the fragment
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_grudge, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.grudge_delete:
                GrudgePit.getInstance(getActivity()).deleteGrudgeById(grudge.getId());
                getActivity().finish();
                return true;

            case R.id.grudge_info:
                InfoFragment infoFragment = new InfoFragment();
                infoFragment.show(getFragmentManager(), "info");
        }
        return super.onOptionsItemSelected(item);
    }
}
