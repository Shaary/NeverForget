package com.shaary.neverforget.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.shaary.neverforget.R;
import com.shaary.neverforget.model.Grudge;
import com.shaary.neverforget.model.GrudgePit;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.CompoundButton.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrudgeFragment extends Fragment {

    private static final String ARG_GRUDGE_ID = "grudge_id";

    private Grudge grudge;

    @BindView(R.id.grudge_title) EditText titleField;
    @BindView(R.id.grudge_date_button) Button button;
    @BindView(R.id.grudge_remind) CheckBox remindCheckBox;
    @BindView(R.id.grudge_revenge) CheckBox revengeCheckBox;

    public static GrudgeFragment newInstance(UUID grudgeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GRUDGE_ID, grudgeId);

        GrudgeFragment fragment = new GrudgeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grudge, container, false);
        ButterKnife.bind(this, view);

        titleField.setText(grudge.getTitle());
        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                grudge.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        button.setText(grudge.getDate());
        button.setEnabled(false);

        remindCheckBox.setChecked(grudge.isRemind());
        remindCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                grudge.setRemind(isChecked);
            }
        });

        revengeCheckBox.setChecked(grudge.isRevenge());
        revengeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                grudge.setRevenge(isChecked);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID grudgeId = (UUID) getArguments().getSerializable(ARG_GRUDGE_ID);
        grudge = GrudgePit.get(getActivity()).getGrudge(grudgeId);
    }
}
