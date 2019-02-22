package com.shaary.neverforget.controller;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.shaary.neverforget.R;
import com.shaary.neverforget.view.InfoFragment;
import com.shaary.neverforget.view.VictimChooserDialogFragment;
import com.shaary.neverforget.viewModel.BasicFragmentVM;
import com.shaary.neverforget.databinding.FragmentBasicBinding;
import com.shaary.neverforget.model.Grudge;
import com.shaary.neverforget.model.GrudgePit;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicFragment extends Fragment {

    private static final String ARG_EVENT_ID = "event_id";
    private static final String DIALOG_DATE = "DialogDate";
    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_CONTACT = 1;
    public static final int REQUEST_PHOTO = 2;
    public static final int REQUEST_PHOTO_AND_STORAGE = 3;
    public static final int REQUEST_TIME = 5;
    public static final int REQUEST_DELETE_IMAGE = 6;

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

        setUpDateButton(view);
        setUpTimeButton(view);
        setUpNameButton(view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long grudgeId = getArguments().getLong(ARG_GRUDGE_ID);
        Log.d(TAG, "onCreate: grudge id " + grudgeId);
        grudge = GrudgePit.getInstance(getActivity()).getGrudge(grudgeId);
        photoFile = GrudgePit.getInstance(getActivity()).getPhotoFile(grudge);

        setHasOptionsMenu(true);
    }

    //TODO: move to view model
    private void setUpDateButton(View view) {
        Button dateButton = view.findViewById(R.id.basic_date_button);
        dateButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getFragmentManager();
            DatePickerFragment dialog = DatePickerFragment
                    .newInstance(grudge.getDate());

            //Sets this fragment as a receiver of a picked date
            dialog.setTargetFragment(BasicFragment.this, REQUEST_DATE);
            dialog.show(fragmentManager, DIALOG_DATE);
        });
    }

    private void setUpTimeButton(View view) {
        Button timeButton = view.findViewById(R.id.basic_time_button);
        timeButton.setOnClickListener(v -> {
            DialogFragment newFragment = TimePickerFragment
                    .newInstance(grudge.getTime());
            newFragment.setTargetFragment(BasicFragment.this, REQUEST_TIME);
            newFragment.show(getFragmentManager(), "timePicker");

        });
    }

    private void setUpNameButton(View view) {
        Button nameButton = view.findViewById(R.id.basic_person_button);
        nameButton.setOnClickListener(v -> openVictimChooserDialog());
    }

    private void openVictimChooserDialog() {
        VictimChooserDialogFragment dialogFragment = VictimChooserDialogFragment.newInstance(grudge);
        dialogFragment.setTargetFragment(this, REQUEST_CONTACT);
        dialogFragment.show(getFragmentManager(), "victim");
    }

    public void setViewModel(BasicFragmentVM viewModel) {
        this.viewModel = viewModel;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        viewModel.handleActivityResult(requestCode, resultCode, data);
    }
}
