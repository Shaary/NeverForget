package com.shaary.neverforget.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.shaary.neverforget.R;
import com.shaary.neverforget.controller.GrudgeFragment;
import com.shaary.neverforget.model.Event;
import com.shaary.neverforget.model.Grudge;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VictimChooserDialogFragment extends DialogFragment {

    @BindView(R.id.victim_name_edit_text) EditText editText;
    @BindView(R.id.victim_ok_button) Button okButton;
    @BindView(R.id.victim_cancel_button) Button cancelButton;
    @BindView(R.id.gender_switch) Switch genderSwitch;

    private String gender;

    public static VictimChooserDialogFragment newInstance(Event event) {

        Bundle args = new Bundle();
        args.putString("victim", event.getName());
        args.putString("gender", event.getGender());
        VictimChooserDialogFragment fragment = new VictimChooserDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_victim_chooser_dialog, container, false);
        ButterKnife.bind(this, view);

        String victim = getArguments().getString("victim");
        gender = getArguments().getString("gender");
        if (victim != null) {
            editText.setText(victim);
        }
        if (gender != null) {
            genderSwitch.setText(gender);
            if (gender.equals(getString(R.string.gender_male))) {
                genderSwitch.setChecked(false);
            } else {
                genderSwitch.setChecked(true);
            }
        }

        genderSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                gender = getString(R.string.gender_female);
                genderSwitch.setText(R.string.gender_female);
            } else {
                gender = getString(R.string.gender_male);
                genderSwitch.setText(R.string.gender_male);
            }
        });

        okButton.setOnClickListener(v -> {
            String name = editText.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("victim", name);
            intent.putExtra("gender", gender);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            getDialog().dismiss();
        });

        cancelButton.setOnClickListener(v -> getDialog().dismiss());

        return view;
    }

}
