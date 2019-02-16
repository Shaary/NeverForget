package com.shaary.neverforget.controller;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shaary.neverforget.R;
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

//    @BindView(R.id.basic_title) EditText titleField;
//    @BindView(R.id.basic_description_text) EditText descriptionField;
//    @BindView(R.id.basic_date_button) Button dateButton;
//    @BindView(R.id.basic_time_button) Button timeButton;
//    @BindView(R.id.basic_send_button) Button sendButton;
//    @BindView(R.id.basic_person_button) Button victimButton;
//    @BindView(R.id.basic_remind) CheckBox remindCheckBox;
//    @BindView(R.id.basic_action1) CheckBox revengeCheckBox;
//    @BindView(R.id.grudge_revenged) CheckBox revengedCheckBox;
//    @BindView(R.id.basic_action2) CheckBox forgiveCheckBox;
//    @BindView(R.id.basic_action_text) TextView forgiveText;
//    @BindView(R.id.basic_imageView) ImageView grudgeImage;
//    @BindView(R.id.basic_photo_button) ImageButton photoButton;
//    @BindView(R.id.basic_scroll_view) ScrollView layout;

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
}
